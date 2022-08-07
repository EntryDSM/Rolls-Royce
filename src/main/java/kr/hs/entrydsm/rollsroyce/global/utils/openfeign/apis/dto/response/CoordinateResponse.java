package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;
import lombok.Getter;

import java.util.List;

@Getter
public class CoordinateResponse {

    @JsonProperty("coordinateInfo")
    private CoordinateInfo coordinateInfo;

    public String getLat() {
        return coordinateInfo.getCoordinate().get(0).getLat();
    }

    public String getLon() {
        if (coordinateInfo.getCoordinate().get(0) == null)
            throw RequestFailToOtherServerException.EXCEPTION;
        return coordinateInfo.getCoordinate().get(0).getLon();
    }

    @Getter
    public static class CoordinateInfo {
        private List<Coordinate> coordinate;
    }

    @Getter
    public static class Coordinate {
        private String lat;
        private String lon;
    }

}
