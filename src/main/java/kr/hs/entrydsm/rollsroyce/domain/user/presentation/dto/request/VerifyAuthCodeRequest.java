package kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerifyAuthCodeRequest {

    @NotBlank(message = "email은 Null 또는 공백 또는 띄어쓰기를 허용하지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Length(min = 6, max = 6, message = "code는 6글자여야 합니다.") private String code;
}
