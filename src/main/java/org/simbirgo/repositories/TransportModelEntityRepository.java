package org.simbirgo.repositories;

import org.simbirgo.entities.TransportModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportModelEntityRepository extends JpaRepository<TransportModelEntity,Long> {

    public TransportModelEntity findByModel(String model);
}
