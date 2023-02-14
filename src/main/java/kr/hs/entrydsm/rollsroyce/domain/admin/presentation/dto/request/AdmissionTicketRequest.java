package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.types.ApplicationType;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class AdmissionTicketRequest {
    @Nullable
    private final String photoFileName;

    @Nullable
    private final String receiptCode;

    @Nullable
    private final String name;

    @Nullable
    private final String schoolName;

    @Nullable
    private final Boolean isDaejeon;

    @Nullable
    private final ApplicationType applicationType;

    @Nullable
    private final String examCode;

    public AdmissionTicketRequest(String photoFileName, String receiptCode, String name, String schoolName, Boolean isDaejeon,
                                  ApplicationType applicationType, String examCode) {
        this.photoFileName = photoFileName;
        this.receiptCode = receiptCode;
        this.name = name;
        this.schoolName = schoolName;
        this.isDaejeon = isDaejeon;
        this.applicationType = applicationType;
        this.examCode = examCode;
    }
}