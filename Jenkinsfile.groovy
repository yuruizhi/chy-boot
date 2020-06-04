#!groovy
node {

    // docker镜像仓库地址
    def docker_repository = "ccr.ccs.tencentyun.com/"

    properties([
            parameters([
                    string(
                            name: 'project',
                            defaultValue: 'danone-boot',
                            description: """工程名
                            """),
                    string(
                            name: 'version',
                            defaultValue: '1.0.0',
                            description: """当前代码版本号
                            """),
                    string(
                            name: 'branch',
                            defaultValue: 'master',
                            description: """发布分支或版本
                            """),
                    choice(
                            name: 'profile',
                            choices: 'dev\ntest\nprod',
                            description: """部署环境
                            """),
                    string(
                            name: 'memory',
                            defaultValue: '500M',
                            description: """jvm 内存大小设置：1G、2G等，默认500M，请根据机器总内存大小合理分配
                            """),
                    string(
                            name: 'docker_namespace',
                            defaultValue: 'uc-changyi',
                            description: """腾讯云镜像命名空间，区分不同的项目团队
                            """),
                    string(
                            name: 'k8s_namespace',
                            defaultValue: 'danone',
                            description: """Kubernetes命名空间
                            """),
                    string(
                            name: 'port',
                            defaultValue: '8084',
                            description: """服务端口
                            """),
            ])
    ])
    stage('Checking Parameter') {
        if (!params.project) {
            error "请输入项目名称"
        }
        if (!params.version) {
            error "请输入项目代码版本号"
        }
        if (!params.branch) {
            error "请输入需要发布的分支或版本"
        }
        if (!params.profile) {
            error "请选择需要发布的环境"
        }
        if (!params.k8s_namespace) {
            error "请选择需要发布k8s命名空间"
        }
        if (!params.memory) {
            error "请设置jvm内存大小"
        }

        // 设置发布显示信息
        currentBuild.displayName = "${USER} 发布 ${params.profile}"
        currentBuild.description = """
            发布工程：${params.project}
            发布版本：${params.version}
            发布分支：${params.branch}
            发布环境：${params.profile}
            容器仓库地址：${docker_repository}
            腾讯云镜像命名空间: ${params.docker_namespace}
            Kubernetes命名空间：${params.k8s_namespace}
        """
    }

    stage('Checkout Code') {
        checkout([
                $class                           : 'GitSCM',
                branches                         : [[name: "${params.branch}"]],
                doGenerateSubmoduleConfigurations: false,
                extensions                       : [],
                submoduleCfg                     : [],
                userRemoteConfigs                : [[url: 'ssh://git@gitlab-ce.k8s.tools.vchangyi.com:32201/uc/danone-boot.git']]
        ])
    }

    stage("Check Code") {
        // 不敢开，全是bug
        //sh "./gradlew -Dspring.profiles.active=${Profile} ${proj}:clean ${proj}::build ${proj}:checkstyleMain ${proj}:findbugsMain"
    }

    stage("Maven Build") {
        echo "<<<<<<<<<< mvn clean package -P ${Profile} -Dmaven.test.skip=true"
        sh "mvn clean package -P ${Profile} -Dmaven.test.skip=true"
    }

    // 拼接环境+项目名称
    def repository = docker_repository + params.docker_namespace + "/" + params.project + "-" + params.profile + ":" + params.version

    stage("Docker Build") {
        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PWD} ${DOCKER_REPO}"
        sh "docker build -t ${repository} ."
    }

    stage("Publish Image") {
        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PWD} ${DOCKER_REPO}"
        sh "docker push ${repository}"
    }

    stage("Deploy Kubernetes") {
        withCredentials([string(credentialsId: 'jenkins-k8s-config', variable: 'K8S_CONFIG')]) {
            // some block
            echo "<<<<<<<<<< $K8S_CONFIG"
            sh "mkdir -p ~/.kube"
            sh "echo $K8S_CONFIG | base64 -d > ~/.kube/config"
            sh "sed -e 's#{IMAGE}#${repository}#g;s#{APP_NAME}#${params.project}#g;s#{PROFILE}#${params.profile}#g;s#{PORT}#${params.port}#g;s#{MEMORY}#${params.memory}#g;' k8s-deployment.tpl > k8s-deployment.yml"
            sh "kubectl apply -f k8s-deployment.yml --namespace=${params.k8s_namespace}"
        }
    }
}