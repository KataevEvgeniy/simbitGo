package org.simbirgo.repositories;

import org.simbirgo.controllers.TransportController;
import org.simbirgo.entities.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Repository
public interface TransportEntityRepository extends JpaRepository<TransportEntity,Long> {
    

}
