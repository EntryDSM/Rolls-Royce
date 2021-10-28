package kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SendEmailRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 공백이 될 수 없습니다.")
    private String email;

}
