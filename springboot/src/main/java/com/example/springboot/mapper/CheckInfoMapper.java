package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.CheckInfoDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.request.CheckInfoReq;
import com.example.springboot.response.CheckInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-13
 */
@Mapper
public interface CheckInfoMapper extends BaseMapper<CheckInfoDetail> {

    IPage<CheckInfo> getCheckInfo(IPage<CheckInfo> page,@Param("checkInfoReq") CheckInfoReq checkInfoReq);
}
