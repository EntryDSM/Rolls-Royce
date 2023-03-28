package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import static kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.QEntryInfo.entryInfo;
import static kr.hs.entrydsm.rollsroyce.domain.score.domain.QScore.score;
import static kr.hs.entrydsm.rollsroyce.domain.status.domain.QStatus.status;

import lombok.RequiredArgsConstructor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.math.BigDecimal;
import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.QScoreVo;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.ScoreVo;

@RequiredArgsConstructor
public class ScoreCustomRepositoryImpl implements ScoreCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon) {
        return jpaQueryFactory
                .selectFrom(score)
                .innerJoin(score.entryInfo, entryInfo)
                .innerJoin(status)
                .on(status.entryInfo.eq(entryInfo))
                .where(
                        entryInfo.applicationType.eq(applicationType),
                        entryInfo.isDaejeon.eq(isDaejeon),
                        status.isSubmitted.eq(Boolean.TRUE))
                .orderBy(score.totalScore.desc())
                .fetch();
    }

    @Override
    public List<ScoreVo> findAllByScore(
            BigDecimal thirdGradeScore,
            BigDecimal thirdBeforeScore,
            BigDecimal thirdBeforeBeforeScore,
            BigDecimal totalGradeScore,
            BigDecimal volunteerScore,
            Integer attendanceScore,
            BigDecimal totalScore) {

        return jpaQueryFactory
                .select(new QScoreVo(score))
                .from(score)
                .join(status)
                .on(entryInfo.receiptCode.eq(status.receiptCode))
                .where(
                        score.thirdGradeScore.eq(thirdGradeScore),
                        score.thirdBeforeScore.eq(thirdBeforeScore),
                        score.thirdBeforeBeforeScore.eq(thirdBeforeBeforeScore),
                        score.totalGradeScore.eq(thirdGradeScore),
                        score.volunteerScore.eq(volunteerScore),
                        score.attendanceScore.eq(attendanceScore),
                        score.totalScore.eq(totalScore))
                .fetch();
    }
}
