package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo;

import lombok.Getter;

import com.querydsl.core.annotations.QueryProjection;

@Getter
public class ApplicantCodeVo {
    private final Long receiptCode;
    private final String examCode;
    private final String name;

    @QueryProjection
    public ApplicantCodeVo(Long receiptCode, String examCode, String name) {
        this.receiptCode = receiptCode;
        this.examCode = examCode;
        this.name = name;
    }
}
