package kr.hs.entrydsm.rollsroyce.domain.application.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_qualification_exam_application")
public class Qualification extends Application {

    private LocalDate qualifiedAt;

    public Qualification(EntryInfo entryInfo, LocalDate qualifiedAt) {
        super(entryInfo);
        this.qualifiedAt = qualifiedAt;
    }

    public void updateQualifiedAt(LocalDate qualifiedAt) {
        this.qualifiedAt = qualifiedAt;
    }

    @Builder
    public Qualification(LocalDate qualifiedAt) {
        this.qualifiedAt = qualifiedAt;
    }

    @Override
    public boolean isGraduation() {
        return false;
    }

    @Override
    public String getDate() {
        return qualifiedAt == null
                ? null
                : DateTimeFormatter.ofPattern("yyyyMM")
                        .withZone(ZoneId.of("Asia/Seoul"))
                        .format(qualifiedAt);
    }

    @Override
    public boolean hasEmptyInfo() {
        return qualifiedAt == null;
    }

    @Override
    public String getSchoolName() {
        return null;
    }
}
