package com.hk.dream.onlyDream.configuration;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 坤
 * 2018/7/19 21:35
 * MyBatis逆向工程启动
 */
public class MyBatisGeneratorFile {
    public void generator() throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        System.out.println(new File("").getAbsolutePath());
        File configFile = new File(new File("").getAbsolutePath()+"\\src\\main\\resources\\MyBatisGeneratorConfig.xml");
        System.out.println(configFile.getAbsolutePath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback, warnings);
        myBatisGenerator.generate(null);

    }
//    public static void main(String[] args) throws Exception {
//        try {
//            MyBatisGeneratorFile generatorSqlmap = new MyBatisGeneratorFile();
//            generatorSqlmap.generator();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
