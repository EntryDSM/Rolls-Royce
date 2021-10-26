package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryInformationResponse {

	private final String name;
	private final String sex;
	private final String birthday;
	private final String parentName;
	private final String parentTel;
	private final String telephoneNumber;
	private final String homeTel;
	private final String address;
	private final String detailAddress;
	private final String postCode;

}
