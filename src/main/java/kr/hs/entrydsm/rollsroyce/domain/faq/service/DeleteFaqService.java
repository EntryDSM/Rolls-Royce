package kr.hs.entrydsm.rollsroyce.domain.faq.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository.FaqRepository;

@RequiredArgsConstructor
@Service
public class DeleteFaqService {
    private final FaqRepository faqRepository;

    @Transactional
    public void execute(Long id) {
        faqRepository.deleteById(id);
    }
}
