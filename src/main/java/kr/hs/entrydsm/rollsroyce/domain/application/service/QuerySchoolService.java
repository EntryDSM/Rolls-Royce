package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse.SchoolInformation;
import kr.hs.entrydsm.rollsroyce.domain.school.facade.SchoolFacade;

@RequiredArgsConstructor
@Service
public class QuerySchoolService {

    private final SchoolFacade schoolFacade;

    public QuerySchoolResponse execute(String name, Pageable pageable) {
        return new QuerySchoolResponse(schoolFacade.getSchoolByName(name, pageable).stream()
                .map(school -> new SchoolInformation(school.getCode(), school.getName(), school.getAddress()))
                .collect(Collectors.toList()));
    }
}
