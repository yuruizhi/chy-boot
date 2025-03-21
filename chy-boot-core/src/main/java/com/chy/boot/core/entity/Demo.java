package com.chy.boot.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updatedAt;

    @JsonIgnore
    @Schema(description = "创建人")
    private String createdBy;

    @JsonIgnore
    @Schema(description = "更新人")
    private String updatedBy;

    @JsonIgnore
    @Schema(description = "删除标识(0: 未删除; 删除存时间戳)")
    private Long deletedAt;
}