package com.pazukdev.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Siarhei Sviarkaltsau
 */
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String TITLE = "bearings-info API";
    private static final String DESCRIPTION = "API for bearings-info server";
    private static final String CONTACT_NAME = "Siarhei Sviarkaltsau";
    private static final String CONTACT_WEBSITE_URL ="http://sovietboxers.com/";
    private static final String CONTACT_EMAIL = "pazukdev@gmail.com";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .contact(new Contact(CONTACT_NAME, CONTACT_WEBSITE_URL, CONTACT_EMAIL))
                .build();
    }

}
