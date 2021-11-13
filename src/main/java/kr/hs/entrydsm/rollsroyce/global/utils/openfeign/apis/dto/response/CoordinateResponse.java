package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import lombok.Getter;

@Getter
public class CoordinateResponse {

	private Coordinate coordinate;

	public String getLat() {
		return coordinate.getLat();
	}

	public String getLon() {
		return coordinate.getLon();
	}

	@Getter
	public static class Coordinate {
		private String lat;
		private String lon;
	}

}
