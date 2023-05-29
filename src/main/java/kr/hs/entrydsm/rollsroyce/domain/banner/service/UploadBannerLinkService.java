package kr.hs.entrydsm.rollsroyce.domain.banner.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.hs.entrydsm.rollsroyce.domain.banner.domain.BannerLink;
import kr.hs.entrydsm.rollsroyce.domain.banner.domain.repository.BannerLinkRepository;
import kr.hs.entrydsm.rollsroyce.global.exception.BannerLinkNotFoundException;
import kr.hs.entrydsm.rollsroyce.global.utils.s3.S3Util;

@RequiredArgsConstructor
@Service
public class UploadBannerLinkService {
    private final S3Util s3Util;
    private final BannerLinkRepository bannerLinkRepository;

    @Transactional
    public String execute(MultipartFile file, Long id) {
        BannerLink bannerLink =
                bannerLinkRepository.findById(id).orElseThrow(() -> BannerLinkNotFoundException.EXCEPTION);

        if (bannerLink.getLink() != null) {
            bannerLink.updateBanner(null);
        }

        String fileName = s3Util.upload(file, "banner/");

        bannerLink.updateBanner(fileName);

        return s3Util.generateObjectUrl(fileName);
    }
}
