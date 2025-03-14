# CHY-Boot 企业级应用开发框架

> CHY, 即烟囱。CHY-Boot是一个基于Spring Boot的企业级应用开发框架，提供了丰富的功能和最佳实践，帮助开发者快速构建高质量的企业应用。

## 项目概述

CHY-Boot是一个模块化设计的企业级应用框架，基于Spring Boot 3.x和MyBatis-Plus构建，采用了最新的Java技术栈，支持多数据源、缓存、API文档等企业级应用必备功能。

### 技术栈

- **基础框架**：Spring Boot 3.2.3
- **ORM框架**：MyBatis-Plus 3.5.5
- **数据库连接池**：Druid 1.2.21
- **缓存**：Redis
- **API文档**：SpringDoc OpenAPI 3 & Knife4j 4.4.0
- **通用工具库**：Hutool 5.8.26、Guava 33.0.0-jre
- **JSON处理**：Fastjson 2.0.47
- **HTTP客户端**：OkHttp3、Retrofit 2.3.7
- **多数据源支持**：dynamic-datasource-spring-boot-starter 4.2.0
- **JWT认证**：java-jwt 4.4.0
- **图片处理**：Thumbnailator 0.4.20

### 模块结构

项目采用了经典的多模块结构设计：

- **chy-boot-common**：公共模块，包含通用工具类、配置类、常量定义等
- **chy-boot-core**：核心模块，包含领域模型、数据访问层等
- **chy-boot-service**：服务模块，包含业务逻辑实现
- **chy-boot-rest-api**：API模块，提供REST接口

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 本地开发

1. 克隆仓库
```bash
git clone https://github.com/your-username/chy-boot.git
cd chy-boot
```

2. 配置数据库和Redis
```
修改 chy-boot-rest-api/src/main/resources/application-local.yml 配置文件中的数据库和Redis连接信息
```

3. 编译项目
```bash
mvn clean package -DskipTests
```

4. 运行项目
```bash
cd chy-boot-rest-api
java -jar target/chy-boot-rest-api-1.0-SNAPSHOT.jar
```

5. 访问Swagger文档
```
http://localhost:8084/doc.html
```

## 开发指南

### 代码规范

项目遵循阿里巴巴Java开发手册规范，建议所有开发人员熟读此规范：
- 类名使用UpperCamelCase风格
- 方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格
- 常量命名全部大写，单词间用下划线隔开
- 遵循良好的注释习惯，类、类属性、方法等都必须使用Javadoc规范注释

### 项目分层

- **控制层（Controller）**：负责处理请求，响应结果，参数校验等
- **服务层（Service）**：负责业务逻辑处理
- **数据访问层（Mapper/DAO）**：负责数据库交互
- **实体层（Entity）**：领域模型对象
- **传输对象（DTO/VO）**：数据传输对象，用于不同层之间的数据传递

### 数据库操作

项目使用MyBatis-Plus作为ORM框架，简化了数据库操作：

```java
// 查询
User user = userMapper.selectById(1L);

// 分页查询
Page<User> page = new Page<>(1, 10);
Page<User> userPage = userMapper.selectPage(page, new QueryWrapper<User>().eq("status", 1));

// 插入
User newUser = new User().setName("张三").setAge(18);
userMapper.insert(newUser);

// 更新
userMapper.updateById(user.setName("李四"));

// 删除
userMapper.deleteById(1L);
```

## 后续升级规划

根据Java和Spring Boot开发的最佳实践，项目计划进行以下升级：

### 1. 架构优化

- **引入领域驱动设计(DDD)**：将业务逻辑按领域进行划分，使代码结构更加清晰
- **实现CQRS架构**：将查询和命令分离，优化系统性能和可维护性
- **应用事件驱动架构**：使用Spring Events或消息队列实现松耦合的事件驱动模型

### 2. 技术栈升级

- **响应式编程**：集成Spring WebFlux，提高系统并发处理能力
- **容器化部署**：优化Docker和Kubernetes配置，实现更高效的容器化部署
- **服务监控**：集成Micrometer和Prometheus，提供更完善的指标监控
- **分布式追踪**：引入Spring Cloud Sleuth和Zipkin，实现分布式链路追踪
- **API网关**：引入Spring Cloud Gateway，统一管理API路由和安全

### 3. 安全加强

- **深入集成Spring Security**：实现更完善的认证和授权机制
- **OAuth2.0和OIDC支持**：支持标准的OAuth2.0和OpenID Connect协议
- **API安全加固**：实现请求签名验证、防重放攻击等安全机制
- **数据加密**：敏感数据存储和传输加密
- **审计日志**：完善的操作审计和安全日志记录

### 4. 性能优化

- **缓存策略升级**：优化Redis缓存策略，引入多级缓存
- **数据库性能**：优化SQL语句，合理使用索引，实现分库分表
- **异步处理**：大量使用CompletableFuture和Spring的异步特性
- **批处理优化**：大数据量操作采用批处理方式
- **JVM调优**：针对业务场景优化JVM参数配置

### 5. 开发体验提升

- **自动化测试**：完善单元测试、集成测试、端到端测试
- **API版本控制**：实现优雅的API版本控制机制
- **开发工具链**：引入更多开发辅助工具，如Lombok配置优化、MapStruct等
- **文档自动化**：API文档和技术文档自动生成与发布
- **CI/CD流水线**：优化持续集成和持续部署流程

## 贡献指南

欢迎提交问题和功能需求，同时也接受Pull Request贡献。

## 许可证

[Apache License 2.0](LICENSE)

