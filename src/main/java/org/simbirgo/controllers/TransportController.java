package org.simbirgo.controllers;

import org.simbirgo.entities.TransportEntity;
import org.simbirgo.repositories.TransportEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/Transport")
public class TransportController {


    TransportEntityRepository repository;

    @Autowired
    TransportController(TransportEntityRepository repository) {

        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get() {
        try {
            List<TransportEntity> transports = repository.findAll();
            return new ResponseEntity<>(transports, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TransportEntity transport) {



        repository.save(transport);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
