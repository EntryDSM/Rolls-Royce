package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;

import java.util.List;

public interface ScoreCustomRepository {
    List<Score> queryScoreByApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon);
}
