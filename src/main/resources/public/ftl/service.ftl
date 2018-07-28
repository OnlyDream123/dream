package com.hk.dream.service;

import com.hk.dream.model.${classNameUpper};
import com.hk.dream.onlyDream.DreamResult;
/**
 * 坤
 * ${.now}
 */
public interface I${classNameUpper}Service {
    /** 查询list */
    DreamResult selectModelList(${classNameUpper} model);

    /** 保存 */
    DreamResult saveModel(${classNameUpper} model);
}
