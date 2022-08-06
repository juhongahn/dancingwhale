package com.k1ng.doinggajigaji.config;

import com.k1ng.doinggajigaji.argumentresolver.LoginEmailArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/logout", "/member/new", "/css/**", "/js/**", "/*.ico", "/error");
//    }

    @Value("${upload.uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images/**")
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginEmailArgumentResolver());
    }
}
