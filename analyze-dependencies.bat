@echo off
REM 依赖分析和常用Maven任务脚本

echo ===============================
echo 常用Maven命令执行脚本
echo ===============================
echo.
echo 请选择要执行的操作:
echo 1. 清理并编译项目
echo 2. 分析未使用的依赖
echo 3. 显示依赖树
echo 4. 检查代码风格
echo 5. 生成Javadoc文档
echo 6. 运行测试
echo 7. 生成代码覆盖率报告
echo 8. 安装到本地仓库
echo 9. 打包应用
echo 0. 退出
echo.

set /p choice=请输入选项(0-9): 

if "%choice%"=="1" (
    echo 正在清理并编译项目...
    call mvn clean compile
    goto end
)

if "%choice%"=="2" (
    echo 正在分析未使用的依赖...
    call mvn dependency:analyze
    goto end
)

if "%choice%"=="3" (
    echo 正在显示依赖树...
    call mvn dependency:tree
    goto end
)

if "%choice%"=="4" (
    echo 正在检查代码风格...
    call mvn checkstyle:check
    goto end
)

if "%choice%"=="5" (
    echo 正在生成Javadoc文档...
    call mvn javadoc:javadoc
    goto end
)

if "%choice%"=="6" (
    echo 正在运行测试...
    call mvn test
    goto end
)

if "%choice%"=="7" (
    echo 正在生成代码覆盖率报告...
    call mvn jacoco:report
    goto end
)

if "%choice%"=="8" (
    echo 正在安装到本地仓库...
    call mvn clean install
    goto end
)

if "%choice%"=="9" (
    echo 正在打包应用...
    call mvn clean package
    goto end
)

if "%choice%"=="0" (
    echo 退出脚本...
    goto exit
)

echo 无效的选项，请重新运行脚本。

:end
echo.
echo 操作完成。
pause

:exit 