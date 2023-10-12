package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ExcelOException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.ApplicantInformation;
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
public class ApplicationInformationExcelService {
    private final EntryInfoRepository entryInfoRepository;

    public void execute(HttpServletResponse response) {
        ApplicantInformation applicantInformation = new ApplicantInformation();
        Sheet sheet = applicantInformation.getSheet();
        applicantInformation.format();
        List<ApplicantInfoVo> applicants = entryInfoRepository.findApplicationInfoListByStatusIsSubmittedTrue();

        int i = 0;
        for (ApplicantInfoVo applicantInfo : applicants) {
            Row row = sheet.createRow(++i);
            insertUserInfo(row, applicantInfo.getEntryInfo(), applicantInfo.getGraduation(), applicantInfo.getStatus());
            insertRating(row, applicantInfo.getGraduationCase());
            insertScore(row, applicantInfo.getScore());
            insertSelfIntroduceAndStudyPlan(row, applicantInfo.getEntryInfo());
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String formatFilename = "attachment;filename=\"지원자 목록";
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"));
            String fileName = new String((formatFilename + time + ".xlsx\"").getBytes("KSC5601"), "8859_1");
            response.setHeader("Content-Disposition", fileName);

            applicantInformation.getWorkbook().write(response.getOutputStream());
        } catch (IOException e) {
            throw ExcelOException.EXCEPTION;
        }
    }

    private void insertUserInfo(Row row, EntryInfo entryInfo, Graduation graduation, Status status) {
        row.createCell(0).setCellValue(status.getExamCode());
        row.createCell(1).setCellValue(entryInfo.getReceiptCode());
        row.createCell(2).setCellValue(getApplicationType(entryInfo.getApplicationType()));

        row.createCell(3).setCellValue(Boolean.TRUE.equals(entryInfo.getIsDaejeon()) ? "대전" : "전국");
        row.createCell(4).setCellValue(getApplicationRemark(entryInfo.getApplicationRemark()));
        row.createCell(5).setCellValue(entryInfo.getUserName());
        row.createCell(6).setCellValue(entryInfo.getBirthday().toString());
        row.createCell(7).setCellValue(entryInfo.getAddress());
        row.createCell(8).setCellValue(entryInfo.getUserTelephoneNumber());
        row.createCell(9).setCellValue(Sex.MALE.equals(entryInfo.getSex()) ? "남자" : "여자");
        row.createCell(10).setCellValue(getEducationalStatus(entryInfo.getEducationalStatus()));
        row.createCell(11)
                .setCellValue(String.valueOf(
                        graduation != null ? graduation.getGraduatedAt().getYear() : "-"));
        row.createCell(12).setCellValue(graduation != null ? graduation.getSchoolName() : "-");
        row.createCell(13).setCellValue(graduation != null ? graduation.getStudentNumber() : "-");
        row.createCell(14).setCellValue(entryInfo.getParentName());
        row.createCell(15).setCellValue(entryInfo.getParentTel());
    }

    private void insertRating(Row row, GraduationCase graduationCase) {
        String[] koreanScore = getSplitScores(graduationCase != null ? graduationCase.getKoreanGrade() : null);
        String[] socialScore = getSplitScores(graduationCase != null ? graduationCase.getSocialGrade() : null);
        String[] historyScore = getSplitScores(graduationCase != null ? graduationCase.getHistoryGrade() : null);
        String[] mathScore = getSplitScores(graduationCase != null ? graduationCase.getMathGrade() : null);
        String[] scienceScore = getSplitScores(graduationCase != null ? graduationCase.getScienceGrade() : null);
        String[] techAndHomeScore =
                getSplitScores(graduationCase != null ? graduationCase.getTechAndHomeGrade() : null);
        String[] englishScore = getSplitScores(graduationCase != null ? graduationCase.getEnglishGrade() : null);

        row.createCell(16).setCellValue(koreanScore[0]);
        row.createCell(17).setCellValue(socialScore[0]);
        row.createCell(18).setCellValue(historyScore[0]);
        row.createCell(19).setCellValue(mathScore[0]);
        row.createCell(20).setCellValue(scienceScore[0]);
        row.createCell(21).setCellValue(techAndHomeScore[0]);
        row.createCell(22).setCellValue(englishScore[0]);

        row.createCell(23).setCellValue(koreanScore[1]);
        row.createCell(24).setCellValue(socialScore[1]);
        row.createCell(25).setCellValue(historyScore[1]);
        row.createCell(26).setCellValue(mathScore[1]);
        row.createCell(27).setCellValue(scienceScore[1]);
        row.createCell(28).setCellValue(techAndHomeScore[1]);
        row.createCell(29).setCellValue(englishScore[1]);

        row.createCell(30).setCellValue(koreanScore[2]);
        row.createCell(31).setCellValue(socialScore[2]);
        row.createCell(32).setCellValue(historyScore[2]);
        row.createCell(33).setCellValue(mathScore[2]);
        row.createCell(34).setCellValue(scienceScore[2]);
        row.createCell(35).setCellValue(techAndHomeScore[2]);
        row.createCell(36).setCellValue(englishScore[2]);

        row.createCell(37).setCellValue(koreanScore[3]);
        row.createCell(38).setCellValue(socialScore[3]);
        row.createCell(39).setCellValue(historyScore[3]);
        row.createCell(40).setCellValue(mathScore[3]);
        row.createCell(41).setCellValue(scienceScore[3]);
        row.createCell(42).setCellValue(techAndHomeScore[3]);
        row.createCell(43).setCellValue(englishScore[3]);

        row.createCell(48)
                .setCellValue(
                        graduationCase != null
                                ? graduationCase.getVolunteerTime().toString()
                                : "-");

        row.createCell(50).setCellValue(graduationCase != null ? graduationCase.getDayAbsenceCount() : 0);
        row.createCell(51).setCellValue(graduationCase != null ? graduationCase.getLatenessCount() : 0);
        row.createCell(52).setCellValue(graduationCase != null ? graduationCase.getEarlyLeaveCount() : 0);
        row.createCell(53).setCellValue(graduationCase != null ? graduationCase.getLectureAbsenceCount() : 0);
    }

    private void insertScore(Row row, Score score) {
        row.createCell(44).setCellValue(score.getThirdGradeScore().doubleValue());
        row.createCell(45).setCellValue(score.getThirdBeforeScore().doubleValue());
        row.createCell(46).setCellValue(score.getThirdBeforeBeforeScore().doubleValue());
        row.createCell(47).setCellValue(score.getTotalGradeScore().doubleValue());

        row.createCell(49).setCellValue(score.getVolunteerScore().doubleValue());

        row.createCell(54).setCellValue(score.getAttendanceScore());
        row.createCell(55).setCellValue(score.getTotalScore().doubleValue());
    }

    private void insertSelfIntroduceAndStudyPlan(Row row, EntryInfo entryInfo) {
        row.createCell(56).setCellValue(entryInfo.getSelfIntroduce());
        row.createCell(57).setCellValue(entryInfo.getStudyPlan());
    }

    private String getApplicationType(ApplicationType applicationType) {
        if (ApplicationType.COMMON.equals(applicationType)) return "일반전형";
        else if (ApplicationType.MEISTER.equals(applicationType)) return "마이스터인재전형";
        else return "사회통합전형";
    }

    private String getApplicationRemark(ApplicationRemark applicationRemark) {
        if (applicationRemark == null) return "일반";
        else if (ApplicationRemark.ONE_PARENT.equals(applicationRemark)) return "한부모가족";
        else if (ApplicationRemark.FROM_NORTH.equals(applicationRemark)) return "북한이탈주민";
        else if (ApplicationRemark.MULTICULTURAL.equals(applicationRemark)) return "다문화가정";
        else if (ApplicationRemark.BASIC_LIVING.equals(applicationRemark)) return "기초생활수급자";
        else if (ApplicationRemark.LOWEST_INCOME.equals(applicationRemark)) return "차상위계층";
        else if (ApplicationRemark.TEEN_HOUSEHOLDER.equals(applicationRemark)) return "소년소녀가장";
        else if (ApplicationRemark.PRIVILEGED_ADMISSION.equals(applicationRemark)) return "특례입학대상자";
        else if (ApplicationRemark.NATIONAL_MERIT.equals(applicationRemark)) return "국가유공자";
        else if (ApplicationRemark.PROTECTED_CHILDREN.equals(applicationRemark)) return "보호대상아동";
        else return "";
    }

    private String getEducationalStatus(EducationalStatus educationalStatus) {
        if (EducationalStatus.PROSPECTIVE_GRADUATE.equals(educationalStatus)) return "졸업예정자";
        else if (EducationalStatus.GRADUATE.equals(educationalStatus)) return "졸업자";
        else return "검정고시";
    }

    private String[] getSplitScores(String scores) {
        if (scores == null) {
            return Stream.of("-", "-", "-", "-").toArray(String[]::new);
        } else {
            return scores.split("");
        }
    }
}
