package org.simbirgo.services;

import org.simbirgo.entities.*;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.simbirgo.exceptions.UserAccessException;
import org.simbirgo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransportService {

    TransportModelEntityRepository transportModelEntityRepository;
    ColorEntityRepository colorEntityRepository;
    TransportTypeEntityRepository transportTypeEntityRepository;
    TransportEntityRepository transportEntityRepository;
    AccountService accountService;

    @Autowired
    TransportService(TransportModelEntityRepository transportModelEntityRepository, ColorEntityRepository colorEntityRepository, TransportTypeEntityRepository transportTypeEntityRepository, TransportEntityRepository transportEntityRepository,AccountService accountService) {
        this.transportModelEntityRepository = transportModelEntityRepository;
        this.colorEntityRepository = colorEntityRepository;
        this.transportTypeEntityRepository = transportTypeEntityRepository;
        this.transportEntityRepository = transportEntityRepository;
        this.accountService = accountService;
    }

    public List<TransportDto> findTransportsBy(Long start, Long count, String transportType) {
        Optional<TransportTypeEntity> validTransportType = transportTypeEntityRepository.findByTransportType(transportType);

        List<TransportDto> transports;
        if(Objects.equals(transportType.toLowerCase(), "all")){
            transports = transportEntityRepository.findAllBetween(start, start + count - 1);
        } else{
            if (validTransportType.isEmpty()) {
                throw new NoRecordFoundException("Transport Type not found");
            }
            transports = transportEntityRepository.findAllBetweenAndTransportType(start, start + count - 1, validTransportType.get().getIdTransportType());
            if (transports.isEmpty()) {
                throw new NoRecordFoundException("Transports not found");
            }
        }
        return transports;
    }


    public TransportDto getTransportById(Long transportId) {
        Optional<TransportDto> transportDto = transportEntityRepository.findByIdWithAllForeignTables(transportId);
        if (transportDto.isPresent()) {
            return transportDto.get();
        }
        throw new NoRecordFoundException("Transports Not Found");
    }

    public void saveTransport(TransportDto transportDto, Long userId) {
        if(!accountService.existUserById(userId)) {
            throw new NoRecordFoundException("User not found");
        }
        TransportEntity transport = mapDto(transportDto, userId);
        transportEntityRepository.save(transport);
    }

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

    public boolean existById(Long transportId){
        return transportEntityRepository.existsById(transportId);
    }


    public void updateTransport(TransportDto transportDto, Long userId, Long transportId) {
        if(!accountService.existUserById(userId)) {
            throw new NoRecordFoundException("User not found");
        }

        if(transportEntityRepository.existsById(transportId)) {
            TransportEntity transport = mapDto(transportDto, userId);
            transport.setIdTransport(transportId);
            transportEntityRepository.save(transport);
            return;
        }
        throw new NoRecordFoundException("Transport not found");
    }

    public void deleteTransport(Long transportId) {
        if(transportEntityRepository.existsById(transportId)) {
            transportEntityRepository.deleteById(transportId);
            return;
        }
        throw new NoRecordFoundException("Transport not found");
    }

    public boolean isOwner(Long userId, Long transportId) {
        Optional<TransportEntity> transportInDb = transportEntityRepository.findById(transportId);
        if (transportInDb.isPresent()) {
            if (transportInDb.get().getIdOwner().longValue() == userId.longValue()) {
                return true;
            }
            return false;
        }
        throw new NoRecordFoundException("Transport not found");
    }

}
