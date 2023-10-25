package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ApplicantCode {
    private final Workbook workbook = new XSSFWorkbook();
    private final Sheet sheet = workbook.createSheet("지원자 목록");

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void format() {
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("수험번호");
        row.createCell(1).setCellValue("접수번호");
        row.createCell(2).setCellValue("성명");
    }
}
