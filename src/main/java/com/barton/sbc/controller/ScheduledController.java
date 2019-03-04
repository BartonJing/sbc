package com.barton.sbc.controller;

import cn.hutool.core.util.RandomUtil;
import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.ScheduleConfig;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.ScheduledService;
import com.barton.sbc.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author barton
 * 定时 Controller
 */
@RestController
@RequestMapping("/config/scheduled/")
public class ScheduledController {
    @Autowired
    private ScheduledService scheduledService;

    private static AuthUser currentUser;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        currentUser = CurrentUserUtil.getAuthUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }
    /**
     * 保存定时任务配置信息
     * @param scheduleConfig
     * @return 操作结果
     */
    @PostMapping(value = "/save")
    @Transactional
    public ServerResponse save(ScheduleConfig scheduleConfig) {
        if(scheduledService.selectByClass(scheduleConfig.getScheduleClass()) != null){
            scheduleConfig.setId(RandomUtil.randomUUID());
            scheduleConfig.setGmtCreate(new Date());
            scheduleConfig.setUserCreate(currentUser.getId());
            scheduleConfig.setGmtModified(new Date());
            scheduleConfig.setUserModified(currentUser.getId());
            scheduledService.insert(scheduleConfig);
        }else{
            scheduleConfig.setGmtModified(new Date());
            scheduleConfig.setUserModified(currentUser.getId());
            scheduledService.update(scheduleConfig);
        }
        //修改 定时任务
        scheduledService.scheduleChange(scheduleConfig);
        return ServerResponse.createBySuccessMessage("保存成功！");
    }

    /**
     * 删除定时任务
     * @param id
     * @return ServerResponse
     */
    @GetMapping(value = "/delete")
    @Transactional
    public ServerResponse delete(String id) {
        ScheduleConfig  config = scheduledService.selectByClass(id);
        config.setScheduleStatus(ScheduleConfig.STOP);
        //修改 定时任务
        scheduledService.scheduleChange(config);
        scheduledService.deleteById(id);
        return ServerResponse.createBySuccessMessage("删除成功！");
    }

    /**
     * 查询
     * @return List<ScheduleConfig>
     */
    @GetMapping(value = "/selectAll")
    public List<ScheduleConfig> selectAll() {
        return scheduledService.selectAll();
    }
}
