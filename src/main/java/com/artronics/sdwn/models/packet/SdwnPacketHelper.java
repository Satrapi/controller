package com.artronics.sdwn.models.packet;

import java.util.ArrayList;
import java.util.List;

import static com.artronics.sdwn.models.packet.Packet.ByteIndex.*;

public final class SdwnPacketHelper
{
    public static int getNetId(List<Integer> content)
    {
        return content.get(NET_ID.getValue());
    }

    public static int getSize(List<Integer> content)
    {
        return content.get(LENGTH.getValue());
    }

    @Deprecated
    public static boolean validate(List<Integer> receivedBytes)
    {
        final int length = getLength(receivedBytes);

        //Now place your validation rules here.
        return (length == receivedBytes.size());

    }

    public static int getLength(List<Integer> receivedBytes)
    {
        return receivedBytes.get(LENGTH.getValue());
    }

    public static Packet.Type getType(List<Integer> receivedBytes)
    {
        int typeIndex = receivedBytes.get(TYPE.getValue());
        for (Packet.Type type : Packet.Type.values()) {
            if (typeIndex == type.getValue())
                return type;
        }

        return Packet.Type.MALFORMED;
    }

    // gets two part of address and returns int address
    public static int getIntAddress(int sourceL, int sourceH)
    {
        int addr = sourceH;
        addr = (addr << 8) | sourceL;

        return addr;
    }

    public static int getSourceAddress(List<Integer> bytes)
    {
        int addH = bytes.get(SOURCE_H.getValue());
        int addL = bytes.get(SOURCE_L.getValue());
        return joinAddresses(addH, addL);
    }

    @Deprecated
    public static int[] splitAddress(int address)
    {
        int addH = (address & 0x0000FF00) >> 8;
        int addL = address & 0x000000FF;

        return new int[]{addH, addL};
    }

    public static int getHighAddress(int address)
    {
        int addH = (address & 0x0000FF00) >> 8;

        return addH;
    }

    public static int getLowAddress(int address)
    {
        int addL = address & 0x000000FF;

        return addL;
    }

    public static int joinAddresses(int add_H, int add_L)
    {
        return (add_H << 8) | add_L;
    }

    public static List<Integer> createDummyPayload(int size)
    {
        List<Integer> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add(i);
        }

        return data;
    }

    public static int getDestinationAddress(List<Integer> bytes)
    {
        int addHIndex = DESTINATION_H.getValue();
        int addLIndex = DESTINATION_L.getValue();
        int addH = bytes.get(addHIndex);
        int addL = bytes.get(addLIndex);

        return joinAddresses(addH, addL);
    }

    public static int getNextHopAddress(List<Integer> bytes)
    {
        int addHIndex = NEXT_HOP_H.getValue();
        int addLIndex = NEXT_HOP_L.getValue();
        int addH = bytes.get(addHIndex);
        int addL = bytes.get(addLIndex);

        return joinAddresses(addH, addL);
    }
}
