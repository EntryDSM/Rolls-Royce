package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class GetNewApplicantsRequest {
    @Nullable
    private final String receiptCode;

    @Nullable
    private final String schoolName;

    @Nullable
    private final EducationalStatus educationalStatus;

    @Nullable
    private final LocalDate graduatedAt;

    @Nullable
    private final ApplicationType applicationType;

    @Nullable
    private final String name;

    @Nullable
    private final String studentNumber;

    @Nullable
    private final Boolean isDaejeon;

    @Nullable
    private final LocalDate birthday;

    @Nullable
    private final String telephoneNumber;

    @Nullable
    private final ApplicationRemark applicationRemark;

    @Nullable
    private final Sex sex;

    @Nullable
    private final String parentTel;

    @Nullable
    private final String koreanGrade;

    @Nullable
    private final String socialGrade;

    @Nullable
    private final String historyGrade;

    @Nullable
    private final String mathGrade;

    @Nullable
    private final String scienceGrade;

    @Nullable
    private final String techAndHomeGrade;

    @Nullable
    private final String englishGrade;

    @Nullable
    private final Integer volunteerTime;

    @Nullable
    private final Integer latenessCount;

    @Nullable
    private final Integer dayAbsenceCount;

    @Nullable
    private final Integer earlyLeaveCount;

    @Nullable
    private final Integer lectureAbsenceCount;

    @Nullable
    private final BigDecimal thirdGradeScore;

    @Nullable
    private final BigDecimal thirdBeforeScore;

    @Nullable
    private final BigDecimal thirdBeforeBeforeScore;

    @Nullable
    private final BigDecimal volunteerScore;

    @Nullable
    private final BigDecimal totalGradeScore;

    @Nullable
    private final Integer attendanceScore;

    @Nullable
    private final BigDecimal totalScore;


    public GetNewApplicantsRequest(String receiptCode, String schoolName, EducationalStatus educationalStatus, LocalDate graduatedAt,
                                   ApplicationType applicationType, String name, String studentNumber, Boolean isDaejeon, LocalDate birthday,
                                   String telephoneNumber, ApplicationRemark applicationRemark, Sex sex, String parentTel, String koreanGrade,
                                   String socialGrade, String historyGrade, String mathGrade, String scienceGrade, String techAndHomeGrade,
                                   String englishGrade, Integer volunteerTime, Integer latenessCount, Integer dayAbsenceCount, Integer earlyLeaveCount,
                                   Integer lectureAbsenceCount, BigDecimal thirdGradeScore, BigDecimal thirdBeforeScore, BigDecimal thirdBeforeBeforeScore,
                                   BigDecimal volunteerScore, BigDecimal totalGradeScore, Integer attendanceScore, BigDecimal totalScore) {
        this.receiptCode = receiptCode;
        this.schoolName = schoolName;
        this.educationalStatus = educationalStatus;
        this.graduatedAt = graduatedAt;
        this.applicationType = applicationType;
        this.name = name;
        this.studentNumber = studentNumber;
        this.isDaejeon = isDaejeon;
        this.birthday = birthday;
        this.telephoneNumber = telephoneNumber;
        this.applicationRemark = applicationRemark;
        this.sex = sex;
        this.parentTel = parentTel;
        this.koreanGrade = koreanGrade;
        this.socialGrade = socialGrade;
        this.historyGrade = historyGrade;
        this.mathGrade = mathGrade;
        this.scienceGrade = scienceGrade;
        this.techAndHomeGrade = techAndHomeGrade;
        this.englishGrade = englishGrade;
        this.volunteerTime = volunteerTime;
        this.latenessCount = latenessCount;
        this.dayAbsenceCount = dayAbsenceCount;
        this.earlyLeaveCount = earlyLeaveCount;
        this.lectureAbsenceCount = lectureAbsenceCount;
        this.thirdGradeScore = thirdGradeScore;
        this.thirdBeforeScore = thirdBeforeScore;
        this.thirdBeforeBeforeScore = thirdBeforeBeforeScore;
        this.volunteerScore = volunteerScore;
        this.totalGradeScore = totalGradeScore;
        this.attendanceScore = attendanceScore;
        this.totalScore = totalScore;
    }
}
