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

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#*_])[a-zA-Z0-9~!@#*_]{8,32}$")
    private String password;

    @NotBlank
    @Length(min = 1, max = 5, message = "INVALID NAME")
    private String name;
}
