package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateQualificationRequest {

    @Digits(integer = 2, fraction = 3)
    private BigDecimal gedAverageScore;

}
