package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

public interface ScoreCustomRepository {
    List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
