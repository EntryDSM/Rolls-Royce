package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class AdmissionTicket {

    private final Workbook workbook = new XSSFWorkbook();
    private final Sheet sheet = workbook.createSheet("수험표");

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void format(
            int rowIndex,
            int colIndex,
            String examCode,
            String name,
            String middleSchool,
            String area,
            String applicationType,
            String receiptCode) {
        sheet.setDefaultColumnWidth(14);
        CellStyle alignCenter = alignCenter();
        Font bold = bold();

        XSSFRichTextString title = new XSSFRichTextString("2023학년도 대덕소프트웨어마이스터고등학교\n입학전형 수험표");
        title.applyFont(24, 32, bold);
        CellRangeAddress titleAddress = new CellRangeAddress(rowIndex, rowIndex + 1, colIndex, colIndex + 5);
        merge(titleAddress, title, alignCenter);
        mergeCell(new CellRangeAddress(rowIndex + 2, rowIndex + 13, colIndex, colIndex + 1));
        String[] attributes = {"수험번호", "성명", "출신 중학교", "지역", "전형 유형", "접수 번호"};
        String[] studentInfo = {examCode, name, middleSchool, area, applicationType, receiptCode};
        for (int i = rowIndex + 1; i <= rowIndex + attributes.length * 2; i += 2) {
            merge(
                    new CellRangeAddress(i + 1, i + 2, colIndex + 2, colIndex + 3),
                    attributes[(i - rowIndex) / 2],
                    alignCenter);
            merge(
                    new CellRangeAddress(i + 1, i + 2, colIndex + 4, colIndex + 5),
                    studentInfo[(i - rowIndex) / 2],
                    alignCenter);
        }
        XSSFRichTextString bottom = new XSSFRichTextString("대덕소프트웨어마이스터고등학교장");
        bottom.applyFont(bold);
        merge(new CellRangeAddress(rowIndex + 14, rowIndex + 15, colIndex, colIndex + 5), bottom, alignCenter);

        sheet.createRow(rowIndex + 16).createCell(colIndex + 6);
    }

    private CellStyle alignCenter() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private Font bold() {
        Font font = workbook.createFont();
        font.setBold(true);
        return font;
    }

    private void merge(CellRangeAddress cellAddress, String value, CellStyle style) {
        Cell cell = getFirstCell(cellAddress);
        cell.setCellValue(value);
        cell.setCellStyle(style);

        mergeCell(cellAddress);
    }

    private void merge(CellRangeAddress cellAddress, XSSFRichTextString value, CellStyle style) {
        Cell cell = getFirstCell(cellAddress);
        cell.setCellValue(value);
        cell.setCellStyle(style);

        mergeCell(cellAddress);
    }

    private Cell getFirstCell(CellRangeAddress cellAddress) {
        Row existRow = sheet.getRow(cellAddress.getFirstRow());
        if (existRow != null) {
            return existRow.createCell(cellAddress.getFirstColumn());
        }
        return sheet.createRow(cellAddress.getFirstRow()).createCell(cellAddress.getFirstColumn());
    }

    private void mergeCell(CellRangeAddress region) {
        sheet.addMergedRegion(region);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }
}
