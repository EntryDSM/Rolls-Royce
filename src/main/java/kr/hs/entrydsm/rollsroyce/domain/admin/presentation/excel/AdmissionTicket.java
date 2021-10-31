package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

@Component
public class AdmissionTicket {

    private final HSSFWorkbook workbook = new HSSFWorkbook();
    private final HSSFSheet sheet = workbook.createSheet("수험표");

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    public void format(int rowIndex, int colIndex, String examCode, String name, String middleSchool, String area, String applicationType, String receiptCode) {
        sheet.setDefaultColumnWidth(14);
        CellStyle alignCenter = alignCenter();
        Font bold = bold();

        HSSFRichTextString title = new HSSFRichTextString("2022학년도 대덕소프트웨어마이스터고등학교\n입학전형 수험표");
        title.applyFont(24, 32, bold);
        CellRangeAddress titleAddress = new CellRangeAddress(rowIndex, rowIndex + 1, colIndex,  colIndex + 5);
        merge(titleAddress, title, alignCenter);
        mergeCell(new CellRangeAddress(rowIndex + 2, rowIndex + 13, colIndex, colIndex + 1));
        String[] attributes = {"수험번호", "성명", "출신 중학교", "지역", "전형 유형", "접수 번호"};
        String[] studentInfo = {examCode, name, middleSchool, area, applicationType, receiptCode};
        for (int i = rowIndex + 1 ; i <= rowIndex + attributes.length * 2 ; i += 2) {
            merge(new CellRangeAddress(i + 1, i + 2, colIndex + 2, colIndex + 3),
                    attributes[(i - rowIndex) / 2],
                    alignCenter);
            merge(new CellRangeAddress(i + 1, i + 2, colIndex + 4, colIndex + 5),
                    studentInfo[(i - rowIndex) / 2],
                    alignCenter);
        }
        HSSFRichTextString bottom = new HSSFRichTextString("대덕소프트웨어마이스터고등학교장");
        bottom.applyFont(bold);
        merge(new CellRangeAddress(rowIndex + 14, rowIndex + 15, colIndex, colIndex + 5), bottom, alignCenter);

        sheet.createRow(rowIndex + 16).createCell(rowIndex + 6);
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

    private void merge(CellRangeAddress cellAddress, HSSFRichTextString value, CellStyle style) {
        Cell cell = getFirstCell(cellAddress);
        cell.setCellValue(value);
        cell.setCellStyle(style);

        mergeCell(cellAddress);
    }

    private Cell getFirstCell(CellRangeAddress cellAddress) {
        Row existRow = sheet.getRow(cellAddress.getFirstRow());
        if ( existRow != null) { return existRow.createCell(cellAddress.getFirstColumn()); }
        return sheet.createRow(cellAddress.getFirstRow()).createCell(cellAddress.getFirstColumn());
    }

    private void mergeCell(CellRangeAddress cellAddress) {
        sheet.addMergedRegion(cellAddress);
        HSSFRegionUtil.setBorderTop(BorderStyle.THIN.getCode(), cellAddress, sheet, workbook);
        HSSFRegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), cellAddress, sheet, workbook);
        HSSFRegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), cellAddress, sheet, workbook);
        HSSFRegionUtil.setBorderRight(BorderStyle.THIN.getCode(), cellAddress, sheet, workbook);
    }

}
