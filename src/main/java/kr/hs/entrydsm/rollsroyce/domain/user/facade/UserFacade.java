package kr.hs.entrydsm.rollsroyce.domain.user.facade;

import javax.transaction.Transactional;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeInformationRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeIntroduceRequest;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request.ChangeTypeRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.HeadCount;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
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

	@Transactional
	public void changeType(ChangeTypeRequest request) {
		User user = getUserByCode(
				getCurrentReceiptCode()
		);
		user.updateUserApplication(
				EnumUtil.getEnum(EducationalStatus.class, request.getEducationalStatus()),
				EnumUtil.getEnum(ApplicationType.class, request.getApplicationType()),
				request.getIsDaejeon(), EnumUtil.getEnum(ApplicationRemark.class, request.getApplicationRemark()),
				EnumUtil.getEnum(HeadCount.class, request.getHeadcount()));
	}

	@Transactional
	public void changeIntroduce(ChangeIntroduceRequest request) {
		User user = getUserByCode(
				getCurrentReceiptCode()
		);
		user.updateSelfIntroduce(request.getContent());

	}

	@Transactional
	public void changeInformation(ChangeInformationRequest request) {
		User user = getUserByCode(
				getCurrentReceiptCode()
		);
		user.updateUserInformation(
				request.getName(), request.getSex(), request.getBirthday(),
				request.getParentName(), request.getParentTel(), request.getTelephoneNumber(), request.getHomeTel(),
				request.getAddress(), request.getPostCode(), request.getDetailAddress()
		);
	}

}
