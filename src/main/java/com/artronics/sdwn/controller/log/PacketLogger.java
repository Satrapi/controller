package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.packet.Packet;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;

public interface PacketLogger
{
    Logger logBuffer = Logger.getLogger("com.artronics.sdwn.logger.packet.buffer");
    Logger logDevice = Logger.getLogger("com.artronics.sdwn.logger.packet.device");

    void logBuffer(Packet packet);
    void logDevice(PacketEntity packet);
}
