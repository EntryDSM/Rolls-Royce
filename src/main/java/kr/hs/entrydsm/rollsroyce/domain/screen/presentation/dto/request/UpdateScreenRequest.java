package kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateScreenRequest {
    @NotBlank(message = "image은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    private MultipartFile image;
}
