package kr.hs.entrydsm.rollsroyce.domain.application.service;

import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QuerySchoolResponse.SchoolInformation;
import kr.hs.entrydsm.rollsroyce.domain.school.facade.SchoolFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySchoolService {

	private final SchoolFacade schoolFacade;

	public QuerySchoolResponse execute(String name,
			int size, int page) {
		return new QuerySchoolResponse(schoolFacade.getSchoolByName(name, size, page)
				.stream()
				.map(school -> new SchoolInformation(school.getCode(), school.getName(),
						school.getInformation(), school.getAddress()))
				.collect(Collectors.toList()));
	}

}
