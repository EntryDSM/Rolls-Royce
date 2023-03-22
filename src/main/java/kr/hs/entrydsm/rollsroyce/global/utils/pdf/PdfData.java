package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class PdfData {

    private final Map<String, Object> data;

    public Map<String, Object> toMap() {
        return data;
    }

    public long getReceiptCode() {
        return (long) data.get("receiptCode");
    }

    public String getEntranceYear() {
        return (String) data.get("entranceYear");
    }

    public String getUserName() {
        return (String) data.get("userName");
    }

    public String getIsMale() {
        return (String) data.get("isMale");
    }

    public String getIsFemale() {
        return (String) data.get("isFemale");
    }

    public String getAddress() {
        return (String) data.get("address");
    }

    public String getBirthday() {
        return (String) data.get("birthday");
    }

    public String getGender() {
        return (String) data.get("gender");
    }

    public String getSchoolCode() {
        return (String) data.get("schoolCode");
    }

    public String getSchoolClass() {
        return (String) data.get("schoolClass");
    }

    public String getSchoolTel() {
        return (String) data.get("schoolTel");
    }

    public String getSchoolName() {
        return (String) data.get("schoolName");
    }

    public String getApplicantTel() {
        return (String) data.get("applicantTel");
    }

    public String getParentTel() {
        return (String) data.get("parentTel");
    }

    public String getQualificationExamPassedYear() {
        return (String) data.get("qualificationExamPassedYear");
    }

    public String getQualificationExamPassedMonth() {
        return (String) data.get("qualificationExamPassedMonth");
    }

    public String getGraduateYear() {
        return (String) data.get("graduateYear");
    }

    public String getGraduateMonth() {
        return (String) data.get("graduateMonth");
    }

    public String getProspectiveGraduateYear() {
        return (String) data.get("prospectiveGraduateYear");
    }

    public String getProspectiveGraduateMonth() {
        return (String) data.get("prospectiveGraduateMonth");
    }

    public String getIsQualificationExam() {
        return (String) data.get("isQualificationExam");
    }

    public String getIsGraduate() {
        return (String) data.get("isGraduate");
    }

    public String getIsProspectiveGraduate() {
        return (String) data.get("isProspectiveGraduate");
    }

    public String getIsDaejeon() {
        return (String) data.get("isDaejeon");
    }

    public String getIsNotDaejeon() {
        return (String) data.get("isNotDaejeon");
    }

    public String getIsBasicLiving() {
        return (String) data.get("isBasicLiving");
    }

    public String getIsFromNorth() {
        return (String) data.get("isFromNorth");
    }

    public String getIsLowestIncome() {
        return (String) data.get("isLowestIncome");
    }

    public String getIsMulticultural() {
        return (String) data.get("isMulticultural");
    }

    public String getIsOneParent() {
        return (String) data.get("isOneParent");
    }

    public String getIsTeenHouseholder() {
        return (String) data.get("isTeenHouseholder");
    }

    public String getIsPrivilegedAdmission() {
        return (String) data.get("isPrivilegedAdmission");
    }

    public String getIsNationalMerit() {
        return (String) data.get("isNationalMerit");
    }

    public String getIsCommon() {
        return (String) data.get("isCommon");
    }

    public String getIsMeister() {
        return (String) data.get("isMeister");
    }

    public String getIsSocialMerit() {
        return (String) data.get("isSocialMerit");
    }

    public String getConversionScore1st() {
        return (String) data.get("conversionScore1st");
    }

    public String getConversionScore2nd() {
        return (String) data.get("conversionScore2nd");
    }

    public String getConversionScore3rd() {
        return (String) data.get("conversionScore3rd");
    }

    public String getConversionScore() {
        return (String) data.get("conversionScore");
    }

    public String getAttendanceScore() {
        return (String) data.get("attendanceScore");
    }

    public String getVolunteerScore() {
        return (String) data.get("volunteerScore");
    }

    public String getFinalScore() {
        return (String) data.get("finalScore");
    }

    public String getYear() {
        return (String) data.get("year");
    }

    public String getMonth() {
        return (String) data.get("month");
    }

    public String getDay() {
        return (String) data.get("day");
    }

    public String getSelfIntroduction() {
        return (String) data.get("selfIntroduction");
    }

    public String getStudyPlan() {
        return (String) data.get("studyPlan");
    }

    public String getParentName() {
        return (String) data.get("parentName");
    }

    public String getIsDaejeonAndMeister() {
        return (String) data.get("isDaejeonAndMeister");
    }

    public String getIsDaejeonAndSocialMerit() {
        return (String) data.get("isDaejeonAndSocialMerit");
    }

    public String getIsNotDaejeonAndMeister() {
        return (String) data.get("isNotDaejeonAndMeister");
    }

    public String getIsNotDaejeonAndSocialMerit() {
        return (String) data.get("isNotDaejeonAndSocialMerit");
    }
}
