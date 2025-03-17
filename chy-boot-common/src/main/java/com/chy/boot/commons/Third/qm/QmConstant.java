package com.chy.boot.commons.Third.qm;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmConstant {

    /**
     * 加密类型
     */
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum EncryType {
        ERR_2000007(2000007, "Member not found");

        int value;
        String desc;

        EncryType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return this.value;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getLocaleDesc(int value) {
            for (EncryType s : EncryType.values()) {
                if (s.value == value) {
                    return s.getDesc();
                }
            }
            return "";
        }
    }

}



