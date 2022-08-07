package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final PasswordEncoder passwordEncoder;

    private final UserAuthCodeFacade authCodeFacade;
    private final UserRepository userRepository;

    @Transactional
    public void execute(PasswordRequest request) {
        String email = request.getEmail();

        userRepository.findByEmail(email)
                .filter(user -> authCodeFacade.checkVerified(
                        authCodeFacade.isVerified(email)
                ))
                .map(user -> user.updatePassword(passwordEncoder.encode(request.getNewPassword())))
                .map(userRepository::save)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

}
