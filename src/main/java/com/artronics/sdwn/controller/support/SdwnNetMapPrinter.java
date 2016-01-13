package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.controller.map.SdwnNodeComprator;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SdwnNetMapPrinter implements NetworkMapPrinter<SdwnNodeEntity>
{
    @Override
    public String printNetworkMap(NetworkMap<SdwnNodeEntity> map, DeviceConnectionEntity device)
    {
        List<SdwnNodeEntity> nodes = map.getAllNodes();
        nodes.sort(new SdwnNodeComprator());

        String s = "\n";
        s += device == null ? "\nDevice: null" : device.toString();
        s += "\n";

        for (SdwnNodeEntity node : nodes) {
            if (!node.getDevice().equals(device))
                break;

            Long nodeAdd = node.getAddress();
            s += "\t" + "Node: " + nodeAdd + "\n";

            Set<Neighbor<SdwnNodeEntity>> neighbors = map.getNeighbors(node);
            for (Neighbor<SdwnNodeEntity> neighbor : neighbors) {
                SdwnNodeEntity n = neighbor.getNode();
                Long neighborAdd = n.getAddress();
                s += "\t\t" + formatNeighbor(nodeAdd, neighbor.getWeight(), neighborAdd) + "\n";
            }
        }

        return s;
    }

    @Override
    public String printNetworkMap(NetworkMap<SdwnNodeEntity> map)
    {
        List<SdwnNodeEntity> nodes = map.getAllNodes();
        nodes.sort(new SdwnNodeComprator());

        String s = "\n";
        DeviceConnectionEntity device = null;
        for (SdwnNodeEntity node : nodes) {
            if (!node.getDevice().equals(device)) {
                device = node.getDevice();
                s += device == null ? "\nDevice: null" : device.toString();
                s += "\n";
            }
            Long nodeAdd = node.getAddress();
            s += "\t" + "Node: " + nodeAdd + "\n";

            Set<Neighbor<SdwnNodeEntity>> neighbors = map.getNeighbors(node);
            for (Neighbor<SdwnNodeEntity> neighbor : neighbors) {
                SdwnNodeEntity n = neighbor.getNode();
                Long neighborAdd = n.getAddress();
                s += "\t\t" + formatNeighbor(nodeAdd, neighbor.getWeight(), neighborAdd) + "\n";
            }

        }

        return s;
    }

    static String formatNeighbor(Long nodeAdd, Double weight, Long neiAdd)
    {
        return String.format("Node: %-5d <---[ %-5.0f ]---> Node: %-5d", nodeAdd, weight, neiAdd);
    }
}
