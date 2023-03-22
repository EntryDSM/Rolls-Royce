package kr.hs.entrydsm.rollsroyce.domain.reserve.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GetReserveLinkService {
    @Value("${reserve.link}")
    private String reserveLink;

    public String execute() {
        return reserveLink;
    }
}
