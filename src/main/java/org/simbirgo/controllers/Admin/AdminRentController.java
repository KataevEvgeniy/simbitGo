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
        try {
            RentEntity rent = rentService.findByRentId(rentId);
            return new ResponseEntity<>(rent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/UserHistory/{userId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getUserHistory(@PathVariable Long userId) {
        try {
            List<RentEntity> rents = rentService.getRentHistory(userId);
            return new ResponseEntity<>(rents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/TransportHistory/{transportId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getTransportHistory(@PathVariable Long transportId) {
        try {
            List<RentEntity> rents = rentService.getTransportHistory(transportId);
            return new ResponseEntity<>(rents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> saveRent(@RequestBody RentEntity rentEntity) {
        try {
            rentService.save(rentEntity);
            return new ResponseEntity<>("saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/End/{rentId}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> endRent(@PathVariable Long rentId, @RequestBody RentEndData endData) {
        try {
            rentService.endRentAdmin(rentId, endData);
            return new ResponseEntity<>("end", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RentEntity rent) {
        try {
            rentService.update(rent, id);
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            rentService.delete(id);
            return new ResponseEntity<>("deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }


}
