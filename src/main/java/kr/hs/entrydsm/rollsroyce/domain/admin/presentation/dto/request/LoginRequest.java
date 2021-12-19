package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @Size(min = 8, max = 8)
    @NotBlank
    private String id;

    @NotBlank
    private String password;

}
