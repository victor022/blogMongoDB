package com.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan
@Configuration
@Import( {MongoConfig.class, SwaggerConfig.class} )
public class AppConfig {

}
