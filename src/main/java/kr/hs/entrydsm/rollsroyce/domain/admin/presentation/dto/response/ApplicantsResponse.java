package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantsResponse {

    private long totalElements;

    private long totalPages;

    private List<ApplicantDto> applicants;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicantDto {

        private Long receiptCode;

        private String name;

        private String email;

        private Boolean isDaejeon;

        private String applicationType;

        private Boolean isPrintsArrived;

        private Boolean isSubmitted;

        private String headcount;

    }

}
