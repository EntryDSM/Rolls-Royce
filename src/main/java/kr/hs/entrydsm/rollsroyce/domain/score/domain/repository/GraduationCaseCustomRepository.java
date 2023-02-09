package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.GraduationCase;

import java.util.List;

interface GraduationCaseCustomRepository {
    List<GraduationCase> findAllByGraduationCase(String koreanGrade, String socialGrade, String historyGrade, String mathGrade, String scienceGrade,
                                                 String techAndHomeGrade, String englishGrade, Integer volunteerTime, Integer latenessCount,
                                                 Integer dayAbsenceCount, Integer earlyLeaveCount, Integer lectureAbsenceCount);
}
