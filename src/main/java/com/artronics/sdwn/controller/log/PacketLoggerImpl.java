package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.packet.Packet;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PacketLoggerImpl implements PacketLogger
{
    private final static Logger log = Logger.getLogger(PacketLoggerImpl.class);

    @Override
    public void logBuffer(Packet packet)
    {
        logBuffer.debug(logBufferLevel(packet));
    }

    private static String logBufferLevel(Packet packet)
    {
        String s = String.format("%-8s", "BUFF");
        s += ":";
        List<Integer> content = packet.getContent();
        for (Integer c : content) {
            s += String.format("%-4d", c);
        }

        return s;
    }

    @Override
    public void logDevice(PacketEntity packet)
    {
        logDevice.debug(createDeviceLog(packet));
    }

    private static String createDeviceLog(PacketEntity packet){
        String s="Device Packet-> src: "+ packet.getSrcNode() + " dst: " + packet.getDstNode();
        return s;
    }

}
