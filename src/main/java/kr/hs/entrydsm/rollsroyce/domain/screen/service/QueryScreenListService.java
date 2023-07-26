package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;
import kr.hs.entrydsm.rollsroyce.domain.screen.presentation.dto.ScreenElement;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class QueryScreenListService {
    private final ScreenRepository screenRepository;
    private final S3Util s3Util;

    @Transactional(readOnly = true)
    public List<ScreenElement> execute() {
        List<Screen> screenList = screenRepository.findAll();
        return screenList.stream()
                .map(screenElement -> ScreenElement.builder()
                        .id(screenElement.getId())
                        .image(s3Util.generateObjectUrl(screenElement.getImage()))
                        .createdAt(screenElement.getCreatedAt())
                        .modifiedAt(screenElement.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
