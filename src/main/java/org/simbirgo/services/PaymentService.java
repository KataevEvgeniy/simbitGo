package org.simbirgo.services;


import org.simbirgo.entities.UserEntity;
import org.simbirgo.exceptions.UserAccessException;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {

    AccountService accountService;

    @Autowired
    PaymentService(AccountService accountService) {
        this.accountService = accountService;
    }


    public void hesoyam(Long destinationAccountId, Long userId) {
        UserEntity destinationUser = accountService.findUserById(destinationAccountId);
        UserEntity actionUser = accountService.findUserById(userId);
        boolean accessCheat = userId.equals(destinationAccountId) || actionUser.isAdmin();
        if (accessCheat) {
            double balance = destinationUser.getBalance()== null ? 0.0 : destinationUser.getBalance();
            destinationUser.setBalance(balance + 250000);
            accountService.updateById(destinationUser,destinationAccountId);
            return;
        }
        throw new UserAccessException("Didn't access");
    }

}
