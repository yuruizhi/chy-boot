package com.chy.boot.common.commons.component.validate;

/**
 * jsr-349 使用,消息常量定义
 *
 * @author YuRuizhi
 * @date 2020/1/15
 */
public interface ValidateMessage {

    /**
     * 内容为空时的提示
     */
    String NotBlank = "{com.chy.valid.NotBlank.message}";

    /**
     * Url格式错误
     */
    String URL = "{com.chy.valid.URL.message}";
    // 邮件地址
    String Email = "{com.chy.valid.Email.message}";
    // IP地址格式错误
    String Ip = "{com.chy.valid.Ip.message}";
    // 交易类型错误
    String TradeType = "{com.chy.valid.TradeType.message}";
    // 不为Empty
    String NotEmpty = "{com.chy.valid.NotEmpty.message}";
    // 不为Null
    String NotNull = "{com.chy.valid.NotNull.message}";
    // 为Null
    String Null = "{com.chy.valid.Null.message}";

    String Past = "{com.chy.valid.Past.message}";
    // 正则错误
    String Pattern = "{com.chy.valid.Pattern.message}";
    // 对象长度判断错误
    String Size = "{com.chy.valid.Size.message}";

    // 对象长度判断错误(固定)
    String fixedSize = "{com.chy.valid.fixedSize.message}";

    // 字串长度判断错误
    String Length = "{com.chy.valid.Length.message}";

    // 字串长度判断错误
    String MaxLength = "{com.chy.valid.MaxLength.message}";

    // 字串长度判断错误
    String MinLength = "{com.chy.valid.MinLength.message}";

    // 最小值错误
    String Min = "{com.chy.valid.Min.message}";
    // 最大值错误
    String Max = "{com.chy.valid.Max.message}";

    String Future = "{com.chy.valid.Future.message}";

    String Digits = "{com.chy.valid.Digits.message}";

    String AssertFalse = "{com.chy.valid.AssertFalse.message}";

    String AssertTrue = "{com.chy.valid.AssertTrue.message}";

    String DecimalMax = "{com.chy.valid.DecimalMax.message}";

    String DecimalMin = "{com.chy.valid.DecimalMin.message}";

}
