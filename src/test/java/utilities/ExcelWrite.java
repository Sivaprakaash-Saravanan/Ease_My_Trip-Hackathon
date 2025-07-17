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

/**
 * Utility class for writing data to Excel (.xlsx) files. Supports adding cell
 * values, coloring cells, and saving changes using Apache POI.
 */
public class ExcelWrite {
	private XSSFWorkbook workbook;
	private Sheet sheet;
	private String filePath;

	/**
	 * Constructor that loads an existing Excel file and accesses (or creates) a
	 * specific sheet.
	 *
	 * @param filePath  Path to the Excel file
	 * @param sheetName Sheet name to modify or create
	 * @throws IOException If the file can't be read or created
	 */
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

	/**
	 * Sets a string value in the specified cell.
	 *
	 * @param rownum  Row index (0-based)
	 * @param cellnum Column index (0-based)
	 * @param value   String value to set
	 */
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

	/**
	 * Colors a cell light green to indicate success or a valid value.
	 *
	 * @param rownum  Row index
	 * @param cellnum Column index
	 */
	public void fillCellGreen(int rownum, int cellnum) {
		fillCellColor(rownum, cellnum, IndexedColors.LIGHT_GREEN);
	}

	/**
	 * Colors a cell red to indicate failure or an invalid value.
	 *
	 * @param rownum  Row index
	 * @param cellnum Column index
	 */
	public void fillCellRed(int rownum, int cellnum) {
		fillCellColor(rownum, cellnum, IndexedColors.RED);
	}

	/**
	 * Internal method to fill a cell with a given color.
	 *
	 * @param rownum  Row index
	 * @param cellnum Column index
	 * @param color   Excel color from IndexedColors enum
	 */
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

	/**
	 * Saves changes to the Excel file and closes the workbook.
	 *
	 * @throws IOException If saving to the file fails
	 */
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
}
