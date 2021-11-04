package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateQualificationRequest {

    @Digits(integer = 3, fraction = 2)
    private BigDecimal gedAverageScore;

}
