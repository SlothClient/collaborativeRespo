package com.example.springboot.service.Impl;

import com.example.springboot.entity.Device;
import com.example.springboot.entity.Params;
import com.example.springboot.mapper.DeviceMapper;
import com.example.springboot.service.DeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询所有医生
     * @return
     */
    @Override
    public List<Device> getAll() {return deviceMapper.getAll();}
    //条件查询
    @Override
    public PageInfo<Device> search(Params params) {
        //处理分页
        Integer currentPage = params.getCurrentPage();
        Integer pageSize = params.getPageSize();
        PageHelper.startPage(currentPage, pageSize);
        List<Device> list = deviceMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    //添加
    @Override
    public void add(Device device) {
        //设置默认密码
        device.setDeviceName("设备");
        deviceMapper.add(device);
    }
    //修改
    @Override
    public void update(Device device) {
        deviceMapper.update(device);
    }

    //删除
    @Override
    public void delete(Integer id) {
          deviceMapper.delete(id);
    }

}
