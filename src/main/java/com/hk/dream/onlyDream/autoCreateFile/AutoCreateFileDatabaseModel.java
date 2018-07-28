package com.hk.dream.onlyDream.autoCreateFile;


public class AutoCreateFileDatabaseModel {
    /**
     * 字段名称
     */
    private String name = null;
    /**
     * 字段类型
     */
    private String type = null;
    /**
     * 注释
     */
    private String comment = null;
    /**
     * 字段名去下划线，首字母大写
     */
    private String nameUpper = null;
    /**
     * 字段名去下划线，首字母小写
     */
    private String nameLower = null;
    /**
     * 字段名全部大写
     */
    private String nameToUpper = null;
    /**
     * 字段名全部小写
     */
    private String nameToLower = null;
    /**
     * 字段类型装换 varchar转String
     */
    private String typeJava = null;
    /**
     * 判断类型是不是时间，如果是时间在页面上加入时间控件
     */
    private String typeEqDate = null;
    /**
     * 字段下划线左边-首字母大写
     */
    private String nameLeftUpper = null;
    /**
     * 字段下划线右边-首字母大写
     */
    private String nameRightUpper = null;
    /**
     * 字段下划线左边-全部小写
     */
    private String nameLeftLower = null;
    /**
     * 字段下划线右边-全部小写
     */
    private String nameRightLower = null;


    AutoCreateFileUtil du = new AutoCreateFileUtil();

    public void setName(String name) {
        this.name = name;
        this.nameUpper = du.upperLower(name, 1);
        this.nameLower = du.upperLower(name, 2);
        this.nameToUpper = name.toUpperCase();
        this.nameToLower = name.toLowerCase();
        this.nameLeftUpper = name.substring(0,name.indexOf("_"));
        this.nameLeftUpper = nameLeftUpper.substring(0,1).toUpperCase()+nameLeftUpper.substring(1).toLowerCase();
        this.nameRightUpper = name.substring(name.indexOf("_")+1);
        this.nameRightUpper = nameRightUpper.substring(0,1).toUpperCase()+nameRightUpper.substring(1).toLowerCase();
        this.nameLeftLower = name.substring(0,name.indexOf("_")).toLowerCase();
        this.nameRightLower = name.substring(name.indexOf("_")+1).toLowerCase();
    }

    public String getName() {
        return this.name;
    }

    public String getNameUpper() {
        return nameUpper;
    }

    public String getNameLower() {
        return nameLower;
    }

    public void setType(String type) {
        this.type = type;
        type = du.upperLower(type, 4);
        if (type.contains("int") || type.contains("number")) {
            this.typeJava = "Integer";
        } else if (type.contains("long")) {
            this.typeJava = "Long";
        } else if (type.contains("date") || type.contains("time")) {
            this.typeEqDate = "Date";
            this.typeJava = "String";
        } else if (type.contains("float")) {
            this.typeJava = "float";
        } else if (type.contains("double")) {
            this.typeJava = "double";
        } else {
            this.typeJava = "String";
        }
    }

    public String getType() {
        return this.type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

    public String getTypeJava() {
        return typeJava;
    }

    public String getNameToUpper() {
        return nameToUpper;
    }

    public String getNameToLower() {
        return nameToLower;
    }

    public String getNameLeftUpper() {
        return nameLeftUpper;
    }

    public String getNameRightUpper() {
        return nameRightUpper;
    }

    public String getNameLeftLower() {
        return nameLeftLower;
    }

    public String getNameRightLower() {
        return nameRightLower;
    }

    public String getTypeEqDate() {
        return typeEqDate;
    }
}
