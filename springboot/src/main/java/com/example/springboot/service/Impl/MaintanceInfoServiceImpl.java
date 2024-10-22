package com.example.springboot.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.*;
import com.example.springboot.request.MaintenancePlanReq;
import com.example.springboot.response.MaintenanceInfo;
import com.example.springboot.response.PlanDetailResp;
import com.example.springboot.service.MaintanceInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.springboot.entity.EquipInfo;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.springboot.enums.maintenanceCodeEnum.*;

/**
 * @author Lenovo
 * @description 针对表【maintance_info】的数据库操作Service实现
 * @createDate 2024-09-24 11:04:20
 */
@Service
public class MaintanceInfoServiceImpl extends ServiceImpl<MaintanceInfoMapper, MaintanceInfoDetail>
        implements MaintanceInfoService {

    @Autowired
    private MaintanceInfoMapper maintenanceMapper;

    @Autowired
    private ApprovalInfoMapper approvalInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EquipInfoMapper equipInfoMapper;


    @Override
    public Result<IPage<MaintenanceInfo>> getMaintenancePlan(MaintenancePlanReq maintenancePlanReq) {

//        int total = maintenanceMapper.getMaintenancePlanCount(maintenancePlanReq);

        IPage<MaintenanceInfo> page = new Page<>(maintenancePlanReq.getCurrentPage(), maintenancePlanReq.getPageSize());


        // 如果查询结果为空，返回查询为空的提示
//        if (maintenanceRespList.isEmpty()) {
//            return Result.fail("查询为空");
//        }

        // 打印总数
//        System.out.println(total);

        // 构建返回响应对象
//        MaintenanceInfoResp maintenanceInfoResp = MaintenanceInfoResp.builder()
//                .maintenanceInfoList(maintenanceRespList)
//                .total(total) // 使用从第一个查询获取的总数
//                .build();

        IPage<MaintenanceInfo> maintenancePage = maintenanceMapper.getMaintenancePlan(page, maintenancePlanReq);

        return Result.success(maintenancePage);
    }


    @Override
    public Result addMaintenancePlan(MaintenanceInfo maintenanceInfo) {
        String userId = (String) StpUtil.getLoginId();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        maintenanceInfo.setCreator(userInfo.getUsername());

        //当前审批计划详情
        MaintanceInfoDetail maintanceInfoDetail = MaintanceInfoDetail
                .builder()
                .maintanceDesc(maintenanceInfo.getMaintanceDesc())
                .maintanceType(maintenanceInfo.getMaintanceType())
                .planName(maintenanceInfo.getPlanName())
                .startTime(maintenanceInfo.getStartTime())
                .endTime(maintenanceInfo.getEndTime())
                .equipId(maintenanceInfo.getEquipId())
                .status(PENDING_APPROVAL.getCode()) //设置整个审批状态为审批中
                .build();
        maintenanceMapper.insert(maintanceInfoDetail);

        //申请人
        ApprovalInfo approvalInfoApplicant = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .applicantId(userId)
                .approvalStatus(APPROVAL_PASS.getCode())//默认申请成功
                .stepOrder(0)
                .manipTime(maintenanceInfo.getCreateTime())
                .build();
        approvalInfoMapper.insert(approvalInfoApplicant);

        //一级审批人
        ApprovalInfo approvalInfoFirst = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .approvalStatus(PENDING_APPROVAL.getCode())//默认为待审批
                .stepOrder(1) //第一级
                .fatherId(approvalInfoApplicant.getApprovalId())
                .build();
        approvalInfoMapper.insert(approvalInfoFirst);

        ApprovalInfo approvalInfoSecond = ApprovalInfo
                .builder()
                .planId(maintanceInfoDetail.getPlanId())
                .approvalStatus(PENDING_APPROVAL.getCode()) //默认为待审批
                .stepOrder(2) //第二级
                .fatherId(approvalInfoFirst.getApprovalId())
                .build();
        approvalInfoMapper.insert(approvalInfoSecond);

        return Result.success("保养计划已成功添加，请等待审批");
    }

    @Override
    @Transactional
    public Result undoMaintenancePlan(String planId) {
        try {
            System.out.println("planId:" + planId);
            int approvalRows = approvalInfoMapper.delete(
                    new LambdaQueryWrapper<ApprovalInfo>()
                            .eq(ApprovalInfo::getPlanId, planId)
            );
            System.out.println("approvalRows:" + approvalRows);
            int maintenancePlanRows = maintenanceMapper.deleteById(planId);
            if (maintenancePlanRows <= 0 || approvalRows <= 0) {
                return Result.fail("撤销失败，请联系管理员处理");
            }
            return Result.success("该保养计划已成功撤销");
        } catch (Exception e) {
            System.err.println("Error undoing maintenance plan: " + e.getMessage());
            e.printStackTrace();
            return Result.fail("撤销失败，请联系管理员处理");
        }
    }
    @Override
    public Result getMaintenancePlanDetail(String planId) {

        PlanDetailResp detailResp = new PlanDetailResp();

        MaintanceInfoDetail maintanceInfoDetail = maintenanceMapper.selectById(planId);

        if (maintanceInfoDetail == null) {
            return Result.fail("该计划详情为空，请联系管理员检查");
        }


        List<ApprovalInfo> approvalInfoList = approvalInfoMapper.selectList(
                new LambdaQueryWrapper<ApprovalInfo>()
                        .eq(ApprovalInfo::getPlanId, planId)
                        .orderByAsc(ApprovalInfo::getStepOrder)
        );

        boolean hasUpdatedPerson = false;  // 标记更新者信息是否已设置

// 遍历审批流程，继续设置更新者信息
        for (ApprovalInfo approvalInfo : approvalInfoList) {
            System.out.println(approvalInfo);
            if (approvalInfo.getManipTime() != null) {
                UserInfo userInfo = userInfoMapper.selectById(approvalInfo.getApplicantId());
                System.out.println(userInfo);

                // 设置创建者信息
                if (approvalInfo.getStepOrder() == 0) {
                    detailResp.setCreator(userInfo.getUsername());
                    detailResp.setCreateTime(approvalInfo.getManipTime());
                }

                // 设置更新者信息，确保只更新一次
                if (approvalInfo.getStepOrder() != 0 && !hasUpdatedPerson) {
                    detailResp.setUpdatePerson(userInfo.getUsername());
                    detailResp.setUpdateTime(approvalInfo.getManipTime());
                    detailResp.setRemark(approvalInfo.getApprovalRemark());
                    hasUpdatedPerson = true;
                }
            } else {
                // 若当前 approvalInfo 还未审批，设置默认信息
                if (!hasUpdatedPerson) {
                    detailResp.setUpdatePerson("计划还未有人审批");
                    detailResp.setUpdateTime(null);
                    detailResp.setRemark("还未审批");
                }
                break;
            }
        }

        EquipInfo equipInfo = equipInfoMapper.selectById(maintanceInfoDetail.getEquipId());
        detailResp.setEquipName(equipInfo.getEquipName());
        detailResp.setEquipPic(equipInfo.getEquipPic());
        detailResp.setEquipId(equipInfo.getEquipId());
        detailResp.setPlanId(maintanceInfoDetail.getPlanId());
        detailResp.setPlanName(maintanceInfoDetail.getPlanName());
        detailResp.setMaintanceType(maintanceInfoDetail.getMaintanceType());
        detailResp.setMaintanceDesc(maintanceInfoDetail.getMaintanceDesc());
        detailResp.setStatus(maintanceInfoDetail.getStatus());
        detailResp.setStartTime(maintanceInfoDetail.getStartTime());
        detailResp.setEndTime(maintanceInfoDetail.getEndTime());
        System.out.println(detailResp);
        return Result.success(detailResp);
    }

    @Override
    public Result updateMaintenance(MaintanceInfoDetail maintanceInfoDetail) {

        MaintanceInfoDetail infoDetail = maintenanceMapper.selectById(maintanceInfoDetail.getPlanId());

        if (infoDetail == null) {
            return Result.fail("修改失败并未找到该计划，请联系后台管理员解决");
        }
        infoDetail.setStartTime(maintanceInfoDetail.getStartTime());
        infoDetail.setEndTime(maintanceInfoDetail.getEndTime());

        infoDetail.setMaintanceDesc(maintanceInfoDetail.getMaintanceDesc());
        infoDetail.setMaintanceType(maintanceInfoDetail.getMaintanceType());

        infoDetail.setEquipId(maintanceInfoDetail.getEquipId());
        infoDetail.setPlanName(maintanceInfoDetail.getPlanName());

        maintenanceMapper.updateById(infoDetail);

        return Result.success("保养计划修改成功");


    }


    @Scheduled(cron = "0 0 0 * * ?") // 每天午夜执行
    public void dynamicMaintenancePlanCreate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(new Date());
        System.out.println("定时任务执行: " + currentTime);

        // 查询所有设备
        List<EquipInfo> equipInfoList = equipInfoMapper.selectList(
                new LambdaQueryWrapper<EquipInfo>()
                        .select(EquipInfo::getEquipId,
                                EquipInfo::getFixBig,
                                EquipInfo::getFixMedium,
                                EquipInfo::getFixSmall,
                                EquipInfo::getEquipName,
                                EquipInfo::getPurchaseDate)
        );

        // 遍历所有设备并生成保养计划
        for (EquipInfo equipInfo : equipInfoList) {
            int daysSincePurchase = getDaysSincePurchase(equipInfo.getPurchaseDate());

            String maintenanceType = null;
            int maintenanceCycle = 0;

            // 判断是否需要大修、中修或小修
            if (daysSincePurchase % equipInfo.getFixBig() == 0) {
                maintenanceCycle = equipInfo.getFixBig();
                maintenanceType = "大修周期";
            } else if (daysSincePurchase % equipInfo.getFixMedium() == 0) {
                maintenanceCycle = equipInfo.getFixMedium();
                maintenanceType = "中修周期";
            } else if (daysSincePurchase % equipInfo.getFixSmall() == 0) {
                maintenanceCycle = equipInfo.getFixSmall();
                maintenanceType = "小修周期";
            }

            // 如果需要维护，则生成计划并插入数据库
            if (maintenanceCycle > 0) {

                MaintanceInfoDetail maintenanceInfo = MaintanceInfoDetail
                        .builder()
                        .equipId(equipInfo.getEquipId())
                        .planName("定期维护-" + maintenanceType)
                        .maintanceType(maintenanceType)
                        .status(PENDING_APPROVAL.getCode())
                        .maintanceDesc("保养计划" + equipInfo.getEquipName() + maintenanceType)
                        .startTime(new Date())
                        .endTime(getDateAfterDays(7))
                        .build();

                // 插入保养计划
                maintenanceMapper.insert(maintenanceInfo);

                ApprovalInfo applicantApproval = ApprovalInfo
                        .builder()
                        .manipTime(new Date())
                        .planId(maintenanceInfo.getPlanId())
                        .applicantId("U003")
                        .approvalStatus(APPROVAL_PASS.getCode())
                        .stepOrder(0)
                        .build();

                // 插入审批流程
                approvalInfoMapper.insert(applicantApproval);

                ApprovalInfo approvalInfoFirst = ApprovalInfo
                        .builder()
                        .planId(maintenanceInfo.getPlanId())
                        .approvalStatus(PENDING_APPROVAL.getCode())//默认为待审批
                        .stepOrder(1) //第一级
                        .fatherId(applicantApproval.getApprovalId())
                        .build();
                approvalInfoMapper.insert(approvalInfoFirst);

                ApprovalInfo approvalInfoSecond = ApprovalInfo
                        .builder()
                        .planId(maintenanceInfo.getPlanId())
                        .approvalStatus(PENDING_APPROVAL.getCode()) //默认为待审批
                        .stepOrder(2) //第二级
                        .fatherId(approvalInfoFirst.getApprovalId())
                        .build();
                approvalInfoMapper.insert(approvalInfoSecond);
            }
        }
    }

    // 计算设备购买至今的天数
    private int getDaysSincePurchase(LocalDateTime purchaseDate) {
        // 获取当前时间的 LocalDateTime 对象
        LocalDateTime now = LocalDateTime.now();
        // 计算两个日期之间的天数差
        long diffInDays = ChronoUnit.DAYS.between(purchaseDate, now);
        return (int) diffInDays;
    }

    // 获取当前日期的n天后日期
    private Date getDateAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

}




