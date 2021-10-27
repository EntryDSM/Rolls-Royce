package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthCodeFacade {

    private final AuthCodeRepository authCodeRepository;

    public AuthCode getAuthCodeById(String email) {
        return authCodeRepository.findById(email).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

}
