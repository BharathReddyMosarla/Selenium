package Orangehrm;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelData 
{
	public static File f;
	public static FileInputStream fis;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	
	public static int TotalRows(String FileName, String SheetName) throws Exception
	{
		f = new File(FileName);
		fis=new FileInputStream(f);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(SheetName);
		int totalrows = sheet.getLastRowNum();
		return totalrows;
	}
	public static int TotalCells(String FileName, String SheetName, int RowNumber) throws Exception
	{
		f = new File(FileName);
		fis=new FileInputStream(f);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(SheetName);
		row = sheet.getRow(RowNumber);
		int totalcells = row.getLastCellNum();
		return totalcells;
	}
	
	public static String getCellData(String FileName, String SheetName, int RowNumber, int CellNumber) throws Exception
	{
		f = new File(FileName);
		fis=new FileInputStream(f);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(SheetName);
		row = sheet.getRow(RowNumber);
		cell = row.getCell(CellNumber);
		String data = null ;
		
		if(cell.getCellType()==cell.CELL_TYPE_STRING)
		{
			data = cell.getStringCellValue();
		}else if(cell.getCellType()==cell.CELL_TYPE_NUMERIC)
		{
			double value = cell.getNumericCellValue();
			data = String.valueOf(value);
		}
		
		return data;
	}
	
	
	public static Object[][] getData(String FileName, String SheetName) throws Exception
	{
		f = new File(FileName);
		fis=new FileInputStream(f);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(SheetName);
		
		int totalrows  = TotalRows(FileName,SheetName);
		int totalcells = TotalCells(FileName,SheetName,1);
		
		Object[][] data=new Object[totalrows][totalcells];
		
		for(int i=1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcells;j++)
			{
				data[i-1][j] =getCellData(FileName,SheetName,i,j);
			}
		}
		return data;
	}

		

}
