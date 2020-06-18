package wipro.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MailUtil {

	public void sendEmail(String tomail, String subject,String message) {
		String url = "http://10.1.231.110:8080/rpaNew/Mail.jsp";
		//String url = "http://localhost:8080/rpa/Mail.jsp";
		try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
				String urlParameters = "tomail="+tomail+"&subject="+subject+"&message="+message;
				
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			
				int responseCode = con.getResponseCode();
				//System.out.println("\nSending 'POST' request to URL : " + url);
				//System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);
			
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
			
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	};
	
	public void sendEmailWithAtttachment(String tomail,String cc,String bcc,String subject,String message,String file) {
		String url = "http://10.1.231.110:8080/rpaNew/MailwithAttachment.jsp";
		//String url = "http://localhost:8080/rpa/MailwithAttachment.jsp";
		try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
				String urlParameters = "tomail="+tomail+"&cc="+cc+"&bcc="+bcc+"&subject="+subject+"&message="+message+"&file="+file;
				
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			
				int responseCode = con.getResponseCode();
				//System.out.println("\nSending 'POST' request to URL : " + url);
				//System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);
			
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
			
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
