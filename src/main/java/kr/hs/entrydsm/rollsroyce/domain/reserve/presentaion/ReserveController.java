package kr.hs.entrydsm.rollsroyce.domain.reserve.presentaion;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kr.hs.entrydsm.rollsroyce.domain.reserve.service.GetReserveLinkService;

@Tag(name = "입학 설명회 참석 예약 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    private final GetReserveLinkService getReserveLinkService;

    @Operation(summary = "예약링크 조회 API")
    @GetMapping
    public String reserveLink() {
        return getReserveLinkService.execute();
    }
}
