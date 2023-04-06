package kr.hs.entrydsm.rollsroyce.domain.user.service;

<<<<<<< refs/remotes/origin/main
<<<<<<< refs/remotes/origin/main
<<<<<<< refs/remotes/origin/main
=======
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request.PasswordRequest;
>>>>>>> ⚡️:: 로직 추가
=======
>>>>>>> ♻️ :: spotlessApply
=======
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
>>>>>>> ♻️ :: 전화번호만 검증
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
<<<<<<< refs/remotes/origin/main
    public void execute(PasswordRequest request) {}
=======
    public void execute(PasswordRequest request) {
        User user = userRepository.findByTelephoneNumber(request.getTelephoneNumber())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }
>>>>>>> ⚡️:: 로직 추가
}
