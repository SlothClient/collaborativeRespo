package com.example.springboot.service;

import com.example.springboot.entity.VisualInfo;

import java.util.List;

public interface VisualizeService {
    public List<VisualInfo> getBigNums();
    public List<VisualInfo> getEquipPlans();
    public List<VisualInfo> getPlanTypes();
    public List<VisualInfo> getWorkerRank();

    public  List<VisualInfo> getEquipType();
}
