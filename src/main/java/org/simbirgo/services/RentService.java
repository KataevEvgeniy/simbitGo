package org.simbirgo.services;

import org.simbirgo.entities.RentTypeEntity;
import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.RentEndData;
import org.simbirgo.entities.dto.RentFindData;
import org.simbirgo.repositories.RentTypeEntityRepository;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.repositories.TransportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RentService {

    TransportEntityRepository transportRepository;
    RentEntityRepository rentRepository;
    RentTypeEntityRepository priceTypeRepository;

    @Autowired
    RentService(TransportEntityRepository transportRepository, RentEntityRepository rentRepository, RentTypeEntityRepository priceTypeRepository) {
        this.transportRepository = transportRepository;
        this.rentRepository = rentRepository;
        this.priceTypeRepository = priceTypeRepository;
    }


    public List<TransportEntity> findByRadius(RentFindData data) {
        double minLongitude = data.getLongitude() - data.getRadius();
        double maxLongitude = data.getLongitude() + data.getRadius();
        double minLatitude = data.getLatitude() - data.getRadius();
        double maxLatitude = data.getLatitude() + data.getRadius();

        List<TransportEntity> transportsInSquare = transportRepository.findAllByCanBeRentedAndLongitudeBetweenAndLatitudeBetween(true, minLongitude, maxLongitude, minLatitude, maxLatitude);

        List<TransportEntity> transportsInCircle = new ArrayList<>();
        transportsInSquare.forEach(transportEntity -> {
            double distance = haversine(transportEntity.getLatitude(), transportEntity.getLongitude(), data.getLatitude(), data.getLongitude());
            if (distance < data.getRadius()) {
                transportsInCircle.add(transportEntity);
            }
        });
        return transportsInCircle;
    }

    @Nullable
    public RentEntity findByRentId(Long rentId, Long userId) {
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent()) {
            RentEntity rent = rentOpt.get();
            TransportEntity transport = transportRepository.findById(rent.getIdTransport()).get();
            if (rent.getIdUser() == userId && transport.getIdOwner() == userId) {
                return rent;
            }
        }
        return null;
    }

    @Nullable
    public RentEntity findByRentId(Long rentId) {
        return rentRepository.findById(rentId).orElse(null);
    }


    public List<RentEntity> getRentHistory(Long userId) {

        return rentRepository.findAllByIdUser(userId);
    }

    public List<RentEntity> getTransportHistory(Long transportId, Long userId) {
        Optional<TransportEntity> transport = transportRepository.findById(transportId);
        if (transport.isPresent() && transport.get().getIdOwner().equals(userId)) {
            return rentRepository.findAllByIdTransport(transportId);
        }
        return new ArrayList<>();
    }

    public List<RentEntity> getTransportHistory(Long transportId) {
        return rentRepository.findAllByIdTransport(transportId);
    }

    public void save(RentEntity rent){
        rentRepository.save(rent);
    }

    public void update(RentEntity rent,Long rentId){
        if(rentRepository.existsById(rentId)){
            rentRepository.save(rent);
        }
    }

    public void delete(Long rentId){
        if(rentRepository.existsById(rentId)){
            rentRepository.deleteById(rentId);
        }
    }


    public void rentNew(Long transportId, Long userId, RentTypeEntity rentType) {
        Optional<RentTypeEntity> validRentType = priceTypeRepository.findByRentType(rentType.getRentType());
        if (validRentType.isEmpty()) {
            return;
        }
        Optional<TransportEntity> transportOpt = transportRepository.findById(transportId);

        if (transportOpt.isPresent()) {
            TransportEntity transport = transportOpt.get();
            RentEntity rent = new RentEntity();
            rent.setTimeStart(new Date());
            rent.setIdTransport(transportId);
            rent.setIdUser(userId);
            rent.setIdPriceType(validRentType.get().getIdPriceType());
            if (validRentType.get().getRentType().equals("Minutes")) {
                rent.setPriceOfUnit(transport.getMinutePrice());
            }
            if (validRentType.get().getRentType().equals("Days")) {
                rent.setPriceOfUnit(transport.getDayPrice());
            }
            rentRepository.save(rent);
        }
    }

    public void endRentUser(Long rentId, RentEndData endData, Long userId) {


        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent() && rentOpt.get().getIdUser() == userId) {
            endRent(rentOpt.get(),endData);
        }
    }

    private void endRent(RentEntity rent, RentEndData endData){
            TransportEntity transport = transportRepository.findById(rent.getIdTransport()).get();
            transport.setLatitude(endData.getLatitude());
            transport.setLongitude(endData.getLongitude());
            transportRepository.save(transport);

            Optional<RentTypeEntity> validRentType = priceTypeRepository.findById(rent.getIdPriceType());
            if (validRentType.isEmpty()) {
                return;
            }

            rent.setTimeEnd(new Date());
            double finalPrice = 0;
            long diff = rent.getTimeStart().getTime() - rent.getTimeEnd().getTime();
            if (validRentType.get().getRentType().equals("Minutes")) {
                finalPrice = rent.getPriceOfUnit() * TimeUnit.MILLISECONDS.toMinutes(diff);
            }
            if (validRentType.get().getRentType().equals("Days")) {
                finalPrice = rent.getPriceOfUnit() * TimeUnit.MILLISECONDS.toDays(diff);
            }
            rent.setFinalPrice(finalPrice);
            rentRepository.save(rent);
    }


    public void endRentAdmin(Long rentId,RentEndData endData){
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent()) {
            endRent(rentOpt.get(),endData);
        }
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
