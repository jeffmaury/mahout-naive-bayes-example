/**
 * 
 */
package com.chimpler.example.bayes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Syspertec Java Dev Team
 *
 */
public class TestPOI {

  /**
   * 
   */
  public TestPOI() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(args[0]);
    XSSFWorkbook wk = new XSSFWorkbook(is);
    Sheet sheet = wk.getSheetAt(0);
    System.out.println("First row:" + sheet.getFirstRowNum());
    System.out.println("Last  row:" + sheet.getLastRowNum());
    for(int i=sheet.getFirstRowNum();i < sheet.getLastRowNum();++i) {
      Row row = sheet.getRow(i);
      for(int j=row.getFirstCellNum();j<row.getLastCellNum();++j) {
        Cell cell = row.getCell(j);
        if (cell != null) {
          System.out.println("(" + i + "," + j + "," + cell.getCellType()
              + ")=" + cell);
        }
      }
    }
    
  }

}
