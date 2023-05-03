package kr.hs.entrydsm.rollsroyce.domain.question.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< main:src/main/java/kr/hs/entrydsm/rollsroyce/domain/qna/service/QueryDetailsQnaService.java
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.exception.AccessDeniedQnaException;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response.QueryDetailsQnaResponse;
=======
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.Reply;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ReplyRepository;
import kr.hs.entrydsm.rollsroyce.domain.question.domain.Question;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.AccessDeniedQuestionException;
import kr.hs.entrydsm.rollsroyce.domain.question.exception.ReplyNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.question.facade.QuestionFacade;
import kr.hs.entrydsm.rollsroyce.domain.question.presentation.dto.response.QueryDetailsQuestionResponse;
>>>>>>> ♻️ :: main rebase:src/main/java/kr/hs/entrydsm/rollsroyce/domain/question/service/QueryDetailsQuestionService.java
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryDetailsQuestionService {
    private final QuestionFacade questionFacade;
    private final UserFacade userFacade;

    @Transactional(readOnly = true)
<<<<<<< main:src/main/java/kr/hs/entrydsm/rollsroyce/domain/qna/service/QueryDetailsQnaService.java
    public QueryDetailsQnaResponse execute(Long questionId) {
        Qna qna = qnaFacade.getQnaById(questionId);
=======
    public QueryDetailsQuestionResponse execute(Long questionId, Long replyId) {
        Reply reply = replyRepository.getById(replyId);
        Question question = questionFacade.getQuestionById(questionId);
>>>>>>> ♻️ :: main rebase:src/main/java/kr/hs/entrydsm/rollsroyce/domain/question/service/QueryDetailsQuestionService.java
        User user = userFacade.getCurrentUser();

        if (!question.getIsPublic() && !user.getId().equals(question.getId())) {
            throw AccessDeniedQuestionException.EXCEPTION;
        }

<<<<<<< main:src/main/java/kr/hs/entrydsm/rollsroyce/domain/qna/service/QueryDetailsQnaService.java
        return getQna(questionId);
    }

    private QueryDetailsQnaResponse getQna(Long qnaId) {
        Qna qna = qnaFacade.getQnaById(qnaId);

        return QueryDetailsQnaResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .createdAt(qna.getCreatedAt())
                .username(qna.getUser().getName())
                .build();
    }
=======
        if (reply == null) {
            throw ReplyNotFoundException.EXCEPTION;
        }

        return QueryDetailsQuestionResponse.builder()
                .question(getQuestion(reply.getQuestionId()))
                .reply(getReply(replyId))
                .build();
    }

    private QueryDetailsQuestionResponse.QuestionDto getQuestion(Long questionId) {
        Question question = questionFacade.getQuestionById(questionId);

        return QueryDetailsQuestionResponse.QuestionDto.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .username(question.getUser().getName())
                .build();
    }

    private QueryDetailsQuestionResponse.ReplyDto getReply(Long replyId) {
        Reply reply = replyRepository.getById(replyId);

        return QueryDetailsQuestionResponse.ReplyDto.builder()
                .title(reply.getTitle())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
>>>>>>> ♻️ :: main rebase:src/main/java/kr/hs/entrydsm/rollsroyce/domain/question/service/QueryDetailsQuestionService.java
}
