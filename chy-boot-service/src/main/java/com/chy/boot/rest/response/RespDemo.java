package com.chy.boot.rest.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author YuRuizhi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "Demo返回")
public class RespDemo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String uuid;

    private String hitokoto;

    private String type;

    private String from;

    private String from_who;

    private String creator;

    private int creator_uid;

    private int reviewer;

    private String commit_from;

    private String created_at;

    private int length;
}
