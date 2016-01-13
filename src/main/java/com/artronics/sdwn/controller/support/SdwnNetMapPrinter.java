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
        s += "\n\n";

        for (SdwnNodeEntity node : nodes) {
            if (!node.getDevice().equals(device))
                break;

            Long nodeAdd = node.getAddress();
            s += "\t" + "Node: " + nodeAdd + "\n";

            Set<Neighbor<SdwnNodeEntity>> neighbors = map.getNeighbors(node);
            for (Neighbor<SdwnNodeEntity> neighbor : neighbors) {
                SdwnNodeEntity n = neighbor.getNode();
                Long neighborAdd = n.getAddress();
                s += "\t\t" + formatNeighbor(node, neighbor.getWeight(), neighbor.getNode()) + "\n";
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
                s+="\n";
                s += device == null ? "\nDevice: null" : device.toString();
                s += "\n\\\n";
            }
            Long nodeAdd = node.getAddress();
            s += " \\_" + node.toString() + "\n";

            Set<Neighbor<SdwnNodeEntity>> neighbors = map.getNeighbors(node);
            for (Neighbor<SdwnNodeEntity> neighbor : neighbors) {
                SdwnNodeEntity n = neighbor.getNode();
                Long neighborAdd = n.getAddress();
                s += " |\t\t" + formatNeighbor(node, neighbor.getWeight(), neighbor.getNode()) + "\n";
            }
            s+=" |\n";

        }

        return s;
    }

    static String formatNeighbor(SdwnNodeEntity n1, Double weight, SdwnNodeEntity n2)
    {
        String s = n1.toString();
        s+=String.format(" <---[ %-5.0f ]---> " ,weight);
        s += n2.toString();
        return s;
    }
}
