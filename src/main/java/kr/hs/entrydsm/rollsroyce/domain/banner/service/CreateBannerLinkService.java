package kr.hs.entrydsm.rollsroyce.domain.banner.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;
import kr.hs.entrydsm.rollsroyce.domain.banner.domain.repository.BannerLinkRepository;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class CreateBannerLinkService {
    private final BannerLinkRepository bannerLinkRepository;
    private final S3Util s3Util;

    @Transactional
    public String execute(MultipartFile file) {
        BannerLink bannerLink = new BannerLink(file.getName());
        bannerLinkRepository.save(bannerLink);

        return s3Util.generateObjectUrl(file.getName());
    }
}
