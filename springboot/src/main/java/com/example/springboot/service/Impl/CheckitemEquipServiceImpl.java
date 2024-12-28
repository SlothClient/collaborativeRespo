package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.CheckitemEquip;
import com.example.springboot.entity.CheckitemInfo;
import com.example.springboot.mapper.CheckitemEquipMapper;
import com.example.springboot.service.CheckitemEquipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@Service
public class CheckitemEquipServiceImpl extends ServiceImpl<CheckitemEquipMapper, CheckitemEquip> implements CheckitemEquipService {

    @Autowired
    private CheckitemEquipMapper checkitemEquipMapper;

    @Override
    public Result<List<CheckitemEquip>> getCheckitem(String equipId) {
        if (equipId == null || equipId.isEmpty()) {
            return Result.fail("equipId不能为空");
        }

        LambdaQueryWrapper<CheckitemEquip> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CheckitemEquip::getEquipId, equipId);

        List<CheckitemEquip> list = checkitemEquipMapper.selectList(queryWrapper);
        return Result.success(list);
    }
}
