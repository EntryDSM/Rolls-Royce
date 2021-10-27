package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.CredentialsNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

	private final UserRepository userRepository;

	public Long getCurrentReceiptCode() {
		Object detail =
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(detail instanceof AuthDetails) {
			return ((AuthDetails)detail).getUser().getReceiptCode();
		}
		throw CredentialsNotFoundException.EXCEPTION;
	}

	public User getUserByCode(Long receiptCode) {
		return userRepository.findById(receiptCode)
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

}
