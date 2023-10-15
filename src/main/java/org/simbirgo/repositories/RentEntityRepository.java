package org.simbirgo.repositories;

import org.simbirgo.entities.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentEntityRepository extends JpaRepository<RentEntity, Long> {

    public List<RentEntity> findAllByIdUser(Long userId);

    public List<RentEntity> findAllByIdTransport(Long transportId);

}
