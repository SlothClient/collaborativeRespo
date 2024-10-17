package com.example.springboot.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.MaintanceType;
import com.example.springboot.entity.Params;
import com.example.springboot.mapper.EquipInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.MaintanceTypeMapper;
import com.example.springboot.request.EquipInfoReq;
import com.example.springboot.response.EquipmentResp;
import com.example.springboot.service.EquipInfoService;
import com.example.springboot.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-08
 */
@Service
public class EquipInfoServiceImpl extends ServiceImpl<EquipInfoMapper, EquipInfo> implements EquipInfoService {

    @Autowired
    private EquipInfoMapper equipInfoMapper;

    @Autowired
    private MaintanceTypeMapper maintanceTypeMapper;

    @Override
    public Result<List<EquipmentResp>> getEquipmentInfo() {
        List<EquipInfo> equipInfoList = equipInfoMapper.selectList(
                new LambdaQueryWrapper<EquipInfo>()
                        .select(EquipInfo::getEquipId, EquipInfo::getEquipName)
        );

        if (equipInfoList.isEmpty()) {
            return Result.fail("查询到的设备为空");
        }
        // 将查询结果映射到 EquipmentResp 对象
        List<EquipmentResp> responseList = equipInfoList.stream()
                .map(equipInfo ->
                        EquipmentResp
                                .builder()
                                .equipName(equipInfo.getEquipName())
                                .equipId(equipInfo.getEquipId()).
                                build())
                .collect(Collectors.toList());
        return Result.success(responseList);
    }

    @Override
    public Result<List<MaintanceType>> getEquipmentMaintenanceType() {
        List<MaintanceType> maintanceTypeList = maintanceTypeMapper.selectList(
                new LambdaQueryWrapper<MaintanceType>()
                        .select(MaintanceType::getTypeId,MaintanceType::getMaintanceName)
        );

        if(maintanceTypeList.isEmpty()){
            return Result.fail("类型为空，请稍后重试");
        }
        return Result.success(maintanceTypeList);
    }

    @Override
    public Result<IPage<EquipInfo>> getEquipInfoList(EquipInfoReq equipInfoReq) {
        IPage<EquipInfo> page = new Page<>(equipInfoReq.getCurrentPage(), equipInfoReq.getPageSize());
        LambdaQueryWrapper<EquipInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(equipInfoReq.getEquipName()!=null) {
            lambdaQueryWrapper.like(EquipInfo::getEquipName, equipInfoReq.getEquipName());
            lambdaQueryWrapper.like(EquipInfo::getDepartmentName,equipInfoReq.getDepartmentName());
            lambdaQueryWrapper.like(EquipInfo::getTypeName,equipInfoReq.getTypeName());
        }
        List<EquipInfo> list = equipInfoMapper.selectList(lambdaQueryWrapper);
        IPage<EquipInfo> equipInfoIPage = equipInfoMapper.selectPage(page, lambdaQueryWrapper);
        return Result.success(equipInfoIPage);
    }

    @Override
    public Result updateEquipInfo(EquipInfo equipInfo) {
       EquipInfo info = equipInfoMapper.selectById(equipInfo.getEquipId());
       if(info ==null) {
           return Result.fail("修改失败并未找到该计划，请联系后台管理员解决");
       }
        info.setEquipId(equipInfo.getEquipId());
        info.setEquipName(equipInfo.getEquipName());
        info.setEquipPic(equipInfo.getEquipPic());
        info.setStatusName(equipInfo.getStatusName());
        info.setCountBig(equipInfo.getCountBig());
        info.setCountMedium(equipInfo.getCountMedium());
        info.setCountSmall(equipInfo.getCountSmall());
        info.setFixBig(equipInfo.getFixBig());
        info.setFixMedium(equipInfo.getFixMedium());
        info.setFixSmall(equipInfo.getFixSmall());
        info.setRemarks(equipInfo.getRemarks());
        info.setLastMaintance(equipInfo.getLastMaintance());
        info.setPurchaseDate(equipInfo.getPurchaseDate());
        info.setValidTime(equipInfo.getValidTime());
        info.setDepartmentName(equipInfo.getDepartmentName());
        info.setTypeName(equipInfo.getTypeName());
        info.setSiteName(equipInfo.getSiteName());
        equipInfoMapper.updateById(info);
        return Result.success("保养计划修改成功");
    }

    @Override
    public Result addEquipInfo(EquipInfo equipInfo) {
        equipInfoMapper.insert(equipInfo);

        return Result.success("设备信息添加成功");
    }

    @Override
    public Result<String> deleteEquipInfo(String equipId) {
        int row = equipInfoMapper.delete(
                new LambdaQueryWrapper<EquipInfo>()
                        .eq(EquipInfo::getEquipId, equipId)
        );
        if(row<0){
            return Result.fail("删除失败");
        }else{
            return Result.success("删除成功");
        }
    }

//    @Override
//    public PageInfo<EquipInfo> getEquipInfoList(EquipInfoReq equipInfoReq) {
//        LambdaQueryWrapper<EquipInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if(equipInfoReq.getEquipName()!=null){
//            lambdaQueryWrapper.like(EquipInfo::getEquipName,equipInfoReq.getEquipName());
//        }
//        //处理分页
//        Integer currentPage = equipInfoReq.getCurrentPage() != null ? equipInfoReq.getCurrentPage() : 1;
//        Integer pageSize = equipInfoReq.getPageSize() != null ? equipInfoReq.getPageSize() : 10;
//        PageHelper.startPage(currentPage, pageSize);
//        List<EquipInfo> list = equipInfoMapper.selectList(lambdaQueryWrapper);
//        PageInfo<EquipInfo> pageInfo = new PageInfo<>(list);
//        return pageInfo;
//    }



}
