package com.example.demo.Controller;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")  // This defines a base URI for all methods in this controller
public class UserController {
    final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/test")  // Maps this method to handle GET requests on /users/test
    public String test() {
        return "hello";
    }


   //creates a user
    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
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

    //deletes all users
    @DeleteMapping("/deleteAll")
    public String deleteAllUsers() {
        return userRepo.deleteAll();
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable int id){
        return userRepo.deleteById(id);
    }

    //reset
    @DeleteMapping("/reset")
    public String resetUsers() {
        return userRepo.resetUsersTable();
    }

    // Inside UserController class
    @GetMapping("/getByNameAndPassword")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> getUserByNameAndPassword(@RequestParam String name, @RequestParam String password) {
        User user = userRepo.getByNameAndPassword(name, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
