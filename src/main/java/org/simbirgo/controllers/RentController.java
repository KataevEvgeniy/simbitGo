package org.simbirgo.controllers;


import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.RentFindData;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.services.JwtService;
import org.simbirgo.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Rent")
public class RentController {


    private final RentService rentService;
    private final JwtService jwtService;

    @Autowired
    RentController(RentService rentService, JwtService jwtService) {
        this.rentService = rentService;
        this.jwtService = jwtService;
    }


    @GetMapping("/Transport")
    public ResponseEntity<?> getByRadius(@RequestBody RentFindData data) {

        try {
            List<TransportEntity> transportEntities = rentService.findByRadius(data);
            return new ResponseEntity<>(transportEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<?> getByRentId(@PathVariable Long rentId, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {
            if (jwtService.isJwtValid(jws)) {
                Long userId = jwtService.getUserId(jws);
                RentEntity rent = rentService.findByRentId(rentId,userId);
                return new ResponseEntity<>(rent, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/MyHistory")
    public ResponseEntity<?> getHistory(HttpServletRequest request){
        String jws = request.getHeader("Authorization");
        try {
            if (jwtService.isJwtValid(jws)) {
                Long userId = jwtService.getUserId(jws);
                List<RentEntity> rentHistory = rentService.getRentHistory(userId);
                return new ResponseEntity<>(rentHistory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/TransportHistory/{transportId}")
    public ResponseEntity<?> getTransportHistory(@PathVariable Long transportId, HttpServletRequest request){
         String jws = request.getHeader("Authorization");
        try {
            if (jwtService.isJwtValid(jws)) {
                Long userId = jwtService.getUserId(jws);
                List<RentEntity> transportRentHistory = rentService.getTransportHistory(transportId);
                return new ResponseEntity<>(transportRentHistory, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }

    }

}
