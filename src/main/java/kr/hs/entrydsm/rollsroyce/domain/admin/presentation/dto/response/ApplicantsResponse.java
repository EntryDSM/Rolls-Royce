package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApplicantsResponse {

    private final long totalElements;

    private final long totalPages;

    private final List<ApplicantDto> applicants;

    @Getter
    @Builder
    public static class ApplicantDto {
        private final Long receiptCode;
        private final String name;
        private final String telephoneNumber;
        private final Boolean isDaejeon;
        private final String applicationType;
        private final Boolean isPrintsArrived;
        private final Boolean isSubmitted;
        private final Boolean isOutOfHeadcount;
    }
}
