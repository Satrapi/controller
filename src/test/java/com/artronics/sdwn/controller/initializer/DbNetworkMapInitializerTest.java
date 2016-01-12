package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.domain.config.PersistenceConfigTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SdwnBaseConfig.class,
        PersistenceConfigTest.class
})
public class DbNetworkMapInitializerTest
{
    @Autowired
    private NetworkMapInitializer mapInitializer;

    @Before
    public void setUp() throws Exception
    {
        

    }
    @Test
    public void it(){

    }
}