package com.github.guoyjalihy.logging;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description:
 * @Auther: guoyanjun
 * @Date: 2021/06/04 17:03
 */
public class TailLogUtil {
    //要查看的日志文件路径
    private static String logFilePath = "D:\\Project\\cpms-service\\logs\\cpms.log";

    private static BufferedReader bufferedReader;

    public static void pushLog(Session session) {
        try {
            // 执行tail命令，获取输入流
            if (bufferedReader == null) {
                Process process = Runtime.getRuntime().exec("tail -f " + logFilePath);
                InputStream inputStream = process.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }
            // 通过WebSocket发送给实时日志给客户端
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                session.getBasicRemote().sendText(line + "<br />");
            }
        } catch (IOException e) {
        }
    }

}
