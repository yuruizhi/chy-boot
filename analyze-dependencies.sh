#!/bin/bash
# 依赖分析和常用Maven任务脚本

echo "==============================="
echo "常用Maven命令执行脚本"
echo "==============================="
echo
echo "请选择要执行的操作:"
echo "1. 清理并编译项目"
echo "2. 分析未使用的依赖"
echo "3. 显示依赖树"
echo "4. 检查代码风格"
echo "5. 生成Javadoc文档"
echo "6. 运行测试"
echo "7. 生成代码覆盖率报告"
echo "8. 安装到本地仓库"
echo "9. 打包应用"
echo "0. 退出"
echo

read -p "请输入选项(0-9): " choice

case $choice in
    1)
        echo "正在清理并编译项目..."
        mvn clean compile
        ;;
    2)
        echo "正在分析未使用的依赖..."
        mvn dependency:analyze
        ;;
    3)
        echo "正在显示依赖树..."
        mvn dependency:tree
        ;;
    4)
        echo "正在检查代码风格..."
        mvn checkstyle:check
        ;;
    5)
        echo "正在生成Javadoc文档..."
        mvn javadoc:javadoc
        ;;
    6)
        echo "正在运行测试..."
        mvn test
        ;;
    7)
        echo "正在生成代码覆盖率报告..."
        mvn jacoco:report
        ;;
    8)
        echo "正在安装到本地仓库..."
        mvn clean install
        ;;
    9)
        echo "正在打包应用..."
        mvn clean package
        ;;
    0)
        echo "退出脚本..."
        exit 0
        ;;
    *)
        echo "无效的选项，请重新运行脚本。"
        exit 1
        ;;
esac

echo
echo "操作完成。"
read -p "按回车键继续..." temp 