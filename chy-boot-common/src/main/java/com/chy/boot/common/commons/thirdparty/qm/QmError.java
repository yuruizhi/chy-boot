package com.chy.boot.common.commons.thirdparty.qm;

import lombok.Data;

import java.io.Serializable;

@Data
public class QmError implements Serializable {
    int code;
    String message;
}
