package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuerySchoolResponse {

    private final List<SchoolInformation> content;

    @Getter
    @AllArgsConstructor
    public static class SchoolInformation {
        private final String code;
        private final String name;
        private final String address;
    }
}
