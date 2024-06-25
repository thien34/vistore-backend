package com.example.back_end.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    @Value("${file.upload-dir}")
    private String uploadDir;
    public String getUploadDir() {
        return uploadDir;
    }
}
