package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.AuthCode;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.AuthCodeRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.InvalidAuthCodeException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserAuthCodeFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.VerifyAuthCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VerifyAuthCodeService {

    private final UserAuthCodeFacade authCodeFacade;
    private final AuthCodeRepository authCodeRepository;

    @Transactional
    public void execute(VerifyAuthCodeRequest request) {
        String email = request.getEmail();
        String code = request.getCode();

        AuthCode authCode = authCodeFacade.getAuthCodeById(email);
    
        Optional.of(authCode)
                .filter(s -> authCodeFacade.isAlreadyVerified(s.isVerified()))
                .filter(s -> authCodeFacade.compareCode(code, s.getCode()))
                .map(AuthCode::verify)
                .map(authCodeRepository::save)
                .orElseThrow(() -> InvalidAuthCodeException.EXCEPTION);
    }

}
