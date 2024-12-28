package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.ApprovalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.request.FiltersReq;
import com.example.springboot.response.ApprovalDetailResp;
import com.example.springboot.response.ApprovalResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Lenovo
 * @description 针对表【approval_info】的数据库操作Mapper
 * @createDate 2024-09-24 11:16:44
 * @Entity com.example.springboot.entity.ApprovalInfo
 */

@Mapper
@Repository
public interface ApprovalInfoMapper extends BaseMapper<ApprovalInfo> {

    IPage<ApprovalResp> selectApprovalRespPage(
            Page<ApprovalResp> page,
            @Param("filters") FiltersReq filtersReq,
            @Param("stepOrder") Integer stepOrder
    );

    ApprovalDetailResp getApprovalDetail(@Param("planId") String planID);
}




