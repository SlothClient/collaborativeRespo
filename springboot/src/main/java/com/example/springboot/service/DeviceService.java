package com.example.springboot.service;

import com.example.springboot.entity.Device;
import com.example.springboot.entity.Params;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {
    /**
     * 查询所有
     * @return
     */
    List<Device> getAll() ;

    /**
     * 条件查询
     * @param params
     * @return
     */
    PageInfo<Device> search(Params params);

    /**
     * 添加
     * @param device
     */
    void add(Device device);

    /**
     * 修改
     * @param device
     */
    void update(Device device);

    /**
     * 删除
     * @param
     */
    void delete(Integer id);
}
