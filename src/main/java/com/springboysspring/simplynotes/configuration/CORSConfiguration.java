//package com.springboysspring.simplynotes.configuration;
//
//import javax.validation.constraints.NotNull;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CORSConfiguration {
//
//    @NotNull
//    @Bean
//    public WebMvcConfigurer CORSConfig() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                    .allowedMethods("GET", "POST", "PUT", "DELETE")
//                    .allowedHeaders("*")
//                    .allowedOrigins("*");
//            }
//
//        };
//    }
//}
