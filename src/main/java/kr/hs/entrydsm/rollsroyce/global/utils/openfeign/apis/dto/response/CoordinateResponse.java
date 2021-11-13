package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class CoordinateResponse {

	private CoordinateInfo coordinateInfo;

	public String getLat() {
		return coordinateInfo.getCoordinate().get(0).getLat();
	}

	public String getLon() {
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
