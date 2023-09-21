package kr.hs.entrydsm.rollsroyce.domain.notice.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadNoticeImageResponse {
    private final String fileUrl;
    private final String fileName;
}
