package com.hk.dream.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * 坤
 * 2018/7/24 10:35
 * 工具类
 */
public class DreamUtil {
    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 把字节转换成K M G T单位
     * @param fileSize 纯数字的String类型
     * @return 返回转换后的结果
     */
    public static String FormetFileSize(String fileSize) {//转换文件大小
        if(!fileSize.matches("[0-9]+")){
            return "格式不正确,请传纯数字的String格式";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        BigDecimal size = new BigDecimal(new BigInteger(fileSize));
        BigDecimal B = new BigDecimal(new BigInteger("1024")); // B
        BigDecimal K = new BigDecimal(new BigInteger("1048576")); // K
        BigDecimal M = new BigDecimal(new BigInteger("1073741824")); // M
        BigDecimal G = new BigDecimal(new BigInteger("1099511627776")); // G
        if(size.compareTo(B) < 0){
            fileSizeString = size + "B";
        }else if(size.compareTo(K) < 0){
            fileSizeString = df.format(size.divide(B,2,3)) + "K";
        }else if(size.compareTo(M) < 0){
            fileSizeString = df.format(size.divide(K,2,3)) + "M";
        }else if(size.compareTo(G) < 0){
            fileSizeString = df.format(size.divide(M,2,3)) + "G";
        }else{
            fileSizeString = df.format(size.divide(G,2,3)) + "T";
        }
        return fileSizeString;
    }
}
