package kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;

@Getter
@Builder
public class QueryFaqInfoResponse {
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final FaqType type;
}
