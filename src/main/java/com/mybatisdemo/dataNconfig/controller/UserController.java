package com.mybatisdemo.dataNconfig.controller;


import com.mybatisdemo.dataNconfig.entity.User;
import com.mybatisdemo.dataNconfig.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类是用来测试jpa的
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){

        User user = userRepository.findById(id).orElse(null);
     //   User user = userRepository.findOne(id);

        return user;
    }

    @GetMapping("/user")
    public User insertUser(User user){
        User save = userRepository.save(user);
        return save;
    }

}
