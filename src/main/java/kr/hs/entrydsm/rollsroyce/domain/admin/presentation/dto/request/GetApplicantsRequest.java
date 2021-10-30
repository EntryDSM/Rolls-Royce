package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.entrydsm.rollsroyce.domain.admin.exception.InvalidKeywordException;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class GetApplicantsRequest {

    @Nullable
    @JsonProperty("receipt_code")
    private final String receiptCode;

    @Nullable
    @JsonProperty("name")
    private final String name;

    @Nullable
    @JsonProperty("school_name")
    private final String schoolName;

    @Nullable
    @JsonProperty("is_deajeon")
    private final boolean isDaejeon;

    @Nullable
    @JsonProperty("is_nationwide")
    private final boolean isNationwide;

    @Nullable
    @JsonProperty("is_common")
    private final boolean isCommon;

    @Nullable
    @JsonProperty("is_meister")
    private final boolean isMeister;

    @Nullable
    @JsonProperty("is_social")
    private final boolean isSocial;

    @Nullable
    @JsonProperty("is_in")
    private final boolean inOfHeadcount;

    @Nullable
    @JsonProperty("is_out")
    private final boolean outOfHeadcount;

    @Nullable
    @JsonProperty("is_submitted")
    private final boolean isSubmitted;

    public GetApplicantsRequest(String receiptCode, String name, String schoolName, boolean isDaejeon, boolean isNationwide, boolean isCommon, boolean isMeister, boolean isSocial, boolean inOfHeadcount, boolean outOfHeadcount, boolean isSubmitted) {
        try {
            this.receiptCode =  "%" + Long.parseLong(receiptCode) + "%";
        } catch (Exception e) {
            throw new InvalidKeywordException();
        }
        this.name = "%" + ((name!=null)?name:"") + "%";
        this.schoolName = "%" + ((schoolName!=null)?schoolName:"") + "%";
        this.isDaejeon = isDaejeon;
        this.isNationwide = isNationwide;
        this.isCommon = isCommon;
        this.isMeister = isMeister;
        this.isSocial = isSocial;
        this.inOfHeadcount = inOfHeadcount;
        this.outOfHeadcount = outOfHeadcount;
        this.isSubmitted = isSubmitted;
    }
}
