package org.fastrackit.onlineshop4shoes.web;

import org.fastrackit.onlineshop4shoes.domain.User;
import org.fastrackit.onlineshop4shoes.service.UserService;
import org.fastrackit.onlineshop4shoes.transfer.user.CreateUserRequest;
import org.fastrackit.onlineshop4shoes.transfer.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid@RequestBody CreateUserRequest request){
        User user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @Valid@RequestBody CreateUserRequest request){
        UserResponse userResponse = userService.updateUser(id, request);

        return new ResponseEntity<>(userResponse,HttpStatus.OK);

    }
}
