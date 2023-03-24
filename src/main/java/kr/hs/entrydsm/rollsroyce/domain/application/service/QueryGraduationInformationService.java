package kr.hs.entrydsm.rollsroyce.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.exception.EducationalStatusUnmatchedException;
import kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response.QueryGraduationInformationResponse;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.facade.EntryInfoFacade;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class QueryGraduationInformationService {

    private final S3Util s3Util;

    private final EntryInfoFacade entryInfoFacade;

    private final GraduationRepository graduationRepository;

    public QueryGraduationInformationResponse execute() {
        EntryInfo entryInfo = entryInfoFacade.getCurrentEntryInfo();

        if (entryInfo.isQualification()) throw EducationalStatusUnmatchedException.EXCEPTION;

        Graduation graduation = graduationRepository
                .findById(entryInfo.getReceiptCode())
                .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);

        return QueryGraduationInformationResponse.builder()
                .postCode(entryInfo.getPostCode())
                .address(entryInfo.getAddress())
                .detailAddress(entryInfo.getDetailAddress())
                .birthday(dateToString(entryInfo.getBirthday()))
                .schoolCode(graduation.getSchoolCode())
                .schoolName(graduation.getSchoolName())
                .schoolTel(graduation.getSchoolTel())
                .studentNumber(graduation.getStudentNumber())
                .name(entryInfo.getUserName())
                .parentName(entryInfo.getParentName())
                .parentTel(entryInfo.getParentTel())
                .photoFileName(s3Util.generateObjectUrl(entryInfo.getPhotoFileName()))
                .sex(entryInfo.getSex() != null ? entryInfo.getSex().name() : null)
                .telephoneNumber(entryInfo.getUserTelephoneNumber())
                .build();
    }

    private String dateToString(LocalDate date) {
        return date == null
                ? null
                : DateTimeFormatter.ofPattern("yyyyMMdd")
                        .withZone(ZoneId.of("Asia/Seoul"))
                        .format(date);
    }
}
