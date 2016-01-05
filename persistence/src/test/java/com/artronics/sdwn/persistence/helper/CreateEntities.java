package com.artronics.sdwn.persistence.helper;

import com.artronics.sdwn.persistence.entities.DeviceConnection;
import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import com.artronics.sdwn.persistence.entities.SdwnNodeEntity;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CreateEntities
{
    private final static Logger log = Logger.getLogger(CreateEntities.class);

    private static List<SdwnControllerEntity> createController(int num){
        List<SdwnControllerEntity> controllers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            controllers.add(createCtrl());
        }

        return controllers;
    }

    public static SdwnNetwork createNet(String ip)
    {
        SdwnNetwork net = new SdwnNetwork(ip);

        return net;
    }

    public static DeviceConnection createDevCon(String conStr)
    {
        DeviceConnection dev = new DeviceConnection(conStr);

        return dev;
    }
    public static DeviceConnection createDevCon(Long id,String conStr)
    {
        DeviceConnection dev = new DeviceConnection(conStr);
        dev.setId(id);

        return dev;
    }

    public static SdwnControllerEntity createCtrl()
    {
        SdwnControllerEntity con = new SdwnControllerEntity();
        con.setSinkAddress(43L);

        return con;
    }

    public static SdwnControllerEntity createCtrl(Long id,Long sinkAdd)
    {
        SdwnControllerEntity con = new SdwnControllerEntity();
        con.setId(id);
        con.setSinkAddress(sinkAdd);

        return con;
    }

    public static List<SdwnNodeEntity> createNodes(int num){
        List<SdwnNodeEntity> nodes = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            SdwnNodeEntity node = new SdwnNodeEntity(Integer.toUnsignedLong(i));
            nodes.add(node);
        }

        return nodes;
    }
}
