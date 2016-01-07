package com.artronics.sdwn.log;

import com.artronics.sdwn.domain.entities.packet.Packet;
import org.apache.log4j.Logger;

public interface PacketLogger
{
    Logger logBuffer = Logger.getLogger("com.artronics.sdwn.packet.buffer");
    Logger logSdwnPacket = Logger.getLogger("com.artronics.sdwn.packet.sdwnPacket");

    void logPacket(Packet packet, Level level);

    enum Level{
        BUFFER,
    }
}
