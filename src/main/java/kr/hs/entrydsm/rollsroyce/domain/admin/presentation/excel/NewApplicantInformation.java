package kr.hs.entrydsm.rollsroyce.domain.admin.presentation.excel;

import static org.apache.poi.ss.util.RegionUtil.setBorderBottom;
import static org.apache.poi.ss.util.RegionUtil.setBorderLeft;
import static org.apache.poi.ss.util.RegionUtil.setBorderRight;
import static org.apache.poi.ss.util.RegionUtil.setBorderTop;

import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class NewApplicantInformation {

	private final Workbook workbook = new XSSFWorkbook();
	private final Sheet sheet = workbook.createSheet("지원자 목록");

	private final short uselessRowSize = 150;
	private final short uselessColumnSize = 250;

	public Workbook getWorkbook() {
		return workbook;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void format(int DH) { //DH = defaultHigh
		//가운데 정렬
//		CellStyle cellStyle = workbook.createCellStyle();
//		cellStyle.setAlignment(HorizontalAlignment.CENTER);
//		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		for (int i = DH ; i < DH+20 ; i++) {
//			Row row = getRow(i);
//			for (int j = 0 ; j < 10 ; j++) {
//				Cell cell = getCell(row, j);
//				cell.setCellStyle(cellStyle);
//			}
//		}

		//안쓰는 row 크기
		List<Integer> uselessRow = List.of(DH, DH+2, DH+6, DH+16);
		uselessRow.forEach(
			size -> getRow(size).setHeight(uselessRowSize)
		);

		//안쓰는 column 크기
		sheet.setColumnWidth(0, uselessColumnSize);
		sheet.setColumnWidth(8, uselessColumnSize);

		//테두리 설정
		Integer[][] allThin =  new Integer[][]{
			{DH+1, DH+1, 1, 7},
			{DH+3, DH+5, 1, 3}, {DH+3, DH+5, 5, 7}, {DH+3, DH+5, 5, 5},
			{DH+7, DH+15, 1, 7}, {DH+7, DH+15, 2, 7}, {DH+8, DH+14, 1, 7},
			{DH+17, DH+18, 1, 7}, {DH+17, DH+18, 1, 2}
		};
		setBorderStyle(allThin, BorderStyle.THIN, Direction.ALL);

		Integer[][] allThick = new Integer[][]{
			{DH+1, DH+1, 2, 2},
			{DH+3, DH+3, 2, 3},
			{DH+15, DH+15, 6, 7}
		};
		setBorderStyle(allThick, BorderStyle.THICK, Direction.ALL);

		Integer[][] bottomDashed = new Integer[][]{
			{DH+3, DH+3, 1, 1}, {DH+4, DH+4, 1, 3},
			{DH+3, DH+3, 5, 7}, {DH+4, DH+4, 5, 7},
			{DH+8, DH+8, 1, 7}, {DH+9, DH+9, 1, 7}, {DH+10, DH+10, 1, 7},
			{DH+11, DH+11, 1, 7}, {DH+12, DH+12, 1, 7}, {DH+13, DH+13, 1, 7},
			{DH+17, DH+17, 1, 7}
		};
		setBorderStyle(bottomDashed, BorderStyle.DASHED, Direction.BOTTOM);

		Integer[][]  rightDashed = new Integer[][]{
			{DH+1, DH+1, 5, 5}, {DH+1, DH+1, 6, 6},
			{DH+3, DH+5, 1, 1}, {DH+3, DH+3, 6, 6},
			{DH+7, DH+14, 2, 2}, {DH+7, DH+15, 3, 3},
			{DH+7, DH+14, 4, 4}, {DH+7, DH+14, 5, 5}, {DH+7, DH+14, 6, 6},
			{DH+17, DH+18, 1, 1}, {DH+17, DH+18, 3, 3}, {DH+17, DH+18, 4, 4},
			{DH+17, DH+18, 5, 5}, {DH+17, DH+18, 6, 6},
		};
		setBorderStyle(rightDashed, BorderStyle.DASHED, Direction.RIGHT);

		//형식에 맞춰 병합
		sheet.addMergedRegion(new CellRangeAddress(DH+1, DH+1, 3, 5));

		sheet.addMergedRegion(new CellRangeAddress(DH+3, DH+3, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(DH+4, DH+4, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(DH+5, DH+5, 2, 3));

		sheet.addMergedRegion(new CellRangeAddress(DH+4, DH+4, 6, 7));
		sheet.addMergedRegion(new CellRangeAddress(DH+5, DH+5, 6, 7));

		sheet.addMergedRegion(new CellRangeAddress(DH+15, DH+15, 2, 3));

		//기본값
		getCell(DH+1, 1).setCellValue("접수번호");
		getCell(DH+3, 5).setCellValue("학년반");
		getCell(DH+3, 7).setCellValue("반");
		getCell(DH+4, 5).setCellValue("학생");
		getCell(DH+5, 5).setCellValue("보호자");
		getCell(DH+7, 1).setCellValue("과목");
		getCell(DH+7, 2).setCellValue("3_2학기");
		getCell(DH+7, 3).setCellValue("3_1학기");
		getCell(DH+7,4).setCellValue("직전");
		getCell(DH+7, 5).setCellValue("직전전");
		getCell(DH+8, 1).setCellValue("국어");
		getCell(DH+9, 1).setCellValue("사회");
		getCell(DH+10, 1).setCellValue("역사");
		getCell(DH+11, 1).setCellValue("수학");
		getCell(DH+12, 1).setCellValue("과학");
		getCell(DH+13, 1).setCellValue("기술가정");
		getCell(DH+14, 1).setCellValue("영어");
		getCell(DH+15, 1).setCellValue("점수");
		getCell(DH+15, 6).setCellValue("성적환산점수");
		getCell(DH+17, 1).setCellValue("봉사시간");
		getCell(DH+17, 2).setCellValue("봉사점수");
		getCell(DH+17, 3).setCellValue("결석");
		getCell(DH+17, 4).setCellValue("지각");
		getCell(DH+17, 5).setCellValue("조퇴");
		getCell(DH+17, 6).setCellValue("결과");
		getCell(DH+17, 7).setCellValue("출석점수");
		getCell(DH+19, 6).setCellValue("총점");
	}

	private void setBorderStyle(Integer[][] regions, BorderStyle borderStyle, Direction direction) {
		for (Integer[] region : regions) {

			CellRangeAddress address = new CellRangeAddress(region[0], region[1],
				region[2], region[3]);

			switch (direction) {
				case TOP: {
					setBorderTop(borderStyle, address, sheet);
					break;
				}
				case BOTTOM: {
					setBorderBottom(borderStyle, address, sheet);
					break;
				}
				case LEFT: {
					setBorderLeft(borderStyle, address, sheet);
					break;
				}
				case RIGHT: {
					setBorderRight(borderStyle, address, sheet);
					break;
				}
				case ALL: {
					setBorderTop(borderStyle, address, sheet);
					setBorderBottom(borderStyle, address, sheet);
					setBorderLeft(borderStyle, address, sheet);
					setBorderRight(borderStyle, address, sheet);
				}
			}
		}
	}

	private Cell getCell(Row row, int cellNum) {
		Cell cell = row.getCell(cellNum);
		if (cell == null) {
			cell = row.createCell(cellNum);
		}
		return cell;
	}

	public Cell getCell(int rowNum, int cellNum) {
		Row row = getRow(rowNum);
		return getCell(row, cellNum);
	}

	private Row getRow(int rowNum) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		return row;
	}
}

enum Direction {
	TOP, BOTTOM, LEFT, RIGHT, ALL
}
