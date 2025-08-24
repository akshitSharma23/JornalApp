package com.example.journalapp.Controller;

import com.example.journalapp.Entity.UserEntity;
import com.example.journalapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/healthcheck")
    public String health(){
        return "OK";
    }

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user){
        return userService.saveEntry(user)?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
