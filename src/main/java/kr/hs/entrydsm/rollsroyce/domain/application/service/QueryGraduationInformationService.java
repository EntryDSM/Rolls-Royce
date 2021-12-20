package kr.hs.entrydsm.rollsroyce.domain.application.service;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.user.facade.UserFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class QueryGraduationInformationService {

    private final UserFacade userFacade;
    private final S3Util s3Util;
    private final GraduationRepository graduationRepository;

    public QueryGraduationInformationResponse execute() {
        User user = userFacade.getCurrentUser();

        if (user.isQualification())
            throw EducationalStatusUnmatchedException.EXCEPTION;

        Graduation graduation =
                graduationRepository.findById(user.getReceiptCode())
                        .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        return QueryGraduationInformationResponse.builder()
                .postCode(user.getPostCode())
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .birthday(dateToString(user.getBirthday()))
                .schoolCode(graduation.getSchoolCode())
                .schoolName(graduation.getSchoolName())
                .schoolTel(graduation.getSchoolTel())
                .studentNumber(graduation.getStudentNumber())
                .name(user.getName())
                .parentName(user.getParentName())
                .parentTel(user.getParentTel())
                .photoFileName(s3Util.generateObjectUrl(user.getPhotoFileName()))
                .sex(user.getSex() != null ? user.getSex().name()
                        : null)
                .telephoneNumber(user.getTelephoneNumber())
                .build();
    }

    private String dateToString(LocalDate date) {
        return date == null ? null :
                DateTimeFormatter.ofPattern("yyyyMMdd")
                        .withZone(ZoneId.of("Asia/Seoul"))
                        .format(date);
    }

}
