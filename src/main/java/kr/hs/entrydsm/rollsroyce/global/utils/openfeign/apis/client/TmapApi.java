package kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.client;

import feign.Headers;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.request.RouteRequest;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.CoordinateResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.openfeign.apis.dto.response.RouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/geo/postcode?addr={address}&version=1&addressFlag=F00&format=json")
    @Headers({
            "Accept-Language:ko",
            "Content-Type:application/x-www-form-urlencoded;charset=UTF-8",
    })
    CoordinateResponse getCoordinate(@RequestHeader("appKey") String key, @PathVariable("address") String addr);

}
