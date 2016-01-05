package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.DeviceConnection;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeviceConnectionRepo extends PagingAndSortingRepository<DeviceConnection,Long>,
                                              DeviceConnectionCustomRepo
{
}
