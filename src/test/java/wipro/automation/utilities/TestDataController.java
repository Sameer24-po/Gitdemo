package wipro.automation.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import wipro.utils.DBUtil;

/**
 * Class contains methods to controller test case data
 * @author Harshal.e
 *
 */
public class TestDataController {
		
	Hashtable<String, String> testData = null;
	public String baseDirPath = null;
	private DBUtil dbutil;
	
	public TestDataController() {
		try {
			baseDirPath = BasePath.basedirpath;//new java.io.File(".").getCanonicalPath();
			testData = new Hashtable<String, String>();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Generic method to get properties from specified file
	 * @param fileName - String type contains the property file name
	 * @return
	 */
	public Hashtable<String, String> getPropertiesFromFile(String fileName) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String strLine = null;
			String []prop = null;
			while((strLine = br.readLine()) != null) {
				if(strLine.contains("=")) {
					prop = strLine.split("=");
					  if(prop[1].contains("%3A"))
						  prop[1] = prop[1].replace("%3A", ":");
					testData.put(prop[0], prop[1]);
				}
			}
			br.close();
			fr.close();
			
		}catch(Exception exception) {
			System.out.println("Unable to get / read properties from property file: " + exception.getMessage());
		}
		return testData;
	}
	
	
	/**
	 * Method to get properties from test case data property file
	 * @param fileName
	 * @return
	 */
	public Hashtable<String, String> getTestData(String fileName) {
		//testData.putAll(getSMTConfigurationProperty());		
		if(fileName != null) {
			fileName = BasePath.basedirpath+"\\processData\\" + fileName + ".properties";
			testData.putAll(getPropertiesFromFile(fileName));
		}
		dbutil=new DBUtil();
		return testData;
	}


	/**
	 * Method to create new property file
	 * @param ht
	 * @param fileName
	 * @throws IOException
	 */
	public void createPropertyFile(Hashtable<String, String> ht,String fileName) throws IOException	{
		try {
	        Properties prop = new Properties();
	        Set<String> keys = ht.keySet();
	        for(String key: keys) {
	            prop.setProperty(key, ht.get(key));
	        }
        	FileOutputStream fileOutputStream = new FileOutputStream(baseDirPath + "\\proccessData\\" + fileName + ".properties");
            prop.store(fileOutputStream, null);
            fileOutputStream.close();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new IOException("Unable to create propertiy file [createPropertyFile]:" + fileNotFoundException.getMessage());
        }
	}
	
	/*public void updatePropertyFile(Hashtable<String, String> parametersToUpdate, String fileName) throws Exception{
		try {
			FileOutputStream outputStream = new FileOutputStream(baseDirPath+"\\"+fileName);
			FileInputStream inputStream = new FileInputStream(baseDirPath+"\\"+fileName);
			Properties props = new Properties();
			props.load(inputStream);
			inputStream.close();
			 Set<String> keys = parametersToUpdate.keySet();
		        for(String key: keys) {
		            props.setProperty(key, parametersToUpdate.get(key));
		        }
			props.store(outputStream, null);
			outputStream.close();
		} catch (FileNotFoundException e) {
			 throw new FileNotFoundException(fileName+" file Not Found [updatePropertyFile]:" + e.getMessage());
		} catch (IOException e) {
			 throw new IOException("Unable to read "+fileName+" file [updatePropertyFile]:" + e.getMessage());
		} catch (Exception e) {
			 throw new IOException("Unable to upadte "+fileName+" file [updatePropertyFile]:" + e.getMessage());
		}
		
	}*/
	
	/**
	 * Method to create new property file with suite execution status
	 * @param ht
	 * @param fileName
	 * @throws IOException
	 */
	public void generateExecutionStatus(Hashtable<String, String> parametersToSet,String fileName) throws Exception	{
		try {
			boolean flag =true;
			File f1 = new File(baseDirPath + "\\processData\\" + fileName + ".properties"); 
			if(f1.exists())
				flag =f1.delete();
			if (flag) {
				Properties prop = new Properties();
				Set<String> keys = parametersToSet.keySet();
				for (String key : keys) {
					prop.setProperty(key, parametersToSet.get(key));
				}
				FileOutputStream fileOutputStream = new FileOutputStream(baseDirPath + "\\" + fileName + ".properties");
				prop.store(fileOutputStream, null);
				fileOutputStream.close();
			}
        } catch (FileNotFoundException fileNotFoundException) {
            throw new IOException("Unable to create propertiy file [createPropertyFile]:" + fileNotFoundException.getMessage());
        }
	}
}