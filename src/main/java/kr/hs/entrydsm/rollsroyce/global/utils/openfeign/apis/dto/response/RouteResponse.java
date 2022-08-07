package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;
import lombok.Getter;

import java.util.List;

@Getter
public class RouteResponse {

    private String type;
    private List<Feature> features;

    public int getTotalDistance() {
        try {
            return features.get(0).getProperties().getTotalDistance();
        } catch (NullPointerException e) {
            throw RequestFailToOtherServerException.EXCEPTION;
        }
    }

    @Getter
    public static class Feature {
        private String type;
        private Properties properties;

        @Getter
        public static class Properties {
            @JsonProperty("totalDistance")
            private int totalDistance;
            @JsonProperty("totalTime")
            private int totalTime;
            @JsonProperty("totalFare")
            private int totalFare;
            @JsonProperty("taxiFare")
            private int taxiFare;
        }

    }

}
