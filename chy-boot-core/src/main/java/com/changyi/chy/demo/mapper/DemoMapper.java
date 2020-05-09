package com.changyi.chy.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changyi.chy.demo.entity.Demo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * demo
 *
 * @author Henry.Yu
 * @since 2020.5.8
 */
public interface DemoMapper extends BaseMapper<Demo> {

    Demo selectByParams(@Param(value = "params") Map<String, Object> params);
}
