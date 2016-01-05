package com.artronics.sdwn.helpers;

import com.artronics.sdwn.models.packet.Packet;
import com.artronics.sdwn.models.packet.SdwnPacketHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeBufferFactory
{
    private final static Logger log = Logger.getLogger(FakeBufferFactory.class);
    private static final int HEADER_LEN = 10;

    public static List<Integer> createData(int src, int dst, int payloadLen)
    {
        List<Integer> buf = new ArrayList<>(createHeader(src, dst, payloadLen+HEADER_LEN, Packet.Type.DATA));
        buf.addAll(createPayload(payloadLen));

        return buf;
    }

    public static List<Integer> createABuffer(){
        List<Integer> buff = new ArrayList<>(createAHeader());
        buff.addAll(createPayload(10));

        return buff;
    }
    public static List<Integer> createPayload(int len){
        List<Integer> pl = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            pl.add(i);
        }

        return pl;
    }

    public static List<Integer> createHeader(int src, int dst, int len, Packet.Type type){
        return new ArrayList<>(Arrays.asList(
                len,
                Packet.DEF_NET_ID,
                SdwnPacketHelper.getHighAddress(src),
                SdwnPacketHelper.getLowAddress(src),
                SdwnPacketHelper.getHighAddress(dst),
                SdwnPacketHelper.getLowAddress(dst),
                type.getValue(),
                Packet.DEF_MAX_TTL,
                SdwnPacketHelper.getHighAddress(0),
                SdwnPacketHelper.getLowAddress(0)
        ));
    }
    public static List<Integer> createAHeader(){
        return createHeader(30, 0, 20, Packet.Type.DATA);
    }
}
