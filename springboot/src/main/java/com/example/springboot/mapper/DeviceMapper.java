package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Device;
import com.example.springboot.entity.Params;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    List<Device> getAll();

    /**
     * 条件查询
     *
     * @param params
     * @return
     */
    List<Device> findBySearch(Params params);


    /**
     * 添加
     *
     * @param device
     */
    void add(Device device);

    /**
     * 修改
     *
     * @param device
     */
    void update(Device device);
    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);
}
