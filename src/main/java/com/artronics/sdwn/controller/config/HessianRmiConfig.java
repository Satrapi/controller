package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.SdwnController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianRmiConfig
{
    private final static Logger log = Logger.getLogger(HessianRmiConfig.class);

    @Autowired
    private SdwnController sdwnController;

    @Bean(name = "/sdwnController")
    public HessianServiceExporter pingServiceExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        he.setService(sdwnController);
        he.setServiceInterface(SdwnController.class);
        return he;
    }

}
