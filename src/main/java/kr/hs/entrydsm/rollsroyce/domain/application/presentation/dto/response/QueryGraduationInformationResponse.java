package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryGraduationInformationResponse {

	private final String name;

	private final String sex;

	private final String birthday;

	private final String schoolCode;

	private final String schoolTel;

	private final String schoolName;

	private final String studentNumber;

	private final String parentName;

	private final String telephoneNumber;

	private final String parentTel;

	private final String address;

	private final String detailAddress;

	private final String postCode;

	private String photoFileName;

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

}
