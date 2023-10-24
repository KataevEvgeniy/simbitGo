package org.simbirgo.repositories;

import org.simbirgo.controllers.TransportController;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.TransportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransportEntityRepository extends JpaRepository<TransportEntity,Long> {


    @Query("SELECT new org.simbirgo.entities.dto.TransportDto(t.canBeRented, tt.transportType, tm.model, c.color, t.identifier, t.description, t.latitude, t.longitude, t.minutePrice, t.dayPrice,t.idOwner) " +
            "FROM TransportEntity t " +
            "JOIN TransportModelEntity tm ON t.idModel = tm.idTransportModel " +
            "JOIN TransportTypeEntity tt ON t.idTransportType = tt.idTransportType " +
            "JOIN ColorEntity c ON t.idColor = c.idColor where t.idOwner = :idOwner" )
    public List<TransportDto> findByOwnerIdWithAllForeignTables(@Param("idOwner") Long ownerId);

    @Query("SELECT new org.simbirgo.entities.dto.TransportDto(t.canBeRented, tt.transportType, tm.model, c.color, t.identifier, t.description, t.latitude, t.longitude, t.minutePrice, t.dayPrice,t.idOwner) " +
            "FROM TransportEntity t " +
            "JOIN TransportModelEntity tm ON t.idModel = tm.idTransportModel " +
            "JOIN TransportTypeEntity tt ON t.idTransportType = tt.idTransportType " +
            "JOIN ColorEntity c ON t.idColor = c.idColor where t.idTransport = :idTransport" )
    public Optional<TransportDto> findByIdWithAllForeignTables(@Param("idTransport") Long transportId);

    @Query("SELECT new org.simbirgo.entities.dto.TransportDto(t.canBeRented, tt.transportType, tm.model, c.color, t.identifier, t.description, t.latitude, t.longitude, t.minutePrice, t.dayPrice,t.idOwner) " +
            "FROM TransportEntity t " +
            "JOIN TransportModelEntity tm ON t.idModel = tm.idTransportModel " +
            "JOIN TransportTypeEntity tt ON t.idTransportType = tt.idTransportType " +
            "JOIN ColorEntity c ON t.idColor = c.idColor where t.idTransport between :start and :end and t.idTransportType = :idTransportType")
    public List<TransportDto> findAllBetweenAndTransportType(@Param("start") Long start, @Param("end") Long end, @Param("idTransportType") Long idTransportType);


    public List<TransportEntity> findAllByCanBeRentedAndLongitudeBetweenAndLatitudeBetween(boolean canBeRented, double longitudeMin, double longitudeMax, double latitudeMin, double latitudeMax);
}
