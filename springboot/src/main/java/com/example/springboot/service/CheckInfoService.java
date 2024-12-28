package com.example.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.CheckInfoDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.request.CheckInfoReq;
import com.example.springboot.response.CheckInfo;
import com.example.springboot.response.CheckInfoResp;
import com.example.springboot.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-10-13
 */
public interface CheckInfoService extends IService<CheckInfoDetail> {

    Result<IPage<CheckInfo>> getCheckInfo(CheckInfoReq checkInfoReq);

    Result addCheckInfo(CheckInfo checkInfo);

    Result undoCheckInfo(String checkId);

    Result getCheckInfoDetail(String checkId);

    Result updateCheck(CheckInfoDetail checkInfoDetail);
}
