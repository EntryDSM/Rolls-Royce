package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.PasswordNotValidException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public TokenResponse execute(LoginRequest request) {
        User user = userRepository
                .findByTelephoneNumber(request.getTelephoneNumber())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw PasswordNotValidException.EXCEPTION;
        }

        return tokenProvider.generateToken(user.getTelephoneNumber(), Role.USER.toString());
    }
}
