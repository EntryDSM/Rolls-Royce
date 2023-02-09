package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;

import java.math.BigDecimal;
import java.util.List;

public interface ScoreCustomRepository {
    List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);

    List<Score> findAllByScore(BigDecimal thirdGradeScore, BigDecimal thirdBeforeScore, BigDecimal thirdBeforeBeforeScore,
                               BigDecimal totalGradeScore, BigDecimal volunteerScore, Integer attendanceScore, BigDecimal totalScore);
}
