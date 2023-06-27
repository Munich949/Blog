package com.dlnu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${blog.path.urlMapper}")
    String urlMapper;
    @Value("${blog.path.imagesFolder}")
    String imagesFolder;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(urlMapper + "**")
                .addResourceLocations("file:" + imagesFolder);
    }
}
