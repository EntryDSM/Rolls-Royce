package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CreateReplyService {
    private final AdminFacade adminFacade;
    private final QuestionFacade questionFacade;
    private final ReplyRepository replyRepository;

    @Transactional
    public void execute(CreateReplyRequest request, Long questionId) {
        Admin admin = adminFacade.getAdmin();
        Question question = questionFacade.getQuestionById(questionId);

        question.updateIsReplied(true);

        replyRepository.save(Reply.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .admin(admin)
                .question(question)
                .build());
    }
}
