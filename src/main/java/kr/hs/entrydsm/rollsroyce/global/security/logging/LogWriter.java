package kr.hs.entrydsm.rollsroyce.global.security.logging;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PreDestroy;

@Component
public class LogWriter {

    private final LogProperties logProperties;

    private final File file;
    private BufferedWriter writer;

    public LogWriter(LogProperties logProperties) throws IOException {
        this.logProperties = logProperties;
        file = new File(logProperties.getPath(), logProperties.getName());
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
    }

    @Async
    public void writeLog(String content) throws IOException {
        writer.write(content + "\n");
        writer.flush();
        if (isLogOverSize()) {
            createZipFile();
        }
    }

    @PreDestroy
    public void closeStream() throws IOException {
        writer.close();
    }

    private boolean isLogOverSize() throws IOException {
        double size = BigDecimal.valueOf(Files.size(file.toPath()))
                .divide(BigDecimal.valueOf(1048576), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return logProperties.getSize() < size;
    }

    private void createZipFile() throws IOException {
        String now = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        File zipFile = new File(logProperties.getPath(), logProperties.getName() + "." + now + ".zip");
        FileInputStream logInputStream = new FileInputStream(file);
        FileOutputStream logOutputStream = new FileOutputStream(zipFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(logOutputStream);
        ZipEntry zipEntry = new ZipEntry(logProperties.getName());
        zipOutputStream.putNextEntry(zipEntry);

        int length;
        byte[] buffer = new byte[8196];
        while ((length = logInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, length);
        }

        zipOutputStream.closeEntry();
        zipOutputStream.finish();
        logInputStream.close();
        zipOutputStream.close();
        resetLogFile();
    }

    private void resetLogFile() throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    }
}
