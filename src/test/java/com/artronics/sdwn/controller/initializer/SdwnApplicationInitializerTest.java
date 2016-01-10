package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.controller.config.LoadSdwnControllerEntityBean;
import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.domain.config.PersistenceConfigTest;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SdwnApplicationInitializerTest.LoadEssentials.class)
@TestPropertySource({"classpath:application-defaults-test.properties"})
public class SdwnApplicationInitializerTest
{
    private SpringApplicationBuilder builder;

    private AnnotationConfigApplicationContext context;

    @Autowired
    private SdwnControllerRepo controllerRepo;

    @Value("${com.artronics.sdwn.controller.url}")
    private String controllerUrl;

    @Before
    public void setUp() throws Exception
    {
        context = new AnnotationConfigApplicationContext(
                SdwnApplicationInitializerTest.SdwnConfigTest.class,
                LoadSdwnControllerEntityBean.class
        );
    }

    @Test
    public void it_should_create_an_sdwnController_if_not_exists(){
//        context.register(LoadSdwnControllerEntityBean.class);
        assertNotNull(controllerRepo.findByUrl(controllerUrl));
    }

    @Configuration
    @ComponentScan(basePackages = {"com.artronics.sdwn.controller","com.artronics.sdwn.domain"},
            excludeFilters = @ComponentScan.Filter(
            value= Configuration.class,
            type = FilterType.ANNOTATION))
    @ActiveProfiles("test")
    @PropertySource("classpath:application-defaults-test.properties")
//    @TestPropertySource({"classpath:application-defaults-test.properties"})
    @Import({PersistenceConfigTest.class, SdwnBaseConfig.class})
    static class SdwnConfigTest{

        @Bean
        public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
        {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Configuration
    @Import({PersistenceConfigTest.class, SdwnBaseConfig.class})
    static class LoadEssentials{
    }

}