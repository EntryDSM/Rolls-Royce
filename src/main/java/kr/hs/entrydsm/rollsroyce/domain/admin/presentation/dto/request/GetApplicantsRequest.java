package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidKeywordException;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class GetApplicantsRequest {

    @Nullable
    private final String receiptCode;

    @Nullable
    private final String name;

    @Nullable
    private final String schoolName;

    @Nullable
    private final boolean isDaejeon;

    @Nullable
    private final boolean isNationwide;

    @Nullable
    private final boolean isCommon;

    @Nullable
    private final boolean isMeister;

    @Nullable
    private final boolean isSocial;

    @Nullable
    private final boolean inOfHeadcount;

    @Nullable
    private final boolean outOfHeadcount;

    @Nullable
    private final boolean isSubmitted;

    @Nullable
    private final boolean isNotSubmitted;

    public GetApplicantsRequest(String receiptCode, String name, String schoolName, boolean isDaejeon, boolean isNationwide, boolean isCommon, boolean isMeister, boolean isSocial, boolean inOfHeadcount, boolean outOfHeadcount, boolean isSubmitted, boolean isNotSubmitted) {
        try {
            String r = receiptCode != null ? receiptCode.replaceAll(" ", "") : "";
            this.receiptCode =  "%" + ((r.equals("")) ? r : Long.parseLong(receiptCode)) + "%";
        } catch (NumberFormatException e) {
            throw InvalidKeywordException.EXCEPTION;
        }
        this.name = "%" + ((name!=null)?name:"") + "%";
        if (schoolName != null) {
            this.schoolName = "%" + schoolName + "%";
        } else {
            this.schoolName = null;
        }
        this.isDaejeon = isDaejeon;
        this.isNationwide = isNationwide;

        if (!isCommon && !isMeister && !isSocial) {
            this.isCommon = true;
            this.isMeister = true;
            this.isSocial = true;
        } else {
            this.isCommon = isCommon;
            this.isMeister = isMeister;
            this.isSocial = isSocial;
        }
        this.inOfHeadcount = inOfHeadcount;
        this.outOfHeadcount = outOfHeadcount;
        this.isSubmitted = isSubmitted;
        this.isNotSubmitted = isNotSubmitted;
    }
}
