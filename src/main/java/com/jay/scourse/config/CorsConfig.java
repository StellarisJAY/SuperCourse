package com.jay.scourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>
 * 跨域配置
 * </p>
 *
 * @author Jay
 * @date 2021/8/25
 **/
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许的请求来源，如果设置了AllowCredentials就必须使用OriginPattern
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许的header
        corsConfiguration.addAllowedHeader("*");
        // 允许请求方法
        corsConfiguration.addAllowedMethod("*");
        // 是否携带Credentials
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
