package wipro.automation.utilities;


import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripperByArea;


public class PDFController {


	
/**
 * This method verifies if the given PDF file has expected text present in it
 * */
	public boolean isTextPresentInPDF(File pdfFile ,String expectedText)
	{
		try 
		{				
			String pdfText = getTextFromPDF(pdfFile);
			if(pdfText.contains(expectedText))
				return true;
			else
				return false;
		} 
		catch (Exception e)
		{
			throw new RuntimeException("Unable to verify the text in PDF");
		}
	}
	
	/**
	 * This method retrieves the URL/links from given pdf 
	 * */
	
	public List<String> getURLFromPDF(File file)
	{
		List<String> urlLists = new ArrayList<String>() ; 
		try{
			if(isFileFormatPDF(file))
				{
					PDDocument pdfDoc = PDDocument.load(file);
					int numberOfPages = pdfDoc.getNumberOfPages();
					int counter = 0;
					
					while (counter < numberOfPages)
						{
							PDPage page = pdfDoc.getPage(counter); 
							List<PDAnnotation> annotationList = page.getAnnotations();
							Iterator<PDAnnotation> annotationiterator = annotationList.iterator();
							
							while(annotationiterator.hasNext())
							{	
								PDAnnotation annotation = (PDAnnotation)annotationiterator.next();
								if(annotation instanceof PDAnnotationLink)
								{
									PDAnnotationLink link = (PDAnnotationLink)annotation;
									
									PDAction action = link.getAction();
									
									PDActionURI uri = (PDActionURI) action;
									
									urlLists.add(uri.getURI().toString());				
								}
							}
							counter++;
						}
				}
			
			else 
			{
				throw new RuntimeException("Given file format is not PDF.Required format is .pdf");
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to retrieve URL/Link from given PDF");
		}
		return urlLists;
	}
	
	
	/** This method will identify if the given file
	 *  is a PDF file format or not */
	
	private  boolean isFileFormatPDF(File file)
	{
		String fileExtension = FilenameUtils.getExtension(file.getAbsolutePath());
		
		if(fileExtension.equalsIgnoreCase("pdf"))
			return true;
		else 
			return false;
	}
	
	/** This method will retrieve text from the given PDF file*/
	
	public String getTextFromPDF(File pdfFile) {
		String pdfText = "";
		try {
			if (isFileFormatPDF(pdfFile)) {
				// Loading an existing PDF file
				PDDocument document = PDDocument.load(pdfFile);
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();

				stripper.setSortByPosition(true);
				stripper.addRegion("pdfdata", new Rectangle(0, 0, 2000, 2000));
				PDPage pdPage = document.getPage(0);
				stripper.extractRegions(pdPage);
				pdfText = stripper.getTextForRegion("pdfdata");
				return pdfText;

			} else {
				throw new RuntimeException("Given file format is not PDF.Required format is .pdf");
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to retrive text from the given PDF file");
		}
	}
	public static void main(String[] args) throws IOException {
		PDFController est = new PDFController();
		File test = new File("D:\\share\\UnpledgeProcessPart1\\test.pdf");
		
		String[] pdfData = est.getTextFromPDF(test).split("\n");
	
		for (String string : pdfData) {
			if(string.contains("High Risk Cat %")){
				String srtfall=string.split(" ")[0];
				   System.out.println("srtfall is"+srtfall);
				
				Pattern pattern = Pattern.compile("High Risk Cat %  (.*)");
				Matcher matcher = pattern.matcher(string);
				if (matcher.find())
				{
					String highRisk=(matcher.group(1));
				    System.out.println("highRisk is"+highRisk);
				}
			} 
			
			if(string.contains("Grand Total")){
				Pattern pattern = Pattern.compile("Grand Total  (.*?) ");
				Matcher matcher = pattern.matcher(string);
				if (matcher.find())
				{
				   
				    String granTotal=matcher.group(1);
				    System.out.println("GranTotal is"+granTotal);
				}
			}
		}
		
	}
}
