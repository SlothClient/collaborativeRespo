package com.example.springboot.mapper;

import com.example.springboot.entity.VisualInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisualizeMapper {
    public List<VisualInfo> getBigNums();
    public List<VisualInfo> getEquipPlans();
    public List<VisualInfo> getPlanTypes();
    public List<VisualInfo> getWorkerRank();

    List<VisualInfo> getEquipType();
}
