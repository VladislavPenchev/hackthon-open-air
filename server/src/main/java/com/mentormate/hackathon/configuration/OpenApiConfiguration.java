package com.mentormate.hackathon.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * {@link OpenApiConfiguration} contains all configuration that needed about OpenAPI 3
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * Allows definitions to be appeared globally
     */
    private final String moduleName;
    private final String apiVersion;

    /**
     * Instantiates a new Open api 3.0 config.
     *
     * @param moduleName the module name
     * @param apiVersion the api version
     */
    public OpenApiConfiguration(
            @Value("${module-name}") String moduleName,
            @Value("${api-version}") String apiVersion) {
        this.moduleName = moduleName;
        this.apiVersion = apiVersion;
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "JWT Token Authorization";
        final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title(apiTitle).version(apiVersion));
    }
}


