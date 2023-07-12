package kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.request;

import lombok.Getter;

import org.springframework.web.multipart.MultipartFile;

import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kr.hs.entrydsm.rollsroyce.domain.notice.domain.type.NoticeType;

@Getter
public class UpdateNoticeRequest {
    @NotBlank(message = "title은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 100, message = "title은 최대 100자까지 가능합니다.")
    private String title;

    @NotBlank(message = "content은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @Size(max = 5000, message = "content은 최대 5000자까지 가능합니다.")
    private String content;

    @NotNull private NoticeType type;

    @Nullable private Boolean isPinned;

    @Nullable private MultipartFile file;
}
