package com.barton.sbc.service.impl;

import com.barton.sbc.config.ScheduledConfig;
import com.barton.sbc.dao.ScheduleConfigMapper;
import com.barton.sbc.domain.entity.ScheduleConfig;
import com.barton.sbc.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author barton
 */
@Service
public class ScheduledServiceImpl implements ScheduledService,ApplicationRunner {
    @Autowired
    private ScheduleConfigMapper scheduleConfigMapper;

    private static final String START = "1";

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public int insert(ScheduleConfig config) {
        return scheduleConfigMapper.insertSelective(config);
    }

    @Override
    public int update(ScheduleConfig config) {
        return scheduleConfigMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public List<ScheduleConfig> selectAll() {
        return scheduleConfigMapper.selectAll();
    }

    /**
     * 修改定时任务批量
     * @param configList
     */
    @Override
    public void scheduleChange(List<ScheduleConfig> configList) {
        try {
            //遍历所有库中动态数据，根据库中class取出所属的定时任务对象进行关闭，每次都会把之前所有的定时任务都关闭，根据新的状态重新启用一次，达到最新配置
            for (ScheduleConfig config : configList){
                ScheduledFuture<?> scheduledFuture = ScheduledConfig.scheduleMap.get(config.getScheduleClass());
                if (scheduledFuture != null){
                    scheduledFuture.cancel(true);
                }
            }
            //因为下边存储的是新的定时任务对象，以前的定时任务对象已经都停用了，所以旧的数据没用清除掉
            ScheduledConfig.scheduleMap.clear();
            //遍历库中数据，之前已经把之前所有的定时任务都停用了，现在判断库中如果是启用的重新启用并读取新的数据，把开启的数据对象保存到定时任务对象中以便下次停用
            for (ScheduleConfig config : configList){
                //判断当前定时任务是否有效
                if (START.equals(config.getScheduleStatus())) {
                    //开启一个新的任务，库中存储的是全类名（包名加类名）通过反射成java类，读取新的时间
                    ScheduledFuture<?> future = threadPoolTaskScheduler.schedule((Runnable) Class.forName(config.getScheduleClass()).newInstance(), new CronTrigger(config.getScheduleCron()));
                    //这一步非常重要，之前直接停用，只停用掉了最后启动的定时任务，前边启用的都没办法停止，所以把每次的对象存到map中可以根据key停用自己想要停用的
                    ScheduledConfig.scheduleMap.put(config.getScheduleClass(),future);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改定时任务批量 单个
     * @param config
     */
    @Override
    public void scheduleChange(ScheduleConfig config) {
        try{
            ScheduledFuture<?> scheduledFuture = ScheduledConfig.scheduleMap.get(config.getScheduleClass());
            if (scheduledFuture != null){
                scheduledFuture.cancel(true);
            }
            //判断当前定时任务是否有效
            if (START.equals(config.getScheduleStatus())) {
                //开启一个新的任务，库中存储的是全类名（包名加类名）通过反射成java类，读取新的时间
                ScheduledFuture<?> future = threadPoolTaskScheduler.schedule((Runnable) Class.forName(config.getScheduleClass()).newInstance(), new CronTrigger(config.getScheduleCron()));
                //这一步非常重要，之前直接停用，只停用掉了最后启动的定时任务，前边启用的都没办法停止，所以把每次的对象存到map中可以根据key停用自己想要停用的
                ScheduledConfig.scheduleMap.put(config.getScheduleClass(),future);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduleChange(selectAll());
    }
}
