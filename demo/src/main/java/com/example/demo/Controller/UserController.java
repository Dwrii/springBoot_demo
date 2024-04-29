package com.example.demo.Controller;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    final
    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //creates a user
    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        return userRepo.saveUser(user);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user){
        return userRepo.updateUser(user);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id){
        return userRepo.getById(id);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepo.allUsers();
    }
}
