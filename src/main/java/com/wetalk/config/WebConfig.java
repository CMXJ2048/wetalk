package com.wetalk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC configuration hook point.
 * - You can customize view resolvers, formatters, interceptors, CORS, and static resources mapping.
 * - With Spring Boot, most MVC defaults are auto-configured; override only when necessary.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /** Configure view resolvers (e.g., Thymeleaf, JSP). Not used by default in this REST-first app. */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // e.g., registry.jsp(); or registry.viewResolver(thymeleafViewResolver)
    }

    /** Map additional static resource locations beyond the default classpath:/static, /public, /resources. */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // e.g., registry.addResourceHandler("/files/**").addResourceLocations("file:/opt/app/files/");
    }
}