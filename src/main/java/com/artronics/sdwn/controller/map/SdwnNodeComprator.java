package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;

import java.util.Comparator;

public class SdwnNodeComprator implements Comparator<SdwnNodeEntity>
{
    private final static Logger log = Logger.getLogger(SdwnNodeComprator.class);

    @Override
    public int compare(SdwnNodeEntity o1, SdwnNodeEntity o2)
    {
        if (o1.equals(o2))
            return 0;

        Long id1 = o1.getDevice()==null ? 0:o1.getDevice().getId();
        Long id2 = o2.getDevice()==null ? 0:o2.getDevice().getId();

        if (id1>id2)
            return 1;
        if (id1<id2)
            return -1;

        else {
            Long add1 = o1.getAddress();
            Long add2 = o2.getAddress();

            if (add1>add2)
                return 1;
            if (add1<add2)
                return -1;
        }

        return 0;
    }
}
