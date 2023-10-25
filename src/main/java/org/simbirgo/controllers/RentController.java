package org.simbirgo.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.simbirgo.entities.RentTypeEntity;
import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.RentEndData;
import org.simbirgo.entities.dto.RentFindData;
import org.simbirgo.services.JwtService;
import org.simbirgo.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getByRadius(@RequestParam("lat") Double latitude, @RequestParam("long") Double longitude, @RequestParam Long radius,@RequestParam String type ) {
        try {
            List<TransportEntity> transportEntities = rentService.findByRadius(latitude, longitude, radius, type);
            return new ResponseEntity<>(transportEntities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{rentId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getByRentId(@PathVariable Long rentId, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {
            Long userId = jwtService.getUserId(jws);
            RentEntity rent = rentService.findByRentId(rentId, userId);
            return new ResponseEntity<>(rent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/MyHistory")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getHistory(HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {
            Long userId = jwtService.getUserId(jws);
            List<RentEntity> rentHistory = rentService.getRentHistory(userId);
            return new ResponseEntity<>(rentHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/TransportHistory/{transportId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getTransportHistory(@PathVariable Long transportId, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {

            Long userId = jwtService.getUserId(jws);
            List<RentEntity> transportRentHistory = rentService.getTransportHistory(transportId, userId);
            return new ResponseEntity<>(transportRentHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/New/{transportId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> create(HttpServletRequest request, @PathVariable Long transportId, @RequestBody RentTypeEntity rentType) {
        String jws = request.getHeader("Authorization");
        try {
            Long userId = jwtService.getUserId(jws);
            rentService.rentNew(transportId, userId, rentType);
            return new ResponseEntity<>("rent success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/End/{rentId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> end(HttpServletRequest request, @PathVariable Long rentId, @RequestBody RentEndData rentEndData) {
        String jws = request.getHeader("Authorization");
        try {
            Long userId = jwtService.getUserId(jws);
            rentService.endRentUser(rentId, rentEndData, userId);
            return new ResponseEntity<>("rent ended", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }

    }

}
