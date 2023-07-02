package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.QueryQuestionDetailAdminResponse;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.repository.QuestionRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.QuestionNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.ReplyNotFoundException;

@RequiredArgsConstructor
@Service
public class QueryQuestionDetailAdminService {
    private final QuestionRepository questionRepository;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public QueryQuestionDetailAdminResponse execute(Long questionId) {
        Question question =
                questionRepository.findById(questionId).orElseThrow(() -> QuestionNotFoundException.EXCEPTION);

        return QueryQuestionDetailAdminResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .username(question.getUser().getName())
                .isReplied(question.getIsReplied())
                .createdAt(question.getCreatedAt())
                .reply(getReply(question))
                .build();
    }

    private QueryQuestionDetailAdminResponse.ReplyDto getReply(Question question) {
        Reply reply = replyRepository.findByQuestion(question).orElseThrow(() -> ReplyNotFoundException.EXCEPTION);

        return QueryQuestionDetailAdminResponse.ReplyDto.builder()
                .title(reply.getTitle())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
