package com.pazukdev.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Siarhei Sviarkaltsau
 */
@Configuration
public class Config {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
