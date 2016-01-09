package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.SdwnController;
import com.artronics.sdwn.controller.remote.DeviceRegistrationService;
import com.artronics.sdwn.controller.remote.DeviceRegistrationServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.annotation.PostConstruct;

@Configuration
public class HessianRmiConfig
{
    private final static Logger log = Logger.getLogger(HessianRmiConfig.class);

    @Autowired
    private SdwnController sdwnController;

    private DeviceRegistrationService registrationService;

    @PostConstruct
    public void initBean(){
        this.registrationService = new DeviceRegistrationServiceImpl();
    }

    @Bean
    public DeviceRegistrationService getRegistrationService(){
        return this.registrationService;
    }


    @Bean(name = "/sdwnController")
    public HessianServiceExporter sdwnControllerServiceExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        log.debug("Creating SdwnController Hessian service: "+sdwnController.toString());
        he.setService(sdwnController);
        he.setServiceInterface(SdwnController.class);
        return he;
    }

    @Bean(name = "/registerDevice")
    public HessianServiceExporter deviceRegistrationExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        log.debug("Creating DeviceRegistration Hessian service: "+registrationService.toString());
        he.setService(registrationService);
        he.setServiceInterface(DeviceRegistrationService.class);
        return he;
    }

}
