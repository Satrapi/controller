package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import com.artronics.sdwn.domain.repositories.SessionRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateNodesGraph
{
    private final static Logger log = Logger.getLogger(CreateNodesGraph.class);
    private NetworkSession activeSession = new NetworkSession();
    private NetworkSession expiredSession = new NetworkSession();

    private SdwnControllerEntity controller = new SdwnControllerEntity("http://controller:port");

    private DeviceConnectionEntity device1 = new DeviceConnectionEntity("http://device1:port");
    private DeviceConnectionEntity device2 = new DeviceConnectionEntity("http://device2:port");

    private SdwnNodeEntity sink1 = new SdwnNodeEntity(0L,device1);
    private SdwnNodeEntity sink2 = new SdwnNodeEntity(1L,device2);

    private SdwnNodeEntity sameAddNode1 = new SdwnNodeEntity(30L,device1);
    private SdwnNodeEntity sameAddNode2 = new SdwnNodeEntity(30L,device2);

    private SdwnNodeEntity node135 = new SdwnNodeEntity(135L,device1);
    private SdwnNodeEntity node136 = new SdwnNodeEntity(136L,device1);
    private SdwnNodeEntity node137 = new SdwnNodeEntity(137L,device1);

    private SdwnNodeEntity node245 = new SdwnNodeEntity(245L,device2);
    private SdwnNodeEntity node246 = new SdwnNodeEntity(246L,device2);

    private SessionRepo sessionRepo;
    private SdwnControllerRepo controllerRepo;
    private DeviceConnectionRepo deviceRepo;
    private NodeRepo nodeRepo;

    public void createSimulatedNetwork(){
        controllerRepo.save(controller);

        device1.setSdwnController(controller);
        device1.setSinkAddress(0L);

        device2.setSdwnController(controller);
        device2.setSinkAddress(1L);

        deviceRepo.save(device1);
        deviceRepo.save(device2);

        expiredSession.setStatus(NetworkSession.Status.EXPIRED);

        sessionRepo.save(expiredSession);
        sessionRepo.save(activeSession);

        createNetwork(activeSession);
        createNetwork(expiredSession);
    }

    public void createNetwork(NetworkSession session)
    {
        persistNodes(session);
    }

    private void persistNodes(NetworkSession session){
        sink1.setSession(session);
        sink2.setSession(session);

        sameAddNode1.setSession(session);
        sameAddNode2.setSession(session);

        node135.setSession(session);
        node136.setSession(session);
        node137.setSession(session);

        node245.setSession(session);
        node246.setSession(session);

        nodeRepo.save(sink1);
        nodeRepo.save(sink2);

        nodeRepo.save(sameAddNode1);
        nodeRepo.save(sameAddNode2);

        nodeRepo.save(node135);
        nodeRepo.save(node136);
        nodeRepo.save(node137);

        nodeRepo.save(node245);
        nodeRepo.save(node246);
    }

    private void persistNeighbors(NetworkSession session){
        /*
            Device1
         */

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
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

    @Autowired
    public void setDeviceRepo(DeviceConnectionRepo deviceRepo)
    {
        this.deviceRepo = deviceRepo;
    }

}
