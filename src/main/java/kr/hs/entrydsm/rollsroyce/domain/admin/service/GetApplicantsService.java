package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.facade.AdminFacade;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.UserRepository;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.repository.vo.ApplicantVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetApplicantsService {


    private final UserRepository userRepository;

    public ApplicantsResponse execute(Pageable page, GetApplicantsRequest request) {

        Boolean isDaejeonQuery;
        if (request.isDaejeon() == request.isNationwide()) isDaejeonQuery = null;
        else isDaejeonQuery = request.isDaejeon();

        Boolean isOutOfHeadcount = true;
        if(!request.isOutOfHeadcount()) isOutOfHeadcount = null;

        Boolean isSubmitted;
        if (request.isSubmitted() == request.isNotSubmitted()) isSubmitted = null;
        else isSubmitted = request.isSubmitted();

        Page<ApplicantVo> applicants = userRepository.findAllByUserInfo(request.getReceiptCode(), request.getSchoolName(), request.getName(),
                isDaejeonQuery, isOutOfHeadcount,
                request.isCommon(), request.isMeister(), request.isSocial(), isSubmitted, page);

        return ApplicantsResponse.builder()
                .totalElements(applicants.getTotalElements())
                .totalPages(applicants.getTotalPages())
                .applicants(
                        applicants.stream().map(
                                        a -> ApplicantsResponse.ApplicantDto.builder()
                                                    .receiptCode(a.getReceiptCode())
                                                    .name(a.getName())
                                                    .email(a.getEmail())
                                                    .isDaejeon(a.getIsDaejeon())
                                                    .applicationType(a.getApplicationType())
                                                    .isPrintsArrived(a.getIsPrintsArrived())
                                                    .isSubmitted(a.getIsSubmitted())
                                                    .isOutOfHeadcount(a.getIsOutOfHeadcount())
                                                    .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

}
