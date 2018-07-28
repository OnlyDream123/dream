package com.hk.dream.onlyDream.autoCreateFile;

import com.hk.dream.onlyDream.DreamResult;
import com.hk.dream.util.ToString;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.StringUtils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 坤
 * 2018/7/26 15:06
 */
public class AutoCreateFileSservice {
    /**
     * 获取xml配置
     **/
    public DreamResult selectModel() {
        DreamResult dreamResult = new DreamResult();
        AutoCreateFileUtil autoCreateFileUtil = new AutoCreateFileUtil();
        dreamResult.setData(autoCreateFileUtil.init());
        return dreamResult;
    }

    /**
     * 测试数据库连接
     */
    public DreamResult testConnect(AutoCreateFileModel model) {
        DreamResult dreamResult = new DreamResult();
        if (model != null && StringUtils.hasText(model.getConnectionURL()) && StringUtils.hasText(model.getUserId()) && StringUtils.hasText(model.getPassword())) {

            try {
                DriverManager.getConnection(model.getConnectionURL(), model.getUserId(), model.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
                dreamResult.setCode(1);
                dreamResult.setMsg("获取数据库连接失败:"+e.toString());
            }
        } else {
            dreamResult.setCode(1);
            dreamResult.setMsg("数据库连接配置有误");
        }
        return dreamResult;
    }

    /**  获取表结构 **/
    public DreamResult getDatabaseModel(AutoCreateFileModel model) throws SQLException {
        DreamResult dreamResult = new DreamResult();
        try {
            Connection connection = DriverManager.getConnection(model.getConnectionURL(), model.getUserId(), model.getPassword());
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM information_schema.columns WHERE table_schema = '" + model.getDatabase() + "' AND table_name = '" + model.getTableName() + "'";
            System.out.println("执行SQL:"+sql);
            ResultSet resultSet = statement.executeQuery(sql);
            List<AutoCreateFileDatabaseModel> modelList = new ArrayList<>();
            while (resultSet.next()) {
                AutoCreateFileDatabaseModel dataModel = new AutoCreateFileDatabaseModel();
                dataModel.setName(resultSet.getString("COLUMN_NAME")); //字段名
                dataModel.setType(resultSet.getString("COLUMN_TYPE")); //字段类型
                dataModel.setComment(resultSet.getString("COLUMN_COMMENT")); //备注
                modelList.add(dataModel);
            }
            dreamResult.setData(modelList);
        }catch (Exception e){
            dreamResult.setCode(1);
            dreamResult.setMsg("获取表结构异常："+e.toString());
            e.printStackTrace();
        }
        return dreamResult;
    }

    /**  保存配置 */
    public DreamResult saveModel(AutoCreateFileModel model){
        DreamResult dreamResult = new DreamResult();
        new AutoCreateFileUtil().saveModel(model);
        return dreamResult;
    }

    /** 生成文件 */
    public DreamResult createFile(AutoCreateFileModel model){
        DreamResult dreamResult = new DreamResult();
        /******************** 设置数据 ********************/
        Map<String, Object> data = new HashMap<>();
        try {
            DreamResult databaseModel = getDatabaseModel(model);
            if(databaseModel.getCode() == 0){
                data.put("tableList",databaseModel.getData());//表结构
            }else{
                return databaseModel;
            }
        } catch (SQLException e) {
            dreamResult.setCode(1);
            dreamResult.setMsg("获取表结构异常："+e.toString());
            e.printStackTrace();
            return dreamResult;
        }
        AutoCreateFileUtil du = new AutoCreateFileUtil();
        //表名去下划线，首字母大写
        String classNameUpper = du.upperLower(model.getTableName(),1);
        data.put("classNameUpper", classNameUpper);
        //表名去下划线，首字母大写
        String classNameLower = du.upperLower(model.getTableName(),2);
        data.put("classNameLower", classNameLower);
        //没有dream前缀的表名 字母小写
        String tableNameNoDream = model.getTableName().substring(model.getTableName().indexOf("_")+1).toLowerCase();
        data.put("tableNameNoDream",tableNameNoDream);
        //获取表名前缀 字母大写
        AutoCreateFileDatabaseModel autoCreateFileDatabaseModel = ((List<AutoCreateFileDatabaseModel>) data.get("tableList")).get(0);
        data.put("nameLeftUpper",autoCreateFileDatabaseModel.getNameLeftUpper());


        /******************** 设置模板路径 ********************/

        Map<String, String> path = new HashMap<>();
        path.put("ftlPath", model.getPathFtl());


        /******************** 开始生成 ********************/
        AutoCreateFileUtil util = new AutoCreateFileUtil();
        File filePath;
        //生成 html
        if(model.isPathHtmlIf()) {
            path.put("ftlName", "html.ftl");
            filePath = new File(model.getPathHtml() + "/" + tableNameNoDream);
            if (!filePath.isDirectory()) filePath.mkdir();
            path.put("filePath", filePath.getAbsolutePath());
            path.put("fileName", tableNameNoDream + ".html");
            util.createFile(path, data);
        }
        //生成contorller
        if(model.isPathControllerIf()) {
            path.put("ftlName", "controller.ftl");
            filePath = new File(model.getPathController());
            if (!filePath.isDirectory()) filePath.mkdir();
            path.put("filePath", filePath.getAbsolutePath());
            path.put("fileName", classNameUpper + "Controller.java");
            util.createFile(path, data);
        }

        //生成Service
        if(model.isPathServiceIf()) {
            path.put("ftlName", "service.ftl");
            filePath = new File(model.getPathService());
            if (!filePath.isDirectory()) filePath.mkdir();
            path.put("filePath", filePath.getAbsolutePath());
            path.put("fileName", "I" + classNameUpper + "Service.java");
            util.createFile(path, data);
        }

        //生成ServiceImpl
        if(model.isPathServiceImplIf()) {
            path.put("ftlName", "serviceImpl.ftl");
            filePath = new File(model.getPathServiceImpl());
            if (!filePath.isDirectory()) filePath.mkdir();
            path.put("filePath", filePath.getAbsolutePath());
            path.put("fileName", classNameUpper + "ServiceImpl.java");
            util.createFile(path, data);
        }

        //利用MyBatis逆向工程生成mapper model dao
        filePath = new File(model.getPathMapper()+"/"+du.upperLower(model.getTableName(),1)+"Mapper.xml");
        if(filePath.exists())filePath.delete(); //先判断mapper是否存在，如果存在先删除
        util.updateXml("//table", "tableName", model.getTableName());
        if(!util.myBatisCreateFile()){
            dreamResult.setCode(1);
            dreamResult.setMsg("调用MyBatis逆向工程生成文件失败");
            return dreamResult;
        }

        //个性化修改MyBatis生成的文件
        if(!util.uploadMyBatisCreateFile(model)){
            dreamResult.setCode(1);
            dreamResult.setMsg("修改MyBatis逆向工程生成文件失败");
        }
        return dreamResult;
    }



}
