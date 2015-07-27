package com.github.firelore.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages="com.github.firelore.service")
@Import(DataConfig.class)
public class AppConfig {

}
