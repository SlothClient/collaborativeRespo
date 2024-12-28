package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.CheckitemInfo;
import com.example.springboot.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-10-27
 */
public interface CheckitemInfoService extends IService<CheckitemInfo> {

    Result<List<CheckitemInfo>> getCheckitem(String checkId);
    Result<Integer> createCheckitem(List<CheckitemInfo> checkitemInfos);
    Result<Integer> updateCheckitem(List<CheckitemInfo> checkitemInfos);
    Result<Integer> deleteCheckitem(List<String> checkitemIds);
}
