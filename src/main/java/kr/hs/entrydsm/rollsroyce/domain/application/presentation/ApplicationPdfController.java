package kr.hs.entrydsm.rollsroyce.domain.application.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import kr.hs.entrydsm.rollsroyce.global.utils.pdf.ApplicationPdfService;

@Tag(name = "원서 접수 PDF API")
@RequiredArgsConstructor
@RequestMapping("/pdf")
@RestController
public class ApplicationPdfController {

    private final ApplicationPdfService applicationPdfService;

    private static final String FILE_NAME = "대덕소프트웨어마이스터고등학교_입학원서";

    @Operation(summary = "미리보기용 원서 PDF API")
    @GetMapping(value = "/preview", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPreviewApplicationPdf() {
        return applicationPdfService.getPreviewApplicationPdf();
    }

    @Operation(summary = "최종제출용 원서 PDF API")
    @GetMapping(value = "/final", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getFinalApplicationPdf(HttpServletResponse response) {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.pdf\"", encodeFileName()));
        return applicationPdfService.getFinalApplicationPdf();
    }

    private String encodeFileName() {
        return new String(
                ApplicationPdfController.FILE_NAME.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
}
