package kr.hs.entrydsm.rollsroyce.domain.application.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class QueryInformationResponse {

    private final String name;
    private final String sex;
    private final LocalDate birthday;
    private final String parentName;
    private final String parentTel;
    private final String telephoneNumber;
    private final String address;
    private final String detailAddress;
    private final String postCode;
    private String photoFileName;

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }
}
