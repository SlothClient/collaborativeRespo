package com.jxutcm.equipmentMaintance.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;

import java.util.List;

public interface MaintanceService {
    public List<MaintanceInfo> getMaintance(String conditionJson) throws JsonProcessingException;
    public void addMaintanceInfo(String maintanceInfoJson,String uname) throws JsonProcessingException;
    public void delDocInfo(String dID,String uName);
    public void updDocInfo(String docInfoJson,String uName) throws JsonProcessingException;
}
