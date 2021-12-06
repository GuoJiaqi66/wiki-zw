package com.zw.service;

import com.zw.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
* Async 注解 生成一个新类，所以要重新写一个类
* */
@Service
public class WsService {

    @Resource
    private WebSocketServer webSocketServer;

    // 加一个线程开启异步化
    @Async
    public void sendInfo(String message, String logId) {
        MDC.put("LOG_ID", logId);
        webSocketServer.sendInfo(message);
    }
}
