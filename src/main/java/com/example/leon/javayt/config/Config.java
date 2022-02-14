package com.example.leon.javayt.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
class Config {

    @Bean
    public Queue queue() {
    	return new ActiveMQQueue("inmemory.queue");
    }
}
