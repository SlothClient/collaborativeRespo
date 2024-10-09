package com.example.springboot.mapper;

import com.example.springboot.entity.WorkerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author Lenovo
* @description 针对表【worker_info】的数据库操作Mapper
* @createDate 2024-10-07 17:24:27
* @Entity com.example.springboot.entity.WorkerInfo
*/

@Repository
@Mapper
public interface WorkerInfoMapper extends BaseMapper<WorkerInfo> {

}




