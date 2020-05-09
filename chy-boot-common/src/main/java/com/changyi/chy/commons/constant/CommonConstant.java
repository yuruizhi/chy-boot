package com.changyi.chy.commons.constant;

import com.changyi.chy.commons.component.comstant.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CommonConstant {

    /**
     * 识别结果状态
     */
    public enum IdentResult implements IEnum<Integer, String> {
        SUCCESS(1,"成功"),
        FAIL(0,"失败");

        Integer value;
        String desc;

        IdentResult(Integer value, String desc){
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }
    }

    /**
     * 便便外型
     */
    public enum PooFeature implements IEnum<Integer, String> {
        COLOR(1,"颜色"),
        SHAPE(2,"外型");

        Integer value;
        String desc;

        PooFeature(Integer value, String desc){
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }
    }

    /**
     * 与群脉性别数据转化
     */
    public enum MemberGender implements IEnum<Integer, String> {
        UNKNOWN(0,""),
        MALE(1,"male"),
        FEMALE(2,"female");

        Integer value;
        String desc;

        MemberGender(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }

        public static String getLocaleDesc(Integer value) {

            for (MemberGender s : MemberGender.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }

        public static Integer getLocaleValue(String desc) {

            for (MemberGender s : MemberGender.values()) {
                if (s.desc.equals(desc)) {
                    return s.getValue();
                }
            }

            return 0;
        }
    }


    /**
     * 与群脉妈妈状态数据转化
     */
    public enum MotherStatus implements IEnum<Integer, String> {
        PREGNANCY_PREPARATION(1,"备孕"),
        PREGNANCY(2,"怀孕"),
        MOTHER(3,"宝妈");

        Integer value;
        String desc;

        MotherStatus(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (MotherStatus s : MotherStatus.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }


    public enum Visitors implements IEnum<Integer, String> {
        IS(1,"访客"),
        NOT(0,"正常客户");

        Integer value;
        String desc;

        Visitors(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (Visitors s : Visitors.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }

    public enum RegisteredFlag implements IEnum<Integer, String> {
        REGISTERED_QM(1,"注册群脉"),
        UPDATE_QM(2,"更新群脉"),
        ALI(3, "阿里注册，不需要同步群脉");

        Integer value;
        String desc;

        RegisteredFlag(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (Visitors s : Visitors.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }

    public enum First implements IEnum<Integer, String> {
        IS(1,"第一个孩子"),
        NOT(0,"非第一个孩子");

        Integer value;
        String desc;

        First(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (First s : First.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }


    public enum DataSource implements IEnum<Integer, String> {
        REGISTER(1,"注册"),
        SYNC(0,"同步群脉");

        Integer value;
        String desc;

        DataSource(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (DataSource s : DataSource.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }

    public enum Role implements IEnum<Integer, String> {
        MOTHER(1,"妈妈"),
        FATHER(2,"爸爸"),
        GRANDFATHER(3,"爷爷"),
        GRANDMOTHER(4,"奶奶"),
        MATER_GRANDFATHER(5,"外公"),
        MATER_GRANDMOTHER(6,"外婆"),
        UNCLE(7,"叔叔"),
        AUNT(8,"阿姨"),
        GG_AUNT(9,"姑姑"),
        GF_UNCLE(10,"姑父"),
        JJ_UNCLE(11,"舅舅"),
        JM_AUNT(12,"舅妈");

        Integer value;
        String desc;

        Role(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public Integer getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleDesc(Integer value) {

            for (Role s : Role.values()) {
                if (s.value.intValue() == value.intValue()) {
                    return s.getDesc();
                }
            }

            return "";
        }
    }

    public enum ChannelId implements IEnum<String, String> {

        ALI("ali-channel","阿里"),
        H5("h5-channel","H5");
        String value;
        String desc;

        ChannelId(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public String getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }

    }


    /**
     * 注册渠道
     */
    public enum SourceType implements IEnum<String, String> {

        WECHAT_MINI_PROGRAM("wechat-mp","wechat-mp-channel"),
        H5("h5","h5-channel"),
        QM("qm","qm");
        String value;
        String desc;

        SourceType(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public String getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }


        public static String getLocaleValue(String desc) {

            for (SourceType s : SourceType.values()) {
                if (s.desc.equals(desc)) {
                    return s.getValue();
                }
            }

            return "";
        }
    }

    public enum Brand implements IEnum<String, String> {

        Aptamil("Aptamil","爱他美"),
        Nutrilon("Nutrilon","诺优能");
        String value;
        String desc;

        Brand(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        @Override
        public String getValue(){
            return this.value;
        }

        @Override
        public String getDesc(){
            return this.desc;
        }

        public static boolean exist(String value){
            for (Brand s : Brand.values()) {
                if (s.value.equals(value)) {
                    return true;
                }
            }
            return false;
        }

    }


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
