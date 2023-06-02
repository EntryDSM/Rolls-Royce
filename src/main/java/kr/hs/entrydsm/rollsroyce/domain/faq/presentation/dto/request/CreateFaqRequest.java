package kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;

@Getter
@NoArgsConstructor
public class CreateFaqRequest {

    @NotBlank(message = "title은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 100, message = "title은 최대 100자까지 가능합니다.")
    private String title;

    @NotBlank(message = "content는 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 100, message = "content는 최대 100자까지 가능합니다.")
    private String content;

    private FaqType faqType;
}
