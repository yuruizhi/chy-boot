# 持久层工具使用示例

本文档展示了如何在Controller中使用持久层提供的工具类，包括分页查询、统一返回格式等。

## 1. 分页查询示例

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表", description = "根据条件分页查询用户列表")
    public R<PageResult<UserVO>> page(UserPageRequest request) {
        // 1. 将请求参数转换为MyBatis-Plus的Page对象
        Page<User> page = PageUtils.convertToPage(request);
        
        // 2. 构建查询条件
        LambdaQueryWrapper<User> wrapper = QueryBuilder.lambdaQuery();
        QueryBuilder.like(wrapper, User::getUsername, request.getUsername());
        QueryBuilder.eq(wrapper, User::getStatus, request.getStatus());
        QueryBuilder.between(wrapper, User::getCreateTime, request.getStartTime(), request.getEndTime());
        
        // 3. 执行查询
        IPage<User> userPage = userService.page(page, wrapper);
        
        // 4. 将查询结果转换为VO，并包装为统一返回格式
        return PageUtils.toResultDto(userPage, this::convertToVO);
    }
    
    /**
     * 实体转换为VO
     */
    private UserVO convertToVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        // 其他转换逻辑...
        return vo;
    }
    
    /**
     * 手动分页示例
     */
    @GetMapping("/manual-page")
    @Operation(summary = "手动分页示例", description = "不使用数据库分页，在内存中进行分页")
    public R<PageResult<UserVO>> manualPage(PageRequest request) {
        // 1. 获取所有数据
        List<User> allUsers = userService.list();
        
        // 2. 转换为VO
        List<UserVO> userVOList = allUsers.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        // 3. 手动分页并返回
        return PageUtils.manualPagingResult(userVOList, request.getCurrent(), request.getSize());
    }
}
```

## 2. 统一返回格式示例

```java
@RestController
@RequestMapping("/api/demo")
@Tag(name = "示例接口", description = "演示统一返回格式的使用")
public class DemoController {

    /**
     * 返回成功
     */
    @GetMapping("/success")
    @Operation(summary = "返回成功示例")
    public R<String> success() {
        return R.success("操作成功");
    }
    
    /**
     * 返回带数据的成功结果
     */
    @GetMapping("/data")
    @Operation(summary = "返回带数据的成功示例")
    public R<Map<String, Object>> data() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("name", "测试");
        return R.data(data);
    }
    
    /**
     * 返回自定义消息的成功结果
     */
    @GetMapping("/success-message")
    @Operation(summary = "返回自定义消息的成功示例")
    public R<Void> successWithMessage() {
        return R.success("自定义成功消息");
    }
    
    /**
     * 返回失败结果
     */
    @GetMapping("/fail")
    @Operation(summary = "返回失败示例")
    public R<Void> fail() {
        return R.fail("操作失败");
    }
    
    /**
     * 返回业务错误码的失败结果
     */
    @GetMapping("/fail-code")
    @Operation(summary = "返回业务错误码的失败示例")
    public R<Void> failWithCode() {
        return R.fail(ResultCode.PARAM_ERROR);
    }
    
    /**
     * 返回自定义错误码和消息的失败结果
     */
    @GetMapping("/fail-custom")
    @Operation(summary = "返回自定义错误码和消息的失败示例")
    public R<Void> failWithCustom() {
        return R.fail(ResultCode.DATA_NOT_EXIST, "数据不存在，请检查参数");
    }
    
    /**
     * 根据条件返回成功或失败
     */
    @GetMapping("/status")
    @Operation(summary = "根据条件返回成功或失败示例")
    public R<Void> status(@RequestParam Boolean success) {
        return R.status(success);
    }
}
```

## 3. 查询条件构建示例

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据条件查询用户
     */
    public List<User> findByCondition(UserQueryRequest request) {
        // 使用QueryBuilder构建查询条件
        LambdaQueryWrapper<User> wrapper = QueryBuilder.lambdaQuery();
        
        // 添加等值条件（非空）
        QueryBuilder.eq(wrapper, User::getStatus, request.getStatus());
        
        // 添加模糊查询条件（非空）
        QueryBuilder.like(wrapper, User::getUsername, request.getUsername());
        QueryBuilder.like(wrapper, User::getNickname, request.getNickname());
        
        // 添加日期范围条件（非空）
        QueryBuilder.between(wrapper, User::getCreateTime, request.getStartTime(), request.getEndTime());
        
        // 添加IN条件（非空）
        QueryBuilder.in(wrapper, User::getDeptId, request.getDeptIds());
        
        // 添加排序
        wrapper.orderByDesc(User::getCreateTime);
        
        // 执行查询
        return list(wrapper);
    }
    
    /**
     * 条件断言示例
     */
    public List<User> findWithAssert(UserQueryRequest request) {
        LambdaQueryWrapper<User> wrapper = QueryBuilder.lambdaQuery();
        
        // 根据对象非空添加条件
        QueryBuilder.conditionNotNull(wrapper, request.getStatus(), w -> 
            w.eq(User::getStatus, request.getStatus())
        );
        
        // 根据字符串非空添加条件
        QueryBuilder.conditionNotEmpty(wrapper, request.getUsername(), w -> 
            w.like(User::getUsername, request.getUsername())
        );
        
        // 根据集合非空添加条件
        QueryBuilder.conditionNotEmpty(wrapper, request.getRoleIds(), w -> 
            w.in(User::getRoleId, request.getRoleIds())
        );
        
        // 执行查询
        return list(wrapper);
    }
}
``` 