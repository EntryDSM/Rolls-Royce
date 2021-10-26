package kr.hs.entrydsm.rollsroyce.domain.admin.facade;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotFoundException;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class AdminAuthenticationFacade {

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw AdminNotFoundException.EXCEPTION;
        }
        return authentication.getName();
    }

}
