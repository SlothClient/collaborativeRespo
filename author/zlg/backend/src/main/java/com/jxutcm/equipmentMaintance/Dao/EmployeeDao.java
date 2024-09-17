package com.jxutcm.equipmentMaintance.Dao;

import com.jxutcm.equipmentMaintance.Bean.Condition;
import com.jxutcm.equipmentMaintance.Bean.EmployeeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {
    public List<EmployeeInfo> getEmployees();
}
