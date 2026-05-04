package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private final Workbook workbook;

    // Constructor: load once
    public ExcelReader(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + path, e);
        }
    }

    // Get total rows (including header)
    public int getRowCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        return sheet.getLastRowNum() + 1;
    }

    // Get total columns (based on header row)
    public int getColumnCount(String sheetName) {
        Sheet sheet = getSheet(sheetName);
        Row header = sheet.getRow(0);
        return (header == null) ? 0 : header.getLastCellNum();
    }

    // Get cell data using column index
    public String getCellData(String sheetName, int colNum, int rowNum) {
        Sheet sheet = getSheet(sheetName);

        Row row = sheet.getRow(rowNum - 1);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        return formatCellValue(cell);
    }

    // Get cell data using column name
    public String getCellData(String sheetName, String colName, int rowNum) {
        Sheet sheet = getSheet(sheetName);
        Row header = sheet.getRow(0);

        if (header == null) return "";

        int colIndex = -1;
        for (int i = 0; i < header.getLastCellNum(); i++) {
            if (header.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
                colIndex = i;
                break;
            }
        }

        if (colIndex == -1) return "";

        return getCellData(sheetName, colIndex, rowNum);
    }

    // Helper: safely get sheet
    private Sheet getSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
        return sheet;
    }

    // Helper: handle all cell types cleanly
    private String formatCellValue(Cell cell) {
        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                }
                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }

}