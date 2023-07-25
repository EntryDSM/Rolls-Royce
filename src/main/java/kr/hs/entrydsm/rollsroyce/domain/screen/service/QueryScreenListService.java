package kr.hs.entrydsm.rollsroyce.domain.screen.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.screen.domain.Screen;
import kr.hs.entrydsm.rollsroyce.domain.screen.domain.repository.ScreenRepository;

@RequiredArgsConstructor
@Service
public class QueryScreenListService {
    private final ScreenRepository screenRepository;

    @Transactional(readOnly = true)
    public List<Screen> execute() {
        List<Screen> screenList = screenRepository.findAll();
        return screenList;
    }
}
