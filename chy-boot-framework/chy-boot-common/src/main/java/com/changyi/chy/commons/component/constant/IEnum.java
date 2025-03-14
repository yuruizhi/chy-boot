package com.changyi.chy.commons.component.constant;

import java.io.Serializable;

/**
 * 通用常量定义接口.
 *
 * @author three
 * @date 2016.5.11
 */
public interface IEnum<T, D> extends Serializable {

    /**
     * 得到常量key或值
     *
     * @return {T}
     */
    T getValue();

    /**
     * 得到常量的定义或描述
     * 如果国际化,在这里实现
     *
     * @return {D}
     */
    D getDesc();
}
