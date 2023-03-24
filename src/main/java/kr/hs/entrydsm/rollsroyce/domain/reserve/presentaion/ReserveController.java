package kr.hs.entrydsm.rollsroyce.domain.reserve.presentaion;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hs.entrydsm.rollsroyce.domain.reserve.service.GetReserveLinkService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    private final GetReserveLinkService getReserveLinkService;

    @GetMapping
    public String reserveLink() {
        return getReserveLinkService.execute();
    }
}
