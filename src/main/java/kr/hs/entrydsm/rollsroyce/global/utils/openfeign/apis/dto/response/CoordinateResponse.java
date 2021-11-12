package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import lombok.Getter;

@Getter
public class CoordinateResponse {

	private Coordinate coordinate;

	@Getter
	public static class Coordinate {
		private String lat;
		private String lon;
	}

}
