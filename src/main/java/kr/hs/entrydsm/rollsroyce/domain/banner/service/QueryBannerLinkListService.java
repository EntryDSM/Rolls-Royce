package kr.hs.entrydsm.rollsroyce.domain.banner.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;
import kr.hs.entrydsm.rollsroyce.domain.banner.domain.repository.BannerLinkRepository;
import kr.hs.entrydsm.rollsroyce.domain.banner.presentation.dto.response.QueryBannerLinkListResponse;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class QueryBannerLinkListService {
    private final BannerLinkRepository bannerLinkRepository;
    private final S3Util s3Util;

    @Transactional(readOnly = true)
    public List<QueryBannerLinkListResponse> execute() {
        List<BannerLink> bannerLinkList = bannerLinkRepository.findAll();

        List<QueryBannerLinkListResponse> bannerLinkListUrl = bannerLinkList.stream()
                .map(bannerLink -> new QueryBannerLinkListResponse(s3Util.generateObjectUrl(bannerLink.getLink())))
                .collect(Collectors.toList());

        return bannerLinkListUrl;
    }
}
