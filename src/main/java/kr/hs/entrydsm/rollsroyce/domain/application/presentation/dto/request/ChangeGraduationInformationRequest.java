package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class ChangeGraduationInformationRequest {

	private static final String TEL_REGEXP = "^\\d{3}\\d{3,4}\\d{4}$";

	@Length(max = 5, message = "student_number는 5자 이하여야 합니다.")
	private String studentNumber;

	@Length(max = 7, min = 7, message = "schoolCode는 7자여야 합니다.")
	private String schoolCode;

	@NotEmpty(message = "school_tel는 Null 또는 공백을 허용하지 않습니다.")
	@Pattern(regexp = TEL_REGEXP, message = "INVALID TEL")
	private String schoolTel;

}
