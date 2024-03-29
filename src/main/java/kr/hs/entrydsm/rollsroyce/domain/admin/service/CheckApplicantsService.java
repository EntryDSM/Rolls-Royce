package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ExcelOException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.CheckApplicantInformation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.ApplicantInfoVo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.status.domain.Status;

@RequiredArgsConstructor
@Service
public class CheckApplicantsService {

    private final EntryInfoRepository entryInfoRepository;

    CheckApplicantInformation applicantInformation;

    public void execute(HttpServletResponse response) {
        applicantInformation = new CheckApplicantInformation();
        List<ApplicantInfoVo> applicants = entryInfoRepository.findApplicationInfoListByStatusIsSubmittedTrue();

        int DH = 0;
        for (ApplicantInfoVo applicantInfo : applicants) {
            applicantInformation.format(DH);

            insertInfo(
                    DH,
                    applicantInfo.getEntryInfo(),
                    applicantInfo.getGraduation(),
                    applicantInfo.getGraduationCase(),
                    applicantInfo.getStatus(),
                    applicantInfo.getScore());
            DH = DH + 20;
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String formatFilename = "attachment;filename=\"1차 검증";
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"));
            String fileName = new String((formatFilename + time + ".xlsx\"").getBytes("KSC5601"), "8859_1");
            response.setHeader("Content-Disposition", fileName);

            applicantInformation.getWorkbook().write(response.getOutputStream());
        } catch (IOException e) {
            throw ExcelOException.EXCEPTION;
        }
    }

    private void insertInfo(
            int DH,
            EntryInfo entryInfo,
            Graduation graduation,
            GraduationCase graduationCase,
            Status status,
            Score score) {
        // 기본정보
        applicantInformation.getCell(DH + 1, 2).setCellValue(entryInfo.getReceiptCode());
        applicantInformation.getCell(DH + 1, 3).setCellValue(graduation != null ? graduation.getSchoolName() : "-");
        applicantInformation.getCell(DH + 1, 6).setCellValue(getEducationalStatus(entryInfo.getEducationalStatus()));
        applicantInformation
                .getCell(DH + 1, 7)
                .setCellValue(String.valueOf(
                        graduation != null ? graduation.getGraduatedAt().getYear() : "-"));

        applicantInformation.getCell(DH + 3, 1).setCellValue(getApplicationType(entryInfo.getApplicationType()));
        applicantInformation.getCell(DH + 3, 2).setCellValue(entryInfo.getUserName());
        applicantInformation.getCell(DH + 3, 6).setCellValue(graduation != null ? graduation.getStudentNumber() : "-");

        applicantInformation
                .getCell(DH + 4, 1)
                .setCellValue(Boolean.TRUE.equals(entryInfo.getIsDaejeon()) ? "대전" : "전국");
        applicantInformation
                .getCell(DH + 4, 2)
                .setCellValue(entryInfo.getBirthday().toString());
        applicantInformation.getCell(DH + 4, 6).setCellValue(entryInfo.getUserTelephoneNumber());

        applicantInformation.getCell(DH + 5, 1).setCellValue(getApplicationRemark(entryInfo.getApplicationRemark()));
        applicantInformation.getCell(DH + 5, 2).setCellValue(Sex.MALE.equals(entryInfo.getSex()) ? "남자" : "여자");
        applicantInformation.getCell(DH + 5, 6).setCellValue(entryInfo.getParentTel());

        insertScore(DH, graduationCase, score);
    }

    private void insertScore(int DH, GraduationCase graduationCase, Score score) {
        String[] koreanScore = getSplitScores(graduationCase != null ? graduationCase.getKoreanGrade() : null);
        String[] socialScore = getSplitScores(graduationCase != null ? graduationCase.getSocialGrade() : null);
        String[] historyScore = getSplitScores(graduationCase != null ? graduationCase.getHistoryGrade() : null);
        String[] mathScore = getSplitScores(graduationCase != null ? graduationCase.getMathGrade() : null);
        String[] scienceScore = getSplitScores(graduationCase != null ? graduationCase.getScienceGrade() : null);
        String[] techAndHomeScore =
                getSplitScores(graduationCase != null ? graduationCase.getTechAndHomeGrade() : null);
        String[] englishScore = getSplitScores(graduationCase != null ? graduationCase.getEnglishGrade() : null);

        // 과목별 성적
        applicantInformation.getCell(DH + 8, 2).setCellValue(koreanScore[0]);
        applicantInformation.getCell(DH + 8, 3).setCellValue(koreanScore[1]);
        applicantInformation.getCell(DH + 8, 4).setCellValue(koreanScore[2]);
        applicantInformation.getCell(DH + 8, 5).setCellValue(koreanScore[3]);

        applicantInformation.getCell(DH + 9, 2).setCellValue(socialScore[0]);
        applicantInformation.getCell(DH + 9, 3).setCellValue(socialScore[1]);
        applicantInformation.getCell(DH + 9, 4).setCellValue(socialScore[2]);
        applicantInformation.getCell(DH + 9, 5).setCellValue(socialScore[3]);

        applicantInformation.getCell(DH + 10, 2).setCellValue(historyScore[0]);
        applicantInformation.getCell(DH + 10, 3).setCellValue(historyScore[1]);
        applicantInformation.getCell(DH + 10, 4).setCellValue(historyScore[2]);
        applicantInformation.getCell(DH + 10, 5).setCellValue(historyScore[3]);

        applicantInformation.getCell(DH + 11, 2).setCellValue(mathScore[0]);
        applicantInformation.getCell(DH + 11, 3).setCellValue(mathScore[1]);
        applicantInformation.getCell(DH + 11, 4).setCellValue(mathScore[2]);
        applicantInformation.getCell(DH + 11, 5).setCellValue(mathScore[3]);

        applicantInformation.getCell(DH + 12, 2).setCellValue(scienceScore[0]);
        applicantInformation.getCell(DH + 12, 3).setCellValue(scienceScore[1]);
        applicantInformation.getCell(DH + 12, 4).setCellValue(scienceScore[2]);
        applicantInformation.getCell(DH + 12, 5).setCellValue(scienceScore[3]);

        applicantInformation.getCell(DH + 13, 2).setCellValue(techAndHomeScore[0]);
        applicantInformation.getCell(DH + 13, 3).setCellValue(techAndHomeScore[1]);
        applicantInformation.getCell(DH + 13, 4).setCellValue(techAndHomeScore[2]);
        applicantInformation.getCell(DH + 13, 5).setCellValue(techAndHomeScore[3]);

        applicantInformation.getCell(DH + 14, 2).setCellValue(englishScore[0]);
        applicantInformation.getCell(DH + 14, 3).setCellValue(englishScore[1]);
        applicantInformation.getCell(DH + 14, 4).setCellValue(englishScore[2]);
        applicantInformation.getCell(DH + 14, 5).setCellValue(englishScore[3]);

        // 점수
        applicantInformation
                .getCell(DH + 15, 2)
                .setCellValue(score.getThirdGradeScore().doubleValue());
        applicantInformation
                .getCell(DH + 15, 4)
                .setCellValue(score.getThirdBeforeScore().doubleValue());
        applicantInformation
                .getCell(DH + 15, 5)
                .setCellValue(score.getThirdBeforeBeforeScore().doubleValue());
        applicantInformation
                .getCell(DH + 15, 7)
                .setCellValue(score.getTotalGradeScore().doubleValue());

        applicantInformation
                .getCell(DH + 18, 1)
                .setCellValue(
                        graduationCase != null
                                ? graduationCase.getVolunteerTime().toString()
                                : "-");
        applicantInformation
                .getCell(DH + 18, 2)
                .setCellValue(score.getVolunteerScore().doubleValue());
        applicantInformation
                .getCell(DH + 18, 3)
                .setCellValue(graduationCase != null ? graduationCase.getDayAbsenceCount() : 0);
        applicantInformation
                .getCell(DH + 18, 4)
                .setCellValue(graduationCase != null ? graduationCase.getLatenessCount() : 0);
        applicantInformation
                .getCell(DH + 18, 5)
                .setCellValue(graduationCase != null ? graduationCase.getLectureAbsenceCount() : 0);
        applicantInformation
                .getCell(DH + 18, 6)
                .setCellValue(graduationCase != null ? graduationCase.getEarlyLeaveCount() : 0);
        applicantInformation.getCell(DH + 18, 7).setCellValue(score.getAttendanceScore());
        applicantInformation
                .getCell(DH + 19, 7)
                .setCellValue(score.getTotalScore().doubleValue());
    }

    private String getEducationalStatus(EducationalStatus educationalStatus) {
        if (EducationalStatus.PROSPECTIVE_GRADUATE.equals(educationalStatus)) {
            return "졸업예정자";
        } else if (EducationalStatus.GRADUATE.equals(educationalStatus)) {
            return "졸업자";
        } else {
            return "검정고시";
        }
    }

    private String getApplicationType(ApplicationType applicationType) {
        if (ApplicationType.COMMON.equals(applicationType)) {
            return "일반전형";
        } else if (ApplicationType.MEISTER.equals(applicationType)) {
            return "마이스터인재전형";
        } else {
            return "사회통합전형";
        }
    }

    private String getApplicationRemark(ApplicationRemark applicationRemark) {
        if (applicationRemark == null) {
            return "일반";
        } else if (ApplicationRemark.ONE_PARENT.equals(applicationRemark)) {
            return "한부모가족";
        } else if (ApplicationRemark.FROM_NORTH.equals(applicationRemark)) {
            return "북한이탈주민";
        } else if (ApplicationRemark.MULTICULTURAL.equals(applicationRemark)) {
            return "다문화가정";
        } else if (ApplicationRemark.BASIC_LIVING.equals(applicationRemark)) {
            return "기초생활수급자";
        } else if (ApplicationRemark.LOWEST_INCOME.equals(applicationRemark)) {
            return "차상위계층";
        } else if (ApplicationRemark.TEEN_HOUSEHOLDER.equals(applicationRemark)) {
            return "소년소녀가장";
        } else if (ApplicationRemark.PRIVILEGED_ADMISSION.equals(applicationRemark)) {
            return "특례입학대상자";
        } else if (ApplicationRemark.NATIONAL_MERIT.equals(applicationRemark)) {
            return "국가유공자";
        } else if (ApplicationRemark.PROTECTED_CHILDREN.equals(applicationRemark)) {
            return "보호대상아동";
        } else {
            return "";
        }
    }

    private String[] getSplitScores(String scores) {
        if (scores == null) {
            return Stream.of("-", "-", "-", "-").toArray(String[]::new);
        } else {
            return scores.split("");
        }
    }
}
