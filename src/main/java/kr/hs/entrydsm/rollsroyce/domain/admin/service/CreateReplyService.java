package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;

@RequiredArgsConstructor
@Service
public class CreateReplyService {
    private final AdminFacade adminFacade;
    private final ReplyRepository replyRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void execute(CreateReplyRequest request, Long questionId) {
        Admin admin = adminFacade.getAdmin();
        Question question =
                questionRepository.findById(questionId).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);
        ;

        question.updateIsReplied(true);

        replyRepository.save(Reply.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .admin(admin)
                .question(question)
                .build());
    }
}
