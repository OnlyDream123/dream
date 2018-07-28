package com.hk.dream.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hk.dream.dao.${classNameUpper}Mapper;
import com.hk.dream.model.${classNameUpper};
import com.hk.dream.onlyDream.DreamResult;
import com.hk.dream.service.I${classNameUpper}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * 坤
 * ${.now}
 */
@Service
public class ${classNameUpper}ServiceImpl implements I${classNameUpper}Service {
    @Autowired
    private ${classNameUpper}Mapper ${classNameLower}Mapper;

    @Override
    public DreamResult selectModelList(${classNameUpper} model) {
        DreamResult dreamResult = new DreamResult();
        PageHelper.startPage(model.getPage(),model.getLimit());
        PageInfo<${classNameUpper}> pageInfo = new PageInfo<>(${classNameLower}Mapper.selectByExample(null));
        dreamResult.setData(pageInfo.getList());
        dreamResult.setCount(pageInfo.getTotal());
        return dreamResult;
    }

    @Override
    public DreamResult saveModel(${classNameUpper} model) {
        model.set${nameLeftUpper}Uuid(java.util.UUID.randomUUID().toString().replace("-",""));
        model.set${nameLeftUpper}Cdate(new Date());
        model.set${nameLeftUpper}Udate(new Date());
        model.set${nameLeftUpper}Status("1");
        DreamResult dreamResult = new DreamResult();
        try {
            dreamResult.setData(${classNameLower}Mapper.insert(model));
        }catch (Exception e){
            dreamResult.setCode(1);
            dreamResult.setMsg(e.toString());
        }
        return dreamResult;
    }
}
