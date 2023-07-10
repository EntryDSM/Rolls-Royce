package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import kr.hs.entrydsm.rollsroyce.domain.application.domain.Graduation;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.GraduationRepository;
import kr.hs.entrydsm.rollsroyce.domain.application.domain.repository.QualificationRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Component
public class PdfDataConverter {

    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;
    private final S3Util s3Util;

    public PdfData applicationToInfo(EntryInfo entryInfo, Score score) {
        Map<String, Object> values = new HashMap<>();
        setReceiptCode(entryInfo, values);
        setEntranceYear(values);
        setPersonalInfo(entryInfo, values);
        setGenderInfo(entryInfo, values);
        setSchoolInfo(entryInfo, values);
        setPhoneNumber(entryInfo, values);
        setGraduationClassification(entryInfo, values);
        setUserType(entryInfo, values);
        setGradeScore(entryInfo, score, values);
        setLocalDate(values);
        setIntroduction(entryInfo, values);
        setParentInfo(entryInfo, values);

        if (entryInfo.isRecommendationsRequired()) {
            setRecommendations(entryInfo, values);
        }

        if (entryInfo.getPhotoFileName() != null
                && !entryInfo.getPhotoFileName().isBlank()) {
            setBase64Image(entryInfo, values);
        }

        return new PdfData(values);
    }

    private void setReceiptCode(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("receiptCode", entryInfo.getReceiptCode());
    }

    private void setEntranceYear(Map<String, Object> values) {
        int entranceYear = LocalDate.now().plusYears(1).getYear();
        values.put("entranceYear", String.valueOf(entranceYear));
    }

    private void setPersonalInfo(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("userName", setBlankIfNull(entryInfo.getUserName()));
        values.put("isMale", toBallotBox(entryInfo.isMale()));
        values.put("isFemale", toBallotBox(entryInfo.isFemale()));
        values.put("address", setBlankIfNull(entryInfo.getAddress()));
        values.put("detailAddress", setBlankIfNull(entryInfo.getDetailAddress()));

        String birthday = "";
        if (entryInfo.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            birthday = entryInfo.getBirthday().format(formatter);
        }
        values.put("birthday", birthday);
    }

    private void setGenderInfo(EntryInfo entryInfo, Map<String, Object> values) {
        String gender = null;
        if (entryInfo.isFemale()) {
            gender = "여";
        } else if (entryInfo.isMale()) {
            gender = "남";
        }
        values.put("gender", setBlankIfNull(gender));
    }

    private void setSchoolInfo(EntryInfo entryInfo, Map<String, Object> values) {
        if (!entryInfo.isEducationalStatusEmpty() && !entryInfo.isQualification()) {
            Graduation application = graduationRepository
                    .findById(entryInfo.getReceiptCode())
                    .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
            values.put("schoolCode", setBlankIfNull(application.getSchoolCode()));
            values.put("schoolClass", setBlankIfNull(application.getSchoolClass()));
            values.put("schoolTel", toFormattedPhoneNumber(application.getSchoolTel()));
            values.put("schoolName", setBlankIfNull(application.getSchoolName()));
        } else {
            values.putAll(emptySchoolInfo());
        }
    }

    private void setPhoneNumber(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("applicantTel", toFormattedPhoneNumber(entryInfo.getUserTelephoneNumber()));
        values.put("parentTel", toFormattedPhoneNumber(entryInfo.getParentTel()));
    }

    private void setGraduationClassification(EntryInfo entryInfo, Map<String, Object> values) {
        values.putAll(emptyGraduationClassification());

        switch (entryInfo.getEducationalStatus().name()) {
            case EducationalStatus.QUALIFICATION_EXAM:
                qualificationRepository
                        .findById(entryInfo.getReceiptCode())
                        .filter(qualificationExam -> qualificationExam.getQualifiedAt() != null)
                        .ifPresent(qualificationExam -> {
                            values.put(
                                    "qualificationExamPassedYear",
                                    String.valueOf(
                                            qualificationExam.getQualifiedAt().getYear()));
                            values.put(
                                    "qualificationExamPassedMonth",
                                    String.valueOf(
                                            qualificationExam.getQualifiedAt().getMonthValue()));
                        });
                break;
            case EducationalStatus.GRADUATE:
                graduationRepository
                        .findById(entryInfo.getReceiptCode())
                        .filter(graduation -> graduation.getGraduatedAt() != null)
                        .ifPresent(graduation -> {
                            values.put(
                                    "graduateYear",
                                    String.valueOf(graduation.getGraduatedAt().getYear()));
                            values.put(
                                    "graduateMonth",
                                    String.valueOf(graduation.getGraduatedAt().getMonthValue()));
                        });
                break;
            case EducationalStatus.PROSPECTIVE_GRADUATE:
                graduationRepository
                        .findById(entryInfo.getReceiptCode())
                        .filter(graduation -> graduation.getGraduatedAt() != null)
                        .ifPresent(graduation -> {
                            values.put(
                                    "prospectiveGraduateYear",
                                    String.valueOf(graduation.getGraduatedAt().getYear()));
                            values.put(
                                    "prospectiveGraduateMonth",
                                    String.valueOf(graduation.getGraduatedAt().getMonthValue()));
                        });
                break;
            default:
                break;
        }
    }

    private void setUserType(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("isQualificationExam", toBallotBox(entryInfo.isQualificationExam()));
        values.put("isGraduate", toBallotBox(entryInfo.isGraduate()));
        values.put("isProspectiveGraduate", toBallotBox(entryInfo.isProspectiveGraduate()));
        values.put("isDaejeon", toBallotBox(entryInfo.getIsDaejeon()));
        values.put("isNotDaejeon", toBallotBox(!entryInfo.getIsDaejeon()));
        values.put("isBasicLiving", toBallotBox(entryInfo.isBasicLiving()));
        values.put("isFromNorth", toBallotBox(entryInfo.isFromNorth()));
        values.put("isLowestIncome", toBallotBox(entryInfo.isLowestIncome()));
        values.put("isMulticultural", toBallotBox(entryInfo.isMulticultural()));
        values.put("isOneParent", toBallotBox(entryInfo.isOneParent()));
        values.put("isTeenHouseholder", toBallotBox(entryInfo.isTeenHouseholder()));
        values.put("isPrivilegedAdmission", toBallotBox(entryInfo.isPrivilegedAdmission()));
        values.put("isNationalMerit", toBallotBox(entryInfo.isNationalMerit()));
        values.put("isProtectedChildren", toBallotBox(entryInfo.isProtectedChildren()));
        values.put("isCommon", toBallotBox(entryInfo.isCommonApplicationType()));
        values.put("isMeister", toBallotBox(entryInfo.isMeisterApplicationType()));
        values.put("isSocialMerit", toBallotBox(entryInfo.isSocialApplicationType()));
    }

    private void setGradeScore(EntryInfo entryInfo, Score score, Map<String, Object> values) {
        values.put(
                "conversionScore1st",
                entryInfo.isQualificationExam()
                        ? ""
                        : score.getThirdBeforeBeforeScore().toString());
        values.put(
                "conversionScore2nd",
                entryInfo.isQualificationExam()
                        ? ""
                        : score.getThirdBeforeScore().toString());
        values.put(
                "conversionScore3rd",
                entryInfo.isQualificationExam()
                        ? ""
                        : score.getThirdGradeScore().toString());
        values.put("conversionScore", score.getTotalGradeScore().toString());
        values.put("attendanceScore", String.valueOf(score.getAttendanceScore()));
        values.put("volunteerScore", score.getVolunteerScore().toString());
        values.put("finalScore", score.getTotalScore().toString());
    }

    private void setLocalDate(Map<String, Object> values) {
        LocalDateTime now = LocalDateTime.now();
        values.put("year", String.valueOf(now.getYear()));
        values.put("month", String.valueOf(now.getMonthValue()));
        values.put("day", String.valueOf(now.getDayOfMonth()));
    }

    private void setIntroduction(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("selfIntroduction", setBlankIfNull(entryInfo.getSelfIntroduce()));
        values.put("studyPlan", setBlankIfNull(entryInfo.getStudyPlan()));
        values.put("newLineChar", "\n");
    }

    private void setParentInfo(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("parentName", entryInfo.getParentName());
    }

    private void setRecommendations(EntryInfo entryInfo, Map<String, Object> values) {
        values.put("isDaejeonAndMeister", markIfTrue(entryInfo.getIsDaejeon() && entryInfo.isMeisterApplicationType()));
        values.put(
                "isDaejeonAndSocialMerit", markIfTrue(entryInfo.getIsDaejeon() && entryInfo.isSocialApplicationType()));
        values.put(
                "isNotDaejeonAndMeister",
                markIfTrue(!entryInfo.getIsDaejeon() && entryInfo.isMeisterApplicationType()));
        values.put(
                "isNotDaejeonAndSocialMerit",
                markIfTrue(!entryInfo.getIsDaejeon() && entryInfo.isSocialApplicationType()));
    }

    private void setBase64Image(EntryInfo entryInfo, Map<String, Object> values) {
        byte[] imageBytes = s3Util.getObject(entryInfo.getPhotoFileName());
        String base64EncodedImage = new String(Base64.getEncoder().encode(imageBytes), StandardCharsets.UTF_8);
        values.put("base64Image", base64EncodedImage);
    }

    private String markIfTrue(boolean isTrue) {
        return isTrue ? "◯" : "";
    }

    private Map<String, Object> emptySchoolInfo() {
        return Map.of(
                "schoolCode", "",
                "schoolClass", "",
                "schoolTel", "",
                "schoolName", "");
    }

    private Map<String, Object> emptyGraduationClassification() {
        return Map.of(
                "qualificationExamPassedYear", "20__",
                "qualificationExamPassedMonth", "__",
                "graduateYear", "20__",
                "graduateMonth", "__",
                "prospectiveGraduateMonth", "__");
    }

    private String toFormattedPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "";
        }

        if (phoneNumber.length() == 8) {
            return phoneNumber.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
        }

        return phoneNumber.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
    }

    private String setBlankIfNull(String input) {
        return input == null ? "" : input;
    }

    private String toBallotBox(boolean is) {
        return is ? "☑" : "☐";
    }
}
