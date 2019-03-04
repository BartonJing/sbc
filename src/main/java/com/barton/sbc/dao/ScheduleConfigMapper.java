package com.barton.sbc.dao;

import com.barton.sbc.domain.entity.ScheduleConfig;

import java.util.List;

public interface ScheduleConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(ScheduleConfig record);

    int insertSelective(ScheduleConfig record);

    ScheduleConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ScheduleConfig record);

    int updateByPrimaryKey(ScheduleConfig record);

    List<ScheduleConfig> selectAll();
}