package kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateScreenRequest {
    @NotBlank(message = "image은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 255)
    private MultipartFile image;
}
