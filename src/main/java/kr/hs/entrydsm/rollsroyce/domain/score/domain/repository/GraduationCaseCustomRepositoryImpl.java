package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.GraduationCaseVo;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.QGraduationCaseVo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.hs.entrydsm.rollsroyce.domain.score.domain.QGraduationCase.graduationCase;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;
import static kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.QEntryInfo.entryInfo;

@RequiredArgsConstructor
public class GraduationCaseCustomRepositoryImpl implements GraduationCaseCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<GraduationCaseVo> findAllByGraduationCase(String koreanGrade, String socialGrade, String historyGrade, String mathGrade, String scienceGrade,
                                                          String techAndHomeGrade, String englishGrade, Integer volunteerTime, Integer latenessCount,
                                                          Integer dayAbsenceCount, Integer earlyLeaveCount, Integer lectureAbsenceCount) {
        return jpaQueryFactory.select(new QGraduationCaseVo(graduationCase))
                .join(status)
                .on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        graduationCase.koreanGrade.eq(koreanGrade),
                        graduationCase.socialGrade.eq(socialGrade),
                        graduationCase.historyGrade.eq(historyGrade),
                        graduationCase.mathGrade.eq(mathGrade),
                        graduationCase.scienceGrade.eq(scienceGrade),
                        graduationCase.techAndHomeGrade.eq(techAndHomeGrade),
                        graduationCase.englishGrade.eq(englishGrade),
                        graduationCase.volunteerTime.eq(volunteerTime),
                        graduationCase.latenessCount.eq(latenessCount),
                        graduationCase.dayAbsenceCount.eq(dayAbsenceCount),
                        graduationCase.earlyLeaveCount.eq(earlyLeaveCount),
                        graduationCase.lectureAbsenceCount.eq(lectureAbsenceCount)
                ).fetch();
    }
}
