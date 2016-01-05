package com.artronics.sdwn;

import com.artronics.sdwn.config.SdwnBeanConfig;
import com.artronics.sdwn.config.SdwnTaskExecutorConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SdwnApplication {

	public static void main(String[] args) {
//        new SpringApplicationBuilder()
//                .bannerMode(Banner.Mode.OFF)
//                .child(SdwnApplication.class)
//                .sources(SdwnBeanConfig.class)
//                .run(args);
//        new SpringApplicationBuilder()
//                .bannerMode(Banner.Mode.OFF)
//                .sources(SdwnBeanConfig.class)
//                .child(SdwnApplication.class)
//                .run(args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SdwnBeanConfig.class,
                SdwnTaskExecutorConfig.class);
        context.start();
	}
}
