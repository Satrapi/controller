package com.artronics.sdwn.log;

import com.artronics.gsdwn.suren.entities.packet.Packet;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PacketLoggerImpl implements PacketLogger
{
    private final static Logger log = Logger.getLogger(PacketLoggerImpl.class);

    @Override
    public void logPacket(Packet packet, Level level)
    {
        switch (level){
            case BUFFER:
                logBuffer.debug(logBufferLevel(packet));
        }
    }

    private static String logBufferLevel(Packet packet){
        String s =String.format("%-8s","BUFF");
        s+=":";
        List<Integer> content = packet.getContent();
        for (Integer c:content){
            s+=String.format("%-4d",c);
        }

        return s;
    }
}
