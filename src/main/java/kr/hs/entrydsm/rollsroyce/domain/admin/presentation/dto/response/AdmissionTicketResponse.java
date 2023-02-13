package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdmissionTicketResponse {
    private final List<AdmissionTicket> admissionTickets;

    @Getter
    @Builder
    public static class AdmissionTicket {
        private final String photoFileName; //증명사진
        private final Long receiptCode; //접수 번호
        private final String name; //이름
        private final String applicationType; //전형 유형
        private final Boolean isDaejeon; //지역
        private final String schoolName; //출신 중학교 이름
        private final String examCode; //수험번호
    }
}
