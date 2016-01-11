package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.session.SessionManager;
import com.artronics.sdwn.domain.entities.NetworkSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadNetworkSession extends SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(LoadNetworkSession.class);

    @Autowired
    @Qualifier("simpleSessionManager")
    private SessionManager sessionManager;

    @Bean(name = "networkSession")
    public NetworkSession getNetworkSession(){
        return sessionManager.open();
    }

}
