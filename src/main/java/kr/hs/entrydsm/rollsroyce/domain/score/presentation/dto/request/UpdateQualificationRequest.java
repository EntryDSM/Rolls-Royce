package kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateQualificationRequest {

    @DecimalMin("60.0")
    @DecimalMax("100.0")
    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "ged_average_score는 null이면 안됩니다.") private BigDecimal gedAverageScore;
}
