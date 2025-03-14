# CHY-Boot 升级指南

本文档详细说明了CHY-Boot项目的升级历史、当前状态和后续升级计划，为开发团队提供明确的升级路线图。

## 已完成的升级

### 1. SpringBoot 3.x 升级

已将项目从SpringBoot 2.2.2.RELEASE升级到SpringBoot 3.2.3，主要变更包括：

- 升级Java版本要求至JDK 17
- 将javax.servlet包替换为jakarta.servlet包
- 更新相关依赖至兼容SpringBoot 3的版本

### 2. MyBatis-Plus集成

已将传统MyBatis替换为MyBatis-Plus 3.5.5，主要变更包括：

- 实体类添加@TableName、@TableId等注解
- Mapper接口继承BaseMapper<T>
- Service接口继承IService<T>
- Service实现类继承ServiceImpl<M, T>
- 简化和统一数据库操作API

### 3. API文档升级

已将Swagger 2升级为SpringDoc OpenAPI 3，主要变更包括：

- 替换io.swagger相关注解为io.swagger.v3注解
- 配置OpenAPI和GroupedOpenApi替代原有的Docket配置
- 接入Knife4j 4.x提供更友好的文档UI

## 待升级项目

### 1. 代码质量提升 (优先级：高)

#### 1.1 引入静态代码分析工具

```xml
<!-- pom.xml添加 -->
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.9.1.2184</version>
</plugin>
```

#### 1.2 统一异常处理机制

创建`GlobalExceptionHandler`类：

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ApiResult.fail(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ApiResult.fail("系统繁忙，请稍后再试");
    }
}
```

#### 1.3 请求参数校验

引入校验依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

在控制器中应用：

```java
@PostMapping("/users")
public ApiResult<UserVO> createUser(@RequestBody @Valid UserCreateDTO userDTO) {
    // 业务逻辑
}
```

### 2. 架构升级 (优先级：中)

#### 2.1 引入领域驱动设计(DDD)

建议的包结构调整：

```
com.changyi.chy
  ├── application         // 应用服务层
  │   └── service
  ├── domain              // 领域层
  │   ├── model
  │   ├── service
  │   └── repository
  ├── infrastructure      // 基础设施层
  │   ├── persistence
  │   ├── cache
  │   └── messaging
  └── interfaces          // 接口层
      ├── api
      ├── dto
      ├── assembler
      └── facade
```

#### 2.2 引入CQRS模式

命令模型与查询模型分离：

```java
// 命令处理器
@Component
public class CreateUserCommandHandler {
    public void handle(CreateUserCommand command) {
        // 处理创建用户命令
    }
}

// 查询处理器
@Component
public class UserQueryService {
    public UserDTO findById(String id) {
        // 查询用户信息
    }
}
```

### 3. 安全增强 (优先级：高)

#### 3.1 集成Spring Security

添加依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

基本配置：

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
```

#### 3.2 优化JWT实现

```java
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(jwtSecret));
    }
    
    // 验证token方法
}
```

### 4. 性能优化 (优先级：中)

#### 4.1 缓存优化

自定义缓存键生成策略：

```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
                
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
```

#### 4.2 异步处理优化

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("chy-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
```

### 5. 监控与可观测性 (优先级：中)

#### 5.1 集成Actuator和Micrometer

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

配置:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}
```

#### 5.2 日志链路追踪

添加依赖：

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

配置：

```yaml
management:
  tracing:
    sampling:
      probability: 1.0
    propagation:
      type: W3C
```

## 升级路线图

### 短期目标 (1-3个月)

1. 完善静态代码分析和代码质量工具集成
2. 统一异常处理和参数校验机制
3. 增强安全性：全面集成Spring Security和优化JWT实现
4. 优化缓存策略和异步处理能力

### 中期目标 (3-6个月)

1. 逐步引入领域驱动设计(DDD)思想重构代码
2. 引入CQRS模式分离查询和命令
3. 增强监控和可观测性能力
4. 优化Docker和Kubernetes配置

### 长期目标 (6个月以上)

1. 微服务化拆分
2. 引入响应式编程模型
3. API网关和服务发现集成
4. 分布式事务支持

## 升级注意事项

1. 每次升级前先进行充分的单元测试和集成测试
2. 遵循渐进式迭代原则，避免大规模重构
3. 保持文档与代码的同步更新
4. 使用feature分支进行开发，完成后合并到主分支
5. 优先解决安全漏洞和性能瓶颈

## 参考资料

- [Spring Boot 3.x 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [MyBatis-Plus 官方文档](https://baomidou.com/pages/24112f/)
- [阿里巴巴Java开发手册](https://github.com/alibaba/p3c)
- [Spring Security 参考文档](https://docs.spring.io/spring-security/reference/) 