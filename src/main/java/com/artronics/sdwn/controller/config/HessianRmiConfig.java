package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.SdwnController;
import com.artronics.sdwn.controller.remote.DeviceRegistrationService;
import com.artronics.sdwn.controller.remote.DeviceRegistrationServiceImpl;
import com.artronics.sdwn.controller.services.PacketService;
import com.artronics.sdwn.controller.services.PacketServiceImpl;
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
    private PacketService packetService;

    @PostConstruct
    public void initBean(){
        this.registrationService = new DeviceRegistrationServiceImpl();
        this.packetService = new PacketServiceImpl();
    }

    @Bean
    public DeviceRegistrationService getRegistrationService(){
        return this.registrationService;
    }

    @Bean
    public PacketService getPacketService()
    {
        return this.packetService;
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

    @Bean(name = "/packetService")
    public HessianServiceExporter packetServiceExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        log.debug("Creating PacketService Hessian service: "+packetService.toString());
        he.setService(packetService);
        he.setServiceInterface(PacketService.class);
        return he;
    }

}
