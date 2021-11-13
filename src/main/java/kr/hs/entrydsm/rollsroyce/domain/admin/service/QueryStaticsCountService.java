package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import java.util.ArrayList;
import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.StaticsCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryStaticsCountService {

	private final UserRepository userRepository;

	public List<StaticsCountResponse> execute() {
		List<StaticsCountResponse> responseList = new ArrayList<>();
		for(ApplicationType type : ApplicationType.values()) {
			for(int i = 0; i < 2; i++) {
				int count =
						userRepository
								.findByApplicationTypeAndIsDaejeon(type, i != 0).size();
				responseList.add(
						new StaticsCountResponse(type, i != 0, count)
				);
			}
		}
		return responseList;
	}

}
