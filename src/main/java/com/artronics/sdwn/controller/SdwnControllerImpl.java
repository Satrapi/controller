package com.artronics.sdwn.controller;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import com.caucho.hessian.client.HessianProxyFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private Map<Long, SwitchingNetworkService> devices = new HashMap<>();

    private DeviceConnectionRepo deviceRepo;

    private SdwnControllerEntity controllerEntity;

    private PacketRepo packetRepo;
    private SdwnControllerRepo controllerRepo;

    @Override
    @Transactional
    public DeviceConnectionEntity registerDeviceConnection(DeviceConnectionEntity device) throws
            MalformedURLException
    {
        log.debug("Registering new Switching Network Device...");
        DeviceConnectionEntity persistedDev;

        DeviceConnectionEntity dev = deviceRepo.findByUrl(device.getUrl());

        if (dev == null) {
            persistedDev = deviceRepo.create(device, controllerEntity.getId());
        }else {
            dev.setSdwnController(controllerEntity);
            persistedDev = deviceRepo.create(dev, controllerEntity.getId());
        }

        log.debug("Device persisted: " + persistedDev.toString());

        registerDevService(persistedDev);

        return persistedDev;
    }

    private void registerDevService(DeviceConnectionEntity device) throws MalformedURLException
    {
        final String serviceUrl = device.getUrl() + "/deviceService";
        HessianProxyFactory factory = new HessianProxyFactory();
        SwitchingNetworkService service;
        service = (SwitchingNetworkService)
                factory.create(SwitchingNetworkService.class, serviceUrl);

        devices.put(device.getId(), service);
    }

    @Override
    public void addPacket(PacketEntity packet)
    {
        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.save(packet);

    }

    @Autowired
    public void setControllerEntity(
            SdwnControllerEntity controllerEntity)
    {
        this.controllerEntity = controllerEntity;
    }

    @Autowired
    public void setDeviceRepo(DeviceConnectionRepo deviceRepo)
    {
        this.deviceRepo = deviceRepo;
    }

    @Autowired
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo)
    {
        this.packetRepo = packetRepo;
    }
}
