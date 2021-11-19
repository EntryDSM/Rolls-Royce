package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryApplicationCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryApplicationCountService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/application-count")
public class ApplicationCountController {

	private final QueryApplicationCountService queryApplicationCountService;

	@GetMapping
	public List<QueryApplicationCountResponse> queryApplicationCount() {
		return queryApplicationCountService.execute();
	}

}
