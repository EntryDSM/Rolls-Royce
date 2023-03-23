package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.types.Role;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;

@RequiredArgsConstructor
@Service
public class UserSignupService {

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Transactional
    public TokenResponse execute(SignupRequest request) {
        String name = request.getName();
        String telephoneNumber = request.getTelephoneNumber();
        String password = passwordEncoder.encode(request.getPassword());

        if (userRepository.existsByTelephoneNumber(request.getTelephoneNumber())) {
            throw UserAlreadyExistsException.EXCEPTION;
        }

        User user = userRepository.save(User.builder()
                .name(name)
                .telephoneNumber(telephoneNumber)
                .password(password)
                .build());

        return tokenProvider.generateToken(user.getTelephoneNumber(), Role.USER.toString());
    }
}
