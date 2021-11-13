package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class RouteResponse {

	private String type;
	private List<Feature> featureList;

	public int getTotalDistance() {
		return featureList.get(0).getProperties().getTotalDistance();
	}

	@Getter
	public static class Feature {
		private String type;
		private Properties properties;

		@Getter
		public static class Properties {
			private int totalDistance;
			private int totalTime;
			private int totalFare;
			private int taxiFare;
		}

	}

}
