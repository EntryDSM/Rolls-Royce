package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AdmissionTicketResponse {
    private final List<AdmissionTicket> admissionTickets;
    private final String schoolName;
    private final String examCode;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AdmissionTicket {
        //증명 사진, 접수 번호, 이름, 지역, 전형 유형
        private final String photoFileName;
        private final Long receiptCode;
        private final String name;
        private final String applicationType;
        private final Boolean isDaejeon;
    }
}
