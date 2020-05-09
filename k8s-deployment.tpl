apiVersion: apps/v1beta2
kind: Deployment
metadata:
  name: {APP_NAME}-{PROFILE}
  labels:
    app: {APP_NAME}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: {APP_NAME}
  template:
    metadata:
      labels:
        app: {APP_NAME}
    spec:
      containers:
      - name: {APP_NAME}
        image: {IMAGE}
        imagePullPolicy: Always
        ports:
          - containerPort: {PORT}
        env:
          - name: SPRING_PROFILES_ACTIVE
            value: {PROFILE}
          - name: JAVA_OPTS
            value:
              -server
              -Xmx{MEMORY}
              -Xms{MEMORY}
              -Xss512k
              -XX:NewRatio=2
              -XX:+UseConcMarkSweepGC
              -XX:+CMSClassUnloadingEnabled
              -XX:CMSInitiatingOccupancyFraction=70
              -XX:SoftRefLRUPolicyMSPerMB=0
              -XX:MaxTenuringThreshold=7
              -XX:+UseCMSInitiatingOccupancyOnly
              -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses
              -XX:GCTimeRatio=19
              -Xnoclassgc
              -XX:+HeapDumpOnOutOfMemoryError
              -XX:HeapDumpPath=/data/logs/jvm/{APP_NAME}
              -XX:+PrintGCDetails
              -XX:+PrintGCDateStamps
              -XX:+UseGCLogFileRotation
              -XX:NumberOfGCLogFiles=8
              -XX:GCLogFileSize=100M
              -Xloggc:/data/logs/jvm/{APP_NAME}.log
      imagePullSecrets:
        - name: uc-private-ticket