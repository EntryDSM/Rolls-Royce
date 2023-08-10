package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeInformationRequest {

    private static final String TEL_REGEXP = "^\\d{3}\\d{3,4}\\d{4}$";

    @NotNull(message = "sex는 null일 수 없습니다.") @Pattern(regexp = "^(MALE|FEMALE)$", message = "INVALID SEX")
    private String sex;

    @NotNull(message = "birthday는 Null을 허용하지 않습니다.") private LocalDate birthday;

    @Length(max = 5, message = "TOO LONG NAME") private String name;

    @NotEmpty(message = "telephone_number은 Null 또는 공백을 허용하지 않습니다.")
    @Length(max = 11) @Pattern(regexp = TEL_REGEXP, message = "INVALID TEL")
    private String telephoneNumber;

    @Length(max = 5, message = "TOO LONG NAME") private String parentName;

    @NotEmpty(message = "parent_tel은 Null 또는 공백을 허용하지 않습니다.")
    @Length(max = 11) @Pattern(regexp = TEL_REGEXP, message = "INVALID TEL")
    private String parentTel;

    @NotEmpty(message = "address는 Null 또는 공백을 허용하지 않습니다.")
    @Length(max = 300, message = "TOO LONG ADDRESS") private String address;

    @NotEmpty(message = "detail_address는 Null 또는 공백을 허용하지 않습니다.")
    @Length(max = 100, message = "TOO LONG DETAIL_ADDRESS") private String detailAddress;

    @Length(min = 5, max = 5, message = "INVALID POST_CODE") private String postCode;
}
