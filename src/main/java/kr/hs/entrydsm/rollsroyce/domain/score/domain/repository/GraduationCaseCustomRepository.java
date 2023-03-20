package kr.hs.entrydsm.rollsroyce.domain.score.domain.repository;

import kr.hs.entrydsm.rollsroyce.domain.score.domain.repository.vo.GraduationCaseVo;

import java.util.List;

public interface GraduationCaseCustomRepository {
    List<GraduationCaseVo> findAllByGraduationCase(String koreanGrade, String socialGrade, String historyGrade, String mathGrade, String scienceGrade,
                                                   String techAndHomeGrade, String englishGrade, Integer volunteerTime, Integer latenessCount,
                                                   Integer dayAbsenceCount, Integer earlyLeaveCount, Integer lectureAbsenceCount);
}
