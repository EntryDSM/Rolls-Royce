package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.UpdateReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void execute(Long replyId, UpdateReplyRequest request) {
        Reply reply = replyRepository.getById(replyId);

        reply.updateReply(
                request.getTitle(),
                request.getContent()
        );
    }
}
