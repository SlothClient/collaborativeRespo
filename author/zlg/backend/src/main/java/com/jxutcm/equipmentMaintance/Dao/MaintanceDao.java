package com.jxutcm.equipmentMaintance.Dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jxutcm.equipmentMaintance.Bean.Condition;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintanceDao {
    public List<MaintanceInfo> getMaintance(Condition condition);
    public int addMaintanceInfo(MaintanceInfo maintanceInfo);
    public int delDocInfo(MaintanceInfo docInfo);
    public int updDocInfo(MaintanceInfo docInfo);
}
