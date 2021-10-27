package kr.hs.entrydsm.rollsroyce.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @Email(message = "email 형식을 지켜야 합니다.")
    @NotBlank(message = "email은 공백이 될 수 없습니다.")
    private String email;

    @NotBlank(message = "password는 공백이 될 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!#$%&'()*+,-./:;<=>?@＼^_`{|}~]){8,32}$",
            message = "password는 소문자, 숫자, 특수문자가 포함되어야 합니다.")
    private String password;

    @NotBlank(message = "name은 공백이 될 수 없습니다.")
    @Length(min = 1, max = 5, message = "INVALID NAME")
    private String name;

}
