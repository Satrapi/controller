package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.map.FixedWeightCalculator;
import com.artronics.sdwn.controller.map.WeightCalculator;
import com.artronics.sdwn.domain.entities.NetworkSession;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapSeederConfig
{
    private final static Logger log = Logger.getLogger(MapSeederConfig.class);

    @Bean(name = "fixedWeightCalculator")
    public WeightCalculator getWeightCalculator()
    {
        return new FixedWeightCalculator();
    }

    @Bean(name = "fakeNetworkSession")
    public NetworkSession getNetworkSession()
    {
        NetworkSession session = new NetworkSession();
        session.setId(1000L);
        return session;
    }
}
