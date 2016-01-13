package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.controller.map.SdwnNetworkMap;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.helpers.SeedNetworkGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeedNetworkGraphAndMap extends SeedNetworkGraph
{
    private NetworkMap<SdwnNodeEntity> networkMap;

    public void seed(boolean persist)
    {
        super.seed(persist);

        if (networkMap==null){
            networkMap = new SdwnNetworkMap();
        }

        updateNetMap();
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
                        sink:0
                          /   \
                        w50  w10
                        /      \
                     135 --w20-- 30
                       \         /
                       w25    w100
                         \    /
                          136
                           |     |
                          w30
                          /
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

    public NetworkMap<SdwnNodeEntity> getNetworkMap()
    {
        return networkMap;
    }

    @Autowired
    public void setNetworkMap(
            NetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }
}
