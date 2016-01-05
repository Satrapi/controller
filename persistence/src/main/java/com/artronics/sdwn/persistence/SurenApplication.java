package com.artronics.sdwn.persistence;

import com.artronics.sdwn.persistence.persistence.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = {
        "com.artronics.sdwn.persistence",
})
public class SurenApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SurenApplication.class, args);
    }
}
