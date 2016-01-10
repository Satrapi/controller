package com.artronics.sdwn.controller.map;


import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNode;

public interface WeightCalculator
{
    double getWeight(SdwnNode node, Neighbor neighbor);
}
