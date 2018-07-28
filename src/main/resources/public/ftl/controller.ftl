package com.hk.dream.controller;

import com.hk.dream.model.${classNameUpper};
import com.hk.dream.onlyDream.DreamResult;
import com.hk.dream.service.I${classNameUpper}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 坤
 * 2018/7/25 21:38
 */
@RestController
@RequestMapping(value="/dream/${tableNameNoDream}")
public class ${classNameUpper}Controller {
    @Autowired
    private I${classNameUpper}Service i${classNameUpper}Service;
    @RequestMapping(method=RequestMethod.GET)
    public DreamResult selectModelList(${classNameUpper} model){
        return i${classNameUpper}Service.selectModelList(model);
    }
}
