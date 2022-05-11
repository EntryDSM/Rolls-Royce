package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.CheckPasswordLimit;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.CheckPasswordLimitRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.PasswordNotValidException;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CheckPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckPasswordService {
    private final CheckPasswordLimitRepository checkPasswordLimitRepository;

    private final AdminFacade adminFacade;
    private final PasswordEncoder passwordEncoder;

    public void execute(CheckPasswordRequest request) {
        Admin admin = adminFacade.getAdmin();

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw PasswordNotValidException.EXCEPTION;
        }

        checkPasswordLimitRepository.save(
                new CheckPasswordLimit(admin.getId())
        );
    }

}
