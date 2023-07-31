package kr.hs.entrydsm.rollsroyce.domain.banner.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.annotation.Nullable;

import kr.hs.entrydsm.rollsroyce.domain.banner.presentation.dto.response.QueryBannerLinkListResponse;
import kr.hs.entrydsm.rollsroyce.domain.banner.service.CreateBannerLinkService;
import kr.hs.entrydsm.rollsroyce.domain.banner.service.QueryBannerLinkListService;
import kr.hs.entrydsm.rollsroyce.domain.banner.service.UploadBannerLinkService;

@Tag(name = "배너 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/banner")
public class BannerLinkController {
    private final CreateBannerLinkService createBannerLinkService;
    private final UploadBannerLinkService uploadBannerLinkService;
    private final QueryBannerLinkListService queryBannerLinkListService;

    @Operation(summary = "배너 생성 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createBannerLink(@RequestPart(name = "photo") MultipartFile file) {
        return createBannerLinkService.execute(file);
    }

    @Operation(summary = "배너 수정 API")
    @PostMapping("/{banner-id}")
    public String uploadBanner(
            @RequestPart(name = "photo") @Nullable MultipartFile multipartFile,
            @PathVariable("banner-id") Long bannerId) {
        return uploadBannerLinkService.execute(multipartFile, bannerId);
    }

    @Operation(summary = "배너 조회 API")
    @GetMapping
    public List<QueryBannerLinkListResponse> getBannerLink() {
        return queryBannerLinkListService.execute();
    }
}
