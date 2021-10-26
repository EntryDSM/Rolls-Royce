package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CheckPasswordRequest {

    @NotBlank
    private final String password;

}
