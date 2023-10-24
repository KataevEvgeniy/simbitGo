package org.simbirgo.services;

import org.simbirgo.entities.BlacklistEntity;
import org.simbirgo.repositories.BlacklistEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {

    BlacklistEntityRepository blacklistEntityRepository;

    BlackListService(BlacklistEntityRepository blacklistEntityRepository){
        this.blacklistEntityRepository = blacklistEntityRepository;
    }

    public void addToBlackList(String token){
        blacklistEntityRepository.save(new BlacklistEntity(token,0));
    }

    public boolean isExist(String token){
        return blacklistEntityRepository.existsByToken(token);
    }

}
