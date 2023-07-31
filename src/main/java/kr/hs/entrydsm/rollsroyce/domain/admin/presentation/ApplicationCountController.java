package kr.hs.entrydsm.rollsroyce.domain.admin.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.ChangeApplicationCountRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryApplicationCountResponse;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.ChangeApplicationCountService;
import kr.hs.entrydsm.rollsroyce.domain.admin.service.QueryApplicationCountService;

@Tag(name = "어드민 입학 원서 지원자 통계 API")
@RequiredArgsConstructor
@RequestMapping("/admin/application-count")
@RestController
public class ApplicationCountController {

    private final QueryApplicationCountService queryApplicationCountService;
    private final ChangeApplicationCountService changeApplicationCountService;

    @Operation(summary = "유형별 인원 가져오기 API")
    @GetMapping
    public List<QueryApplicationCountResponse> queryApplicationCount() {
        return queryApplicationCountService.execute();
    }

    @Operation(summary = "유형별 인원 변경 API")
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeApplicationCount(@RequestBody @Valid ChangeApplicationCountRequest request) {
        changeApplicationCountService.execute(request);
    }
}
