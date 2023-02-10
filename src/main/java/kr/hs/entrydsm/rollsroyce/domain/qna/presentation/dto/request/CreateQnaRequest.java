package kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateQnaRequest {
    @NotBlank(message = "title은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    private String title;

    @NotBlank(message = "content은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    private String content;

    @NotNull
    private Boolean isPubic;

    @NotNull
    private Boolean isReplied;
}
