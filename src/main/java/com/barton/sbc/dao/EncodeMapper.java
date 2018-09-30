package com.barton.sbc.dao;

import com.barton.sbc.domain.entity.Encode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EncodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Encode record);

    int insertSelective(Encode record);

    Encode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Encode record);

    int updateByPrimaryKey(Encode record);

    List<Encode> selectByKind(String kind);

    List<Encode> selectByKinds(@Param("list") List<String> list);
}