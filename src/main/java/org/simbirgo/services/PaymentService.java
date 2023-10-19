package org.simbirgo.services;


import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {

    UserEntityRepository userEntityRepository;

    @Autowired
    PaymentService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }


    public void hesoyam(Long destinationAccountId, Long userId) {
        Optional<UserEntity> userOpt = userEntityRepository.findById(userId);
        boolean accessCheat = userId.equals(destinationAccountId) || userOpt.get().isAdmin();
        if (userOpt.isPresent() && accessCheat) {
            UserEntity user = userOpt.get();
            user.setBalance(user.getBalance() + 250000);
            userEntityRepository.save(user);
        }
    }

}
