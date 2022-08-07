package kr.hs.entrydsm.rollsroyce.global.utils.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static kr.hs.entrydsm.rollsroyce.global.config.Font.fonts;

@Component
public class ConverterPropertiesCreator {

    @Value("${rolls-royce.font.path}")
    private String fontPath;

    public ConverterProperties createConverterProperties() {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider(false, false, false);

        fonts.forEach(font -> {
            try {
                FontProgram fontProgram = FontProgramFactory.createFont(fontPath + font);
                fontProvider.addFont(fontProgram);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        properties.setFontProvider(fontProvider);
        return properties;
    }

}
