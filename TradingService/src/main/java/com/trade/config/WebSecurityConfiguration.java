package com.trade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Objects.nonNull;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String CORS_CONF_BEAN_NAME = "corsConfigurationSource";

    @Value("${swagger.pathmatchers:#{null}}")
    private String[] pathMatchers;

    @Autowired(required = false)
    @Qualifier(CORS_CONF_BEAN_NAME)
    private CorsConfigurationSource corsConfigurationSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        HttpSecurity httpBuilder = http.authorizeRequests()
                .antMatchers(pathMatchers).permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable();

        CorsConfigurer<HttpSecurity> cors = httpBuilder.cors();

        if (nonNull(corsConfigurationSource)) {
            cors.configurationSource(corsConfigurationSource);
        } else {
            cors.disable();
        }
    }

    @Configuration
    static class WebCorsConfig {

        /* only enable cors if explicitly needed */
        @Bean(CORS_CONF_BEAN_NAME)
        CorsConfigurationSource corsConfigurationSource(
                @Value("${web.cors.origins}") List<String> origins,
                @Value("${web.cors.methods}") List<String> methods,
                @Value("${web.cors.headers}") List<String> headers,
                @Value("${web.cors.pattern}") String pattern
        ) {

            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(origins);
            configuration.setAllowedMethods(methods);
            configuration.setAllowedHeaders(headers);

            org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration(pattern, configuration);
            return source;
        }
    }

}
