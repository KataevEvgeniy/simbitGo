package org.simbirgo.configs;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*@Component*/
public class UserDetailsServiceImpl /*implements UserDetailsService*/ {

    UserEntityRepository userEntityRepository;

    @Autowired
    UserDetailsServiceImpl(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }


/*
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserEntity user = userEntityRepository.findById(Long.parseLong(userId)).orElseThrow(()-> new UsernameNotFoundException("user not found"));

        return new User(user.getIdUser().toString(),"",getUserAuthorities(user));
    }

    public List<GrantedAuthority> getUserAuthorities(UserEntity user){
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }

        return authorities;
    }
*/

}
