package com.example.demo.Controller;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // This defines a base URI for all methods in this controller
public class UserController {
    final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/test")  // Maps this method to handle GET requests on /users/test
    public String test() {
        return "hello";
    }


   //creates a user
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userRepo.saveUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userRepo.updateUser(user);
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable int id){
        return userRepo.getById(id);
    }

    @GetMapping("/getAll")
    public List<User> getUsers(){
        return userRepo.allUsers();
    }
}
