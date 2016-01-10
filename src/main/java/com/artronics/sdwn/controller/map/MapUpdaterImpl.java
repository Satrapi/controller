package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Primary
public class MapUpdaterImpl implements MapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    private NodeRepo nodeRepo;

    @Override
    public SdwnNodeEntity addSink(DeviceConnectionEntity device)
    {

        log.debug("Update NetworkMap. Adding sink node: "+device.getSinkAddress() + " to NetworkMap.");

        SdwnNodeEntity sink = new SdwnNodeEntity(device.getSinkAddress());
        sink.setDevice(device);
        sink.setType(SdwnNodeEntity.Type.SINK);
        sink.setStatus(SdwnNodeEntity.Status.ACTIVE);

        nodeRepo.save(sink);
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

    }

    @Resource(name = "netMap")
    public void setNetMap(
            Map<Long, NetworkMap<SdwnNodeEntity>> netMap)
    {
        this.netMap = netMap;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

}
