package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {

	private final double endX = 127.363585;

	private final double endY = 36.391636;

	private double startX;

	private double startY;

	private int totalValue;

}
