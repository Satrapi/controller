package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.Node;
import com.artronics.sdwn.domain.entities.node.SdwnNode;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Primary
public class MapUpdaterImpl implements MapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    private WeightCalculator weightCalculator;

    private NodeRepo nodeRepo;

    @Override
    public SdwnNodeEntity addSink(DeviceConnectionEntity device)
    {
        log.debug("Update NetworkMap. Adding sink node: "+device.getSinkAddress() + " to NetworkMap.");

        SdwnNodeEntity sink = new SdwnNodeEntity(device.getSinkAddress(),device);
        sink.setType(SdwnNodeEntity.Type.SINK);
        sink.setStatus(SdwnNodeEntity.Status.ACTIVE);

        sink = nodeRepo.save(sink);
        log.debug("Sink Node persisted: " +sink.toString());

        log.debug("Registering new NetworkMap for this device.");
        NetworkMap map =  new SdwnNetworkMap();
        netMap.put(device.getId(),map);

        map.addNode(sink);

        return sink;
    }

    @Override
    public void addPacket(PacketEntity packet)
    {
        switch (packet.getType()) {
            case REPORT:
                processReportPacket(packet);
                break;
        }

    }

    private void processReportPacket(PacketEntity packet)
    {

    }

    @Resource(name = "netMap")
    public void setNetMap(
            Map<Long, NetworkMap<SdwnNodeEntity>> netMap)
    {
        this.netMap = netMap;
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

    private class Report
    {
        private final Node src;
        private final Node dst;
        private final Set<Neighbor> neighbors;

        public Report(PacketEntity packet)
        {
            src = new SdwnNode(Integer.toUnsignedLong(packet.getSrcShortAdd()));
            dst = new SdwnNode(Integer.toUnsignedLong(packet.getDstShortAdd()));
            neighbors = new HashSet<>(Neighbor.createNeighbors(packet));
        }
    }
}
