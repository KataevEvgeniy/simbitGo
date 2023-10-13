package org.simbirgo.services;

import org.simbirgo.entities.*;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.repositories.TransportModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportService {

    TransportModelRepository transportModelRepository;

    @Autowired
    TransportService(TransportModelRepository transportModelRepository){
       this.transportModelRepository = transportModelRepository;

    }

    public TransportEntity saveTransport(TransportDto transportDto) {
        TransportEntity transportEntity = new TransportEntity();
        transportEntity.setCanBeRented(transportDto.isCanBeRented());
        transportEntity.setIdentifier(transportDto.getIdentifier());
        transportEntity.setDescription(transportDto.getDescription());
        transportEntity.setLatitude(transportDto.getLatitude());
        transportEntity.setLongitude(transportDto.getLongitude());
        transportEntity.setMinutePrice(transportDto.getMinutePrice());
        transportEntity.setDayPrice(transportDto.getDayPrice());

        TransportModelEntity modelEntity = transportModelRepository.findByModel(transportDto.getModel());
        if (modelEntity == null) {
            modelEntity = new TransportModelEntity();
            modelEntity.setModel(transportDto.getModel());
            modelEntity = transportModelRepository.save(modelEntity);
        }
        transportEntity.setModel(modelEntity);

        ColorEntity colorEntity = colorRepository.findByColor(transportDto.getColor());
        if (colorEntity == null) {
            colorEntity = new ColorEntity();
            colorEntity.setColor(transportDto.getColor());
            colorEntity = colorRepository.save(colorEntity);
        }
        transportEntity.setColor(colorEntity);

        UserEntity userEntity = userRepository.findById(transportDto.getIdOwner());
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setIdUser(transportDto.getIdOwner());
            userEntity = userRepository.save(userEntity);
        }
        transportEntity.setIdOwner(userEntity.getIdUser());

        TransportTypeEntity transportTypeEntity = transportTypeRepository.findByTransportType(transportDto.getTransportType());
        if (transportTypeEntity == null) {
            transportTypeEntity = new TransportTypeEntity();
            transportTypeEntity.setTransportType(transportDto.getTransportType());
            transportTypeEntity = transportTypeRepository.save(transportTypeEntity);
        }
        transportEntity.setTransportType(transportTypeEntity);

    }

}
