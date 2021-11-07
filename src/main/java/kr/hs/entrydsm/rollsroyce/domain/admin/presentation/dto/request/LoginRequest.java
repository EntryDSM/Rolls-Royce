package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @Size(min = 8, max = 8)
    @NotBlank
    private final String id;

    @NotBlank
    private final String password;

}
