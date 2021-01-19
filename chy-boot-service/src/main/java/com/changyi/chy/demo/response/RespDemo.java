package com.changyi.chy.demo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RepDemo", description="RepDemo")
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
