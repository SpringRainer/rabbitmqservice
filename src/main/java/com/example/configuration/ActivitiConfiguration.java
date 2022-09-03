package com.example.configuration;

import org.activiti.engine.ProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/8/21 23:24
 * activiti 配置类
 */
@Configuration
public class ActivitiConfiguration {
    @Bean
    public ProcessEngineConfiguration engineConfiguration() {
        return ProcessEngineConfiguration
        .createProcessEngineConfigurationFromResource("activiti/config/activiti.cfg.xml");
    }
}