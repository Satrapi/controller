package com.artronics.sdwn.controller.map;


import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RssiSimpleWeightCalculator implements WeightCalculator<SdwnNeighbor>
{
    @Override
    public double getWeight(SdwnNodeEntity node, SdwnNeighbor neighbor)
    {
        return (double) (256 - neighbor.getRssi());
    }
}
