package com.artronics.sdwn.controller;

import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private BlockingQueue<PacketEntity> packetQueue;

    private Map<Long, DeviceConnectionService> devices = new HashMap<>();

    private DeviceConnectionRepo deviceRepo;

    private SdwnControllerEntity controllerEntity;

    private PacketRepo packetRepo;
    private SdwnControllerRepo controllerRepo;

    private volatile boolean isStarted = false;

    @Override
    public void start()
    {
        Thread packLst = new Thread(new PacketListener(),"PacketThr");
        isStarted = true;
        packLst.start();
    }

    @Transactional
    public void addPacket(PacketEntity packet)
    {
        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.create(packet,packet.getDevice().getId());

    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(
            BlockingQueue<PacketEntity> packetQueue)
    {
        this.packetQueue = packetQueue;
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

    @Override
    public String toString()
    {
        return super.toString();
    }

    private class PacketListener implements Runnable
    {
        @Override
        public void run()
        {
            try {
                while (isStarted)
                    while (!packetQueue.isEmpty()) {
                        PacketEntity packet = packetQueue.take();
                        addPacket(packet);
                    }

            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
