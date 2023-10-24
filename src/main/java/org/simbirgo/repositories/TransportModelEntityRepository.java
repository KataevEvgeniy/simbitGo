package org.simbirgo.repositories;

import org.simbirgo.entities.TransportModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportModelEntityRepository extends JpaRepository<TransportModelEntity,Long> {

    public Optional<TransportModelEntity> findByModel(String model);
}
