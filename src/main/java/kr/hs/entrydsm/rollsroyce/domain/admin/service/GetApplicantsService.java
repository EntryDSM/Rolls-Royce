package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserInformationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetApplicantsService {

    private final UserInformationRepositoryImpl userCustomRepository;

    private final AdminFacade adminFacade;

    public ApplicantsResponse execute(Pageable page, GetApplicantsRequest request) {
        adminFacade.getAdmin();

        Boolean isDaejeonQuery;
        if (request.isDaejeon() == request.isNationwide()) isDaejeonQuery = null;
        else isDaejeonQuery = request.isDaejeon();

        Page<User> users = userCustomRepository.findAllByUserInfo(request.getReceiptCode(), isDaejeonQuery,
                request.getSchoolName(), request.getName(),
                request.isInOfHeadcount(), request.isOutOfHeadcount(),
                request.isCommon(), request.isMeister(), request.isSocial(), request.isSubmitted(), page);

        return ApplicantsResponse.builder()
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .applicants(
                        users.stream().map(
                                user -> ApplicantsResponse.ApplicantDto.builder()
                                        .receiptCode(user.getReceiptCode())
                                        .name(user.getName())
                                        .email(user.getEmail())
                                        .isDaejeon(user.getIsDaejeon())
                                        .applicationType(user.getApplicationType().name())
                                        .isPrintsArrived(user.getStatus().getIsPrintsArrived())
                                        .isSubmitted(user.getStatus().getIsSubmitted())
                                        .headcount(user.getHeadcount().name())
                                        .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }

}
