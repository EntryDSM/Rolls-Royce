package kr.hs.entrydsm.rollsroyce.domain.admin.service;

import kr.hs.entrydsm.rollsroyce.domain.admin.exception.ExcelOException;
import kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel.ApplicantCode;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.EntryInfoRepository;
import kr.hs.entrydsm.rollsroyce.domain.entryinfo.domain.repository.vo.ApplicantCodeVo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrintApplicantCodesService {
    private final EntryInfoRepository entryInfoRepository;

    public void execute(HttpServletResponse response) {
        ApplicantCode applicantCode = new ApplicantCode();
        Sheet sheet = applicantCode.getSheet();
        applicantCode.format();
        List<ApplicantCodeVo> applicantCodes = entryInfoRepository.findApplicantCodesByIsFirstRoundPass();

        int i = 0;
        for (ApplicantCodeVo applicantCodeVo : applicantCodes) {
            Row row = sheet.createRow(++i);
            insertCode(row, applicantCodeVo);
        }

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String formatFilename = "attachment;filename=\"지원자번호목록";
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"));
            String fileName = new String((formatFilename + time + ".xlsx\"").getBytes("KSC5601"), "8859_1");
            response.setHeader("Content-Disposition", fileName);

            applicantCode.getWorkbook().write(response.getOutputStream());
        } catch (IOException e) {
            throw ExcelOException.EXCEPTION;
        }
    }

    private void insertCode(Row row, ApplicantCodeVo applicantCodeVo) {
        row.createCell(0).setCellValue(applicantCodeVo.getExamCode());
        row.createCell(1).setCellValue(applicantCodeVo.getReceiptCode());
        row.createCell(2).setCellValue(applicantCodeVo.getName());
    }
}