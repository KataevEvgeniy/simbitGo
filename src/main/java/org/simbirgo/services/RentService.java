package org.simbirgo.services;

import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.RentFindData;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.repositories.TransportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    TransportEntityRepository transportRepository;
    RentEntityRepository rentRepository;

    @Autowired
    RentService(TransportEntityRepository transportRepository, RentEntityRepository rentRepository){
        this.transportRepository = transportRepository;
        this.rentRepository = rentRepository;
    }


    public List<TransportEntity> findByRadius(RentFindData data){
        double minLongitude = data.getLongitude() - data.getRadius();
        double maxLongitude = data.getLongitude() + data.getRadius();
        double minLatitude = data.getLatitude() - data.getRadius();
        double maxLatitude = data.getLatitude() + data.getRadius();

        List<TransportEntity> transportsInSquare = transportRepository.findAllByCanBeRentedAndLongitudeBetweenAndLatitudeBetween(true,minLongitude,maxLongitude,minLatitude,maxLatitude);

        List<TransportEntity> transportsInCircle = new ArrayList<>();
        transportsInSquare.forEach(transportEntity -> {
            double distance = haversine(transportEntity.getLatitude(),transportEntity.getLongitude(), data.getLatitude(), data.getLongitude());
            if( distance < data.getRadius()){
                transportsInCircle.add(transportEntity);
            }
        });
        return transportsInCircle;
    }

    @Nullable
    public RentEntity findByRentId(Long rentId,Long userId){
        Optional<RentEntity> rentOpt =  rentRepository.findById(rentId);
        if(rentOpt.isPresent()){
            RentEntity rent = rentOpt.get();
            TransportEntity transport = transportRepository.findById(rent.getIdTransport()).get();
            if(rent.getIdUser() == userId && transport.getIdOwner() == userId){
                return rent;
            }
        }
        return null;
    }


    public List<RentEntity> getRentHistory(Long userId){

        return rentRepository.findAllByIdUser(userId);
    }

    public List<RentEntity> getTransportHistory(Long transportId){
        return rentRepository.findAllByIdTransport(transportId);
    }


    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        return distance;
    }

}
