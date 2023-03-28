package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo;

import lombok.Getter;

import com.querydsl.core.annotations.QueryProjection;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

@Getter
public class AdmissionTicketVo {
    private final String photoFileName;
    private final Long receiptCode;
    private final String name;
    private final String schoolName;
    private final String applicationType;
    private final Boolean isDaejeon;
    private final String examCode;

    @QueryProjection
    public AdmissionTicketVo(EntryInfo entryInfo, Status status, School school) {
        this.photoFileName = entryInfo.getPhotoFileName();
        this.receiptCode = entryInfo.getReceiptCode();
        this.name = entryInfo.getUserName();
        this.schoolName = school.getName();
        this.isDaejeon = entryInfo.getIsDaejeon();
        this.applicationType = entryInfo.getApplicationType().name();
        this.examCode = status.getExamCode();
    }
}
