package com.hk.dream.onlyDream.autoCreateFile;

import com.hk.dream.util.ToString;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 坤
 * 2018/7/26 11:22
 */
public class AutoCreateFileUtil {
    String xmlPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\MyBatisGeneratorConfig.xml";

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     * 修改元素值
     * @param xpath XPath 使用路径表达式来选取 XML 文档中的节点或节点集。
     * @param name 属性名
     * @param value 属性值
     */
    public void updateXml(String xpath,String name, String value) {
        /*
         * 1. 得到Document
         * 2. 得到root元素
         * 3. 要把User对象转换成Element元素
         * 4. 把user元素插入到root元素中
         * 5. 回写document
         */
        try {
            /*
             * 1. 得到Docuembnt
             */
            // 创建解析器
            SAXReader reader = new SAXReader();
            // 调用读方法，得到Document
            Document doc = reader.read(xmlPath);
            //调用document方法完成查询
            Element element = (Element)doc.selectSingleNode(xpath);
            element.addAttribute(name, value);
            /*
             * 2. 得到根元素
             */
//            Element root = doc.getRootElement();
            /*
             * 3. 完成添加元素，并返回添加的元素！
             * 向root中添加一个名为user的元素！并返回这个元素
             */
//            Element userElement = root.addElement("user");
            // 设置userElement的属性！
//            userElement.addAttribute("username", "6666");


            /*
             * 回写
             * 注意：创建的users.xml需要使用工具修改成UTF-8编码！
             * Editplus：标记列--> 重新载入为 --> UTF-8
             */

            // 创建目标输出流，它需要与xml文件绑定
            Writer out = new PrintWriter(xmlPath, "UTF-8");
            // 创建格式化器
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);//先干掉原来的空白(\t和换行和空格)！

            // 创建XMLWrtier
            XMLWriter writer = new XMLWriter(out, format);

            // 调用它的写方法，把document对象写到out流中。
            writer.write(doc);

            // 关闭流
            out.close();
            writer.close();

        } catch(Exception e) {
            // 把编译异常转换成运行异常！
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取XML
     * @param xpath XPath 使用路径表达式来选取 XML 文档中的节点或节点集。
     * @param name 属性
     * @return 元素的属性值
     */
    public String readXml(String xpath,String name) {
        /*
         * 1. 得到Docuemnt
         * 2. 给出xpath表达式
         * 3. 调用docuemnt的方法进行xpath查询，得到Element
         * 4. 把Element转换成User对象，返回！
         */
        try {
            /*
             * 1. 得到Docuembnt
             */
            // 创建解析器
            SAXReader reader = new SAXReader();
            // 调用读方法，得到Document
            Document doc = reader.read(xmlPath);

            /*
             * 2. 准备xpath
             *  //开头表示没有深的限制，可以在文档查询子元素、子元素的子元素！
             *  []中放的叫谓语，其实就是查询条件
             *  @username表示username属性，限定其必须等于方法参数username
             */
            /*
             * 3. 调用document方法完成查询
             */
            Element element = (Element)doc.selectSingleNode(xpath);
            if(element == null) {
                return null;
            }
            /*
             * 4. 把元素转换成User类的对象，然后返回
             */
//            System.out.println(userEle.attributeValue("username"));
            return element.attributeValue(name);

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化 model
     * @return
     */
    public AutoCreateFileModel init(){
        System.out.println(new File("").getAbsolutePath()+"\\src\\main\\resources\\MyBatisGeneratorConfig.xml");
        String driverClass = readXml("//jdbcConnection", "driverClass");
        String connectionURL = readXml("//jdbcConnection", "connectionURL");
        String userId = readXml("//jdbcConnection", "userId");
        String password = readXml("//jdbcConnection", "password");
        String tableName = readXml("//table", "tableName");

        String pathController = readXml("//property[@name=\"pathController\"]", "value");
        String pathService = readXml("//property[@name=\"pathService\"]", "value");
        String pathServiceImpl = readXml("//property[@name=\"pathServiceImpl\"]", "value");
        String pathHtml = readXml("//property[@name=\"pathHtml\"]", "value");
        String pathFtl = readXml("//property[@name=\"pathFtl\"]", "value");

        String pathModelPackage = readXml("//javaModelGenerator", "targetPackage");
        String pathModelProject = readXml("//javaModelGenerator", "targetProject");
        String pathMapperTargetPackage = readXml("//sqlMapGenerator", "targetPackage");
        String pathMapperTargetProject = readXml("//sqlMapGenerator", "targetProject");
        String pathDaoTargetPackage = readXml("//javaClientGenerator", "targetPackage");
        String pathDaoTargetProject = readXml("//javaClientGenerator", "targetProject");
        AutoCreateFileModel autoCreateFileModel = new AutoCreateFileModel();
        autoCreateFileModel.setDriverClass(driverClass);
        autoCreateFileModel.setConnectionURL(connectionURL);
        autoCreateFileModel.setUserId(userId);
        autoCreateFileModel.setPassword(password);
        autoCreateFileModel.setTableName(tableName);
        autoCreateFileModel.setPathController(pathController);
        autoCreateFileModel.setPathService(pathService);
        autoCreateFileModel.setPathServiceImpl(pathServiceImpl);
        autoCreateFileModel.setPathHtml(pathHtml);
        autoCreateFileModel.setPathFtl(pathFtl);
        autoCreateFileModel.setPathModel(pathModelProject.replace("\\","/").replace("./","")+"/"+pathModelPackage.replace(".","/"));
        autoCreateFileModel.setPathMapper(pathMapperTargetProject.replace("\\","/").replace("./","")+"/"+pathMapperTargetPackage.replace(".","/"));
        autoCreateFileModel.setPathDao(pathDaoTargetProject.replace("\\","/").replace("./","")+"/"+pathDaoTargetPackage.replace(".","/"));
        autoCreateFileModel.setDatabase(autoCreateFileModel.getConnectionURL().substring(autoCreateFileModel.getConnectionURL().lastIndexOf("/")+1));
        return autoCreateFileModel;
    }

    public void saveModel(AutoCreateFileModel model){

        updateXml("//jdbcConnection", "driverClass",model.getDriverClass());
        updateXml("//jdbcConnection", "connectionURL",model.getConnectionURL());
        updateXml("//jdbcConnection", "userId",model.getUserId());
        updateXml("//jdbcConnection", "password",model.getPassword());
        updateXml("//table", "tableName",model.getTableName());

        updateXml("//property[@name=\"pathController\"]", "value",model.getPathController());
        updateXml("//property[@name=\"pathService\"]", "value",model.getPathService());
        updateXml("//property[@name=\"pathServiceImpl\"]", "value",model.getPathServiceImpl());
        updateXml("//property[@name=\"pathHtml\"]", "value",model.getPathHtml());
        updateXml("//property[@name=\"pathFtl\"]", "value",model.getPathFtl());
//        updateXml("//javaModelGenerator", "targetPackage");
//        updateXml("//javaModelGenerator", "targetProject");
//        updateXml("//sqlMapGenerator", "targetPackage");
//        updateXml("//sqlMapGenerator", "targetProject");
//        updateXml("//javaClientGenerator", "targetPackage");
//        updateXml("//javaClientGenerator", "targetProject");
    }

    public void createFile(Map<String, String> path, Map<String,Object> data){

        String ftlPath = path.get("ftlPath"),ftlName= path.get("ftlName"),filePath= path.get("filePath"),fileName= path.get("fileName");
//        Map<String,Object> m = new HashMap<>();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        try {
            cfg.setDirectoryForTemplateLoading(new File(ftlPath));
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
            Template temp = cfg.getTemplate(ftlName);
            File file = new File(filePath + "/"+fileName);
            if(!new File(filePath).isDirectory())new File(filePath).mkdir();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            temp.setObjectWrapper(new BeansWrapperBuilder(new Version("2.3.23")).build());
            temp.process(data, bw);
            bw.flush();
            fw.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    /** 使用mybatis逆向工程 */
    public boolean myBatisCreateFile(){
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            //指定 逆向工程配置文件
            File configFile = new File(xmlPath);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            org.mybatis.generator.config.Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /** 对mybatis生成的文件进行个性化修改 */
    public boolean uploadMyBatisCreateFile(AutoCreateFileModel model){
        /** 给model添加继承 */
        String pathModel = model.getPathModel();//获取生成model的位置
        String modelName = upperLower(model.getTableName(), 1)+".java";//获取文件名称
        File file = new File(pathModel+"/"+modelName);
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(true) {
                String d = null;
                if((d = br.readLine()) != null) {
                    if(d.equals("public class "+upperLower(model.getTableName(), 1)+" {")){
                        d = "import com.hk.dream.onlyDream.BaseModel;\n\npublic class "+upperLower(model.getTableName(), 1)+" extends BaseModel {";
                    }
                    sb.append(d+"\n");
                }else{
                    break;
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 大小写转换
     * 1.首字母大写，下划线后首字母大写 user_Pwd --> UserPwd
     * 2.首字母小写，下划线后首字母大写 user_Pwd --> userPwd
     * 3.去下划线，全部大写
     * 4.去下划线，全部小写
     */
    public String upperLower(String str, int i) {
        if (i != 3 && i != 4) {
            String[] strSplit = str.split("_");
            if (i == 1) {
                strSplit[0] = strSplit[0].substring(0, 1).toUpperCase() + strSplit[0].substring(1).toLowerCase();
            } else if (i == 2) {
                strSplit[0] = strSplit[0].substring(0, 1).toLowerCase() + strSplit[0].substring(1).toLowerCase();
            }
            if (strSplit.length > 1) {
                for (int k = 1; k < strSplit.length; k++) {
                    strSplit[k] = strSplit[k].substring(0, 1).toUpperCase() + strSplit[k].substring(1).toLowerCase();
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : strSplit) stringBuilder.append(s);
            return stringBuilder.toString();
        } else {
            if (i == 3) {
                str = str.replace("_", "").toUpperCase();
            } else {
                str = str.replace("_", "").toLowerCase();
            }
            return str;
        }
    }
}
