package com.github.guoyjalihy.logging.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guoyanjun
 * @Description
 * @create 21-6-11 上午9:49
 */
@ConfigurationProperties(prefix = "logging.file")
public class LoggingProperties {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
