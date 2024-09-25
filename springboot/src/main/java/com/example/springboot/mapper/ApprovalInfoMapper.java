package com.example.springboot.mapper;

import com.example.springboot.entity.ApprovalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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

}




