package com.jxutcm.equipmentMaintance.Service.ServiceImpl;

import com.jxutcm.equipmentMaintance.Bean.EmployeeInfo;
import com.jxutcm.equipmentMaintance.Dao.EmployeeDao;
import com.jxutcm.equipmentMaintance.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public List<EmployeeInfo> getEmployees() {
        List<EmployeeInfo> list = employeeDao.getEmployees();
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到员工数据！");
        }
        return list;
    }
}
