package org.simbirgo.services;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {


    UserEntityRepository userRepository;

    AccountService(UserEntityRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAllBy(int start,int count){

        List<UserEntity> userEntities = userRepository.findAllByIdUserBetween(start,start+count);
        return userEntities;
    }

    public Optional<UserEntity> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public void createUser(UserEntity user){
        if(!userRepository.existsByUsername(user.getUsername())){
            userRepository.save(user);
        }
    }

    public void updateById(UserEntity user,Long userId){
        if(userRepository.existsById(userId)){
            user.setIdUser(userId);
            userRepository.save(user);
        }
    }

    public void deleteById(Long userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
        }
    }

}
