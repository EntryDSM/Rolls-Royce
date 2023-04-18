package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.CreateReplyRequest;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;

@RequiredArgsConstructor
@Service
public class CreateReplyService {
    private final AdminFacade adminFacade;
    private final QnaFacade qnaFacade;
    private final ReplyRepository replyRepository;

    @Transactional
    public void execute(CreateReplyRequest request, Long qnaId) {
        Admin admin = adminFacade.getAdmin();
        Qna qna = qnaFacade.getQnaById(qnaId);

        qna.updateIsReplied(true);

        replyRepository.save(Reply.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .admin(admin)
                .qna(qna)
                .build());
    }
}
