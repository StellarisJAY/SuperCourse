package com.jay.scourse.controller;


import com.jay.scourse.entity.User;
import com.jay.scourse.entity.WatchRecord;
import com.jay.scourse.service.IWatchRecordService;
import com.jay.scourse.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-27
 */
@RestController
@RequestMapping("/watch-record")
public class WatchRecordController {

    private final IWatchRecordService watchRecordService;

    @Autowired
    public WatchRecordController(IWatchRecordService watchRecordService) {
        this.watchRecordService = watchRecordService;
    }

    @PostMapping("/save")
    public CommonResult saveWatchRecord(User user, @RequestBody WatchRecord watchRecord){
        return watchRecordService.addWatchRecord(user, watchRecord);
    }
}
