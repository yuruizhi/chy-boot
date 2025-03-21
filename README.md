# chy-boot

> Chy , 即烟囱。烟囱种子工程： Spring-Boot 脚手架工程

## 项目简介

chy-boot是一个基于Spring Boot 3.2.3的现代Java项目脚手架，采用模块化设计，提供了丰富的功能和开发工具支持，帮助开发人员更高效地构建企业级应用。

## 项目特点

- 基于Spring Boot 3.2.3，支持JDK 17
- 采用多模块架构，良好的代码组织和分层
- 集成MyBatis、Druid数据库连接池
- 支持多数据源配置和动态切换
- 集成Redis缓存支持
- JWT认证支持
- 集成Knife4j和SpringDoc，优雅地构建API文档
- JSON处理使用Jackson库
- 优化的Maven依赖管理和插件配置
- 内置丰富的工具类和公共组件

## 项目结构

```
chy-boot/
├── chy-boot-common/        # 通用模块：工具类、常量、异常处理等
├── chy-boot-core/          # 核心模块：实体类、数据访问层等
├── chy-boot-service/       # 服务模块：业务逻辑实现
├── chy-boot-rest-api/      # REST API模块：控制器和接口定义
├── analyze-dependencies.bat # Windows下的依赖分析脚本
├── analyze-dependencies.sh  # Linux/MacOS下的依赖分析脚本
├── checkstyle.xml          # 代码风格检查配置
├── pom.xml                 # 主POM文件
└── README.md               # 项目说明文档
```

## Maven优化

本项目采用优化的Maven配置管理：

1. **依赖版本集中管理**：所有依赖版本均在父POM的`properties`和`dependencyManagement`部分统一管理，子模块引用无需声明版本号
2. **插件配置集中管理**：在`pluginManagement`部分统一配置Maven插件，保证构建过程一致性
3. **编译器配置优化**：配置了Java 17，并添加了编译器参数以控制警告显示
4. **代码质量工具集成**：
    - Checkstyle：代码风格检查
    - PMD：代码缺陷分析
    - SpotBugs：Bug检测
    - JaCoCo：代码覆盖率分析
5. **依赖分析工具**：集成了依赖分析插件，便于查找和清理未使用的依赖

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 构建与运行

1. 克隆代码仓库
   ```bash
   git clone [repository-url]
   cd chy-boot
   ```

2. 编译项目
   ```bash
   mvn clean compile
   ```

3. 打包项目
   ```bash
   mvn clean package
   ```

4. 运行应用
   ```bash
   java -jar chy-boot-rest-api/target/chy-boot-rest-api.jar
   ```

### 使用依赖分析工具

Windows环境:

```bash
.\analyze-dependencies.bat
```

Linux/MacOS环境:

```bash
chmod +x analyze-dependencies.sh
./analyze-dependencies.sh
```

## 开发规范

- 代码风格遵循Checkstyle配置规范
- 每个类和公共方法应当有完整的Javadoc注释
- 所有API接口需要使用Swagger/OpenAPI注解进行文档化
- 遵循RESTful API设计原则

## 模块说明

- **chy-boot-common**: 提供通用工具类、异常处理、全局配置等
- **chy-boot-core**: 包含核心业务实体、数据访问接口等
- **chy-boot-service**: 业务服务实现，处理核心业务逻辑
- **chy-boot-rest-api**: REST API接口实现，处理HTTP请求

