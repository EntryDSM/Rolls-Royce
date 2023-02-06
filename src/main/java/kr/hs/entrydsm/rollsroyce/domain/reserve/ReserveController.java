package kr.hs.entrydsm.rollsroyce.domain.reserve;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Value("${reserve.link}")
    private String reserveLink;

    public String reserveLink() {
        return reserveLink;
    }
}
