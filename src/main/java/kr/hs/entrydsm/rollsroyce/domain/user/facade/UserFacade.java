package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.CredentialsNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetails;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Object detail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(detail instanceof AuthDetails)) {
            throw CredentialsNotFoundException.EXCEPTION;
        }
        return userRepository
                .findById(Long.valueOf(((AuthDetails) detail).getUsername()))
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public Long getCurrentReceiptCode() {
        return getCurrentUser().getReceiptCode();
    }

    public User getUserByCode(Long receiptCode) {
        return userRepository.findById(receiptCode).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public boolean isAlreadyExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw UserAlreadyExistsException.EXCEPTION;
        }
        return true;
    }

    public QueryInformationResponse queryInformation() {
        return getCurrentUser().queryInformation();
    }

    public String querySelfIntroduce() {
        return getCurrentUser().getSelfIntroduce();
    }

    public String queryStudyPlan() {
        return getCurrentUser().getStudyPlan();
    }
}
