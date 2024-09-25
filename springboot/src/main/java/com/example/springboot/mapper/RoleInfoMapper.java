package com.example.springboot.mapper;

import com.example.springboot.entity.RoleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【role_info】的数据库操作Mapper
* @createDate 2024-09-23 20:21:56
* @Entity com.example.springboot.entity.RoleInfo
*/

@Repository
@Mapper
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

    List<String> selectRoleListByUserId(@Param("userId") Object loginId);
}




