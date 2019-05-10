package sample.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.model.DataContainer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExcelParser {
    public static void parse (File file, DataContainer dataContainer) {

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            List<String> person = new ArrayList<>();
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        person.add(cell.getStringCellValue());
                        System.out.println(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        person.add(String.valueOf(cell.getNumericCellValue()));
                        System.out.println(cell.getNumericCellValue());
                        break;
                    default:
                        break;
                }
            }
            dataContainer.addPerson(person);
        }
    }
}
