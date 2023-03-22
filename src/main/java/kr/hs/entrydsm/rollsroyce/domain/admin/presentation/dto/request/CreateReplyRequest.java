package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateReplyRequest {
    @NotBlank(message = "title은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 150, message = "title은 최대 150자까지 가능합니다.")
    private String title;

    @NotBlank(message = "content은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 5000, message = "content은 최대 150자까지 가능합니다.")
    private String content;
}
