package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.ChangeApplicationCountRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryApplicationCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.ChangeApplicationCountService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryApplicationCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/application-count")
@RestController
public class ApplicationCountController {

	private final QueryApplicationCountService queryApplicationCountService;
	private final ChangeApplicationCountService changeApplicationCountService;

	@GetMapping
	public List<QueryApplicationCountResponse> queryApplicationCount() {
		return queryApplicationCountService.execute();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeApplicationCount(@RequestBody @Valid ChangeApplicationCountRequest request) {
		changeApplicationCountService.execute(request);
	}

}
