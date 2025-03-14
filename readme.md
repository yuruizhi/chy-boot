# CHY-Boot 项目

CHY-Boot 是一个基于 Spring Boot 的企业级开发脚手架，提供了丰富的功能和模块化的设计，帮助开发人员快速构建高质量的企业级应用。

## 项目结构

```
chy-boot/
├── chy-boot-framework/          # 框架层 - 提供基础组件和通用功能
│   ├── chy-boot-common/         # 公共工具类和通用组件
│   ├── chy-boot-security/       # 安全相关功能，包括认证、授权、加密等
│   ├── chy-boot-cache/          # 缓存相关功能，包括本地缓存和分布式缓存
│   ├── chy-boot-web/            # Web相关功能，包括控制器基类、过滤器、拦截器、统一响应等
│   └── chy-boot-persistence/    # 数据持久化相关功能，包括MyBatis-Plus配置、数据库通用操作等
├── chy-boot-infrastructure/      # 基础设施层 - 提供外部系统集成和基础服务
│   ├── chy-boot-redis/          # Redis相关功能，包括Redis配置、工具类等
│   ├── chy-boot-datasource/     # 数据源相关功能，包括数据源配置、连接池等
│   └── chy-boot-job/            # 任务调度相关功能，包括定时任务、异步任务等
├── chy-boot-api/                # API层 - 提供对外接口定义和数据传输对象
├── chy-boot-modules/            # 业务模块层 - 包含所有业务功能实现
│   ├── chy-boot-system/         # 系统管理模块，包括用户、角色、菜单、权限等
│   └── chy-boot-business/       # 业务功能模块，包括具体业务领域实现
└── chy-boot-starter/            # 应用启动模块 - 包含主启动类和配置
```

## 技术栈

- **核心框架**：Spring Boot 2.7.x
- **安全框架**：Spring Security
- **持久层框架**：MyBatis-Plus
- **数据库连接池**：HikariCP、Druid
- **缓存框架**：Redis、Caffeine
- **任务调度**：Quartz
- **API文档**：SpringDoc OpenAPI 3
- **工具库**：Hutool、Guava、Jackson、FastJSON

## 功能特性

- **用户管理**：用户的增删改查、状态管理、密码重置等功能
- **角色权限**：基于RBAC的权限控制，支持按钮级别的权限管理
- **数据权限**：提供数据权限过滤，支持多种数据权限规则
- **接口文档**：集成SpringDoc，自动生成API文档，支持在线调试
- **代码生成**：提供代码生成功能，支持自定义模板
- **日志管理**：系统操作日志记录和查询功能
- **Redis缓存**：集成Redis，提供多种缓存操作和工具类
- **任务调度**：基于Quartz的任务调度功能，支持集群部署
- **多数据源**：支持动态数据源，可以方便地切换数据源
- **分布式锁**：提供基于Redis的分布式锁实现
- **异常处理**：统一的异常处理机制，友好的错误提示
- **数据校验**：基于JSR303的数据校验功能
- **跨域处理**：支持跨域请求，便于前后端分离开发
- **XSS防御**：提供XSS防御功能，保障系统安全
- **接口限流**：支持接口限流，防止恶意请求
- **幂等性保证**：支持接口幂等性，防止重复提交

## 快速开始

### 环境要求

- JDK 11+
- Maven 3.5+
- MySQL 5.7+
- Redis 5.0+

### 项目启动

1. 克隆项目到本地

   ```bash
   git clone https://github.com/yourusername/chy-boot.git
   ```

2. 导入数据库脚本

   在MySQL中创建数据库`chy_boot`，然后导入`docs/db`目录下的SQL脚本。

3. 修改配置文件

   修改`chy-boot-starter/src/main/resources/application-local.yml`中的数据库连接信息和Redis连接信息。

4. 编译项目

   ```bash
   mvn clean package -DskipTests
   ```

5. 运行项目

   ```bash
   java -jar chy-boot-starter/target/chy-boot-starter.jar
   ```

6. 访问项目

   浏览器访问：http://localhost:8084/doc.html

## 开发指南

### 新增业务模块

1. 在`chy-boot-modules`下创建新的模块目录，例如`chy-boot-modules/chy-boot-order`
2. 配置`pom.xml`，添加必要的依赖
3. 按照业务需求，创建实体类、DTO、Mapper、Service、Controller等

### 代码规范

- 遵循阿里巴巴Java开发手册
- 类名使用大驼峰命名法，方法名使用小驼峰命名法
- 变量名使用小驼峰命名法，常量使用全大写，单词间用下划线分隔
- 表名使用小写字母，单词间用下划线分隔，实体类对应的表使用单数形式
- 注释规范：类、字段、方法都应该有注释，说明其作用

## 贡献指南

1. Fork本仓库
2. 创建feature分支
3. 提交代码
4. 新建Pull Request

## 版权信息

Copyright © 2023 YuRuizhi

