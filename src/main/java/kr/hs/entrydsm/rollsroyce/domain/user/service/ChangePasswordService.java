package kr.hs.entrydsm.rollsroyce.domain.user.service;

import kr.hs.entrydsm.rollsroyce.domain.auth.domain.PassInfo;
import kr.hs.entrydsm.rollsroyce.domain.auth.domain.repository.PassInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.auth.exception.PassInfoNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;

@RequiredArgsConstructor
@Service
public class ChangePasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PassInfoRepository passInfoRepository;

    @Transactional
    public void execute(PasswordRequest request) {

        if(passInfoRepository.findByPhoneNumber(request.getTelephoneNumber()).isEmpty()) {
            throw PassInfoNotFoundException.EXCEPTION;
        }

        User user = userRepository
                .findByTelephoneNumber(request.getTelephoneNumber())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
