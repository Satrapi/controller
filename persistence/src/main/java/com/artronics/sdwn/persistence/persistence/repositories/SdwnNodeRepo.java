package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnNodeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SdwnNodeRepo extends PagingAndSortingRepository<SdwnNodeEntity,Long>
{
}
