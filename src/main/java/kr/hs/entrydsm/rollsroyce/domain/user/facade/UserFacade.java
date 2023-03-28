package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String telephoneNumber =
                SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByTelephoneNumber(telephoneNumber).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public User getUserByTelephoneNumber(String telephoneNumber) {
        return userRepository.findByTelephoneNumber(telephoneNumber).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
