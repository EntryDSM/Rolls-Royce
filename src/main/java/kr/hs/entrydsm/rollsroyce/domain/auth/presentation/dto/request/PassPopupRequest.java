package kr.hs.entrydsm.rollsroyce.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PassPopupRequest {

    @NotBlank(message = "redirect_url은 Null 또는 공백 또는 띄어쓰기를 허용하지 않습니다.")
    private String redirectUrl;
}
