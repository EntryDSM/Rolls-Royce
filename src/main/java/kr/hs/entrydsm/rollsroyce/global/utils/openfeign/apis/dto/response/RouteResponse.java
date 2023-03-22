package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;

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
