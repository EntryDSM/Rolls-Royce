package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.domain.ApplicationCount;
import kr.hs.entrydsm.rollsroyce.domain.admin.domain.repository.ApplicationCountRepository;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.ChangeApplicationCountRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.global.utils.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChangeApplicationCountService {

    private final ApplicationCountRepository applicationCountRepository;

    public void execute(ChangeApplicationCountRequest request) {
        ApplicationType applicationType =
                EnumUtil.getEnum(ApplicationType.class, request.getApplicationType());

        applicationCountRepository
                .findByApplicationTypeAndIsDaejeon(applicationType, request.getIsDaejeon())
                .or(() -> Optional.of(new ApplicationCount(applicationType, request.getIsDaejeon(), request.getCount())))
                .ifPresent(count -> applicationCountRepository.save(count.update(request.getCount())));
    }

}
