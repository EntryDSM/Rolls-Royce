package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.repository.StatusRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UnVerifiedAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
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

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final StatusRepository statusRepository;

    private final UserAuthCodeFacade authCodeFacade;
    private final UserFacade userFacade;
    private final UserRepository userRepository;

    @Transactional
    public TokenResponse execute(SignupRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        userFacade.isAlreadyExists(email);

        if (!authCodeFacade.isVerified(email)) {
            throw UnVerifiedAuthCodeException.EXCEPTION;
        }

        User user = userRepository.save(User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build());

        statusRepository.save(Status.builder()
                .user(user)
                .isPrintsArrived(false)
                .isSubmitted(false)
                .isFirstRoundPass(false)
                .build());

        return tokenProvider.generateToken(user.getReceiptCode().toString(), "USER");
    }

}
