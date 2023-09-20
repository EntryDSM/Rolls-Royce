package kr.hs.entrydsm.rollsroyce.domain.faq.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.util.annotation.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.CreateFaqRequest;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.UpdateFaqRequest;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqResponse;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.CreateFaqService;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.DeleteFaqService;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.QueryFaqInfoService;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.QueryFaqListByTypeService;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.UpdateFaqService;

@Tag(name = "자주 묻는 질문 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/faq")
public class FaqController {
    private final CreateFaqService createFaqService;
    private final DeleteFaqService deleteFaqService;
    private final QueryFaqInfoService queryFaqInfoService;
    private final QueryFaqListByTypeService queryFaqListByTypeService;
    private final UpdateFaqService updateFaqService;

    @Operation(summary = "FAQ 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFaq(@RequestBody @Valid CreateFaqRequest request) {
        createFaqService.execute(request);
    }

    @Operation(summary = "FAQ 수정 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{faq-id}")
    public void updateFaq(@PathVariable("faq-id") Long id, @RequestBody @Valid UpdateFaqRequest request) {
        updateFaqService.execute(id, request);
    }

    @Operation(summary = "FAQ 삭제 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{faq-id}")
    public void deleteFaq(@PathVariable("faq-id") Long id) {
        deleteFaqService.execute(id);
    }

    @Operation(summary = "FAQ 상세조회 API")
    @GetMapping("/{faq-id}")
    public QueryFaqInfoResponse queryFaqInfo(@PathVariable("faq-id") Long id) {
        return queryFaqInfoService.execute(id);
    }

    @Operation(summary = "FAQ 유형별 전체조회 API")
    @GetMapping
    public List<QueryFaqResponse> queryFaqListByType(@RequestParam("type") @Nullable FaqType type) {
        return queryFaqListByTypeService.execute(type);
    }
}
