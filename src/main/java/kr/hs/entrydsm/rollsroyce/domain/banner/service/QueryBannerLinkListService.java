package kr.hs.entrydsm.rollsroyce.domain.banner.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;
import kr.hs.entrydsm.rollsroyce.domain.banner.domain.repository.BannerLinkRepository;
import kr.hs.entrydsm.rollsroyce.domain.banner.presentation.dto.response.QueryBannerLinkListResponse;

@RequiredArgsConstructor
@Service
public class QueryBannerLinkListService {
    private final BannerLinkRepository bannerLinkRepository;

    @Transactional(readOnly = true)
    public QueryBannerLinkListResponse execute() {
        List<BannerLink> bannerLinkList = bannerLinkRepository.findAll();

        return new QueryBannerLinkListResponse(bannerLinkList);
    }
}
