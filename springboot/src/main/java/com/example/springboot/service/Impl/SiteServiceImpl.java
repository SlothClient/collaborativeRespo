package com.example.springboot.service.impl;

import com.example.springboot.entity.Site;
import com.example.springboot.mapper.SiteMapper;
import com.example.springboot.service.SiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-15
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {

}
