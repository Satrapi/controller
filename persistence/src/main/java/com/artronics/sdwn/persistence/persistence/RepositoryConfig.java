package com.artronics.sdwn.persistence.persistence;

import org.apache.log4j.Logger;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.artronics.sdwn.persistence.persistence.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "com.artronics.sdwn.persistence.entities")
public class RepositoryConfig
{
    private final static Logger log = Logger.getLogger(RepositoryConfig.class);
}
