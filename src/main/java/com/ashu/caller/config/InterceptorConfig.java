package com.ashu.caller.config;

import com.ashu.caller.service.AuthService;
import com.ashu.caller.service.CurrentUserService;
import com.ashu.caller.exception.CallerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private static final String TOKEN_HEADER_NAME = "token";
    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorConfig.class);

    public InterceptorConfig(AuthService authService, CurrentUserService currentUser) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        AuthInterceptor authInterceptor = new AuthInterceptor(authService);

        interceptorRegistry
                .addInterceptor(authInterceptor)
                .addPathPatterns("/search/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/auth/logout")
                .excludePathPatterns("data/populate")
                .excludePathPatterns("auth/login")
                .excludePathPatterns("auth/otp")
                .excludePathPatterns("auth/register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private class AuthInterceptor extends HandlerInterceptorAdapter {

        private final AuthService authService;

        AuthInterceptor(AuthService authService) {
            this.authService = authService;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            LOGGER.debug(request.getRequestURI());
            String token = request.getHeader(TOKEN_HEADER_NAME);
            if (!StringUtils.isEmpty(token)) {
                if (!authService.validateToken(token)) {
                    throw new Exception("Token is Invalid!");
                }
            } else {
                throw new CallerException("Token not present in headers");
            }
            return true;
        }
    }
}
