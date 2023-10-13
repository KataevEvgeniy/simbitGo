package org.simbirgo.repositories;

import org.simbirgo.entities.BlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistEntityRepository extends JpaRepository<BlacklistEntity,Long> {


    public boolean existsByToken(String token);

}
