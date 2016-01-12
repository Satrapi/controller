package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.node.Node;

public interface NetworkMapPrinter<N extends Node,M extends N>
{
    String printNetworkMap(NetworkMap<N,M> map);
}
