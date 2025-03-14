# CHY-Boot 最佳实践指南

本文档提供了CHY-Boot项目开发的最佳实践指南，帮助团队成员编写高质量、可维护的代码，并遵循行业最佳实践。

## 1. 项目架构最佳实践

### 1.1 分层架构

遵循标准的分层架构，每层职责明确：

- **表示层(Controller)**：处理HTTP请求，参数校验，返回响应
- **业务层(Service)**：实现业务逻辑，处理事务
- **数据访问层(Repository/Mapper)**：与数据库交互
- **实体层(Entity)**：领域模型和数据传输对象

各层交互遵循单向依赖原则：表示层 -> 业务层 -> 数据访问层。

### 1.2 包结构规范

```
com.changyi.chy
  ├── controller        // 控制器
  │   └── vo            // 视图对象
  ├── service           // 服务接口
  │   └── impl          // 服务实现
  ├── mapper            // MyBatis-Plus的Mapper接口
  ├── entity            // 实体类
  ├── dto               // 数据传输对象
  ├── config            // 配置类
  ├── common            // 通用组件
  │   ├── annotation    // 自定义注解
  │   ├── aspect        // AOP切面
  │   ├── constant      // 常量定义
  │   ├── enums         // 枚举类
  │   ├── exception     // 异常类
  │   ├── utils         // 工具类
  │   └── validation    // 自定义校验器
  └── security          // 安全相关
```

### 1.3 命名规范

- **类名**：使用UpperCamelCase，如`UserService`
- **方法名**：使用lowerCamelCase，如`getUserById`
- **变量名**：使用lowerCamelCase，如`userId`
- **常量**：全部大写，下划线分隔，如`MAX_RETRY_COUNT`
- **包名**：全部小写，如`com.changyi.chy.service`

## 2. 编码最佳实践

### 2.1 接口设计

遵循RESTful API设计规范：

- 使用HTTP方法表示操作类型：
  - `GET`：查询资源
  - `POST`：创建资源
  - `PUT`：更新资源(全量更新)
  - `PATCH`：部分更新资源
  - `DELETE`：删除资源

- URL命名使用名词复数形式：`/api/users`而非`/api/user`

- 正确使用HTTP状态码：
  - `200 OK`：成功
  - `201 Created`：创建成功
  - `400 Bad Request`：客户端错误
  - `401 Unauthorized`：未认证
  - `403 Forbidden`：权限不足
  - `404 Not Found`：资源不存在
  - `500 Internal Server Error`：服务器错误

### 2.2 异常处理

- 业务异常应该继承自自定义的`BusinessException`基类
- 所有异常应当在最外层被捕获并转换为友好的响应
- 避免直接暴露技术细节和堆栈信息给客户端
- 使用统一的响应格式：

```java
public class ApiResult<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;
    
    // 构造方法和静态工厂方法
    
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "操作成功", data, System.currentTimeMillis());
    }
    
    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(500, message, null, System.currentTimeMillis());
    }
    
    public static <T> ApiResult<T> fail(int code, String message) {
        return new ApiResult<>(code, message, null, System.currentTimeMillis());
    }
}
```

### 2.3 代码风格

- 使用Lombok简化代码，但不要过度依赖
- 方法长度控制在50行以内，过长的方法应当拆分
- 每个类都应有清晰的Javadoc注释说明其用途
- 所有公共方法都应有Javadoc注释
- 复杂的业务逻辑应当添加必要的注释
- 使用Optional处理可能为null的情况
- 使用Stream API简化集合操作
- 使用`@Slf4j`进行日志记录

## 3. 数据库访问最佳实践

### 3.1 MyBatis-Plus使用规范

- 实体类使用`@TableName`指定表名
- 主键使用`@TableId`注解，并指定生成策略
- 审计字段(创建时间/更新时间)使用`@TableField`注解的`fill`属性结合自动填充处理器
- 乐观锁和逻辑删除使用`@Version`和`@TableLogic`注解
- 尽量使用Lambda表达式构建查询条件

```java
// 推荐的写法
List<User> users = userMapper.selectList(
    new LambdaQueryWrapper<User>()
        .eq(User::getStatus, 1)
        .like(StringUtils.isNotBlank(username), User::getUsername, username)
        .orderByDesc(User::getCreateTime)
);
```

### 3.2 事务管理

- 在Service层使用`@Transactional`注解管理事务
- 明确指定事务的传播行为和隔离级别
- 只读操作使用`readOnly=true`优化性能
- 捕获异常时注意不要意外吞没异常导致事务无法回滚

```java
@Transactional(rollbackFor = Exception.class)
public void createOrder(OrderDTO orderDTO) {
    // 业务逻辑
}

@Transactional(readOnly = true)
public List<Order> queryOrders(OrderQueryDTO queryDTO) {
    // 查询逻辑
}
```

### 3.3 性能优化

- 合理使用索引
- 避免大事务，将大事务拆分为小事务
- 使用分页查询避免一次性返回大量数据
- 避免在循环中执行数据库操作，使用批量操作替代
- 合理使用多表连接，避免过多的表连接
- 使用缓存减轻数据库压力

## 4. 安全最佳实践

### 4.1 认证与授权

- 使用JWT或OAuth2.0进行认证
- 密码存储使用强哈希算法(如BCrypt)，不要使用MD5/SHA1
- 敏感操作需要二次验证
- 使用HTTPS传输数据
- 实现访问控制列表(ACL)或基于角色的访问控制(RBAC)

### 4.2 防止常见攻击

- SQL注入：使用参数化查询和ORM框架
- XSS攻击：输入输出过滤，使用CSP
- CSRF攻击：使用CSRF令牌
- 点击劫持：设置X-Frame-Options响应头
- 敏感信息泄露：日志中不记录敏感信息
- 限制请求频率：实现频率限制器

```java
@Bean
public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter() {
    FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new RateLimiterFilter());
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
}
```

## 5. 测试最佳实践

### 5.1 单元测试

- 每个Service类都应有对应的测试类
- 使用JUnit 5和Mockito进行测试
- 测试方法名应清晰地描述测试的场景和预期结果
- 使用`@DisplayName`注解增加测试可读性
- 使用断言清晰地表达预期结果

```java
@SpringBootTest
@DisplayName("用户服务测试")
class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @MockBean
    private UserMapper userMapper;
    
    @Test
    @DisplayName("当用户ID存在时应返回正确的用户信息")
    void shouldReturnUserWhenIdExists() {
        // 准备测试数据
        User expectedUser = new User().setId("1").setUsername("test");
        when(userMapper.selectById("1")).thenReturn(expectedUser);
        
        // 执行测试
        User actualUser = userService.getById("1");
        
        // 验证结果
        assertNotNull(actualUser);
        assertEquals("test", actualUser.getUsername());
        verify(userMapper).selectById("1");
    }
}
```

### 5.2 集成测试

- 使用测试容器(如TestContainers)进行集成测试
- 使用独立的测试数据库
- 测试前准备测试数据，测试后清理数据
- 模拟真实的用户请求场景

### 5.3 性能测试

- 使用JMeter或Gatling进行性能测试
- 关注响应时间、吞吐量和错误率
- 测试系统在高负载下的表现
- 找出性能瓶颈并优化

## 6. 部署与运维最佳实践

### 6.1 容器化部署

- 使用Docker构建镜像，优化Dockerfile
- 遵循多阶段构建模式
- 使用轻量级基础镜像
- 不在容器中存储状态数据

```dockerfile
# 多阶段构建示例
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/chy-boot-rest-api/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 6.2 配置管理

- 使用环境变量或配置中心(如Spring Cloud Config)管理配置
- 敏感配置(如密码、密钥)使用加密或密钥管理系统
- 不同环境(开发、测试、生产)使用不同的配置文件
- 合理使用配置的优先级

### 6.3 监控与日志

- 实现健康检查接口
- 使用Prometheus和Grafana监控系统指标
- 集中式日志收集(ELK堆栈)
- 结构化日志格式，包含关键信息
- 实现应用程序的自动告警机制

## 7. 微服务最佳实践(长期规划)

### 7.1 服务划分

- 按业务功能划分服务边界
- 服务粒度适中，不过大也不过小
- 考虑服务的可重用性和可替换性
- 避免服务间的紧耦合

### 7.2 服务通信

- 使用RESTful API或gRPC进行同步通信
- 使用消息队列(如Kafka或RabbitMQ)进行异步通信
- 实现服务发现机制
- 使用API网关统一管理服务入口

### 7.3 弹性设计

- 实现熔断机制(如使用Resilience4j)
- 添加重试和超时机制
- 实现服务降级策略
- 使用缓存提高系统弹性

## 参考资料

- [Spring官方文档](https://spring.io/guides)
- [阿里巴巴Java开发手册](https://github.com/alibaba/p3c)
- [Google Java编程风格指南](https://google.github.io/styleguide/javaguide.html)
- [Effective Java, 3rd Edition](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997)
- [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- [领域驱动设计](https://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215) 