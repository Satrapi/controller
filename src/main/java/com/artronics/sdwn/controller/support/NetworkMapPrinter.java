package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Node;

public interface NetworkMapPrinter<N extends Node>
{
    String printNetworkMap(NetworkMap<N> map);

    String printNetworkMap(NetworkMap<N> map, DeviceConnectionEntity device);
}
