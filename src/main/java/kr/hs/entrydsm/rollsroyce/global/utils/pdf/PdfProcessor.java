package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfProcessor {

	private final ConverterPropertiesCreator converterPropertiesCreator;

	public ByteArrayOutputStream concat(ByteArrayOutputStream first, ByteArrayOutputStream second) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfDocument mergedDocument = new PdfDocument(new PdfWriter(outputStream));
		PdfMerger merger = new PdfMerger(mergedDocument);

		PdfDocument firstDocument = getPdfDocument(first);
		PdfDocument secondDocument = getPdfDocument(second);

		mergeDocument(merger, firstDocument);
		mergeDocument(merger, secondDocument);

		mergedDocument.close();
		return outputStream;
	}

	public ByteArrayOutputStream convertHtmlToPdf(String html) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(html, outputStream, converterPropertiesCreator.createConverterProperties());
		return outputStream;
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


}
