package com.github.guoyjalihy.logging;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Auther: guoyanjun
 * @Date: 2021/06/04 17:07
 */

@ServerEndpoint("/wsServer")
@Component
public class WebSocketServer{

    private Process process;
    private InputStream inputStream;

    @OnOpen
    public void onOpen(Session session) throws Exception{
        // 执行tail -f命令
        String fileName = session.getQueryString().split("=")[1];
        String command = "tail -f -n 100 " + fileName;
        process = Runtime.getRuntime().exec(command);
        inputStream = process.getInputStream();

        // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
        TailLogThread thread = new TailLogThread(inputStream, session);
        thread.start();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.close();
    }

    @OnError
    public void onError(Throwable e) {

    }

}

