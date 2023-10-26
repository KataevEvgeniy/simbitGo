package org.simbirgo.controllers.Admin;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.simbirgo.entities.RentEntity;
import org.simbirgo.entities.dto.RentEndData;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.services.RentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin/Rent")
public class AdminRentController {


    RentService rentService;

    AdminRentController(RentService rentService) {
        this.rentService = rentService;
    }


    @GetMapping("/{rentId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getRent(@PathVariable Long rentId) {
        RentEntity rent = rentService.findByRentId(rentId);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @GetMapping("/UserHistory/{userId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getUserHistory(@PathVariable Long userId) {
        List<RentEntity> rents = rentService.findRentHistory(userId);
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/TransportHistory/{transportId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getTransportHistory(@PathVariable Long transportId) {
        List<RentEntity> rents = rentService.findTransportHistory(transportId);
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> saveRent(@RequestBody RentEntity rentEntity) {
        rentService.save(rentEntity);
        return new ResponseEntity<>("saved", HttpStatus.CREATED);
    }//TODO: добавить проверку на правильного пользователя

    @PostMapping("/End/{rentId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> endRent(@PathVariable Long rentId, @RequestParam("lat") Double latitude, @RequestParam("long") Double longitude) {
        rentService.endRentAdmin(rentId, latitude, longitude);
        return new ResponseEntity<>("end", HttpStatus.OK);
    }//TODO: добавить проверку на правильного пользователя

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RentEntity rent) {
        rentService.update(rent, id);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        rentService.delete(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }


}
