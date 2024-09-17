package com.jxutcm.equipmentMaintance.Service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxutcm.equipmentMaintance.Bean.Condition;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import com.jxutcm.equipmentMaintance.Bean.MaintanceInfo;
import com.jxutcm.equipmentMaintance.Dao.MaintanceDao;
import com.jxutcm.equipmentMaintance.Service.MaintanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MaintanceServiceImpl implements MaintanceService {

    @Autowired
    private MaintanceDao maintanceDao;
    @Override
    public List<MaintanceInfo> getMaintance(String conditionJson) throws JsonProcessingException {
        Condition condition = new ObjectMapper().readValue(conditionJson,Condition.class);
        List<MaintanceInfo> list = maintanceDao.getMaintance(condition);
        if(list.size() == 0 || list == null){
            throw new RuntimeException("没有找到数据！");
        }
        return list;
    }

    @Override
    public void delDocInfo(String dID, String uName) {

    }

    @Override
    public void updDocInfo(String docInfoJson, String uName) throws JsonProcessingException {

    }

    @Override
    public void addMaintanceInfo(String maintanceInfoJson, String uname) throws JsonProcessingException {
        MaintanceInfo maintanceInfo = new ObjectMapper().readValue(maintanceInfoJson,MaintanceInfo.class);
        String pid = UUID.randomUUID().toString();
        String eid = UUID.randomUUID().toString();
        maintanceInfo.set计划编号(pid);
        maintanceInfo.set设备编号(eid);
        int count = maintanceDao.addMaintanceInfo(maintanceInfo);
        if(count==0){
            throw new RuntimeException("新增计划失败！");
        }
    }
//
//    @Override
//    public void delDocInfo(String dID, String uName) {
//        if(dID.equals("")){
//            throw new RuntimeException("主键错误，删除行不存在！");
//        }
//        DocInfo docInfo = new DocInfo();
//        docInfo.setManipUser(uName);
//        Date delTime = new Date(new java.util.Date().getTime());
//        docInfo.setDelTime(delTime);
//        docInfo.setdID(dID);
//        int count = docDao.delDocInfo(docInfo);
//        if(count==0){
//            throw new RuntimeException("删除失败，联系管理员维护！");
//        }
//    }
//
//    @Override
//    public void updDocInfo(String docInfoJson, String uName) throws JsonProcessingException {
//        DocInfo docInfo = new ObjectMapper().readValue(docInfoJson,DocInfo.class);
//        docInfo.setManipUser(uName);
//        Date updTime = new Date(new java.util.Date().getTime());
//        docInfo.setUpdTime(updTime);
//        int count = docDao.updDocInfo(docInfo);
//        if(count==0){
//            throw new RuntimeException("修改检查项失败！");
//        }
//    }
}
