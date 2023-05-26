package kr.hs.entrydsm.rollsroyce.domain.banner.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

import kr.hs.entrydsm.rollsroyce.domain.banner.service.UploadBannerLinkService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/banner")
public class BannerLinkController {
    private final UploadBannerLinkService uploadBannerLinkService;

    @PostMapping("/{banner-id}")
    public String uploadBanner(
            @RequestPart(name = "photo") @Nullable MultipartFile multipartFile,
            @PathVariable("banner-id") Long bannerId) {
        return uploadBannerLinkService.execute(multipartFile, bannerId);
    }
}
