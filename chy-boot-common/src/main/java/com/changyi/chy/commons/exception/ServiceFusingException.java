package com.changyi.chy.commons.exception;


import com.changyi.chy.commons.jackson.JsonUtil;
import com.changyi.chy.commons.util.UUIDGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 服务熔断异常
 *
 * @author Henry.Yu
 * @date 2020.3.4
 */
public class ServiceFusingException extends BaseException {

    private IdentData data;

    public ServiceFusingException(String code, String msg) {
        super(code, msg);
    }

    public ServiceFusingException(String msg) {
        super(msg);
    }

    public static IdentData getData() {
        String data = "{\"aiResult\":0,\"recordId\":\"默认\",\"colorList\":[{\"id\":\"75E853A48DF93289BB93D7E263BB57A4\",\"code\":\"7\",\"name\":\"灰白色\",\"alias\":\"Pale/Clay\",\"remark\":\"\",\"sort\":1,\"aiResult\":0},{\"id\":\"F03F91658664F252DD8FCBEA8F0366CC\",\"code\":\"6\",\"name\":\"黄色\",\"alias\":\"Yellow\",\"remark\":\"\",\"sort\":2,\"aiResult\":1},{\"id\":\"57BAD5AED3D2624DA9477A468D75A0E0\",\"code\":\"5\",\"name\":\"橙色\",\"alias\":\"Orange\",\"remark\":\"\",\"sort\":3,\"aiResult\":0},{\"id\":\"F94495F93AE4565CB76DBF71CFADB405\",\"code\":\"4\",\"name\":\"红色\",\"alias\":\"Red\",\"remark\":\"\",\"sort\":4,\"aiResult\":0},{\"id\":\"46299A21FAF5C32B6C0F24EC42BB26BF\",\"code\":\"3\",\"name\":\"棕色\",\"alias\":\"Brown\",\"remark\":\"\",\"sort\":5,\"aiResult\":0},{\"id\":\"A9EE348B9B95824D1EDD8028909AE64C\",\"code\":\"2\",\"name\":\"绿色\",\"alias\":\"Green\",\"remark\":\"\",\"sort\":6,\"aiResult\":0},{\"id\":\"3991B9663963641354432B86E85F7C8B\",\"code\":\"1\",\"name\":\"黑色\",\"alias\":\"Black\",\"remark\":\"\",\"sort\":7,\"aiResult\":0}],\"shapeList\":[{\"id\":\"5B24C363D4D0F5F77E9C865B42E77E67\",\"code\":\"1\",\"name\":\"颗粒状\",\"alias\":\" 硬\",\"remark\":\"\",\"sort\":1,\"aiResult\":0},{\"id\":\"6311A36AC370C4D0CB01D6763258764F\",\"code\":\"2\",\"name\":\"凹凸香肠状\",\"alias\":\"硬\",\"remark\":\"\",\"sort\":2,\"aiResult\":0},{\"id\":\"68882B9EE58490A37BBEB88C0F71DA55\",\"code\":\"3\",\"name\":\"裂痕香肠状\",\"alias\":\"硬\",\"remark\":\"\",\"sort\":3,\"aiResult\":0},{\"id\":\"7E8B3D68A48C05CD83A457E5161634D0\",\"code\":\"4\",\"name\":\"光滑香肠状\",\"alias\":\"成形\",\"remark\":\"\",\"sort\":4,\"aiResult\":1},{\"id\":\"CD7D827CBEF175F6792CA249E11CEFAC\",\"code\":\"5\",\"name\":\"柔软块状物\",\"alias\":\"松散\",\"remark\":\"\",\"sort\":5,\"aiResult\":0},{\"id\":\"2A0C282DA2A27CDEE09BE99337A5896F\",\"code\":\"6\",\"name\":\"蓬松块糊状\",\"alias\":\"松散\",\"remark\":\"\",\"sort\":6,\"aiResult\":0},{\"id\":\"4D52513FAB8C0F2D20EE0A06887FAB20\",\"code\":\"7\",\"name\":\"水状\",\"alias\":\"稀烂\",\"remark\":\"\",\"sort\":7,\"aiResult\":0}]}";
        IdentData identData = JsonUtil.parse(data, IdentData.class);
        identData.setRecordId(UUIDGenerator.generate());
        return identData;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


    @Getter
    @Setter
    public static class IdentData implements Serializable {
        private static final long serialVersionUID = 1L;
        private int aiResult;
        private String recordId;
        private List<Color> colorList;
        private List<Shape> shapeList;

        @Getter
        @Setter
        public static class Color implements Serializable {
            private static final long serialVersionUID = 1L;
            private String id;
            private String code;
            private String name;
            private String alias;
            private String remark;
            private Integer sort;
            private int aiResult;
        }

        @Getter
        @Setter
        public static class Shape implements Serializable {
            private static final long serialVersionUID = 1L;
            private String id;
            private String code;
            private String name;
            private String alias;
            private String remark;
            private Integer sort;
            private int aiResult;
        }
    }

}
