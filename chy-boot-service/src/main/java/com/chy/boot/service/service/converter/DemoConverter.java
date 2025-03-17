package com.chy.boot.service.service.converter;

import com.chy.boot.rest.core.entity.Demo;
import com.chy.boot.rest.dto.CreateDemoDTO;
import com.chy.boot.rest.dto.UpdateDemoDTO;
import com.chy.boot.rest.vo.DemoVO;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Demo数据转换工具
 *
 * @author YuRuizhi
 * @date 2024/3/17
 */
@Component
public class DemoConverter {

    /**
     * 创建DTO转实体
     */
    public Demo convertToDO(CreateDemoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Demo demo = new Demo();
        demo.setDemoId(UUID.randomUUID().toString().replace("-", ""));
        demo.setDemoName(dto.getDemoName())
            .setDemoDescription(dto.getDemoDescription())
            .setDemoCode(dto.getDemoCode())
            .setDemoContent(dto.getDemoContent())
            .setDemoConfig(dto.getDemoConfig())
            .setDemoCategory(dto.getDemoCategory())
            .setDemoStatus(1); // 初始状态
        
        return demo;
    }
    
    /**
     * 更新DTO转实体
     */
    public Demo convertToDO(UpdateDemoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Demo demo = new Demo();
        demo.setDemoId(dto.getDemoId())
            .setDemoName(dto.getDemoName())
            .setDemoDescription(dto.getDemoDescription())
            .setDemoCode(dto.getDemoCode())
            .setDemoContent(dto.getDemoContent())
            .setDemoConfig(dto.getDemoConfig())
            .setDemoCategory(dto.getDemoCategory())
            .setDemoUpdated(LocalDateTime.now());
        
        return demo;
    }
    
    /**
     * 实体转VO
     */
    public DemoVO convertToVO(Demo demo) {
        if (demo == null) {
            return null;
        }
        
        DemoVO vo = new DemoVO();
        vo.setId(demo.getDemoId());
        vo.setName(demo.getDemoName());
        vo.setDescription(demo.getDemoDescription());
        vo.setCode(demo.getDemoCode());
        vo.setContent(demo.getDemoContent());
        vo.setConfig(demo.getDemoConfig());
        vo.setCategory(demo.getDemoCategory());
        vo.setStatus(demo.getDemoStatus());
        
        // 设置状态描述
        if (demo.getDemoStatus() != null) {
            switch (demo.getDemoStatus()) {
                case 1:
                    vo.setStatusDesc("正常");
                    break;
                case 2:
                    vo.setStatusDesc("已更新");
                    break;
                case 3:
                    vo.setStatusDesc("已删除");
                    break;
                default:
                    vo.setStatusDesc("未知");
            }
        }
        
        vo.setCreatedTime(demo.getDemoCreated());
        vo.setUpdatedTime(demo.getDemoUpdated());
        
        return vo;
    }
    
    /**
     * 批量转换实体到VO
     */
    public List<DemoVO> convertToVOList(List<Demo> demoList) {
        if (demoList == null) {
            return new ArrayList<>();
        }
        
        return demoList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }
} 