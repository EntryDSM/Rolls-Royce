package kr.hs.entrydsm.rollsroyce.global.security.logging;

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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogWriter {

	@Value("${rolls-royce.logging.name}")
	private String logFileName;

	@Value("${rolls-royce.logging.path}")
	private String logPath;

	@Value("${rolls-royce.logging.size}")
	private int logSize;

	private File file;
	private BufferedWriter writer;

	@Async
	public void writeLog(String content) throws IOException {
		writer.write(content + "\n");
		writer.flush();
		if (isLogOverSize()) {
			createZipFile();
		}
	}

	@PostConstruct
	public void setup() throws IOException {
		file = new File(logPath, logFileName);
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
	}

	@PreDestroy
	public void closeStream() throws IOException {
		writer.close();
	}

	private boolean isLogOverSize() throws IOException {
		double size = BigDecimal.valueOf(Files.size(file.toPath()))
				.divide(BigDecimal.valueOf(1048576), 2, RoundingMode.HALF_UP)
				.doubleValue();
		return logSize < size;
	}

	private void createZipFile() throws IOException {
		String now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		File zipFile = new File(logPath, logFileName + "." + now + ".zip");
		FileInputStream logInputStream = new FileInputStream(file);
		FileOutputStream logOutputStream = new FileOutputStream(zipFile);
		ZipOutputStream zipOutputStream = new ZipOutputStream(logOutputStream);
		ZipEntry zipEntry = new ZipEntry(logFileName);
		zipOutputStream.putNextEntry(zipEntry);

		int length;
		byte[] buffer = new byte[8196];
		while((length = logInputStream.read(buffer)) > 0) {
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
