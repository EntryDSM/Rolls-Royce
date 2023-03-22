package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.global.exception.RequestFailToOtherServerException;

@Getter
public class CoordinateResponse {

    @JsonProperty("coordinateInfo")
    private CoordinateInfo coordinateInfo;

    public String getLat() {
        return coordinateInfo.getCoordinate().get(0).getLat();
    }

    public String getLon() {
        if (coordinateInfo.getCoordinate().get(0) == null) throw RequestFailToOtherServerException.EXCEPTION;
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
