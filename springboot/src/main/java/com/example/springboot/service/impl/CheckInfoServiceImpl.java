package com.example.springboot.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.ApprovalInfo;
import com.example.springboot.entity.CheckInfoDetail;
import com.example.springboot.entity.EquipInfo;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.ApprovalInfoMapper;
import com.example.springboot.mapper.CheckInfoMapper;
import com.example.springboot.mapper.EquipInfoMapper;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.request.CheckInfoReq;
import com.example.springboot.response.CheckDetailResp;
import com.example.springboot.response.CheckInfo;
import com.example.springboot.response.CheckInfoResp;
import com.example.springboot.service.CheckInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.springboot.enums.maintenanceCodeEnum.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-13
 */
@Service
public class CheckInfoServiceImpl extends ServiceImpl<CheckInfoMapper, CheckInfoDetail> implements CheckInfoService {

    @Autowired
    private CheckInfoMapper checkInfoMapper;

    @Autowired
    private ApprovalInfoMapper approvalInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EquipInfoMapper equipInfoMapper;

    @Override
    public Result<IPage<CheckInfo>> getCheckInfo(CheckInfoReq checkInfoReq) {
        IPage<CheckInfo> page = new Page<>(checkInfoReq.getCurrentPage(), checkInfoReq.getPageSize());
        IPage<CheckInfo> checkInfoIPage = checkInfoMapper.getCheckInfo(page,checkInfoReq);
        return Result.success(checkInfoIPage);
    }

    @Override
    public Result addCheckInfo(CheckInfo checkInfo) {
        String userId = (String) StpUtil.getLoginId();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        checkInfo.setCreator(userInfo.getUsername());

        //当前审批计划详情
        CheckInfoDetail checkInfoDetail = CheckInfoDetail
                .builder()
                .checkDesc(checkInfo.getCheckDesc())
                .checkSite(checkInfo.getCheckSite())
                .checkContent(checkInfo.getCheckContent())
                .checkName(checkInfo.getCheckName())
                .startTime(checkInfo.getStartTime())
                .endTime(checkInfo.getEndTime())
                .status(PLAN_PENDING.getCode())
                .equipId(checkInfo.getEquipId())
                .build();
        checkInfoMapper.insert(checkInfoDetail);
        return Result.success("点巡检计划已成功添加");
    }

    @Override
    @Transactional
    public Result undoCheckInfo(String checkId) {
        try {
            System.out.println("checkId: " + checkId);
            int checkRows = checkInfoMapper.deleteById(checkId);
            if (checkRows <= 0) {
                return Result.fail("撤销失败，请联系管理员处理");
            }
            return Result.success("该点巡检计划已成功撤销");
        } catch (Exception e) {
            System.err.println("Error undoing check info: " + e.getMessage());
            e.printStackTrace();
            return Result.fail("撤销失败，请联系管理员处理");
        }
    }

    @Override
    public Result getCheckInfoDetail(String checkId) {
        CheckDetailResp detailResp = new CheckDetailResp();
        CheckInfoDetail checkInfoDetail = checkInfoMapper.selectById(checkId);
        if(checkInfoDetail == null){
            return Result.fail("该计划详情为空，请联系管理员检查");
        }
        EquipInfo equipInfo = equipInfoMapper.selectById(checkInfoDetail.getEquipId());
        detailResp.setEquipName(equipInfo.getEquipName());
        detailResp.setEquipId(equipInfo.getEquipId());
        detailResp.setCheckId(checkInfoDetail.getCheckId());
        detailResp.setCheckName(checkInfoDetail.getCheckName());
        detailResp.setCheckDesc(checkInfoDetail.getCheckDesc());
        detailResp.setCheckSite(checkInfoDetail.getCheckSite());
        detailResp.setCheckContent(checkInfoDetail.getCheckContent());
        detailResp.setStatus(checkInfoDetail.getStatus());
        detailResp.setStartTime(checkInfoDetail.getStartTime());
        detailResp.setEndTime(checkInfoDetail.getEndTime());
        System.out.println(detailResp);
        return Result.success(detailResp);
    }

    @Override
    public Result updateCheck(CheckInfoDetail checkInfoDetail) {
        CheckInfoDetail infoDetail = checkInfoMapper.selectById(checkInfoDetail.getCheckId());
        if (infoDetail == null) {
            return Result.fail("修改失败并未找到该计划，请联系后台管理员解决");
        }
        infoDetail.setCheckName(checkInfoDetail.getCheckName());
        infoDetail.setCheckDesc(checkInfoDetail.getCheckDesc());
        infoDetail.setCheckSite(checkInfoDetail.getCheckSite());
        infoDetail.setCheckContent(checkInfoDetail.getCheckContent());
        infoDetail.setStartTime(checkInfoDetail.getStartTime());
        infoDetail.setEndTime(checkInfoDetail.getEndTime());
        infoDetail.setEquipId(checkInfoDetail.getEquipId());
        checkInfoMapper.updateById(infoDetail);
        return Result.success("点巡检计划修改成功");
    }
}


