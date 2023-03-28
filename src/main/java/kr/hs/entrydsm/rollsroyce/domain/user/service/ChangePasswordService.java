package kr.hs.entrydsm.rollsroyce.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;

@RequiredArgsConstructor
@Service
public class ChangePasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void execute(PasswordRequest request) {
        User user = userRepository.findByTelephoneNumberAndName(request.getTelephoneNumber(), request.getName());

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }
}
