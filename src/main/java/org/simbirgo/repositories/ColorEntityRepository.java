package org.simbirgo.repositories;


import org.simbirgo.entities.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorEntityRepository extends JpaRepository<ColorEntity,Long> {

    public ColorEntity findByColor(String color);
}
