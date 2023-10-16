package org.simbirgo.repositories;

import org.simbirgo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {

    public boolean existsByUsername(String username);

    public UserEntity findUserEntityByUsername(String username);



    public List<UserEntity> findAllByIdUserBetween(long startId, long endId);

}
