package com.barton.sbc.service;

import com.barton.sbc.domain.entity.ScheduleConfig;

import java.util.List;

public interface ScheduledService {
    int insert(ScheduleConfig config);

    int update(ScheduleConfig config);

    List<ScheduleConfig> selectAll();

    void scheduleChange(List<ScheduleConfig> configList);
}
