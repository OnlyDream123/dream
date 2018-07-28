package com.hk.dream.onlyDream.autoCreateFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 坤
 * 2018/7/26 12:36
 */
public class AutoCreateFileModel {
    private String driverClass = "";
    private String connectionURL = "";
    private String userId = "";
    private String password = "";
    private String database = "";
    private String tableName = "";

    private String pathModel = "";
    private String pathController = "";
    private String pathDao = "";
    private String pathService = "";
    private String pathServiceImpl = "";
    private String pathMapper = "";
    private String pathHtml = "";
    private String pathFtl = "";
    private boolean pathControllerIf = true;
    private boolean pathServiceIf = true;
    private boolean pathServiceImplIf = true;
    private boolean pathHtmlIf = true;
    private Map<String,String> strMap = new HashMap<>();

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPathModel() {
        return pathModel;
    }

    public void setPathModel(String pathModel) {
        this.pathModel = pathModel;
    }

    public String getPathController() {
        return pathController;
    }

    public void setPathController(String pathController) {
        this.pathController = pathController;
    }

    public String getPathDao() {
        return pathDao;
    }

    public void setPathDao(String pathDao) {
        this.pathDao = pathDao;
    }

    public String getPathService() {
        return pathService;
    }

    public void setPathService(String pathService) {
        this.pathService = pathService;
    }

    public String getPathServiceImpl() {
        return pathServiceImpl;
    }

    public void setPathServiceImpl(String pathServiceImpl) {
        this.pathServiceImpl = pathServiceImpl;
    }

    public String getPathMapper() {
        return pathMapper;
    }

    public void setPathMapper(String pathMapper) {
        this.pathMapper = pathMapper;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPathHtml() {
        return pathHtml;
    }

    public void setPathHtml(String pathHtml) {
        this.pathHtml = pathHtml;
    }

    public String getPathFtl() {
        return pathFtl;
    }

    public void setPathFtl(String pathFtl) {
        this.pathFtl = pathFtl;
    }

    public Map<String, String> getStrMap() {
        return strMap;
    }

    public void setStrMap(Map<String, String> strMap) {
        this.strMap = strMap;
    }

    public boolean isPathControllerIf() {
        return pathControllerIf;
    }

    public void setPathControllerIf(boolean pathControllerIf) {
        this.pathControllerIf = pathControllerIf;
    }

    public boolean isPathServiceIf() {
        return pathServiceIf;
    }

    public void setPathServiceIf(boolean pathServiceIf) {
        this.pathServiceIf = pathServiceIf;
    }

    public boolean isPathServiceImplIf() {
        return pathServiceImplIf;
    }

    public void setPathServiceImplIf(boolean pathServiceImplIf) {
        this.pathServiceImplIf = pathServiceImplIf;
    }

    public boolean isPathHtmlIf() {
        return pathHtmlIf;
    }

    public void setPathHtmlIf(boolean pathHtmlIf) {
        this.pathHtmlIf = pathHtmlIf;
    }
}
