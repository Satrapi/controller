package com.artronics.sdwn.models.packet;

import java.util.List;

public interface Packet
{
    Integer DEF_NET_ID=1;
    Integer DEF_MAX_TTL=20;

    List<Integer> getContent();

    enum Type
    {
        DATA(0),
        MALFORMED(1),
        REPORT(2),
        BEACON(3),
        RL_REQ(128),
        RL_RES(4),
        OPN_PT(5);

        private int value;

        Type(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }


    enum Direction
    {
        RX,
        TX
    }
    enum ByteIndex
    {
        LENGTH(0),
        NET_ID(1),
        SOURCE_H(2),
        SOURCE_L(3),
        DESTINATION_H(4),
        DESTINATION_L(5),
        TYPE(6),
        TTL(7),
        NEXT_HOP_H(8),
        NEXT_HOP_L(9),
        //        DISTANCE(10),
        BATTERY(11),
//        NEIGHBOUR(12),
//        START_TIME_H(11),
//        START_TIME_L(12),
//        STOP_TIME_H(13),
//        STOP_TIME_L(14),;
        ;
        private int value;

        ByteIndex(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }
}
