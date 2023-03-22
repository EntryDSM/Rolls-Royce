package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RouteRequest {

    @JsonProperty("endX")
    private final double endX = 127.363585;

    @JsonProperty("endY")
    private final double endY = 36.391636;

    @JsonProperty("startX")
    private double startX;

    @JsonProperty("startY")
    private double startY;

    @JsonProperty("totalValue")
    private int totalValue;
}
