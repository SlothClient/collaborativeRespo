package com.example.springboot.mapper;

import com.example.springboot.entity.RoleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-17
 */

@Mapper
@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    List<String> selectRoleListByUserId(@Param("userId") Object loginId);
}
