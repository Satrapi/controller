package com.artronics.sdwn.persistence.persistence;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(RepositoryConfig.class)
@DependsOn("environment")
public class PersistenceConfig
{
    private final static Logger log = Logger.getLogger(PersistenceConfig.class);
    private final static String BASE_PATH="com.artronics.sdwn.persistence.";

    @Autowired
    Environment env;

    private String url;
    private String driver;
    private String username;
    private String password;
    private String dialect;
    private String hbm2ddl;

    @PostConstruct
    public void initBean(){
        url = env.getProperty("com.artronics.sdwn.persistence.persistence.url");
        driver = env.getProperty("com.artronics.sdwn.persistence.persistence.driver");
        username = env.getProperty("com.artronics.sdwn.persistence.persistence.username");
        password = env.getProperty("com.artronics.sdwn.persistence.persistence.password");
        dialect = env.getProperty("com.artronics.sdwn.persistence.persistence.dialect");
        hbm2ddl = env.getProperty("com.artronics.sdwn.persistence.persistence.hbm2ddl");
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
    protected Properties buildHibernateProperties()
    {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("spring.datasource.url",
                                        url);

        hibernateProperties.setProperty("spring.datasource.driverClassName", driver);

        hibernateProperties.setProperty("spring.datasource.username", username);
        hibernateProperties.setProperty("spring.datasource.password", password);

        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.use_sql_comments", "false");
        hibernateProperties.setProperty("hibernate.format_sql", "false");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        hibernateProperties.setProperty("hibernate.generate_statistics", "false");

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource());

        emf.setPackagesToScan("com.artronics.sdwn.persistence.entities");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(buildHibernateProperties());

        return emf;
    }
}
