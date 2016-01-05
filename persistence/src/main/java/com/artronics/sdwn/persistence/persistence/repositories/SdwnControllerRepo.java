package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SdwnControllerRepo extends PagingAndSortingRepository<SdwnControllerEntity,Long>, SdwnControllerCustomRepo
{
}
