package by.tami.userservice.service;

import by.tami.userservice.dto.LoginArgs;
import by.tami.userservice.dto.RegisterArgs;
import by.tami.userservice.dto.Tokens;
import by.tami.userservice.exception.BadRequestException;
import by.tami.userservice.exception.NotFoundException;
import by.tami.userservice.model.User;
import by.tami.userservice.repository.UserRepository;
import by.tami.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Tokens register(RegisterArgs args) {
        User user = new User();

        if (userRepository.findByPhoneNumber(args.phoneNumber()).isPresent()) {
            throw new BadRequestException("Пользователь с таким номером телефона уже существует, " + args.phoneNumber());
        }
        user.setPhoneNumber(args.phoneNumber());
        user.setPassword(passwordEncoder.encode(args.password()));
        user = userRepository.save(user);

        Tokens tokens = new Tokens();
        tokens.setAccessToken(jwtUtil.generateToken(user));
        tokens.setRefreshToken(jwtUtil.generateToken(user));

        return tokens;
    }

    public Tokens login(LoginArgs args) {
        var user = userRepository.findByPhoneNumber(args.phoneNumber())
                .orElseThrow(() -> new NotFoundException("Пользователь с таким номером телефона не найден, " + args.phoneNumber()));

        if (!passwordEncoder.matches(args.password(), user.getPassword())) {
            throw new BadRequestException("Неверный пароль");
        }

        Tokens tokens = new Tokens();
        tokens.setAccessToken(jwtUtil.generateToken(user));
        tokens.setRefreshToken(jwtUtil.generateToken(user));

        return tokens;
    }

    public Boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
