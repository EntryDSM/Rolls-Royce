package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.SignupRequest;
import kr.hs.entrydsm.rollsroyce.global.security.jwt.JwtTokenProvider;
import kr.hs.entrydsm.rollsroyce.global.utils.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSignupService {
    private static final String USER_ROLE = "USER";

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final StatusRepository statusRepository;

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

        statusRepository.save(Status.builder()
                .user(user)
                .isPrintsArrived(false)
                .isSubmitted(false)
                .isFirstRoundPass(false)
                .build());

        return tokenProvider.generateToken(user.getTelephoneNumber(), USER_ROLE);
    }

}
