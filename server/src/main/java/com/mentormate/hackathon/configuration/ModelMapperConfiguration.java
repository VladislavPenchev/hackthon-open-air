package com.mentormate.hackathon.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration is responsible to create and configure {@link ModelMapper}
 * <p>
 * Created by Vladislav Penchev on 2020/09/30
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Register this bean in spring context with scope singleton(default)
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
