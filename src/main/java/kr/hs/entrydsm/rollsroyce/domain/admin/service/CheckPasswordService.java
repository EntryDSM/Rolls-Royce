package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.PasswordNotValidException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthenticationFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckPasswordService {

    private final PasswordEncoder passwordEncoder;
    private final AdminFacade adminFacade;
    private final AdminAuthenticationFacade authenticationFacade;

    public boolean execute(String password) {
        Admin admin = adminFacade.getRootAdmin(authenticationFacade.getEmail());
        if(!passwordEncoder.matches(password, admin.getPassword())) {
            throw PasswordNotValidException.EXCEPTION;
        }
        return true;
    }

}
