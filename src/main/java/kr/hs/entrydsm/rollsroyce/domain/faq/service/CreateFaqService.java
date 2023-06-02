package kr.hs.entrydsm.rollsroyce.domain.faq.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.Faq;
import kr.hs.entrydsm.rollsroyce.domain.faq.domain.repository.FaqRepository;
import kr.hs.entrydsm.rollsroyce.domain.faq.presentation.dto.request.CreateFaqRequest;

@RequiredArgsConstructor
@Service
public class CreateFaqService {
    private final FaqRepository faqRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void execute(CreateFaqRequest request) {
        Admin admin = adminFacade.getAdmin();

        faqRepository.save(Faq.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .faqType(request.getFaqType())
                .admin(admin)
                .build());
    }
}
