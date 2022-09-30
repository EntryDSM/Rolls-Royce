package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

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
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.User;
import kr.hs.entrydsm.rollsroyce.domain.user.exception.ApplicationNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PdfDataConverter {

    private final GraduationRepository graduationRepository;
    private final QualificationRepository qualificationRepository;
    private final S3Util s3Util;

    public PdfData applicationToInfo(User user, Score score) {
        Map<String, Object> values = new HashMap<>();
        setReceiptCode(user, values);
        setEntranceYear(values);
        setPersonalInfo(user, values);
        setGenderInfo(user, values);
        setSchoolInfo(user, values);
        setPhoneNumber(user, values);
        setGraduationClassification(user, values);
        setUserType(user, values);
        setGradeScore(user, score, values);
        setLocalDate(values);
        setIntroduction(user, values);
        setParentInfo(user, values);

        if (user.isRecommendationsRequired()) {
            setRecommendations(user, values);
        }

        if (user.getPhotoFileName() != null && !user.getPhotoFileName().isBlank()) {
            setBase64Image(user, values);
        }

        return new PdfData(values);
    }

    private void setReceiptCode(User user, Map<String, Object> values) {
        values.put("receiptCode", user.getReceiptCode());
    }

    private void setEntranceYear(Map<String, Object> values) {
        int entranceYear = LocalDate.now().plusYears(1).getYear();
        values.put("entranceYear", String.valueOf(entranceYear));
    }

    private void setPersonalInfo(User user, Map<String, Object> values) {
        values.put("userName", setBlankIfNull(user.getName()));
        values.put("isMale", toBallotBox(user.isMale()));
        values.put("isFemale", toBallotBox(user.isFemale()));
        values.put("address", setBlankIfNull(user.getAddress()));
        values.put("detailAddress", setBlankIfNull(user.getDetailAddress()));

        String birthday = "";
        if (user.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            birthday = user.getBirthday().format(formatter);
        }
        values.put("birthday", birthday);
    }

    private void setGenderInfo(User user, Map<String, Object> values) {
        String gender = null;
        if (user.isFemale()) {
            gender = "여";
        } else if (user.isMale()) {
            gender = "남";
        }
        values.put("gender", setBlankIfNull(gender));
    }

    private void setSchoolInfo(User user, Map<String, Object> values) {
        if (!user.isEducationalStatusEmpty() && !user.isQualification()) {
            Graduation application = graduationRepository.findById(user.getReceiptCode())
                    .orElseThrow(() -> ApplicationNotFoundException.EXCEPTION);
            values.put("schoolCode", setBlankIfNull(application.getSchoolCode()));
            values.put("schoolClass", setBlankIfNull(application.getSchoolClass()));
            values.put("schoolTel", toFormattedPhoneNumber(application.getSchoolTel()));
            values.put("schoolName", setBlankIfNull(application.getSchoolName()));
        } else {
            values.putAll(emptySchoolInfo());
        }
    }

    private void setPhoneNumber(User user, Map<String, Object> values) {
        values.put("applicantTel", toFormattedPhoneNumber(user.getTelephoneNumber()));
        values.put("parentTel", toFormattedPhoneNumber(user.getParentTel()));
    }

    private void setGraduationClassification(User user, Map<String, Object> values) {
        values.putAll(emptyGraduationClassification());

        switch (user.getEducationalStatus().name()) {
            case EducationalStatus.QUALIFICATION_EXAM:
                qualificationRepository.findById(user.getReceiptCode())
                        .filter(qualificationExam -> qualificationExam.getQualifiedAt() != null)
                        .ifPresent(qualificationExam -> {
                            values.put("qualificationExamPassedYear", String.valueOf(qualificationExam.getQualifiedAt().getYear()));
                            values.put("qualificationExamPassedMonth", String.valueOf(qualificationExam.getQualifiedAt().getMonthValue()));
                        });
                break;
            case EducationalStatus.GRADUATE:
                graduationRepository.findById(user.getReceiptCode())
                        .filter(graduation -> graduation.getGraduatedAt() != null)
                        .ifPresent(graduation -> {
                            values.put("graduateYear", String.valueOf(graduation.getGraduatedAt().getYear()));
                            values.put("graduateMonth", String.valueOf(graduation.getGraduatedAt().getMonthValue()));
                        });
                break;
            case EducationalStatus.PROSPECTIVE_GRADUATE:
                graduationRepository.findById(user.getReceiptCode())
                        .filter(graduation -> graduation.getGraduatedAt() != null)
                        .ifPresent(graduation -> values.put("prospectiveGraduateMonth", String.valueOf(graduation.getGraduatedAt().getMonthValue())));
                break;
            default:
                break;
        }
    }

    private void setUserType(User user, Map<String, Object> values) {
        values.put("isQualificationExam", toBallotBox(user.isQualificationExam()));
        values.put("isGraduate", toBallotBox(user.isGraduate()));
        values.put("isProspectiveGraduate", toBallotBox(user.isProspectiveGraduate()));
        values.put("isDaejeon", toBallotBox(user.getIsDaejeon()));
        values.put("isNotDaejeon", toBallotBox(!user.getIsDaejeon()));
        values.put("isBasicLiving", toBallotBox(user.isBasicLiving()));
        values.put("isFromNorth", toBallotBox(user.isFromNorth()));
        values.put("isLowestIncome", toBallotBox(user.isLowestIncome()));
        values.put("isMulticultural", toBallotBox(user.isMulticultural()));
        values.put("isOneParent", toBallotBox(user.isOneParent()));
        values.put("isTeenHouseholder", toBallotBox(user.isTeenHouseholder()));
        values.put("isPrivilegedAdmission", toBallotBox(user.isPrivilegedAdmission()));
        values.put("isNationalMerit", toBallotBox(user.isNationalMerit()));
        values.put("isProtectedChildren", toBallotBox(user.isProtectedChildren()));
        values.put("isCommon", toBallotBox(user.isCommonApplicationType()));
        values.put("isMeister", toBallotBox(user.isMeisterApplicationType()));
        values.put("isSocialMerit", toBallotBox(user.isSocialApplicationType()));
    }

    private void setGradeScore(User user, Score score, Map<String, Object> values) {
        values.put("conversionScore1st", user.isQualificationExam() ? "" : score.getThirdBeforeBeforeScore().toString());
        values.put("conversionScore2nd", user.isQualificationExam() ? "" : score.getThirdBeforeScore().toString());
        values.put("conversionScore3rd", user.isQualificationExam() ? "" : score.getThirdGradeScore().toString());
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

    private void setIntroduction(User user, Map<String, Object> values) {
        values.put("selfIntroduction", setBlankIfNull(user.getSelfIntroduce()));
        values.put("studyPlan", setBlankIfNull(user.getStudyPlan()));
        values.put("newLineChar", "\n");
    }

    private void setParentInfo(User user, Map<String, Object> values) {
        values.put("parentName", user.getParentName());
    }

    private void setRecommendations(User user, Map<String, Object> values) {
        values.put("isDaejeonAndMeister", markIfTrue(user.getIsDaejeon() && user.isMeisterApplicationType()));
        values.put("isDaejeonAndSocialMerit", markIfTrue(user.getIsDaejeon() && user.isSocialApplicationType()));
        values.put("isNotDaejeonAndMeister", markIfTrue(!user.getIsDaejeon() && user.isMeisterApplicationType()));
        values.put("isNotDaejeonAndSocialMerit", markIfTrue(!user.getIsDaejeon() && user.isSocialApplicationType()));
    }

    private void setBase64Image(User user, Map<String, Object> values) {
        byte[] imageBytes = s3Util.getObject("images/" + user.getPhotoFileName());
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
                "schoolName", ""
        );
    }

    private Map<String, Object> emptyGraduationClassification() {
        return Map.of(
                "qualificationExamPassedYear", "20__",
                "qualificationExamPassedMonth", "__",
                "graduateYear", "20__",
                "graduateMonth", "__",
                "prospectiveGraduateMonth", "__"
        );
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
