package com.artronics.sdwn.controller.address;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface NodeAddressResolver
{
    PacketEntity resolveNodeAddress(PacketEntity packet);
}
