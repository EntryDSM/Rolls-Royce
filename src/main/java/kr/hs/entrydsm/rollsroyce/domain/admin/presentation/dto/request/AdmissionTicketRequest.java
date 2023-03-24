package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

import org.springframework.lang.Nullable;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;

@Getter
@Builder
public class AdmissionTicketRequest {
    @Nullable private final String photoFileName;

    @Nullable private final String receiptCode;

    @Nullable private final String name;

    @Nullable private final String schoolName;

    @Nullable private final Boolean isDaejeon;

    @Nullable private final ApplicationType applicationType;

    @Nullable private final String examCode;
}
