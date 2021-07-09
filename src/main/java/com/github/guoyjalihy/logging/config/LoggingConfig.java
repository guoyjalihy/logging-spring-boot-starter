package com.github.guoyjalihy.logging.config;

import com.github.guoyjalihy.logging.LoggingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guoyanjun
 * @Description
 * @create 21-6-11 上午9:49
 */
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(
    prefix = "logging.file",
    name = "file",
    havingValue = "true"
)
public class LoggingConfig {
    @Autowired
    private LoggingProperties loggingProperties;

    @Bean
    public LoggingController loggingController(){
        return new LoggingController(loggingProperties.getPath());
    }
}
