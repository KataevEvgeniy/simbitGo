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


    TransportEntityRepository repository;
    TransportService transportService;
    JwtService jwtService;

    @Autowired
    TransportController(TransportEntityRepository repository, TransportService transportService, JwtService jwtService) {
        this.transportService = transportService;
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            TransportDto transports = repository.findByIdWithAllForeignTables(id);
            return new ResponseEntity<>(transports, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody TransportDto transport) {
        String jws = request.getHeader("Authorization");
        try {
            transportService.saveTransport(transport, jwtService.getUserId(jws));
            return new ResponseEntity<>("transport saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TransportDto transport, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {
            transportService.updateTransport(transport, jwtService.getUserId(jws), id);
            return new ResponseEntity<>("transport updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        String jws = request.getHeader("Authorization");
        try {
            Long userId = jwtService.getUserId(jws);
            transportService.deleteTransport(id, userId);
            return new ResponseEntity<>("transport deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }
    }
}
