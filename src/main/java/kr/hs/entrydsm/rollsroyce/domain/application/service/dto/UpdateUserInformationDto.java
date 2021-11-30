package kr.hs.entrydsm.rollsroyce.domain.application.service.dto;

import java.time.LocalDate;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.Sex;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserInformationDto {

	private final String name;

	private final Sex sex;

	private final LocalDate birthday;

	private final String parentName;

	private final String parentTel;

	private final String telephoneNumber;

	private final String address;

	private final String postCode;

	private final String detailAddress;

}
