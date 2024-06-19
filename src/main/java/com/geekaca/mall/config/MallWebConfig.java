package com.geekaca.mall.config;

import com.geekaca.mall.constants.MallConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MallWebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //任意请求路径                          允许来自任何域名的访问
        registry.addMapping("/**").allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问 goods-img 文件夹下的文件是会被重新指向至本地路径
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + MallConstants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + MallConstants.FILE_UPLOAD_LOCAL_DIC);
    }
}
