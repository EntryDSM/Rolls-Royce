package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ApplicationCountRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryApplicationCountResponse;

@RequiredArgsConstructor
@Service
public class QueryApplicationCountService {

    private final ApplicationCountRepository applicationCountRepository;

    public List<QueryApplicationCountResponse> execute() {
        return applicationCountRepository.findAll().stream()
                .map(count -> new QueryApplicationCountResponse(
                        count.getApplicationType(), count.isDaejeon(), count.getCount()))
                .collect(Collectors.toList());
    }
}
