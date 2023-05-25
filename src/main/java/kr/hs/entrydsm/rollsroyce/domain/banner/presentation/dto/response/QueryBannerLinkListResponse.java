package kr.hs.entrydsm.rollsroyce.domain.banner.presentation.dto.response;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryBannerLinkListResponse {
    private final List<BannerLink> bannerLinkList;
}
