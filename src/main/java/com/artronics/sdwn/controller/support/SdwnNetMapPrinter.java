package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.controller.map.SdwnNodeComprator;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.Node;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;

import java.util.List;
import java.util.Set;

public class SdwnNetMapPrinter implements NetworkMapPrinter<SdwnNodeEntity,Neighbor>
{

    @Override
    public String printNetworkMap(NetworkMap<SdwnNodeEntity,Neighbor> map)
    {
        List<SdwnNodeEntity> nodes = map.getAllNodes();
        nodes.sort(new SdwnNodeComprator());

        String s = "";
        DeviceConnectionEntity device = null;
        for (SdwnNodeEntity node : nodes) {
            if (!node.getDevice().equals(device)) {
                device = node.getDevice();
                s += device == null ? "Device: null" : device.toString();
                s += "\n";
            }
            Long nodeAdd = node.getAddress();
            s += "\t" + "Node: " + nodeAdd + "\n";

            Set<Neighbor> neighbors = map.getNeighbors(node);
            for (SdwnNodeEntity neighbor : neighbors) {
                Long neighborAdd = neighbor.getAddress();
                s += "\t\t" + formatNeighbor(nodeAdd, null, neighborAdd) + "\n";
            }

        }

        return s;
    }

    static String formatNeighbor(Long nodeAdd, Double weight, Long neiAdd)
    {
        return String.format("Node: %-5d <---[ %-5d ]---> Node: %-5d", nodeAdd, weight, neiAdd);
    }
}
