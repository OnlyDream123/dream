package com.hk.dream.onlyDream;

/**
 * 坤
 * 2018/7/25 10:17
 */
public class DreamResult {
    public int code = 0;
    public String msg = "";
    public long count;
    public Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public DreamResult(){}
    public DreamResult(int code, String msg, long count, Object data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
