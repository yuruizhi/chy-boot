package com.chy.boot.commons.constant;

import com.chy.boot.commons.component.constant.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CommonConstant {


    /**
     * 回调状态
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum CallbackStatus implements IEnum<Integer, String> {
        NOTCALLBACK(1, "未回调"),
        CALLBACKSUCCESS(2, "回调成功"),
        CALLBACKFAILD(3, "回调失败");

        Integer value;
        String desc;

        CallbackStatus(Integer value, String desc) {

            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue() {

            return this.value;
        }

        @Override
        public String getDesc() {

            return this.desc;
        }

    }

    /**
     * 回调业务系统时,业务系统返回结果
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum CallbackBusinessResult implements IEnum<String, String> {
        SUCCESS("1", "success"),
        INVALID("-1","invalid");

        String value;
        String desc;

        CallbackBusinessResult(String value, String desc) {

            this.value = value;
            this.desc = desc;
        }

        @Override
        public String getValue() {

            return this.value;
        }

        @Override
        public String getDesc() {

            return this.desc;
        }

        public static String getLocaleDesc(String value) {

            for (CallbackBusinessResult s : CallbackBusinessResult.values()) {
                if (s.value.equals(value)) {
                    return s.getDesc();
                }
            }
            return "";
        }
    }
}
