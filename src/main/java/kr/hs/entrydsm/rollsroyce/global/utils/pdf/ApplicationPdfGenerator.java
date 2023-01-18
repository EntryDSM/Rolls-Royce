package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Document;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.EntryInfo;
import kr.hs.entrydsm.rollsroyce.domain.score.domain.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ApplicationPdfGenerator {

    private final PdfProcessor pdfProcessor;
    private final PdfDataConverter pdfDataConverter;
    private final TemplateProcessor templateProcessor;

    public byte[] generate(EntryInfo entryInfo, Score score) {
        return generateApplicationPdf(entryInfo, score);
    }

    private byte[] generateApplicationPdf(EntryInfo entryInfo, Score score) {
        PdfData data = pdfDataConverter.applicationToInfo(entryInfo, score);

        ByteArrayOutputStream[] outputStreams = getTemplateFileNames(entryInfo).parallelStream()
                .map(template -> templateProcessor.convertTemplateIntoHtmlString(template, data.toMap()))
                .map(pdfProcessor::convertHtmlToPdf)
                .toArray(ByteArrayOutputStream[]::new);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument mergedDocument = new PdfDocument(new PdfWriter(outputStream));
        PdfMerger pdfMerger = new PdfMerger(mergedDocument);
        Document document = new Document(mergedDocument);

        for (ByteArrayOutputStream pdfStream : outputStreams) {
            PdfDocument pdfDoc = getPdfDocument(pdfStream);
            mergeDocument(pdfMerger, pdfDoc);
        }

        document.close();

        return outputStream.toByteArray();
    }

    private PdfDocument getPdfDocument(ByteArrayOutputStream outputStream) {
        try {
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new PdfDocument(new PdfReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mergeDocument(PdfMerger merger, PdfDocument document) {
        if (document != null) {
            merger.merge(document, 1, document.getNumberOfPages());
            document.close();
        }
    }

    private List<String> getTemplateFileNames(EntryInfo entryInfo) {
        List<String> result = new LinkedList<>(List.of(
                TemplateFileName.APPLICATION_FOR_ADMISSION,
                TemplateFileName.INTRODUCTION,
                TemplateFileName.NON_SMOKING,
                TemplateFileName.SMOKING_EXAMINE
        ));

        if (!entryInfo.isQualificationExam() && !entryInfo.isCommonApplicationType())
            result.add(2, TemplateFileName.RECOMMENDATION);

        return result;
    }

}
