package kr.hs.entrydsm.rollsroyce.domain.faq.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.Faq;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository.FaqRepository;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.type.FaqType;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.response.QueryFaqResponse;

@RequiredArgsConstructor
@Service
public class QueryFaqListByTypeService {
    private final FaqRepository faqRepository;

    public List<QueryFaqResponse> execute(FaqType faqType) {
        List<Faq> faqList = faqRepository.findAllByFaqType(faqType);

        return faqList.stream()
                .map(faq -> QueryFaqResponse.builder()
                        .title(faq.getTitle())
                        .content(faq.getContent())
                        .createdAt(faq.getCreatedAt())
                        .faqType(faq.getFaqType())
                        .build())
                .collect(Collectors.toList());
    }
}
