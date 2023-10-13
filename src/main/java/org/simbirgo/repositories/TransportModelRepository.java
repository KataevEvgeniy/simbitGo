package org.simbirgo.repositories;

import org.simbirgo.entities.TransportModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportModelRepository extends JpaRepository<TransportModelEntity,Long> {
}
