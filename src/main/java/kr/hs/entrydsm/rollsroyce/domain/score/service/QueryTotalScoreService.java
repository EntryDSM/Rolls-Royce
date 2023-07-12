package kr.hs.entrydsm.rollsroyce.domain.score.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.ScoreRepository;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.response.QueryTotalScoreResponse;

@RequiredArgsConstructor
@Service
public class QueryTotalScoreService {
    private final EntryInfoFacade entryInfoFacade;
    private final ScoreRepository scoreRepository;

    @Transactional(readOnly = true)
    public QueryTotalScoreResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();
        Score score = scoreRepository.findByReceiptCode(entryInfo.getReceiptCode());
        return new QueryTotalScoreResponse(score.getTotalScore());
    }
}
