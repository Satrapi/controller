package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.log.NodeLogger;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMapUpdater implements MapUpdater
{
    protected final static Logger log = Logger.getLogger(AbstractMapUpdater.class);

    @Autowired
    protected NodeLogger nodeLogger;

    protected NetworkSession session;

    protected NetworkMap<SdwnNodeEntity> networkMap;

    protected WeightCalculator weightCalculator;

    protected NodeRepo nodeRepo;

    protected DeviceConnectionEntity device;

    @Override
    public SdwnNodeEntity addSink(DeviceConnectionEntity device)
    {
        log.debug("Update NetworkMap. Adding sink node: " + device.getSinkAddress() + " to " +
                          "NetworkMap.");

        SdwnNodeEntity sink = new SdwnNodeEntity(device.getSinkAddress(), device);
        sink.setType(SdwnNodeEntity.Type.SINK);
        sink.setStatus(SdwnNodeEntity.Status.ACTIVE);
        sink.setSession(session);

        sink = nodeRepo.save(sink);
        log.debug("Sink Node persisted: " + sink.toString());
        nodeLogger.newNode(sink);

        networkMap.addNode(sink);

        return sink;
    }

    @Autowired
    public void setSession(NetworkSession session)
    {
        this.session = session;
    }

    @Autowired
    public void setNetworkMap(NetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }

    @Override
    public NetworkMap<SdwnNodeEntity> getNetworkMap()
    {
        return networkMap;
    }

    @Autowired
    public void setWeightCalculator(WeightCalculator weightCalculator)
    {
        this.weightCalculator = weightCalculator;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    protected class Report
    {
        protected final SdwnNodeEntity src;
        protected final SdwnNodeEntity dst;
        protected final Set<SdwnNeighbor> neighbors;

        public Report(PacketEntity packet)
        {
            src = new SdwnNodeEntity(Integer.toUnsignedLong(packet.getSrcShortAdd()));
            dst = new SdwnNodeEntity(Integer.toUnsignedLong(packet.getDstShortAdd()));
            src.setDevice(packet.getDevice());
            dst.setDevice(packet.getDevice());
            neighbors = new HashSet<>(SdwnNeighbor.createNeighbors(packet));
        }
    }
}
