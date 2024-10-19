package com.example.springboot.mapper;

import com.example.springboot.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
