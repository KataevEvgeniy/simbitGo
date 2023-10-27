package org.simbirgo.services;

import org.simbirgo.entities.RentTypeEntity;
import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.TransportTypeEntity;
import org.simbirgo.entities.dto.RentDto;
import org.simbirgo.exceptions.InvalidDataException;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.simbirgo.exceptions.UserAccessException;
import org.simbirgo.repositories.RentTypeEntityRepository;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.repositories.TransportEntityRepository;
import org.simbirgo.repositories.TransportTypeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RentService {

    TransportEntityRepository transportRepository;
    RentEntityRepository rentRepository;
    RentTypeEntityRepository priceTypeRepository;
    TransportService transportService;
    TransportTypeEntityRepository transportTypeEntityRepository;
    AccountService accountService;

    @Autowired
    RentService(TransportEntityRepository transportRepository, RentEntityRepository rentRepository, RentTypeEntityRepository priceTypeRepository, TransportService transportService, TransportTypeEntityRepository transportTypeEntityRepository,AccountService accountService) {
        this.transportRepository = transportRepository;
        this.rentRepository = rentRepository;
        this.priceTypeRepository = priceTypeRepository;
        this.transportService = transportService;
        this.transportTypeEntityRepository = transportTypeEntityRepository;
        this.accountService = accountService;
    }


    public List<TransportEntity> findByRadius(Double latitude, Double longitude, Long radius, String type) {
        List<TransportEntity> transportsInSquare = findTransportsInSquare(latitude, longitude, radius, type);

        return findTransportsInCircle(latitude, longitude, radius, transportsInSquare);
    }

    private List<TransportEntity> findTransportsInSquare(double latitude, double longitude, Long radius, String type) {
        double minLatitude = latitude - radius / 111f;
        double maxLatitude = latitude + radius / 111f;
        double degreeInRadians = 111 * Math.cos(latitude * Math.PI / 180);
        double minLongitude = longitude - radius / degreeInRadians;
        double maxLongitude = longitude + radius / degreeInRadians;

        List<TransportEntity> transportsInSquare;
        if (type.equalsIgnoreCase("all")) {
            transportsInSquare = transportRepository.findAllByCanBeRentedAndLongitudeBetweenAndLatitudeBetween(true, minLongitude, maxLongitude, minLatitude, maxLatitude);
        } else {
            transportsInSquare = transportRepository.findAllByCanBeRentedAndLongitudeBetweenAndLatitudeBetweenAndIdTransportType(true, minLongitude, maxLongitude, minLatitude, maxLatitude, findTransportType(type).getIdTransportType());
        }

        if (transportsInSquare.isEmpty()) {
            throw new NoRecordFoundException("No transports found");
        }

        return transportsInSquare;
    }

    private List<TransportEntity> findTransportsInCircle(double latitude, double longitude, Long radius, List<TransportEntity> transportsInSquare) {
        List<TransportEntity> transportsInCircle = new ArrayList<>();
        transportsInSquare.forEach(transportEntity -> {
            double distance = getDistanceInKmByHaversineMethod(transportEntity.getLatitude(), transportEntity.getLongitude(), latitude, longitude);
            if (distance < radius) {
                transportsInCircle.add(transportEntity);
            }
        });
        if (transportsInCircle.isEmpty()) {
            throw new NoRecordFoundException("No transports found");
        }
        return transportsInCircle;
    }

    private TransportTypeEntity findTransportType(String transportType) {
        Optional<TransportTypeEntity> transportTypeOpt = transportTypeEntityRepository.findByTransportType(transportType);
        if (transportTypeOpt.isPresent()) {
            return transportTypeOpt.get();
        }
        throw new NoRecordFoundException("No transportType found");
    }

    private double getDistanceInKmByHaversineMethod(double lat1, double lng1, double lat2, double lng2) {
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

    public RentEntity findByRentId(Long rentId, Long userId) {
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent()) {
            Optional<TransportEntity> transport = transportRepository.findById(rentOpt.get().getIdTransport());
            boolean isOwner = transport.get().getIdOwner().longValue() == userId.longValue();
            boolean isRenter = rentOpt.get().getIdUser() == userId;
            if (isOwner || isRenter) {
                return rentOpt.get();
            }
            throw new UserAccessException("User not owner and not renter");
        }
        throw new NoRecordFoundException("No rent found");
    }


    public RentEntity findByRentId(Long rentId) {
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent()) {
            return rentOpt.get();
        }
        throw new NoRecordFoundException("No rent found");
    }


    public List<RentEntity> findRentHistory(Long userId) {
        List<RentEntity> rents = rentRepository.findAllByIdUser(userId);
        if (rents.isEmpty()) {
            throw new NoRecordFoundException("No rent found");
        }
        return rents;
    }

    public List<RentEntity> findTransportHistory(Long transportId, Long userId) {
        Optional<TransportEntity> transport = transportRepository.findById(transportId);
        if (transport.isPresent()) {
            if (transport.get().getIdOwner().equals(userId)) {
                return findTransportHistory(transportId);
            }
            throw new UserAccessException("User isn't owner");
        }
        throw new NoRecordFoundException("No transport found");
    }

    public List<RentEntity> findTransportHistory(Long transportId) {
        List<RentEntity> rents = rentRepository.findAllByIdTransport(transportId);
        if (rents.isEmpty()) {
            throw new NoRecordFoundException("No rent found");
        }
        return rents;
    }

    public void save(RentDto rentDto) {
        RentEntity entity = new RentEntity();
        entity.setIdTransport(rentDto.getTransportId());
        entity.setIdUser(rentDto.getUserId());
        entity.setTimeStart(rentDto.getTimeStart());
        entity.setTimeEnd(rentDto.getTimeEnd());
        entity.setPriceOfUnit(rentDto.getPriceOfUnit());

        entity.setIdPriceType(defineRentType(rentDto.getPriceType()).getIdPriceType());
        checkRentFields(entity);

        rentRepository.save(entity);
    }

    public void update(RentDto rentDto,Long rentId) {
        if (rentRepository.existsById(rentId)) {
            save(rentDto);
            return;
        }
        throw new NoRecordFoundException("Rent not found");
    }

    public void delete(Long rentId) {
        if (rentRepository.existsById(rentId)) {
            rentRepository.deleteById(rentId);
            return;
        }
        throw new NoRecordFoundException("Rent not found");
    }

    private void checkRentFields(RentEntity rent) {
        if(!transportService.existById(rent.getIdTransport())) {
            throw new NoRecordFoundException("Transport not found");
        }
        if(!accountService.existUserById(rent.getIdUser())) {
            throw new NoRecordFoundException("Renter not found");
        }
    }


    public void rentNew(Long transportId, Long userId, RentTypeEntity rentType) {
        RentTypeEntity rentTypeEntity = defineRentType(rentType.getRentType());

        Optional<TransportEntity> transportOpt = transportRepository.findById(transportId);
        if (transportOpt.isEmpty()) {
            throw new NoRecordFoundException("Transport not found");
        }

        if(transportService.isOwner(userId,transportId)){
            throw new UserAccessException("Owner cannot rent his own transport");
        }

        TransportEntity transport = transportOpt.get();
        RentEntity rent = new RentEntity();
        rent.setTimeStart(new Date());
        rent.setIdTransport(transportId);
        rent.setIdUser(userId);
        rent.setIdPriceType(rentTypeEntity.getIdPriceType());

        if (rentTypeEntity.getRentType().equals("Minutes")) {
            if(transport.getMinutePrice() == null) {
                throw new InvalidDataException("Minute price is not allowed for this transport");
            }
            rent.setPriceOfUnit(transport.getMinutePrice());
        }
        else if (rentTypeEntity.getRentType().equals("Days")) {
            if(transport.getDayPrice() == null) {
                throw new InvalidDataException("Day price is not allowed for this transport");
            }
            rent.setPriceOfUnit(transport.getDayPrice());
        }
        rentRepository.save(rent);

    }

    private RentTypeEntity defineRentType(String rentType) {
        if (rentType.equals("Days") || rentType.equals("Minutes")) {
            Optional<RentTypeEntity> validRentType = priceTypeRepository.findByRentType(rentType);
            if(validRentType.isEmpty()) {
                priceTypeRepository.save(new RentTypeEntity(0,rentType));
                validRentType = priceTypeRepository.findByRentType(rentType);
            }
            return validRentType.get();
        }
        throw new NoRecordFoundException("Unresolved rent type");
    }

    public void endRentUser(Long rentId, Double latitude,Double longitude, Long userId) {
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if(rentOpt.isEmpty()) {
            throw new NoRecordFoundException("Rent not found");
        }
        if (rentOpt.get().getIdUser() == userId) {
            endRent(rentOpt.get(), latitude, longitude);
            return;
        }
        throw new UserAccessException("User isn't renter");
    }

    private void endRent(RentEntity rent, Double latitude, Double longitude) {
        checkRentFields(rent);
        TransportEntity transport = transportRepository.findById(rent.getIdTransport()).get();
        transport.setLatitude(latitude);
        transport.setLongitude(longitude);
        transportRepository.save(transport);

        Optional<RentTypeEntity> validRentType = priceTypeRepository.findById(rent.getIdPriceType());
        if (validRentType.isEmpty()) {
            return;
        }

        rent.setTimeEnd(new Date());
        long diff = rent.getTimeEnd().getTime() - rent.getTimeStart().getTime();
        if (validRentType.get().getRentType().equals("Minutes")) {
            rent.setFinalPrice(rent.getPriceOfUnit() * TimeUnit.MILLISECONDS.toMinutes(diff));
        }
        if (validRentType.get().getRentType().equals("Days")) {
            rent.setFinalPrice(rent.getPriceOfUnit() * TimeUnit.MILLISECONDS.toDays(diff));
        }
        rentRepository.save(rent);
    }


    public void endRentAdmin(Long rentId, Double latitude, Double longitude) {
        Optional<RentEntity> rentOpt = rentRepository.findById(rentId);
        if (rentOpt.isPresent()) {
            endRent(rentOpt.get(), latitude, longitude);
            return;
        }
        throw new NoRecordFoundException("No rent found");
    }


}
