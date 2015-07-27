package com.github.firelore.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;;

@Configuration
@ComponentScan(basePackages="com.github.firelore.controller")
@EnableWebMvc
public class WebConfig {

}
