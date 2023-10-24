package org.simbirgo.services;

import org.simbirgo.entities.*;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.simbirgo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService {

    TransportModelEntityRepository transportModelEntityRepository;
    ColorEntityRepository colorEntityRepository;
    TransportTypeEntityRepository transportTypeEntityRepository;
    TransportEntityRepository transportEntityRepository;

    @Autowired
    TransportService(TransportModelEntityRepository transportModelEntityRepository, ColorEntityRepository colorEntityRepository, TransportTypeEntityRepository transportTypeEntityRepository, TransportEntityRepository transportEntityRepository) {
        this.transportModelEntityRepository = transportModelEntityRepository;
        this.colorEntityRepository = colorEntityRepository;
        this.transportTypeEntityRepository = transportTypeEntityRepository;
        this.transportEntityRepository = transportEntityRepository;
    }

    public List<TransportDto> findTransportsBy(Long start, Long count, String transportType) {
        Optional<TransportTypeEntity> validTransportType = transportTypeEntityRepository.findByTransportType(transportType);
        if (validTransportType.isEmpty()) {
            throw new NoRecordFoundException("Transport Type not found");
        }
        List<TransportDto> transports = transportEntityRepository.findAllBetweenAndTransportType(start, start + count - 1, validTransportType.get().getIdTransportType());
        if (transports.isEmpty()) {
            throw new NoRecordFoundException("Transports not found");
        }
        return transports;
    }


    public TransportDto getTransportById(Long transportId) {
        Optional<TransportDto> transportDto = transportEntityRepository.findByIdWithAllForeignTables(transportId);
        if (transportDto.isPresent()) {
            System.out.println("FSASADs");
            return transportDto.get();
        }
        throw new NoRecordFoundException("Transports Not Found");
    }

    public void saveTransport(TransportDto transportDto, Long userId) {
        TransportEntity transport = mapDto(transportDto, userId);
        transportEntityRepository.save(transport);
    }

    /*public void saveTransport(TransportDto transportDto){
        TransportEntity transportEntity = mapDto(transportDto,transportDto.getOwnerId());
        transportEntityRepository.save(transportEntity);
    }*/

    private TransportEntity mapDto(TransportDto transportDto, Long ownerId) {
        TransportEntity transportEntity = new TransportEntity();
        transportEntity.setCanBeRented(transportDto.isCanBeRented());
        transportEntity.setIdentifier(transportDto.getIdentifier());
        transportEntity.setDescription(transportDto.getDescription());
        transportEntity.setLatitude(transportDto.getLatitude());
        transportEntity.setLongitude(transportDto.getLongitude());
        transportEntity.setMinutePrice(transportDto.getMinutePrice());
        transportEntity.setDayPrice(transportDto.getDayPrice());

        transportEntity.setIdModel(findTransportModel(transportDto.getModel()).getIdTransportModel());
        transportEntity.setIdTransportType(findTransportType(transportDto.getTransportType()).getIdTransportType());
        transportEntity.setIdColor(findTransportColor(transportDto.getColor()).getIdColor());

        transportEntity.setIdOwner(ownerId);

        return transportEntity;
    }

    private TransportTypeEntity findTransportType(String transportType) {
        Optional<TransportTypeEntity> validTransportType = transportTypeEntityRepository.findByTransportType(transportType);
        if (validTransportType.isEmpty()) {
            transportTypeEntityRepository.save(new TransportTypeEntity(0, transportType));
            validTransportType = transportTypeEntityRepository.findByTransportType(transportType);
        }
        return validTransportType.get();
    }

    private TransportModelEntity findTransportModel(String transportModel) {
        Optional<TransportModelEntity> validModelEntity = transportModelEntityRepository.findByModel(transportModel);
        if (validModelEntity.isEmpty()) {
            transportModelEntityRepository.save(new TransportModelEntity(0, transportModel));
            validModelEntity = transportModelEntityRepository.findByModel(transportModel);
        }
        return validModelEntity.get();
    }

    private ColorEntity findTransportColor(String transportColor) {
        Optional<ColorEntity> validColorEntity = colorEntityRepository.findByColor(transportColor);
        if (validColorEntity.isEmpty()) {
            colorEntityRepository.save(new ColorEntity(0, transportColor));
            validColorEntity = colorEntityRepository.findByColor(transportColor);
        }
        return validColorEntity.get();
    }

    public void updateTransport(TransportDto transportDto, Long userId, Long transportId) {
        Optional<TransportEntity> transportInDb = transportEntityRepository.findById(transportId);
        if (transportInDb.isPresent() && transportInDb.get().getIdOwner().longValue() == userId.longValue()) {
            TransportEntity transport = mapDto(transportDto, userId);
            transport.setIdTransport(transportId);
            transportEntityRepository.save(transport);
        }
    }

    public void deleteTransport(Long transportId, Long userId) {
        Optional<TransportEntity> transportInDb = transportEntityRepository.findById(transportId);
        if (transportInDb.isPresent() && transportInDb.get().getIdOwner() == userId) {
            transportEntityRepository.deleteById(transportId);
        }
    }

    public void deleteTransport(Long transportId) {
        transportEntityRepository.deleteById(transportId);
    }

}
