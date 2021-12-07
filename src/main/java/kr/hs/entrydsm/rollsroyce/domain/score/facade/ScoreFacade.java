package kr.hs.entrydsm.rollsroyce.domain.score.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.ApplicationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
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

    @Transactional
    public void updateScore(User user, ApplicationCase applicationCase) {
    	if(scoreRepository.findById(user.getReceiptCode()).isPresent())
    		scoreRepository.findById(user.getReceiptCode())
					.ifPresent(score -> score.update(applicationCase));
    	else
        	scoreRepository.save(new Score(user, applicationCase));
    }

    public Score queryScore(long receiptCode) {
    	return scoreRepository.findById(receiptCode)
				.orElseThrow(() -> ScoreNotFoundException.EXCEPTION);
	}

	public List<Score> queryScoreByApplicationTypeAndIsDaejeon(
			ApplicationType applicationType, boolean isDaejeon
	) {
    	return scoreRepository
				.queryScoreByApplicationTypeAndIsDaejeon(applicationType, isDaejeon);
	}

	public void listSort(List<Score> scores) {
    	scores.sort(Comparator.comparing(o -> {
			Score score = (Score) o;
			BigDecimal totalScore = score.getTotalScore();
			if (!userFacade.getUserByCode(score.getReceiptCode()).isCommonApplicationType()) {
				totalScore = totalScore.multiply(BigDecimal.valueOf(1.75)).setScale(3, RoundingMode.HALF_UP);
			}
			return totalScore;
		}).reversed());
	}

}
