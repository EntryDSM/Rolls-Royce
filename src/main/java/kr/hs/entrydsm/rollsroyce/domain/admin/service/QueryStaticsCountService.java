package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.util.ArrayList;
import java.util.List;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoCustomRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryStaticsCountService {

    private final EntryInfoRepository entryInfoRepository;

    public List<StaticsCountResponse> execute() {
        List<StaticsCountResponse> responseList = new ArrayList<>();
        for (ApplicationType type : ApplicationType.values()) {
            for (int i = 0; i < 2; i++) {
                int count = entryInfoRepository.queryStaticsCount(type, i != 0).size();
                responseList.add(
                        new StaticsCountResponse(type, i != 0, count)
                );
            }
        }
        return responseList;
    }
}
