package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.PacketEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PacketRepo extends PagingAndSortingRepository<PacketEntity,Long>
{
}
