package org.simbirgo.services;

import org.simbirgo.entities.*;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveTransport(TransportDto transportDto, Long userId) {
        TransportEntity transport = mapDto(transportDto, userId);
        transportEntityRepository.save(transport);
    }

    private TransportEntity mapDto(TransportDto transportDto, Long userId) {
        TransportEntity transportEntity = new TransportEntity();
        transportEntity.setCanBeRented(transportDto.isCanBeRented());
        transportEntity.setIdentifier(transportDto.getIdentifier());
        transportEntity.setDescription(transportDto.getDescription());
        transportEntity.setLatitude(transportDto.getLatitude());
        transportEntity.setLongitude(transportDto.getLongitude());
        transportEntity.setMinutePrice(transportDto.getMinutePrice());
        transportEntity.setDayPrice(transportDto.getDayPrice());

        TransportModelEntity modelEntity = transportModelEntityRepository.findByModel(transportDto.getModel());
        if (modelEntity == null) {
            modelEntity = new TransportModelEntity();
            modelEntity.setModel(transportDto.getModel());
            modelEntity = transportModelEntityRepository.save(modelEntity);
        }
        transportEntity.setIdModel(modelEntity.getIdTransportModel());

        ColorEntity colorEntity = colorEntityRepository.findByColor(transportDto.getColor());
        if (colorEntity == null) {
            colorEntity = new ColorEntity();
            colorEntity.setColor(transportDto.getColor());
            colorEntity = colorEntityRepository.save(colorEntity);
        }
        transportEntity.setIdColor(colorEntity.getIdColor());

        TransportTypeEntity transportTypeEntity = transportTypeEntityRepository.findByTransportType(transportDto.getTransportType());
        if (transportTypeEntity == null) {
            transportTypeEntity = new TransportTypeEntity();
            transportTypeEntity.setTransportType(transportDto.getTransportType());
            transportTypeEntity = transportTypeEntityRepository.save(transportTypeEntity);
        }
        transportEntity.setIdTransportType(transportTypeEntity.getIdTransportType());

        transportEntity.setIdOwner(userId);

        return transportEntity;
    }

    public void updateTransport(TransportDto transportDto, Long userId, Long transportId) {
        Optional<TransportEntity> transportInDb = transportEntityRepository.findById(transportId);
        if (transportInDb.isPresent() && transportInDb.get().getIdOwner() == userId) {
            TransportEntity transport = mapDto(transportDto, userId);
            transport.setIdTransport(transportId);
            transportEntityRepository.save(transport);
        }
    }

    public void deleteTransport(Long transportId,Long userId){
        Optional<TransportEntity> transportInDb = transportEntityRepository.findById(transportId);
        if (transportInDb.isPresent() && transportInDb.get().getIdOwner() == userId) {
            transportEntityRepository.deleteById(transportId);
        }
    }

}
