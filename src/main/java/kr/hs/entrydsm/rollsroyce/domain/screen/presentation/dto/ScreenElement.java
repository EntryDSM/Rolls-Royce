package kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScreenElement {
    private final Long id;
    private final String image;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
