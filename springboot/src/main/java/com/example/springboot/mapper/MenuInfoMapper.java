package com.example.springboot.mapper;

import com.example.springboot.entity.MenuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【menu_info】的数据库操作Mapper
* @createDate 2024-09-23 20:23:17
* @Entity com.example.springboot.entity.MenuInfo
*/

@Mapper
@Repository
public interface MenuInfoMapper extends BaseMapper<MenuInfo> {

    List<String> selectPermissionByRoleId(@Param("roleId") String roleId);
}




