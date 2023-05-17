package kr.hs.entrydsm.rollsroyce.domain.faq.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.CreateFaqRequest;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.UpdateFaqRequest;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqInfoResponse;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqResponse;
import kr.hs.entrydsm.rollsroyce.domain.faq.service.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/faq")
public class FaqController {
    private final CreateFaqService createFaqService;
    private final DeleteFaqService deleteFaqService;
    private final QueryFaqInfoService queryFaqInfoService;
    private final QueryFaqListService queryFaqListService;
    private final QueryFaqListByTypeService queryFaqListByTypeService;
    private final UpdateFaqService updateFaqService;

    @PostMapping
    public void createFaq(@RequestBody @Valid CreateFaqRequest request) {
        createFaqService.execute(request);
    }

    @PatchMapping("/{faq-id}")
    public void updateFaq(@PathVariable("faq-id") Long id, @RequestBody @Valid UpdateFaqRequest request) {
        updateFaqService.execute(id, request);
    }

    @DeleteMapping("/{faq-id}")
    public void deleteFaq(@PathVariable("faq-id") Long id) {
        deleteFaqService.execute(id);
    }

    @GetMapping("/{faq-id}")
    public QueryFaqInfoResponse queryFaqInfo(@PathVariable("faq-id") Long id) {
        return queryFaqInfoService.execute(id);
    }

    @GetMapping
    public List<QueryFaqResponse> queryFaqListByType(@RequestParam("type") String type) {
        return queryFaqListByTypeService.execute(type);
    }

    @GetMapping("/all")
    public List<QueryFaqResponse> queryFaqList() {
        return queryFaqListService.execute();
    }
}
