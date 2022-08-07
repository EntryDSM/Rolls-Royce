package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.facade.StatusFacade;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetApplicantsService {

    private final AdminFacade adminFacade;

    private final StatusFacade statusFacade;

    private final UserRepository userRepository;

    public ApplicantsResponse execute(Pageable page, GetApplicantsRequest request) {
        adminFacade.findAdmin();

        Boolean isDaejeonQuery;
        if (request.isDaejeon() == request.isNationwide()) isDaejeonQuery = null;
        else isDaejeonQuery = request.isDaejeon();

        Boolean isSubmitted;
        if (request.isSubmitted() == request.isNotSubmitted()) isSubmitted = null;
        else isSubmitted = request.isSubmitted();

        Page<User> users = userRepository.findAllByUserInfo(request.getReceiptCode(), request.getSchoolName(), request.getName(),
                isDaejeonQuery, request.isInOfHeadcount(), request.isOutOfHeadcount(),
                request.isCommon(), request.isMeister(), request.isSocial(), isSubmitted, page);

        return ApplicantsResponse.builder()
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .applicants(
                        users.stream().map(
                                        user -> {
                                            Status status = statusFacade.getStatusByReceiptCode(user.getReceiptCode());
                                            return ApplicantsResponse.ApplicantDto.builder()
                                                    .receiptCode(user.getReceiptCode())
                                                    .name(user.getName())
                                                    .email(user.getEmail())
                                                    .isDaejeon(user.getIsDaejeon())
                                                    .applicationType(user.getApplicationType().name())
                                                    .isPrintsArrived(status.getIsPrintsArrived())
                                                    .isSubmitted(status.getIsSubmitted())
                                                    .build();
                                        }
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

}
