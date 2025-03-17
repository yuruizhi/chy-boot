package com.chy.boot.commons.Third.qm;

import lombok.Data;

import java.io.Serializable;

@Data
public class QmError implements Serializable {
    int code;
    String message;
}
