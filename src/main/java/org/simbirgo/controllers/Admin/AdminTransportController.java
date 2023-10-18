package org.simbirgo.controllers.Admin;


import org.simbirgo.entities.UserEntity;
import org.simbirgo.entities.dto.SelectionTransportParams;
import org.simbirgo.entities.dto.SelectionUsersParams;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.services.AccountService;
import org.simbirgo.services.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin/Transport")
public class AdminTransportController {

    TransportService transportService;

    @Autowired
    AdminTransportController(TransportService transportService){
        this.transportService = transportService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAllTransports(@RequestBody SelectionTransportParams params){
        try {
            List<TransportDto> transports = transportService.getTransportsBy(params);
            return new ResponseEntity<>(transports, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByUserById(@PathVariable Long id){
        try {
            TransportDto transport = transportService.getTransportById(id);
            return new ResponseEntity<>(transport,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("invalid Data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TransportDto transportDto){
        try{
            transportService.saveTransport(transportDto,transportDto.getOwnerId());
            return new ResponseEntity<>("Created",HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("invalid data",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TransportDto transportDto, @PathVariable Long id){
        try{
            transportService.updateTransport(transportDto,transportDto.getOwnerId(),id);
            return new ResponseEntity<>("updated",HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            transportService.deleteTransport(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("invalid data",HttpStatus.BAD_REQUEST);
        }
    }
}
