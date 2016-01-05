package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SdwnNetworkRepo extends PagingAndSortingRepository<SdwnNetwork,Long>, SdwnNetworkCustomRepo
{
}
