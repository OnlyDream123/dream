package com.hk.dream.onlyDream;


import com.hk.dream.util.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseModel {
    private List<Date> dateList;
    private Map<String, Date> dateMap;
    private List<Integer> intList;
    private Map<String, Integer> intMap;
    private List<String> strList;
    private Map<String, String> strMap;
    private List<Object> objList;
    private List<?> list;
    private Map<String, Object> objMap;
    private String logMsg;
    private String entryId;
    private Object result;
    Integer page = 0;//当前页
    Integer limit = 10;//每页多少条
    private Long totalPages;
    private String condition; //sql语句条件

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    public Map<String, Date> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<String, Date> dateMap) {
        this.dateMap = dateMap;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public Map<String, Integer> getIntMap() {
        return intMap;
    }

    public void setIntMap(Map<String, Integer> intMap) {
        this.intMap = intMap;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public Map<String, String> getStrMap() {
        return strMap;
    }

    public void setStrMap(Map<String, String> strMap) {
        this.strMap = strMap;
    }

    public List<Object> getObjList() {
        return objList;
    }

    public void setObjList(List<Object> objList) {
        this.objList = objList;
    }

    public Map<String, Object> getObjMap() {
        return objMap;
    }

    public void setObjMap(Map<String, Object> objMap) {
        this.objMap = objMap;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = this.condition == null?""+ condition:this.condition + condition;
    }

    @Override
    public String toString() {
        return new ToString(this).toString();
    }
}
