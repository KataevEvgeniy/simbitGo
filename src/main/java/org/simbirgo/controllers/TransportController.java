package org.simbirgo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.simbirgo.entities.TransportEntity;
import org.simbirgo.entities.dto.TransportDto;
import org.simbirgo.repositories.TransportEntityRepository;
import org.simbirgo.services.JwtService;
import org.simbirgo.services.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/Transport")
public class TransportController {


    TransportService transportService;
    JwtService jwtService;

    @Autowired
    TransportController(TransportService transportService, JwtService jwtService) {
        this.transportService = transportService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        TransportDto transport = transportService.getTransportById(id);
        return new ResponseEntity<>(transport, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody TransportDto transport) {
        String jws = request.getHeader("Authorization");

        transportService.saveTransport(transport, jwtService.getUserId(jws));
        return new ResponseEntity<>("transport saved", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TransportDto transport, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        Long userId = jwtService.getUserId(jws);
        if (transportService.isOwner(userId, id)) {
            transportService.updateTransport(transport, userId, id);
            return new ResponseEntity<>("transport updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not owner", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");

        Long userId = jwtService.getUserId(jws);
        if (transportService.isOwner(userId, id)) {
            transportService.deleteTransport(id);
            return new ResponseEntity<>("transport deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not owner", HttpStatus.BAD_REQUEST);
    }
}
