package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import com.artronics.sdwn.domain.repositories.SessionRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeedNetworkGraph
{
    private final static Logger log = Logger.getLogger(SeedNetworkGraph.class);
    private NetworkSession activeSession = new NetworkSession();
    private NetworkSession expiredSession = new NetworkSession();

    private SdwnControllerEntity controller = new SdwnControllerEntity("http://controller:port");

    private DeviceConnectionEntity device1 = new DeviceConnectionEntity("http://device1:port");
    private DeviceConnectionEntity device2 = new DeviceConnectionEntity("http://device2:port");

    private SdwnNodeEntity sink1 = new SdwnNodeEntity(0L, device1);
    private SdwnNodeEntity sink2 = new SdwnNodeEntity(1L, device2);

    private SdwnNodeEntity sameAddNode1 = new SdwnNodeEntity(30L, device1);
    private SdwnNodeEntity sameAddNode2 = new SdwnNodeEntity(30L, device2);

    private SdwnNodeEntity node135 = new SdwnNodeEntity(135L, device1);
    private SdwnNodeEntity node136 = new SdwnNodeEntity(136L, device1);
    private SdwnNodeEntity node137 = new SdwnNodeEntity(137L, device1);

    private SdwnNodeEntity node245 = new SdwnNodeEntity(245L, device2);
    private SdwnNodeEntity node246 = new SdwnNodeEntity(246L, device2);

    private SessionRepo sessionRepo;
    private SdwnControllerRepo controllerRepo;
    private DeviceConnectionRepo deviceRepo;
    private NodeRepo nodeRepo;

    private boolean persist = false;
    private NetworkMap<SdwnNodeEntity, Neighbor> networkMap;

    public void seed(boolean persist)
    {
        createNetwork();
        persistNetwork(persist);

        if (persist) {
            createNodes(expiredSession);
        }
        createNodes(activeSession);

        persistNodes(persist);


        updateNetMap();
    }

    private void createNetwork()
    {
        expiredSession.setStatus(NetworkSession.Status.EXPIRED);

        device1.setSdwnController(controller);
        device1.setSinkAddress(0L);

        device2.setSdwnController(controller);
        device2.setSinkAddress(1L);
    }

    private void createNodes(NetworkSession session)
    {
        sink1.setSession(session);
        sink2.setSession(session);

        sameAddNode1.setSession(session);
        sameAddNode2.setSession(session);

        node135.setSession(session);
        node136.setSession(session);
        node137.setSession(session);

        node245.setSession(session);
        node246.setSession(session);
    }

    private void persistNetwork(boolean persist)
    {
        if (persist) {
            controllerRepo.save(controller);

            sessionRepo.save(expiredSession);
            sessionRepo.save(activeSession);

            deviceRepo.save(device1);
            deviceRepo.save(device2);
        }else {
            expiredSession.setId(1L);
            activeSession.setId(2L);

            controller.setId(1L);

            device1.setId(1L);
            device2.setId(2L);
        }
    }

    private void persistNodes(boolean persist)
    {
        if (persist) {
            nodeRepo.save(sink1);
            nodeRepo.save(sink2);

            nodeRepo.save(sameAddNode1);
            nodeRepo.save(sameAddNode2);

            nodeRepo.save(node135);
            nodeRepo.save(node136);
            nodeRepo.save(node137);

            nodeRepo.save(node245);
            nodeRepo.save(node246);
        }else {
            sink1.setId(0L);
            sink2.setId(1L);

            sameAddNode1.setId(100L);
            sameAddNode2.setId(200L);

            node135.setId(135L);
            node136.setId(136L);
            node137.setId(137L);

            node245.setId(245L);
            node246.setId(246L);
        }
    }

    private void updateNetMap()
    {
        addNodes();
        addLinks();
    }

    private void addNodes()
    {
        networkMap.addNode(sink1);
        networkMap.addNode(sink2);

        networkMap.addNode(sameAddNode1);
        networkMap.addNode(sameAddNode2);

        networkMap.addNode(node135);
        networkMap.addNode(node136);
        networkMap.addNode(node137);

        networkMap.addNode(node245);
        networkMap.addNode(node246);
    }

    private void addLinks()
    {
        /*
            Device1
            node number 30 is sameAddNode
            look for code for weight values
            Graph is like
                        sink|
                         __   __
                        |       |
                     135        30
                       |___   __|
                           |  |
                            136
                                |
                                137

         */
        networkMap.addLink(sink1, node135, 50);
        networkMap.addLink(sink1, sameAddNode1, 10);
        networkMap.addLink(node135, sameAddNode1, 20);
        networkMap.addLink(node135, node136, 25);
        networkMap.addLink(sameAddNode1, node136, 100);
        networkMap.addLink(node136, node137, 30);

        /*
            Device2
            node number 30 is sameAddNode
            look for code for weight values
            Graph is like
                        sink|
                            |
                            245
                         __ | __
                        |       |
                     246 -----  30

         */

        networkMap.addLink(sink2, node245, 10);
        networkMap.addLink(node245, sameAddNode2, 100);
        networkMap.addLink(node246, node245, 20);
        networkMap.addLink(node246, sameAddNode2, 30);
    }

    @Autowired
    public void setSessionRepo(SessionRepo sessionRepo)
    {
        this.sessionRepo = sessionRepo;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    @Autowired
    public void setSdwnControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

    @Autowired
    public void setDeviceRepo(DeviceConnectionRepo deviceRepo)
    {
        this.deviceRepo = deviceRepo;
    }

    @Autowired
    public void setNetworkMap(
            NetworkMap<SdwnNodeEntity,Neighbor> networkMap)
    {
        this.networkMap = networkMap;
    }

    public NetworkSession getActiveSession()
    {
        return activeSession;
    }

}
