package com.ashu.caller.config;

import com.ashu.caller.service.CurrentUserService;
import com.ashu.caller.service.impl.CurrentUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CurrentUserConfig implements WebMvcConfigurer {

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public CurrentUserService userService() {
        return new CurrentUserServiceImpl();
    }
}
