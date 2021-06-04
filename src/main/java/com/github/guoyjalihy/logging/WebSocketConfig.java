package com.github.guoyjalihy.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description:
 * @Auther: guoyanjun
 * @Date: 2021/06/04 17:02
 */

@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 扫描@ServerEndpoint,将@ServerEndpoint修饰的类注册为websocket
     * 如果使用外置tomcat，则不需要此配置
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }

}
