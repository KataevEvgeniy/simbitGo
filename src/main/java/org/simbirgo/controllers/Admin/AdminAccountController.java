package org.simbirgo.controllers.Admin;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.simbirgo.entities.UserEntity;
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

    AdminAccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getAllUsers(@RequestParam Long start, @RequestParam Long count) {
        List<UserEntity> users = accountService.findAllBy(start, count);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> getByUserById(@PathVariable Long id) {
        UserEntity user = accountService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> create(@RequestBody UserEntity user) {
        accountService.createUser(user);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> update(@RequestBody UserEntity user, @PathVariable Long id) {
        accountService.updateById(user, id);
        return new ResponseEntity<>("updated", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Authorization")})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
