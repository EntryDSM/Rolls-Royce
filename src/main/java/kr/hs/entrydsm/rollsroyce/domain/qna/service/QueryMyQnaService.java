package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.facade.QnaFacade;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response.QueryQnaResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;

@RequiredArgsConstructor
@Service
public class QueryMyQnaService {
    private final UserFacade userFacade;
    private final QnaFacade qnaFacade;

    @Transactional(readOnly = true)
    public QueryQnaResponse execute() {
        User user = userFacade.getCurrentUser();
        List<Qna> qna = qnaFacade.getQnaAllByUser(user);

        return QueryQnaResponse.builder()
                .qnaList(qna.stream()
                        .map(qnaList -> QueryQnaResponse.QnaDto.builder()
                                .id(qnaList.getId())
                                .title(qnaList.getTitle())
                                .username(qnaList.getUserName())
                                .isReplied(qnaList.getIsReplied())
                                .isPublic(qnaList.getIsPublic())
                                .createdAt(qnaList.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
