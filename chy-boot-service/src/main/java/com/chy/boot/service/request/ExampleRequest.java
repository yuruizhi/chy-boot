package com.chy.boot.service.request;

import com.chy.boot.common.component.validate.ValidGroup;
import com.chy.boot.common.component.validate.ValidateMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 示例请求对象，包含各种参数校验注解
 *
 * @author Example
 * @date 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ExampleRequest", description = "示例请求对象")
public class ExampleRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID字段，更新时必填
     */
    @Schema(description = "ID", example = "1")
    @NotNull(message = ValidateMessage.NotNull, groups = {ValidGroup.Update.class})
    @Null(message = ValidateMessage.Null, groups = {ValidGroup.Add.class})
    private Long id;

    /**
     * 用户名字段，必填，长度3-20
     */
    @Schema(description = "用户名", required = true, example = "exampleUser")
    @NotBlank(message = ValidateMessage.NotBlank)
    @Length(min = 3, max = 20, message = ValidateMessage.Length)
    private String username;

    /**
     * 邮箱字段，必填，必须是合法邮箱
     */
    @Schema(description = "邮箱", required = true, example = "example@example.com")
    @NotBlank(message = ValidateMessage.NotBlank)
    @Email(message = ValidateMessage.Email)
    private String email;

    /**
     * 手机号字段，必填，必须符合手机号格式
     */
    @Schema(description = "手机号", required = true, example = "13800138000")
    @NotBlank(message = ValidateMessage.NotBlank)
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = ValidateMessage.Pattern)
    private String mobile;

    /**
     * 年龄字段，必须在18-120之间
     */
    @Schema(description = "年龄", example = "25")
    @Min(value = 18, message = ValidateMessage.Min)
    @Max(value = 120, message = ValidateMessage.Max)
    private Integer age;

    /**
     * 得分字段，必须在0-100之间
     */
    @Schema(description = "得分", example = "85.5")
    @DecimalMin(value = "0.0", message = ValidateMessage.DecimalMin)
    @DecimalMax(value = "100.0", message = ValidateMessage.DecimalMax)
    private Double score;

    /**
     * 生日字段，必须是过去的日期
     */
    @Schema(description = "生日", example = "1990-01-01")
    @Past(message = ValidateMessage.Past)
    private LocalDate birthday;

    /**
     * 下次预约时间，必须是将来的日期时间
     */
    @Schema(description = "下次预约时间", example = "2023-12-31 10:00:00")
    @Future(message = ValidateMessage.Future)
    private LocalDateTime nextAppointment;

    /**
     * 标签列表，不能为空，且长度在1-10之间
     */
    @Schema(description = "标签列表")
    @NotEmpty(message = ValidateMessage.NotEmpty)
    @Size(min = 1, max = 10, message = ValidateMessage.Size)
    private List<String> tags;

    /**
     * 描述字段，最大长度200
     */
    @Schema(description = "描述", example = "这是一个示例描述")
    @Length(max = 200, message = ValidateMessage.MaxLength)
    private String description;

    /**
     * 地址URL字段，必须是合法URL
     */
    @Schema(description = "地址URL", example = "https://example.com")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = ValidateMessage.URL)
    private String url;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
} 