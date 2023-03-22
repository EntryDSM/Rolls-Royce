package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeStudyPlanRequest {

    @Length(max = 1_600, message = "content는 최대 1,600글자까지만 허용됩니다.") private String content;
}
