package com.artronics.sdwn.controller.map;


import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;

public interface WeightCalculator<N extends Neighbor>
{
    double getWeight(SdwnNodeEntity node, N neighbor);
}
