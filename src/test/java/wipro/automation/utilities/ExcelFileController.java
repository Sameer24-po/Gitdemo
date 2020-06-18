package wipro.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileController {
	

	public static XSSFSheet readXlsxFile(String fileName) throws Exception {
		try {
			XSSFWorkbook wb = ExcelFileController.readFile(fileName + ".xlsx");
			return wb.getSheetAt(0);
			//return wb.get("sdsd");

		} catch (Exception ex) {
			throw new Exception("Unable to read excel file: "+ fileName);
		}
	}
			
	/**
	 * creates an {@link HSSFWorkbook} with the specified OS filename.
	 * @throws Exception 
	 */
	private static XSSFWorkbook readFile(String filename) throws Exception {
	    FileInputStream fis = new FileInputStream(filename);
	    try {
	        return new XSSFWorkbook(fis);
	    } catch (Throwable ex){
	    	throw new Exception("Unable to read excel file");
	    }
	    finally {
	        fis.close();
	    }
	}

	public static void createNewXlsxFile(String fileName, XSSFSheet sheet) throws Exception {
		try {
			String filePath =fileName+".xlsx";
			FileOutputStream fileOut = new FileOutputStream(filePath);
			XSSFWorkbook wb = sheet.getWorkbook();
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception exception){
			throw new Exception("Unable to create result file "+exception.getMessage());
		}
	}
	
	/** This method will identify if the given file
	 *  is a PDF file format or not */
	
	public static boolean isFileFormatXslx(File file)
	{
		String fileExtension = FilenameUtils.getExtension(file.getAbsolutePath());
		if(fileExtension.trim().equalsIgnoreCase("xlsx"))
			return true;
		else 
			return false;
	}
	
	public static HSSFSheet readXlsFile(String filePath) throws Exception{
		try{
			System.out.println(filePath);
			 FileInputStream fis = new FileInputStream(filePath);
			 HSSFWorkbook eb = null;
			    try {
			       eb =  new HSSFWorkbook(fis);
			       HSSFSheet sheet = eb.getSheetAt(0);
			       return sheet;
			    } catch (Throwable ex){
			    	throw new Exception("Unable to read excel file");
			    }
			    finally {
			        fis.close();
			        eb.close();
			    }
		}catch(Exception exception){
			throw new Exception("Unable to read file ::" +filePath);
		}
	}
	
	
	public static XSSFSheet mergeExcelSheets(XSSFSheet srcSheet,XSSFSheet destSheet) throws Exception{
		try{
			int totalRowcount = destSheet.getPhysicalNumberOfRows();
			
			for(int rowNum = 1 ; rowNum < srcSheet.getPhysicalNumberOfRows(); rowNum++){
				
				XSSFRow newRow = destSheet.createRow(totalRowcount + rowNum);
				for (int cellNum = 0; cellNum < srcSheet.getRow(rowNum).getPhysicalNumberOfCells(); cellNum++) {
					switch (srcSheet.getRow(rowNum).getCell(cellNum).getCellTypeEnum()) {
					case NUMERIC:
						newRow.createCell(cellNum).setCellValue(srcSheet.getRow(rowNum).getCell(cellNum).getNumericCellValue());
						break;
					case BOOLEAN:
						newRow.createCell(cellNum).setCellValue(srcSheet.getRow(rowNum).getCell(cellNum).getBooleanCellValue());
						break;
					case STRING:
						newRow.createCell(cellNum).setCellValue(srcSheet.getRow(rowNum).getCell(cellNum).getStringCellValue());
						break;
					default:
						newRow.createCell(cellNum).setCellValue(srcSheet.getRow(rowNum).getCell(cellNum).getStringCellValue());
						break;
					}
				}
			}
			return destSheet;
			
		}catch(Exception ex){
			throw new Exception("Unable to create master sheet "+ex.getMessage());
		}
	}
	
	public static XSSFSheet addRowInSheet(XSSFSheet srcSheet,XSSFRow rowTobeAdded) throws Exception{
		try{
			int totalRowcount;
			if(srcSheet != null){
				totalRowcount = srcSheet.getPhysicalNumberOfRows();
			} else {
				totalRowcount = 0;
			}
			XSSFRow newRow = srcSheet.createRow(totalRowcount + 1);
			for(int cellNum = 0 ; cellNum < rowTobeAdded.getPhysicalNumberOfCells(); cellNum++){
				
					switch (rowTobeAdded.getCell(cellNum).getCellTypeEnum()) {
					case NUMERIC:
						newRow.createCell(cellNum).setCellValue(rowTobeAdded.getCell(cellNum).getNumericCellValue());
						break;
					case BOOLEAN:
						newRow.createCell(cellNum).setCellValue(rowTobeAdded.getCell(cellNum).getBooleanCellValue());
						break;
					case STRING:
						newRow.createCell(cellNum).setCellValue(rowTobeAdded.getCell(cellNum).getStringCellValue());
						break;
					default:
						newRow.createCell(cellNum).setCellValue(rowTobeAdded.getCell(cellNum).getStringCellValue());
						break;
					}
			}
			return srcSheet;
			
		}catch(Exception ex){
			throw new Exception("Unable to add duplicate record in sheet "+ex.getMessage());
		}
	}
	
	
	public static int getColumnNumber(XSSFSheet sheet,String columnName) throws Exception {
		try {
			
			int totalRowCount = sheet.getPhysicalNumberOfRows();
			for (int currentRowNum = 0; currentRowNum < totalRowCount; currentRowNum++) {
				XSSFRow currentRow = sheet.getRow(currentRowNum);
				if (currentRow == null) continue;

				// Get Column cell number of column
				for (int cellNum = 0; cellNum < currentRow.getLastCellNum(); cellNum++){
					XSSFCell currentColumn = currentRow.getCell(cellNum); 
					if(currentColumn.getStringCellValue().toString().equalsIgnoreCase(columnName)){
						return cellNum;
					}
				}
			}
			
		}catch(Exception exception){
			throw new Exception("Failed get Column number" + exception.getMessage());
		}
		return 0;
	}
}
