package com.github.guoyjalihy.logging;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @Description:
 * @Auther: guoyanjun
 * @Date: 2021/06/04 17:07
 */

@ServerEndpoint("/wsServer")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        TailLogUtil.pushLog(session);
    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

}

