package org.simbirgo.services;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.exceptions.InvalidDataException;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.simbirgo.exceptions.RecordAlreadyExistException;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    UserEntityRepository userRepository;

    AccountService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAllBy(Long start, Long count) {
        List<UserEntity> userEntities = userRepository.findAllByIdUserBetween(start, start + count);
        if (userEntities.isEmpty()) {
            throw new NoRecordFoundException("Empty list");
        }
        return userEntities;
    }

    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NoRecordFoundException("User not found");
    }

    public UserEntity findUserByUsername(String username) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new NoRecordFoundException("User not found");
    }

    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }

    public void createUser(UserEntity user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            userRepository.save(user);
            return;
        }
        throw new RecordAlreadyExistException("User already exist");
    }

    public void updateById(UserEntity user, Long userId) {
        if (!userRepository.existsByUsername(user.getUsername()) || Objects.equals(findUserByUsername(user.getUsername()).getIdUser(), userId)) {
            user.setIdUser(userId);
            userRepository.save(user);
            return;
        }
        throw new InvalidDataException("Username already exist");
    }

    public void deleteById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return;
        }
        throw new NoRecordFoundException("User not found");
    }

}
