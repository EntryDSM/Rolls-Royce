package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeTypeRequest {

    private static final String NUMERIC_REGEXP = "^\\d{6}";

    @NotEmpty(message = "educational_status는 Null 또는 공백을 허용하지 않습니다.")
    private String educationalStatus;

    @NotEmpty(message = "application_type은 Null 또는 공백을 허용하지 않습니다.")
    private String applicationType;

    @NotNull(message = "is_daejeon은 Null을 허용하지 않습니다.")
    @JsonProperty(value = "is_daejeon")
    private Boolean isDaejeon;

    private String applicationRemark;

    @Length(min = 6, max = 6, message = "INVALID DATE")
    @Pattern(regexp = NUMERIC_REGEXP, message = "graduated_at은 숫자여야합니다.")
    private String graduatedAt;

    private Boolean isOutOfHeadcount;

    public LocalDate getGraduatedAt() {
        return YearMonth.parse(graduatedAt,
                        DateTimeFormatter.ofPattern("yyyyMM")
                                .withZone(ZoneId.of("Asia/Seoul")))
                .atDay(1);
    }

}
