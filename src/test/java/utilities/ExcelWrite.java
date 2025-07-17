package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {
	private XSSFWorkbook workbook;
	private Sheet sheet;
	private String filePath;

	public ExcelWrite(String filePath, String sheetName) throws IOException {
		this.filePath = filePath;
		FileInputStream fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}
		fis.close();
	}

	public void setCellValue(int rownum, int cellnum, String value) {
		Row row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(cellnum);
		if (cell == null) {
			cell = row.createCell(cellnum);
		}
		cell.setCellValue(value);
	}

	public void fillCellGreen(int rownum, int cellnum) {
		fillCellColor(rownum, cellnum, IndexedColors.LIGHT_GREEN);
	}

	public void fillCellRed(int rownum, int cellnum) {
		fillCellColor(rownum, cellnum, IndexedColors.RED);
	}

	private void fillCellColor(int rownum, int cellnum, IndexedColors color) {
		Row row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		Cell cell = row.getCell(cellnum);
		if (cell == null) {
			cell = row.createCell(cellnum);
		}

		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
	}

	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
}
