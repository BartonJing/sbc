package com.barton.sbc.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.barton.sbc.domain.entity.Encode;
import com.barton.sbc.service.EncodeService;
import com.barton.sbc.utils.CommonUtil;
import com.barton.sbc.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by barton on 2018/09/28.
 */
@RestController
@RequestMapping(value = "/sys/encode")
public class EncodeController {
    private static final Logger logger = LoggerFactory.getLogger(EncodeController.class);
    @Autowired
    private EncodeService encodeService;

    /**
     * 根据kind查询编码
     *
     * @param kind   (多个kind以逗号隔开)
     * @return Map<String,List<Encode>>
     */
    @GetMapping(value = "/selectByKind")
    public Map<String,List<Encode>> selectByKinds(@RequestParam String kind){
        if(StrUtil.isEmpty(kind)){
            return null;
        }
        String[] kindArr = kind.split(",");
        if(kindArr == null || kindArr.length == 0){
            return null;
        }
        List<Encode> encodes = new ArrayList<>();
        //单个查询
        if(kindArr.length == 1){
            encodes = encodeService.selectByKind(kindArr[0]);
        }else{//多个查询
            List<String> list = Arrays.asList(kindArr);
            encodes = encodeService.selectByKinds(list);
        }
        //将查询结果按照kind分组
        Map<String,List<Encode>> map = new HashMap<String,List<Encode>>();
        CommonUtil.groupListToMap(encodes,map,Encode.class,"getKind");
        if(map != null){
            for(Map.Entry<String,List<Encode>> entry:map.entrySet()){
                //将每个类型的encode数组转为树类型的数据结构
                map.put(entry.getKey(),listToTree(entry.getValue()));
            }
        }
        return map;
    }

    /**
     * 将数组转为tree结构
     * @param list
     * @return
     */
    public List<Encode> listToTree(List<Encode> list){
        Encode root = new Encode("0",null);
        root = TreeUtil.getTree(root,list);
        return root.getChildNodes();
    }
}
