package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.log.NodeLogger;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.Node;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class MapUpdaterImpl implements MapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    @Autowired
    private NodeLogger nodeLogger;

    private SdwnNetworkMap<SdwnNodeEntity> networkMap;

//    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;
//
//    //This is the map associated with one device.
//    private NetworkMap<SdwnNodeEntity> subMap;

    private WeightCalculator weightCalculator;

    private NodeRepo nodeRepo;

    private DeviceConnectionEntity device;

    @Override
    public SdwnNodeEntity addSink(DeviceConnectionEntity device)
    {
        log.debug("Update NetworkMap. Adding sink node: " + device.getSinkAddress() + " to " +
                          "NetworkMap.");

        SdwnNodeEntity sink = new SdwnNodeEntity(device.getSinkAddress(), device);
        sink.setType(SdwnNodeEntity.Type.SINK);
        sink.setStatus(SdwnNodeEntity.Status.ACTIVE);

        sink = nodeRepo.save(sink);
        log.debug("Sink Node persisted: " + sink.toString());
        nodeLogger.newNode(sink);

        networkMap.addNode(sink);

        return sink;
    }

    @Override
    public void addPacket(PacketEntity packet)
    {
        device = packet.getDevice();

        switch (packet.getType()) {
            case REPORT:
                processReportPacket(packet);
                break;
        }

    }

    private void processReportPacket(PacketEntity packet)
    {
        Long deviceId = packet.getDevice().getId();

        Report report = new Report(packet);
        SdwnNodeEntity srcNode = report.src;

        if (!networkMap.contains(srcNode)) {
            srcNode = addNode(srcNode);
        }

        Set<SdwnNodeEntity> currentNeighbors = networkMap.getNeighbors
                (srcNode);
        for (Neighbor neighbor : report.neighbors) {

            if (!networkMap.contains(neighbor)) {
                addNode(neighbor);
                connect(srcNode, neighbor);
            }else if (currentNeighbors.contains(neighbor)) {
                currentNeighbors.remove(neighbor);
                connect(srcNode, neighbor);
            }else {
                connect(srcNode, neighbor);
            }
        }

        if (!currentNeighbors.isEmpty()) {
            for (Node neighbor : currentNeighbors) {
                SdwnNodeEntity nodeEntity = (SdwnNodeEntity) neighbor;
                networkMap.removeLink(srcNode, nodeEntity);
                //Look for other link with this node if
                //there is no link this node just became islan
                //and should be removed from graph
                if (networkMap.isIsland(nodeEntity)) {
                    networkMap.removeNode(nodeEntity);
                }
            }
        }

    }

    private void connect(SdwnNodeEntity node, Neighbor neighbor)
    {
        double weight = weightCalculator.getWeight(node, neighbor);
//        SdwnNodeEntity n = (SdwnNodeEntity) neighbor ;
        networkMap.addLink(node, neighbor, weight);
    }

    private SdwnNodeEntity addNode(SdwnNodeEntity node)
    {
        log.debug("Persisting new Node: " + node.toString());
        node = nodeRepo.create(node, device.getId());
        networkMap.addNode(node);
        nodeLogger.newNode(node);

        return node;
    }

    @Autowired
    public void setNetworkMap(SdwnNetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }

    @Override
    public NetworkMap<SdwnNodeEntity> getNetworkMap()
    {
        return networkMap;
    }
//
//    @Resource(name = "netMap")
//    public void setNetMap(
//            Map<Long, NetworkMap<SdwnNodeEntity>> netMap)
//    {
//        this.netMap = netMap;
//    }

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
        private final SdwnNodeEntity src;
        private final SdwnNodeEntity dst;
        private final Set<Neighbor> neighbors;

        public Report(PacketEntity packet)
        {
            src = new SdwnNodeEntity(Integer.toUnsignedLong(packet.getSrcShortAdd()));
            dst = new SdwnNodeEntity(Integer.toUnsignedLong(packet.getDstShortAdd()));
            src.setDevice(packet.getDevice());
            dst.setDevice(packet.getDevice());
            neighbors = new HashSet<>(Neighbor.createNeighbors(packet));
        }
    }
}
