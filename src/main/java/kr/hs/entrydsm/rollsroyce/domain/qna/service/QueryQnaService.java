package kr.hs.entrydsm.rollsroyce.domain.qna.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.qna.domain.Qna;
import kr.hs.entrydsm.rollsroyce.domain.qna.domain.repository.QnaRepository;
import kr.hs.entrydsm.rollsroyce.domain.qna.presentation.dto.response.QueryQnaResponse;

@RequiredArgsConstructor
@Service
public class QueryQnaService {
    private final QnaRepository qnaRepository;

    @Transactional(readOnly = true)
    public QueryQnaResponse execute() {

        return getQnaList();
    }

    private QueryQnaResponse getQnaList() {
        List<Qna> qnaList = qnaRepository.findAllByOrderByIdDesc();

        return QueryQnaResponse.builder()
                .qnaList(qnaList.stream()
                        .map(qnaLists -> QueryQnaResponse.QnaDto.builder()
                                .title(qnaLists.getTitle())
                                .username(qnaLists.getName())
                                .isReplied(qnaLists.getIsReplied())
                                .createdAt(qnaLists.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
