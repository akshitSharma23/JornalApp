package com.example.journalapp.Service;

import com.example.journalapp.Entity.JournalEntry;
import com.example.journalapp.Entity.UserEntity;
import com.example.journalapp.Repository.JournalEntryRepo;
import com.example.journalapp.Repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(UserEntity user) {
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.trace("TRACE");         // (R1) need to make it available in YAML or XML file
            log.debug("DEBUG");         // (R2) need to make it available in YAML or XML file
            log.info("INFO");           // (R3) Automatically available
            log.warn("WARN");           // (R4) Automatically available
            log.error("ERROR");         // (R5) Automatically available
            return false;
        }
    }
    public void saveAdminEntry(UserEntity user) {
        if (userRepository.findByUserName(user.getUserName())!=null) {
            throw new RuntimeException("Username already exists!");
        }
        else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("ADMIN"));
            userRepository.save(user);
        }
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteById(String userName){
        UserEntity user=userRepository.findByUserName(userName);
        userRepository.deleteById(user.getId());
    }

    public UserEntity putValue(UserEntity user,String username){
        UserEntity old=userRepository.findByUserName(username);
        if(old==null)saveEntry(user);
        if(user.getUserName()!=null && user.getUserName().length()!=0){
            old.setUserName(user.getUserName());
        }
        if(user.getPassword()!=null && user.getPassword().length()!=0){
            old.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(old);
        return old;
    }


}