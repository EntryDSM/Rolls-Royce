package kr.hs.entrydsm.rollsroyce.domain.banner.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;

@Getter
@AllArgsConstructor
public class QueryBannerLinkListResponse {
    private final List<BannerLink> bannerLinkList;
}
