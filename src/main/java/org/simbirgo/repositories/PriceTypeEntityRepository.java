package org.simbirgo.repositories;

import org.simbirgo.entities.PriceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTypeEntityRepository extends JpaRepository<PriceTypeEntity,Long> {

    public PriceTypeEntity findByPriceType(String priceType);


}
