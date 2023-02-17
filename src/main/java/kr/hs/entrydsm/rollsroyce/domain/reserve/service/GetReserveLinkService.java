package kr.hs.entrydsm.rollsroyce.domain.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetReserveLinkService {
    @Value("${reserve.link}")
    private final String reserveLink;

    public String execute() {
        return reserveLink;
    }
}
