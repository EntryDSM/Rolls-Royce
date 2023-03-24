package kr.hs.entrydsm.rollsroyce.domain.school.facade;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kr.hs.entrydsm.rollsroyce.domain.school.domain.School;
import kr.hs.entrydsm.rollsroyce.domain.school.domain.repository.SchoolRepository;
import kr.hs.entrydsm.rollsroyce.domain.school.exception.SchoolNotFoundException;

@RequiredArgsConstructor
@Component
public class SchoolFacade {

    private final SchoolRepository schoolRepository;

    public School getSchoolByCode(String code) {
        return schoolRepository.findById(code).orElseThrow(() -> SchoolNotFoundException.EXCEPTION);
    }

    public Page<School> getSchoolByName(String name, Pageable pageable) {
        return schoolRepository.findByNameContaining(name, pageable);
    }
}
