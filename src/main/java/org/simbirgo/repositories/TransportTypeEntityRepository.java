package org.simbirgo.repositories;

import org.simbirgo.entities.TransportTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportTypeEntityRepository extends JpaRepository<TransportTypeEntity,Long> {

    public TransportTypeEntity findByTransportType(String type);



}
