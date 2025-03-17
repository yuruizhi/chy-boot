package com.chy.boot.common.commons.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * API文档配置
 * 从Swagger 2升级到SpringDoc OpenAPI 3
 *
 * @author YuRuizhi
 * @update 2025/03/14
 */
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enabled:true}")
    private boolean enableSwagger;

    /**
     * token header
     */
    private static final String TOKEN_HEADER = "token";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("CHY-Boot API")
                        .description("API文档")
                        .version("3.0"))
                .schemaRequirement(TOKEN_HEADER, securityScheme())
                .security(Collections.singletonList(new SecurityRequirement().addList(TOKEN_HEADER)));
    }

    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default-api")
                .pathsToMatch("/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Operation.class))
                .build();
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(TOKEN_HEADER)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);
    }
}
