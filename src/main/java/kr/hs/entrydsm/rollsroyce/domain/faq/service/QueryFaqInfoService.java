package kr.hs.entrydsm.rollsroyce.domain.faq.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.Faq;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository.FaqRepository;
import kr.hs.entrydsm.rollsroyce.domain.faq.exception.FaqNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqInfoResponse;

@RequiredArgsConstructor
@Service
public class QueryFaqInfoService {
    private final FaqRepository faqRepository;

    public QueryFaqInfoResponse execute(Long id) {
        Faq faq = faqRepository.findById(id).orElseThrow(() -> FaqNotFoundException.EXCEPTION);

        return QueryFaqInfoResponse.builder()
                .title(faq.getTitle())
                .content(faq.getContent())
                .createdAt(faq.getCreatedAt())
                .type(faq.getFaqType())
                .build();
    }
}
