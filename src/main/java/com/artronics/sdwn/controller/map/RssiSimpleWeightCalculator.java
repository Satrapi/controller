package com.artronics.sdwn.controller.map;


import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNode;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RssiSimpleWeightCalculator implements WeightCalculator
{
    @Override
    public double getWeight(SdwnNode node, Neighbor neighbor)
    {
        return (double) (256 - neighbor.getRssi());
    }
}
