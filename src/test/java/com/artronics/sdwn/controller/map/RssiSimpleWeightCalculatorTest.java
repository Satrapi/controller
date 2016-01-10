package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RssiSimpleWeightCalculatorTest
{
    WeightCalculator weightCalculator = new RssiSimpleWeightCalculator();
    SdwnNodeEntity node = new SdwnNodeEntity(10L);
    Neighbor neighbor = new Neighbor(20L, 100);

    @Test
    public void it_should_return_right_weight()
    {
        double weight = weightCalculator.getWeight(node, neighbor);

        assertThat(weight, equalTo(156.0));

    }

}