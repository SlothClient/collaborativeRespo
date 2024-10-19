package com.example.springboot.service.impl;

import com.example.springboot.entity.VisualInfo;
import com.example.springboot.mapper.VisualizeMapper;
import com.example.springboot.service.VisualizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisualizeServiceImpl implements VisualizeService {
    @Autowired
    VisualizeMapper visualizeMapper;
    @Override
    public List<VisualInfo> getBigNums() {
        List<VisualInfo> list = visualizeMapper.getBigNums();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    public List<VisualInfo> getEquipPlans() {
        List<VisualInfo> list = visualizeMapper.getEquipPlans();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    public List<VisualInfo> getPlanTypes() {
        List<VisualInfo> list = visualizeMapper.getPlanTypes();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    public List<VisualInfo> getWorkerRank() {
        List<VisualInfo> list = visualizeMapper.getWorkerRank();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    @Override
    public List<VisualInfo> getEquipType() {
        List<VisualInfo> list = visualizeMapper.getEquipType();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }
}
