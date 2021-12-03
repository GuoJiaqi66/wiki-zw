package com.zw.util;


import java.io.Serializable;


/*
* 远程id+redis解决：重复点赞问题
* 线程本地变量问题
* */
public class RequestContext implements Serializable {

    // 远程地址
    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    /*
    定义第二个多个线程本地地址
    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();
    */

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        com.zw.util.RequestContext.remoteAddr.set(remoteAddr);
    }

}
