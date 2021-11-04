package kr.hs.entrydsm.rollsroyce.domain.score.facade;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.ApplicationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreFacade {

    private final ScoreRepository scoreRepository;

    public void updateScore(long receiptCode, ApplicationCase applicationCase) {
        scoreRepository.save(new Score(receiptCode, applicationCase));
    }

}
