package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entry_info.domain.types.EducationalStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_graduation_case")
public class GraduationCase extends ApplicationCase {

    @Transient
    private final BigDecimal GRADE_RATE = BigDecimal.valueOf(4);

    private Integer volunteerTime;

    private Integer dayAbsenceCount;

    private Integer lectureAbsenceCount;

    private Integer latenessCount;

    private Integer earlyLeaveCount;

    @Column(columnDefinition = "CHAR(4)")
    private String koreanGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String socialGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String historyGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String mathGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String scienceGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String englishGrade;

    @Column(columnDefinition = "CHAR(4)")
    private String techAndHomeGrade;

    public GraduationCase(UpdateGraduationRequest request,
                          long receiptCode,
                          boolean isDaejeon,
                          ApplicationType applicationType,
                          EducationalStatus educationalStatus) {
        super(receiptCode, isDaejeon, applicationType, educationalStatus);
        this.volunteerTime = request.getVolunteerTime();
        this.dayAbsenceCount = request.getDayAbsenceCount();
        this.lectureAbsenceCount = request.getLectureAbsenceCount();
        this.latenessCount = request.getLatenessCount();
        this.earlyLeaveCount = request.getEarlyLeaveCount();
        this.koreanGrade = request.getKoreanGrade();
        this.socialGrade = request.getSocialGrade();
        this.historyGrade = request.getHistoryGrade();
        this.mathGrade = request.getMathGrade();
        this.scienceGrade = request.getScienceGrade();
        this.englishGrade = request.getEnglishGrade();
        this.techAndHomeGrade = request.getTechAndHomeGrade();
    }

    @Override
    public BigDecimal calculateVolunteerScore() {
        if (volunteerTime >= MAX_VOLUNTEER_TIME) {
            return BigDecimal.valueOf(MAX_VOLUNTEER_SCORE);
        } else if (MIN_VOLUNTEER_TIME <= volunteerTime) {
            return BigDecimal.valueOf(volunteerTime)
                .add(BigDecimal.valueOf(3));
        } else {
            return BigDecimal.valueOf(MIN_VOLUNTEER_SCORE);
        }
    }

    @Override
    public Integer calculateAttendanceScore() {
        return Math.max((MAX_ATTENDANCE_SCORE
                - dayAbsenceCount
                - (lectureAbsenceCount + latenessCount + earlyLeaveCount) / 3),
            0);
    }

    @Override
    public BigDecimal[] calculateGradeScores() {
        BigDecimal[] gradeScores = gradeScoreFormula();
        for (int i = 0; i < gradeScores.length; i++) {
            gradeScores[i] = gradeScores[i].setScale(3, RoundingMode.HALF_UP);
        }
        return gradeScores;
    }

    @Override
    public BigDecimal calculateTotalGradeScore() {
        return totalGradeScoreFormula();
    }

    private BigDecimal totalGradeScoreFormula() {
        BigDecimal scoresSum = BigDecimal.ZERO;

        for (BigDecimal score: gradeScoreFormula()) {
            scoresSum = scoresSum.add(score);
        }

        if (isCommon()) {
            scoresSum = scoresSum.multiply(COMMON_GRADE_RATE);
        }

        return scoresSum.setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal[] gradeScoreFormula() {
        BigDecimal[] gradeScores = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
        BigDecimal[] scoresToCalculate = zeroCheckScoresToCalculate();

//        for (int index = 0; index < scoresToCalculate.length - 2; index++) {
//            gradeScores[index] = scoresToCalculate[index].multiply(GRADE_RATE);
//        }
////
//        System.out.println("a " + gradeScores[0] + " / "+ gradeScores[1] + " / "+ gradeScores[2] + " / ");
//        System.out.println("b " + scoresToCalculate[0] + " / "+ scoresToCalculate[1] + " / "+ scoresToCalculate[2] + " / "+ scoresToCalculate[3] + " / ");
////
//        gradeScores[gradeScores.length - 1] = scoresToCalculate[scoresToCalculate.length - 2]
//            .add(scoresToCalculate[scoresToCalculate.length - 1])
//            .multiply(new BigDecimal(8));
//
//        if (scoresToCalculate[0] == BigDecimal.valueOf(0)) {
//            scoresToCalculate[0] = scoresToCalculate[1];
//        }
        gradeScores[2] = scoresToCalculate[0].add(scoresToCalculate[1]).multiply(BigDecimal.valueOf(4));


        gradeScores[1] = scoresToCalculate[2].multiply(BigDecimal.valueOf(4));
        gradeScores[0] = scoresToCalculate[3].multiply(BigDecimal.valueOf(4));

        return gradeScores;
    }

    private BigDecimal[] zeroCheckScoresToCalculate() {
        BigDecimal[] scoresToCalculate = scoresToCalculate();
        BigDecimal summedScore = BigDecimal.ZERO;

        for (BigDecimal scorePerYear: scoresToCalculate) {
            summedScore = summedScore.add(scorePerYear);
        }

        if (scoresToCalculate[3].equals(BigDecimal.ZERO) && scoresToCalculate[2].equals(BigDecimal.ZERO)) {
            BigDecimal scoreToReplace = summedScore.divide(BigDecimal.valueOf(2), 5, RoundingMode.DOWN);
            scoresToCalculate[3] = scoreToReplace;
            scoresToCalculate[2] = scoreToReplace;
        } else if (scoresToCalculate[3].equals(BigDecimal.ZERO)) {
            scoresToCalculate[3] = summedScore.divide(BigDecimal.valueOf(3), 5, RoundingMode.DOWN);
        } else if (scoresToCalculate[2].equals(BigDecimal.ZERO)) {
            scoresToCalculate[2] = summedScore.divide(BigDecimal.valueOf(3), 5, RoundingMode.DOWN);
        }

        return scoresToCalculate;
    }

    private BigDecimal[] scoresToCalculate() {
        BigDecimal[] scoresPerSemester = scoresPerSemester();
        BigDecimal[] scoresToCalculate = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};

        int toCalculateLength = scoresToCalculate.length; //4
        int semesterLength = scoresPerSemester.length - 2; //6

        scoresToCalculate[0] = scoresPerSemester[0];
        if (scoresToCalculate[0].equals(BigDecimal.ZERO)) {
            scoresToCalculate[0] = scoresPerSemester[1];
        }

//        for (int semesterIndex = semesterLength - 2, scoreIndex = toCalculateLength - 2;
//            semesterIndex >= 0 && scoreIndex >= 0;
//            semesterIndex--) {
//            if (!scoresPerSemester[semesterIndex].equals(BigDecimal.ZERO)) {
//                scoresToCalculate[scoreIndex--] = scoresPerSemester[semesterIndex];
//            }
//        }
        scoresToCalculate[1] = scoresPerSemester[1];
        scoresToCalculate[2] = scoresPerSemester[2];
        scoresToCalculate[3] = scoresPerSemester[3];

        return scoresToCalculate;
    }

    private BigDecimal[] scoresPerSemester() {
        String[] gradesPerSemester = gradesPerSemester();
        BigDecimal[] scoresPerSemester = new BigDecimal[]{BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO};

        for (int semester = 0 ; semester < gradesPerSemester.length ; semester++) {
            scoresPerSemester[semester] = gradesToScore(gradesPerSemester[semester]);
        }

        return scoresPerSemester;
    }

    private String[] gradesPerSemester() {
        String[] gradesPerSubject = new String[]{koreanGrade,
            socialGrade,
            historyGrade,
            mathGrade,
            scienceGrade,
            englishGrade,
            techAndHomeGrade};
        String[] gradesPerSemester = new String[]{"", "", "", ""};

        for (String grades: gradesPerSubject) {
            for (int semester = 0 ; semester < grades.length() ; semester++) {
                gradesPerSemester[semester] += grades.charAt(semester);
            }
        }

        return gradesPerSemester;
    }

    private BigDecimal gradesToScore(String grades) {
        int semesterScore = 0;
        int subjectCount = 0;

        for (char grade: grades.toCharArray()) {
            switch (grade) {
                case 'A':
                    semesterScore++;
                case 'B':
                    semesterScore++;
                case 'C':
                    semesterScore++;
                case 'D':
                    semesterScore++;
                case 'E':
                    semesterScore++;
                    subjectCount++;
            }
        }

        if (semesterScore == 0) { return BigDecimal.ZERO; }

        return BigDecimal.valueOf(semesterScore)
            .divide(BigDecimal.valueOf(subjectCount),
                5,
                RoundingMode.DOWN);
    }

}
