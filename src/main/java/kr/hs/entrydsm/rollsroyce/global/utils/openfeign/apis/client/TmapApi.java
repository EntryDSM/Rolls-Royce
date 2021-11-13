package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.client;

import java.util.Map;

import feign.Headers;
import feign.Param;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request.RouteRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.CoordinateResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.RouteResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "tmapApiClient", url = "https://apis.openapi.sk.com/tmap")
public interface TmapApi {

	@PostMapping("/routes?version=1")
	@Headers({
			"Accept-Language:ko",
			"Content-Type:application/json"
	})
	RouteResponse routeGuidance(@RequestHeader("appKey") String key, @RequestBody RouteRequest request);

	@GetMapping("/geo/postcode?version=1&addressFlag=F00&format=json&"
			+ "appKey={key}&addr={addr}")
	@Headers({
			"Accept-Language:ko",
			"Content-Type:application/x-www-form-urlencoded;charset=UTF-8",
	})
	CoordinateResponse getCoordinate(@Param("queryParams") Map<String, String> queryParams);

}
