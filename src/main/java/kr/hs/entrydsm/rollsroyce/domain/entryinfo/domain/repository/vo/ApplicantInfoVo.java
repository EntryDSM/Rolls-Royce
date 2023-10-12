package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo;

import lombok.Getter;

import com.querydsl.core.annotations.QueryProjection;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

@Getter
public class ApplicantInfoVo {
    private final EntryInfo entryInfo;
    private final GraduationCase graduationCase;
    private final Graduation graduation;
    private final Status status;
    private final Score score;

    @QueryProjection
    public ApplicantInfoVo(
            EntryInfo entryInfo, GraduationCase graduationCase, Graduation graduation, Status status, Score score) {
        this.entryInfo = entryInfo;
        this.graduationCase = graduationCase;
        this.graduation = graduation;
        this.status = status;
        this.score = score;
    }
}
