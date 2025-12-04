package auth.res_server.demo.controller;

import auth.res_server.demo.dto.user.CreateUser;
import auth.res_server.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<String> addUser(@Valid @RequestBody CreateUser user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

}
