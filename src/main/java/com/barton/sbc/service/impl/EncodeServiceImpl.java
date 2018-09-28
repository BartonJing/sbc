package com.barton.sbc.service.impl;

import com.barton.sbc.dao.EncodeMapper;
import com.barton.sbc.domain.entity.Encode;
import com.barton.sbc.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EncodeServiceImpl implements EncodeService {
    @Autowired
    private EncodeMapper encodeMapper;
    @Override
    public List<Encode> selectByKind(String kind) {
        return encodeMapper.selectByKind(kind);
    }

    @Override
    public List<Encode> selectByKinds(List<String> list) {
        return encodeMapper.selectByKinds(list);
    }
}
