package org.simbirgo.repositories;

import org.simbirgo.entities.RentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentTypeEntityRepository extends JpaRepository<RentTypeEntity,Long> {

    public Optional<RentTypeEntity> findByRentType(String rentType);


}
