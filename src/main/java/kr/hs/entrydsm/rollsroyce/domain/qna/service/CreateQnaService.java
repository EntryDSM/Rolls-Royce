package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.request.CreateQnaRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CreateQnaService {
    private final UserFacade userFacade;
    private final QnaRepository qnaRepository;

    @Transactional
    public void execute(CreateQnaRequest request) {
        User user = userFacade.getCurrentUser();

        qnaRepository.save(Qna.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .isPublic(request.getIsPubic())
                .isReplied(request.getIsReplied())
                .user(user)
                .build()
        );
    }
}