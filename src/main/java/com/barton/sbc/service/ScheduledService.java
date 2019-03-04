package com.barton.sbc.service;

import com.barton.sbc.domain.entity.ScheduleConfig;

import java.util.List;

public interface ScheduledService {
    int insert(ScheduleConfig config);

    int update(ScheduleConfig config);

    List<ScheduleConfig> selectAll();
    /**
     * 修改定时任务批量 批量
     * @param configList
     */
    void scheduleChange(List<ScheduleConfig> configList);
    /**
     * 修改定时任务批量 单个
     * @param config
     */
    void scheduleChange(ScheduleConfig config);
}
