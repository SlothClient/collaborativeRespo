package com.example.springboot.service;

import com.example.springboot.entity.CheckitemEquip;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
public interface CheckitemEquipService extends IService<CheckitemEquip> {

    Result<List<CheckitemEquip>> getCheckitem(String equipId);
}
