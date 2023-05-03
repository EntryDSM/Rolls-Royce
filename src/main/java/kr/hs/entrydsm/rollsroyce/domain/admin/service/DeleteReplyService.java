package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Admin;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.WriterMisMatchedException;

@RequiredArgsConstructor
@Service
public class DeleteReplyService {
    private final AdminFacade adminFacade;
    private final ReplyRepository replyRepository;

    @Transactional
    public void execute(Long replyId) {
        Admin admin = adminFacade.getAdmin();
        Reply reply = replyRepository.getById(replyId);

        if (reply.getAdminId().equals(admin.getId())) {
            throw WriterMisMatchedException.EXCEPTION;
        }

        replyRepository.delete(reply);
    }
}
