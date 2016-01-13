package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.controller.map.BaseGraphTest;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.controller.support.SeedNetworkGraphAndMap;
import com.artronics.sdwn.domain.config.PersistenceConfigTest;
import com.artronics.sdwn.domain.entities.NetworkSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DbNetworkMapInitializerTest.MapInitializerConfig.class,
})
@TestPropertySource("classpath:application-defaults-test.properties")
public class DbNetworkMapInitializerTest extends BaseGraphTest
{
    @Autowired
    private SeedNetworkGraphAndMap seeder ;

    @Autowired
    private NetworkMapInitializer mapInitializer;


    @Override
    @Before
    public void setUp() throws Exception
    {
        seeder.seed(true);

        map = seeder.getNetworkMap();
        graph = map.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);
    }

    @Test
    @Rollback(value = false)
    public void it(){

    }

    @Configuration
    @Import({
            SdwnBaseConfig.class,
            PersistenceConfigTest.class,
    })
    @ComponentScan(basePackages = {"com.artronics.sdwn.controller.initializer"})
    public static class MapInitializerConfig{

        @Autowired
        SeedNetworkGraphAndMap seeder;

        @Bean
        public NetworkSession getSession(){
            return seeder.getActiveSession();
        }

    }
}