package kr.hs.entrydsm.rollsroyce.domain.application.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryGraduationInformationService {

	private final UserFacade userFacade;

	public QueryGraduationInformationResponse execute() {
		User user = userFacade.getCurrentUser();
		if(user.isQualification())
			throw EducationalStatusUnmatchedException.EXCEPTION;

		return QueryGraduationInformationResponse.builder()
				.postCode(user.getPostCode())
				.address(user.getAddress())
				.detailAddress(user.getDetailAddress())
				.birthday(dateToString(user.getBirthday()))
				.homeTel(user.getHomeTel())
				.name(user.getName())
				.parentName(user.getParentName())
				.parentTel(user.getParentTel())
				.photoFileName(user.getPhotoFileName())
				.sex(user.getSex() != null ? user.getSex().name()
						: null)
				.telephoneNumber(user.getTelephoneNumber())
				.build();
	}

	private String dateToString(LocalDate date) {
		return date == null ? null :
				DateTimeFormatter.ofPattern("yyyyMM")
						.withZone(ZoneId.of("Asia/Seoul"))
						.format(date);
	}

}
