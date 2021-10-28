package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetApplicantsService {

    private final UserCustomRepository userCustomRepository;

    public ApplicantsResponse execute(Pageable page, String receiptCode, String name, String schoolName,
                                      boolean isDaejeon, boolean isNationwide,
                                      boolean isCommon, boolean isMeister, boolean isSocial,
                                      boolean inOfHeadcount, boolean outOfHeadcount, boolean isSubmitted) {
        Boolean isDaejeonQuery;
        if (isDaejeon == isNationwide) isDaejeonQuery = null;
        else isDaejeonQuery = isDaejeon;

        Page<User> users = userCustomRepository.findAllByUserInfo(receiptCode, isDaejeonQuery,
                "%"+schoolName+ "%", "%"+name+"%",
                inOfHeadcount, outOfHeadcount,
                isCommon, isMeister, isSocial, isSubmitted, page);

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
