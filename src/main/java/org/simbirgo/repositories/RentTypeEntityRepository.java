package org.simbirgo.repositories;

import org.simbirgo.entities.RentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentTypeEntityRepository extends JpaRepository<RentTypeEntity,Long> {

    public RentTypeEntity findByRentType(String rentType);


}
