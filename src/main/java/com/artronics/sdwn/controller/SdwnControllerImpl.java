package com.artronics.sdwn.controller;

import com.artronics.sdwn.controller.map.MapUpdater;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private NetworkSession session;

    private BlockingQueue<PacketEntity> packetQueue;

    private MapUpdater mapUpdater;

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
        packet =mapUpdater.addPacket(packet);

        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.save(packet);
    }

    @Override
    public void stop()
    {

    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(
            BlockingQueue<PacketEntity> packetQueue)
    {
        this.packetQueue = packetQueue;
    }

    @Autowired
    public void setSession(NetworkSession session)
    {
        this.session = session;
    }

    @Autowired
    public void setMapUpdater(MapUpdater mapUpdater)
    {
        this.mapUpdater = mapUpdater;
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
