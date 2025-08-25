package com.cg.spring_boot_microservice_3_api_gateway.config;

import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiAggregationConfig {

    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        SwaggerUiConfigParameters params = new SwaggerUiConfigParameters(swaggerUiConfigProperties);

        // Add OpenAPI endpoints for each microservice
        params.addUrl("http://localhost:3333/v3/api-docs");
        params.addUrl("http://localhost:4444/v3/api-docs");
        params.addUrl("http://localhost:5556/v3/api-docs");

        return params;
    }
}