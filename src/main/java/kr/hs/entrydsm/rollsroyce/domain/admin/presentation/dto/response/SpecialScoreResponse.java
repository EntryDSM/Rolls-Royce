package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.dto.response;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.hs.entrydsm.rollsroyce.domain.user.domain.types.ApplicationType;

@Getter
public class SpecialScoreResponse extends StaticsScoreResponse {

    @JsonProperty(value = "98-110")
    private int score98_110;

    @JsonProperty(value = "85-97")
    private int score85_97;

    @JsonProperty(value = "72-84")
    private int score72_84;

    @JsonProperty(value = "59-71")
    private int score59_71;

    @JsonProperty(value = "46-58")
    private int score46_58;

    @JsonProperty(value = "33-45")
    private int score33_45;

    @JsonProperty(value = "20-32")
    private int score20_32;

    @JsonProperty(value = "-19")
    private int score_19;

    public SpecialScoreResponse(ApplicationType applicationType, boolean isDaejeon) {
        this.applicationType = applicationType;
        this.isDaejeon = isDaejeon;
    }

    @Override
    public void addScore(double score) {
        if (score <= 19) score_19++;
        else if (score <= 32) score20_32++;
        else if (score <= 45) score33_45++;
        else if (score <= 58) score46_58++;
        else if (score <= 71) score59_71++;
        else if (score <= 84) score72_84++;
        else if (score <= 97) score85_97++;
        else if (score <= 110) score98_110++;
    }
}
