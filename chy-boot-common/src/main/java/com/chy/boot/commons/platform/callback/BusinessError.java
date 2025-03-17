package com.chy.boot.commons.platform.callback;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessError implements Serializable {
    String status;
    int httpCode;
    int statusCode;
    String message;
}
