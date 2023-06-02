package kr.hs.entrydsm.rollsroyce.domain.faq.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.Faq;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository.FaqRepository;
import kr.hs.entrydsm.rollsroyce.domain.faq.exception.FaqNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.UpdateFaqRequest;

@RequiredArgsConstructor
@Service
public class UpdateFaqService {
    private final FaqRepository faqRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void execute(Long id, UpdateFaqRequest request) {
        Admin admin = adminFacade.getAdmin();
        Faq faq = faqRepository.findById(id).orElseThrow(() -> FaqNotFoundException.EXCEPTION);

        faq.update(request.getTitle(), request.getContent(), request.getFaqType(), admin);
    }
}
