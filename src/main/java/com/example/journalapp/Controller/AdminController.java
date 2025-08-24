package com.example.journalapp.Controller;

import com.example.journalapp.Entity.UserEntity;
import com.example.journalapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> users=userService.getAll();
        if(users!=null && !users.isEmpty())return new ResponseEntity<>(users, HttpStatus.OK);
        return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void SaveUser(@RequestBody UserEntity user){
        userService.saveAdminEntry(user);
    }

}
