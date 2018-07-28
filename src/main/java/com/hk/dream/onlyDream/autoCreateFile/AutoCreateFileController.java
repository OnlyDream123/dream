package com.hk.dream.onlyDream.autoCreateFile;

import com.hk.dream.onlyDream.DreamResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 坤
 * 2018/7/24 18:50
 */
@RestController
@RequestMapping("/dream/autoCreateFile")
public class AutoCreateFileController {
    @RequestMapping(method=RequestMethod.GET)
    public DreamResult selectModel(){
        return new AutoCreateFileSservice().selectModel();
    }

    @RequestMapping(value = "/testConnect",method=RequestMethod.GET)
    public DreamResult testConnect(AutoCreateFileModel model){
        return new AutoCreateFileSservice().testConnect(model);
    }

    @RequestMapping(value = "/getDatabaseModel",method=RequestMethod.GET)
    public DreamResult getDatabaseModel(AutoCreateFileModel model) throws SQLException {
        return new AutoCreateFileSservice().getDatabaseModel(model);
    }

    @RequestMapping(value = "/saveModel",method=RequestMethod.GET)
    public DreamResult saveModel(AutoCreateFileModel model){
        return new AutoCreateFileSservice().saveModel(model);
    }

    @RequestMapping(value = "/createFile", method = RequestMethod.GET)
    public DreamResult createFile(AutoCreateFileModel model){
        return new AutoCreateFileSservice().createFile(model);
    }
}
