package com.artronics.sdwn.controller.support;


import com.artronics.sdwn.controller.map.WeightCalculator;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNode;
import org.springframework.stereotype.Component;

@Component("fixedWeightCalculator")
public class FixedWeightCalculator implements WeightCalculator
{
    private double weight;

    @Override
    public double getWeight(SdwnNode node, Neighbor neighbor)
    {
        return 100;
    }
}
