package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.CredentialsNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserAlreadyExistsException;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.UserNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

	private final UserRepository userRepository;

	public User getCurrentUser() {
		Object detail =
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!(detail instanceof AuthDetails)) {
			throw CredentialsNotFoundException.EXCEPTION;
		}
		return ((AuthDetails)detail).getUser();
	}

	public Long getCurrentReceiptCode() {
		return getCurrentUser().getReceiptCode();
	}

	public String getCurrentEmail() {
		return getCurrentUser().getEmail();
	}

	public String getCurrentName() {
		return getCurrentUser().getName();
	}

	public String getCurrentPhoneNumber() {
		return getCurrentUser().getTelephoneNumber();
	}

	public ApplicationType getCurrentApplicationType() {
		return getCurrentUser().getApplicationType();
	}

	public String getCurrentSelfIntroduce() {
		return getCurrentUser().getSelfIntroduce();
	}

	public String getCurrentStudyPlan() {
		return getCurrentUser().getStudyPlan();
	}

	public User getUserByCode(Long receiptCode) {
		return userRepository.findById(receiptCode)
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	public boolean isAlreadyExists(String email) {
		if(userRepository.findByEmail(email).isPresent())
			throw UserAlreadyExistsException.EXCEPTION;

		return true;
	}

}
