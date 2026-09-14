package by.tami.userservice.controller;

import by.tami.userservice.dto.LoginArgs;
import by.tami.userservice.dto.RegisterArgs;
import by.tami.userservice.dto.Tokens;
import by.tami.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Tokens> register(@RequestBody RegisterArgs args) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(args));
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody LoginArgs args) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.login(args));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(
            @RequestParam("token") String token
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.validateToken(token));
    }

}
