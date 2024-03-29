package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_graduation_application")
public class Graduation extends Application {

    private Boolean isGraduated;

    @Column(length = 5)
    private String studentNumber;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "school_code")
    private School school;

    @Column(length = 11)
    private String schoolTel;

    private LocalDate graduatedAt;

    public Graduation(EntryInfo entryInfo, LocalDate graduatedAt, EducationalStatus educationalStatus) {
        super(entryInfo);
        this.graduatedAt = graduatedAt;
        this.isGraduated = EducationalStatus.GRADUATE.equals(educationalStatus);
    }

    public void updateInformation(LocalDate graduatedAt, EducationalStatus educationalStatus) {
        this.graduatedAt = graduatedAt;
        this.isGraduated = EducationalStatus.GRADUATE.equals(educationalStatus);
    }

    @Builder
    public Graduation(
            boolean isGraduated, String studentNumber, School school, String schoolTel, LocalDate graduatedAt) {
        this.isGraduated = isGraduated;
        this.studentNumber = studentNumber;
        this.schoolTel = schoolTel;
        this.school = school;
        this.graduatedAt = graduatedAt;
    }

    public void changeGraduationInformation(School school, String studentNumber, String schoolTel) {
        this.school = school;
        this.studentNumber = studentNumber;
        this.schoolTel = schoolTel;
    }

    @Override
    public boolean isGraduation() {
        return true;
    }

    @Override
    public String getDate() {
        return graduatedAt == null
                ? null
                : DateTimeFormatter.ofPattern("yyyyMM")
                        .withZone(ZoneId.of("Asia/Seoul"))
                        .format(graduatedAt);
    }

    @Override
    public boolean hasEmptyInfo() {
        return !(isExists(studentNumber) && isGraduated != null && school != null && graduatedAt != null);
    }

    @Override
    public String getSchoolName() {
        if (school == null) {
            return null;
        }
        return this.school.getName();
    }

    public String getSchoolCode() {
        return school == null ? null : school.getCode();
    }

    public String getSchoolClass() {
        String schoolClass = null;
        if (studentNumber != null && !studentNumber.isBlank()) {
            int classNumber = Integer.parseInt(studentNumber.substring(1, 3));
            schoolClass = Integer.toString(classNumber);
        }
        return schoolClass;
    }
}
