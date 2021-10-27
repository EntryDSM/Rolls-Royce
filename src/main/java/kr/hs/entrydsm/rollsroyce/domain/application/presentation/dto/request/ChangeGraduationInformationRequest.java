package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class ChangeGraduationInformationRequest {

	@Length(max = 5, message = "student_number는 5자 이하여야 합니다.")
	private String studentNumber;

	@Length(max = 7, min = 7, message = "schoolCode는 7자여야 합니다.")
	private String schoolCode;

}
