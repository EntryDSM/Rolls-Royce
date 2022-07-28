package kr.hs.entrydsm.rollsroyce.domain.score.domain;

import kr.hs.entrydsm.rollsroyce.domain.score.presentation.dto.request.UpdateGraduationRequest;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.EducationalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor
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
        if (MAX_VOLUNTEER_TIME <= volunteerTime) {
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
        int attendanceScore = MAX_ATTENDANCE_SCORE
                - dayAbsenceCount
                - (lectureAbsenceCount + latenessCount + earlyLeaveCount) / 3;

        return Math.max(attendanceScore, 0);
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

        for (BigDecimal score : gradeScoreFormula()) {
            scoresSum = scoresSum.add(score);
        }

        if (isCommon()) {
            scoresSum = scoresSum.multiply(COMMON_GRADE_RATE);
        }

        return scoresSum.setScale(3, RoundingMode.HALF_UP);
    }

    private BigDecimal[] gradeScoreFormula() {
        BigDecimal[] gradeScores = new BigDecimal[3];
        BigDecimal[] scoresToCalculate = zeroCheckScoresToCalculate();

        for (int index = 0; index < scoresToCalculate.length - 2; index++) {
            gradeScores[index] = scoresToCalculate[index].multiply(GRADE_RATE);
        }

        gradeScores[gradeScores.length - 1] = scoresToCalculate[scoresToCalculate.length - 2]
                .add(scoresToCalculate[scoresToCalculate.length - 1])
                .multiply(GRADE_RATE);

        return gradeScores;
    }

    private BigDecimal[] zeroCheckScoresToCalculate() {
        BigDecimal[] scoresToCalculate = scoresToCalculate();
        BigDecimal summedScore = BigDecimal.ZERO;

        for (BigDecimal scorePerYear : scoresToCalculate) {
            summedScore = summedScore.add(scorePerYear);
        }

        if (scoresToCalculate[0].equals(BigDecimal.ZERO) && scoresToCalculate[1].equals(BigDecimal.ZERO)) {
            BigDecimal scoreToReplace = summedScore.divide(BigDecimal.valueOf(2), 5, RoundingMode.DOWN);
            scoresToCalculate[0] = scoreToReplace;
            scoresToCalculate[1] = scoreToReplace;
        } else if (BigDecimal.ZERO.equals(scoresToCalculate[0])) {
            scoresToCalculate[0] = summedScore.divide(BigDecimal.valueOf(3), 5, RoundingMode.DOWN);
        } else if (BigDecimal.ZERO.equals(scoresToCalculate[1])) {
            scoresToCalculate[1] = summedScore.divide(BigDecimal.valueOf(3), 5, RoundingMode.DOWN);
        }

        return scoresToCalculate;
    }

    private BigDecimal[] scoresToCalculate() {
        BigDecimal[] scoresPerSemester = scoresPerSemester();
        BigDecimal[] scoresToCalculate = new BigDecimal[4];

        int toCalculateLength = scoresToCalculate.length;
        int semesterLength = scoresPerSemester.length;

        scoresToCalculate[toCalculateLength - 1] = scoresPerSemester[semesterLength - 1];
        if (scoresToCalculate[toCalculateLength - 1].equals(BigDecimal.ZERO)) {
            scoresToCalculate[toCalculateLength - 1] = scoresPerSemester[semesterLength - 2];
        }

        for (int semesterIndex = semesterLength - 2, scoreIndex = toCalculateLength - 2;
             semesterIndex >= 0 && scoreIndex >= 0;
             semesterIndex--) {
            if (!scoresPerSemester[semesterIndex].equals(BigDecimal.ZERO)) {
                scoresToCalculate[scoreIndex--] = scoresPerSemester[semesterIndex];
            }
        }

        return scoresToCalculate;
    }

    private BigDecimal[] scoresPerSemester() {
        String[] gradesPerSemester = gradesPerSemester();
        BigDecimal[] scoresPerSemester = new BigDecimal[6];

        for (int semester = 0; semester < gradesPerSemester.length; semester++) {
            scoresPerSemester[semester] = gradesToScore(gradesPerSemester[semester]);
        }

        return scoresPerSemester;
    }

    private String[] gradesPerSemester() {
        String[] gradesPerSubject = new String[]{
                koreanGrade,
                socialGrade,
                historyGrade,
                mathGrade,
                scienceGrade,
                englishGrade,
                techAndHomeGrade
        };
        String[] gradesPerSemester = new String[6];

        for (String grades : gradesPerSubject) {
            for (int semester = 0; semester < grades.length(); semester++) {
                gradesPerSemester[semester] += grades.charAt(semester);
            }
        }

        if (isProspectiveGraduate()) {
            gradesPerSemester[5] = gradesPerSemester[4];
        }

        return gradesPerSemester;
    }

    private BigDecimal gradesToScore(String grades) {
        int semesterScore = 0;
        int subjectCount = 0;

        for (char grade : grades.toCharArray()) {
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
                default:
                    subjectCount++;
            }
        }

        if (semesterScore == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(semesterScore)
                .divide(BigDecimal.valueOf(subjectCount), 5,
                        RoundingMode.DOWN);
    }

}
