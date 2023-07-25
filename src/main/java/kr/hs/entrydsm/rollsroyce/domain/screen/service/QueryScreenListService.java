package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;

@RequiredArgsConstructor
@Service
public class QueryScreenListService {
    private final ScreenRepository screenRepository;

    @Transactional(readOnly = true)
    public List<Screen> execute() {
        List<Screen> screenList = screenRepository.findAll();
        return screenList.stream()
                .map(screen -> Screen.builder().image(screen.getImage()).build())
                .collect(Collectors.toList());
    }
}
