package org.simbirgo.controllers;


import org.simbirgo.repositories.PriceTypeEntityRepository;
import org.simbirgo.repositories.RentEntityRepository;
import org.simbirgo.repositories.TransportEntityRepository;
import org.simbirgo.services.JwtService;
import org.simbirgo.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Payment")
public class PaymentController {

    PaymentService paymentService;
    JwtService jwtService;

    @Autowired
    PaymentController(PaymentService paymentService,JwtService jwtService){
        this.paymentService = paymentService;
        this.jwtService = jwtService;
    }

    @PostMapping("/Hesoyam/{accountId}")
    public ResponseEntity<?> hesoyam(@PathVariable Long accountId, HttpServletRequest request){
        String jws = request.getHeader("Authorization");
        try {
            if (jwtService.isJwtValid(jws)) {
                Long userId = jwtService.getUserId(jws);
                paymentService.hesoyam(accountId,userId);
                return new ResponseEntity<>("hesoyam success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.BAD_REQUEST);
        }


    }

}