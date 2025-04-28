package com.innova.realestate.controllers;

import com.innova.realestate.models.Property;
import com.innova.realestate.models.User;
import com.innova.realestate.services.PropertyService;
import com.innova.realestate.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:8095")
@RequestMapping("/realestate")
public class UserController {
    private UserService userService;
    private PropertyService propertyService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    @PostMapping("/user")
    public void saveUser(@RequestBody User user){
        userService.addUser(user);
    }
    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return "Username already exists!";
        }
        userService.registerUser(user);
        return "Signup successful!";
    }
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // signup.html
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/add-property")
    public ResponseEntity<?> addProperty(@RequestBody Property property) {
        // only ADMINs will be allowed here
        propertyService.save(property);
        return ResponseEntity.ok("Property added!");
    }
}
