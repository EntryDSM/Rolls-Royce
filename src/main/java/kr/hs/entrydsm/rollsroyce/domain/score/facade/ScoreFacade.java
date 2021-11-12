package kr.hs.entrydsm.rollsroyce.domain.score.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.ApplicationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.exception.ScoreNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreFacade {

	private final UserFacade userFacade;
    private final ScoreRepository scoreRepository;

    public void updateScore(long receiptCode, ApplicationCase applicationCase) {
        scoreRepository.save(new Score(receiptCode, applicationCase));
    }

    public Score queryScore(long receiptCode) {
    	return scoreRepository.findById(receiptCode)
				.orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
	}

	public List<Score> queryScoreByApplicationTypeAndIsDaejeon(
			ApplicationType applicationType, boolean isDaejeon
	) {
    	List<Score> scores = userFacade.queryUserByApplicationTypeAndIsDaejeon(applicationType, isDaejeon)
				.parallelStream().map(user -> queryScore(user.getReceiptCode())).collect(Collectors.toList());
    	listSort(scores);
    	return scores;
	}

	public void listSort(List<Score> scores) {
    	scores.sort(Comparator.comparing(o -> {
			Score score = (Score) o;
			BigDecimal totalScore = score.getTotalScore();
			if (!queryScore(score.getReceiptCode()).getUser().isCommonApplicationType()) {
				totalScore = totalScore.multiply(BigDecimal.valueOf(1.75)).setScale(3, RoundingMode.HALF_UP);
			}
			return totalScore;
		}).reversed());
	}

}
