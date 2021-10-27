package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UnprovenAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.response.TokenResponse;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
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

        if(userFacade.isAlreadyExists(email))
            throw new UserAlreadyExistsException();

        if(authCodeFacade.getAuthCodeById(email).isVerified()) {
            userRepository.save(User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .build());
        } else throw new UnprovenAuthCodeException();

        return tokenProvider.generateToken(email, "user");
    }

}
