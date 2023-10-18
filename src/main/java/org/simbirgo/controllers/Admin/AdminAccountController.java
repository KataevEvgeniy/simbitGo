package org.simbirgo.controllers.Admin;

import org.simbirgo.entities.UserEntity;
import org.simbirgo.entities.dto.SelectionUsersParams;
import org.simbirgo.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin/Account")
public class AdminAccountController {


    AccountService accountService;

    AdminAccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAllUsers(@RequestBody SelectionUsersParams params){
        try {
            List<UserEntity> users = accountService.getAllBy(params.getStart(),params.getCount());
            return new ResponseEntity<>(users,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("invalid Data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByUserById(@PathVariable Long id){
        try {
            UserEntity user = accountService.getUserById(id).get();
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("invalid Data", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody UserEntity user){
        try{
            accountService.createUser(user);
            return new ResponseEntity<>("Created",HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("invalid data",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserEntity user, @PathVariable Long id){
        try{
            accountService.updateById(user,id);
            return new ResponseEntity<>("updated",HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("invalid data", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            accountService.deleteById(id);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("invalid data",HttpStatus.BAD_REQUEST);
        }
    }

}
