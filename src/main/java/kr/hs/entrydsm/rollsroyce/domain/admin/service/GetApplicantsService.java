package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request.GetApplicantsRequest;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response.ApplicantsResponse;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.repository.vo.ApplicantVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetApplicantsService {


    private final EntryInfoRepository entryInfoRepository;

    public ApplicantsResponse execute(Pageable page, GetApplicantsRequest request) {

        Boolean isDaejeonQuery;
        if (request.isDaejeon() == request.isNationwide()) {
            isDaejeonQuery = null;
        } else {
            isDaejeonQuery = request.isDaejeon();
        }

        Boolean isOutOfHeadcount = true;
        if (!request.isOutOfHeadcount()) {
            isOutOfHeadcount = null;
        }

        Boolean isSubmitted;
        if (request.isSubmitted() == request.isNotSubmitted()) {
            isSubmitted = null;
        } else {
            isSubmitted = request.isSubmitted();
        }

        Page<ApplicantVo> applicants = entryInfoRepository.findAllByEntryInfo(
                request.getReceiptCode(),
                request.getSchoolName(),
                request.getName(),
                isDaejeonQuery,
                isOutOfHeadcount,
                request.isCommon(),
                request.isMeister(),
                request.isSocial(),
                isSubmitted,
                page
        );

        return ApplicantsResponse.builder()
                .totalElements(applicants.getTotalElements())
                .totalPages(applicants.getTotalPages())
                .applicants(
                        applicants.stream().map(
                                        applicant -> ApplicantsResponse.ApplicantDto.builder()
                                                .receiptCode(applicant.getReceiptCode())
                                                .name(applicant.getName())
                                                .telephoneNumber(applicant.getTelephoneNumber())
                                                .isDaejeon(applicant.getIsDaejeon())
                                                .applicationType(applicant.getApplicationType())
                                                .isPrintsArrived(applicant.getIsPrintsArrived())
                                                .isSubmitted(applicant.getIsSubmitted())
                                                .isOutOfHeadcount(applicant.getIsOutOfHeadcount())
                                                .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

}
