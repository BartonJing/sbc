package com.barton.sbc.service;

import com.barton.sbc.domain.entity.Encode;

import java.util.List;

public interface EncodeService {

    List<Encode> selectByKind(String kind);

    List<Encode> selectByKinds(List<String> list);
}
