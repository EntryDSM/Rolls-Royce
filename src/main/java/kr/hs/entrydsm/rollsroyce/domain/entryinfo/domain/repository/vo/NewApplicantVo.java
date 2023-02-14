package kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationRemark;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.EducationalStatus;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.Sex;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewApplicantVo {
    private final Long receiptCode;
    private final EducationalStatus educationalStatus;
    private final ApplicationType applicationType;
    private final String name;
    private final Boolean isDaejeon;
    private final LocalDate birthday;
    private final String telephoneNumber;
    private final ApplicationRemark applicationRemark;
    private final Sex sex;
    private final String parentTel;

    @QueryProjection
    public NewApplicantVo(EntryInfo entryInfo) {
        this.receiptCode = entryInfo.getReceiptCode();
        this.educationalStatus = entryInfo.getEducationalStatus();
        this.applicationType = entryInfo.getApplicationType();
        this.name = entryInfo.getUserName();
        this.isDaejeon = entryInfo.getIsDaejeon();
        this.birthday = entryInfo.getBirthday();
        this.telephoneNumber = entryInfo.getUserTelephoneNumber();
        this.applicationRemark = entryInfo.getApplicationRemark();
        this.sex = entryInfo.getSex();
        this.parentTel = entryInfo.getParentTel();
    }
}
