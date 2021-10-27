package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.AdminRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.AdminNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminAuthFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.LoginRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final AdminAuthFacade adminAuthFacade;

    public TokenResponse execute(LoginRequest request) {
        return adminRepository.findById(request.getId())
                .filter(admin -> passwordEncoder.matches(request.getPassword(), admin.getPassword()))
                .map(Admin::getId)
                .map(adminAuthFacade::getToken)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
