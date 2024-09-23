package com.jxutcm.equipmentMaintance.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jxutcm.equipmentMaintance.Bean.EmployeeInfo;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeInfo> getEmployees();
}
