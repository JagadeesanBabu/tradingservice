package com.trade.config;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfiguration {

    @Value("${swagger.hostname:#{null}}")
    private String hostname;

    @Value("${swagger.port:#{null}}")
    private String port;

    @Value("${swagger.path:#{null}}")
    private String path;

    @Value("${info.title:#{null}}")
    private String title;

    @Value("${info.description:#{null}}")
    private String description;

    @Value("${info.version:#{null}}")
    private String version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.trade"))
                .paths(PathSelectors.any())
                .build()
                .host(getHost())
                .apiInfo(apiInfo());
        //.securitySchemes(Collections.singletonList(securityScheme()))
        //.securityContexts(Collections.singletonList(securityContext()));
    }

    private String getHost() {
        if (Strings.isNullOrEmpty(hostname)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(hostname);
        if (!Strings.isNullOrEmpty(port)) {
            sb.append(":").append(port);
        }

        if (!Strings.isNullOrEmpty(path)) {
            if (!path.startsWith("/")) {
                sb.append("/");
            }
            sb.append(path);
        }

        return sb.toString();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                title,
                description,
                version,
                "",
                null,
                "",
                "",
                Collections.emptyList()
        );
    }

}
