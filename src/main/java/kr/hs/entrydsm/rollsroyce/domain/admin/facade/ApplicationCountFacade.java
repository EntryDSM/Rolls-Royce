package kr.hs.entrydsm.rollsroyce.domain.admin.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ApplicationCountRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ApplicationCountNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

@RequiredArgsConstructor
@Component
public class ApplicationCountFacade {

    private final ApplicationCountRepository applicationCountRepository;

    public int countOfApplicationTypeAndIsDaejeon(ApplicationType applicationType, boolean isDaejeon) {
        return applicationCountRepository
                .findByApplicationTypeAndIsDaejeon(applicationType, isDaejeon)
                .orElseThrow(() -> ApplicationCountNotFoundException.EXCEPTION)
                .getCount();
    }
}
