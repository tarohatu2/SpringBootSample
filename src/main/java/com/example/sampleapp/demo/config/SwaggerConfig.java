package com.example.sampleapp.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "サンプルAPI", description = "勉強用に作成したAPIです")
)
public class SwaggerConfig {
}
