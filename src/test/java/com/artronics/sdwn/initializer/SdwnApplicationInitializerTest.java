package com.artronics.sdwn.initializer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class SdwnApplicationInitializerTest
{

    @Configuration
    @ComponentScan(basePackages = "com.artronics.sdwn")
    static class Config{

    }
}