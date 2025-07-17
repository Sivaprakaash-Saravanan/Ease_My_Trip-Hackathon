package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {
	public static FileInputStream file;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	public static Row rowObj;
	public static Sheet sheet;

	public static Map<String, Map<String, String>> getData(String filePath, String sheetName) throws IOException {
		Map<String, Map<String, String>> dataMap = new HashMap<>();
		FileInputStream file = new FileInputStream(filePath);
		wb = new XSSFWorkbook(file);
		ws = wb.getSheet(sheetName);
		XSSFRow headerRow = ws.getRow(0);
		DataFormatter formatter = new DataFormatter();
		for (int i = 1; i <= ws.getLastRowNum(); i++) {
			XSSFRow rData = ws.getRow(i);
			if (rData == null || rData.getCell(0) == null || rData.getCell(0).getCellType() == CellType.BLANK) {
				continue; // Skip empty rows or if key cell is blank
			}
			Map<String, String> rowData = new HashMap<>();
//            String key = rData.getCell(0).getStringCellValue();
			String key = formatter.formatCellValue(rData.getCell(0)).trim();

			for (int j = 1; j < rData.getLastCellNum(); j++) {
//            	String header = headerRow.getCell(j).getStringCellValue();
				String header = formatter.formatCellValue(headerRow.getCell(j)).trim();
				String value = "";

				try {
					XSSFCell cell = rData.getCell(j);
					if (cell != null && cell.getCellType() != CellType.BLANK) {
						value = formatter.formatCellValue(cell).trim();
					}
				} catch (Exception e) {
					value = ""; // fallback if something goes wrong
				}
				rowData.put(header, value);

			}
			dataMap.put(key, rowData);
		}
		wb.close();
		return dataMap;
	}
}
