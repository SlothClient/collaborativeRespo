package com.example.springboot.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.CheckitemInfo;
import com.example.springboot.mapper.CheckitemInfoMapper;
import com.example.springboot.service.CheckitemInfoService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-27
 */
@Service
public class CheckitemInfoServiceImpl extends ServiceImpl<CheckitemInfoMapper, CheckitemInfo> implements CheckitemInfoService {

    @Autowired
    private CheckitemInfoMapper checkitemInfoMapper;

    @Override
    public Result<List<CheckitemInfo>> getCheckitem(String checkId) {
        if (checkId == null || checkId.isEmpty()) {
            return Result.fail("checkId不能为空");
        }

        LambdaQueryWrapper<CheckitemInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CheckitemInfo::getCheckId, checkId);

        List<CheckitemInfo> list = checkitemInfoMapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @Override
    @Transactional
    public Result<Integer> createCheckitem(List<CheckitemInfo> checkitemInfos) {
        int count = 0;
        for (CheckitemInfo info : checkitemInfos) {
            count += save(info) ? 1 : 0;
        }
        return Result.success(count);
    }

    @Override
    @Transactional
    public Result<Integer> updateCheckitem(List<CheckitemInfo> checkitemInfos) {
        int count = 0;
        for (CheckitemInfo info : checkitemInfos) {
            count += updateById(info) ? 1 : 0;
        }
        return Result.success(count);
    }

    @Override
    @Transactional
    public Result<Integer> deleteCheckitem(List<String> checkitemIds) {
        int count = 0;
        for (String id : checkitemIds) {
            count += removeById(id) ? 1 : 0;
        }
        return Result.success(count);
    }
}
