package com.example.springboot.controller;


import com.example.springboot.entity.Site;
import com.example.springboot.service.SiteService;
import com.example.springboot.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping("getSite")
    public Result<List<Site>> getSite() {
        List<Site> list = siteService.list();
        return Result.success(list);
    }

}
