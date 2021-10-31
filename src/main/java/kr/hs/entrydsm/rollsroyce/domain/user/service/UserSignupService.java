package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UnprovenAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import kr.hs.entrydsm.rollsroyce.global.security.util.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final UserFacade userFacade;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthCodeFacade authCodeFacade;

    @Transactional
    public TokenResponse execute(SignupRequest request) {
        String email = request.getEmail();

        return userRepository.findByEmail(email)
                .filter(user -> userFacade.isAlreadyExists(email))
                .filter(authCode -> authCodeFacade.getAuthCodeById(email).isVerified())
                .map(user -> {
                    userRepository.save(User.builder()
                            .email(email)
                            .password(passwordEncoder.encode(request.getPassword()))
                            .name(request.getName())
                            .build());
                    return tokenProvider.generateToken(email, "user");
                })
                .orElseThrow(() -> UnprovenAuthCodeException.EXCEPTION);
    }

}
