package wipro.utils;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class JenkinsUtil {

	public void sendPostRequest(String target_url) throws Exception {
		try {
			 URI uri = URI.create(target_url);
		        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		        CredentialsProvider credsProvider = new BasicCredentialsProvider();
		        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials("agilemate", "agilemate"));
		        // Create AuthCache instance
		        AuthCache authCache = new BasicAuthCache();
		        // Generate BASIC scheme object and add it to the local auth cache
		        BasicScheme basicAuth = new BasicScheme();
		        authCache.put(host, basicAuth);
		        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		        HttpPost httpPost = new HttpPost(uri);
		        // Add AuthCache to the execution context
		        HttpClientContext localContext = HttpClientContext.create();
		        localContext.setAuthCache(authCache);
		 
		        HttpResponse response = httpClient.execute(host, httpPost, localContext);
		 
			System.out.println("response================"+response.getEntity());
		} catch (Exception ex) {
			throw new Exception("Unable to process post request: " + ex.getMessage() + " : url = " + target_url);
		} finally {
			// System.out.println("test");
		}
	}
	
	public String getCurrentTime() throws Exception {
		String time = null;
		try {
			Date startDate = new Date();  		  
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			time = df.format(startDate);
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]:" + exception.getMessage());
		}
		return time;
	}
	
	public String getTimeDifference(String startDateTime, String endDateTime) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date endDate = null;
		Date startDate = null;
		Calendar endCalender = null;
		Calendar startCalender = null;
		String totalExecutionTime = "";
		long diff, diffSeconds, diffMinutes, diffHours, remainder = 0;
		try {
			startDate = df.parse(startDateTime);
			endDate = df.parse(endDateTime);

			startCalender = Calendar.getInstance();
			endCalender = Calendar.getInstance();

			startCalender.setTime(startDate);
			endCalender.setTime(endDate);

			diff = endCalender.getTimeInMillis() - startCalender.getTimeInMillis();

			// Get Hours
			diffHours = diff / (60 * 60 * 1000);

			// Get Minutes
			remainder = diff % (60 * 60 * 1000);
			diffMinutes = remainder / (60*1000);

			// Get Seconds
			remainder = diff % (60*1000);
			diffSeconds = remainder / 1000;

			if(diffHours == 0)
				totalExecutionTime = "00:" + diffMinutes + ":" + diffSeconds;
			else if(diffMinutes == 0)
				totalExecutionTime = diffHours + ":00:" + diffSeconds;
			else if(diffMinutes == 0)
				totalExecutionTime = diffHours + ":" + diffMinutes + ":00";
			else
				totalExecutionTime = diffHours + ":" + diffMinutes + ":" + diffSeconds;
			return totalExecutionTime;
		}catch(Exception exception) {
			throw new Exception("Unable to get current time [getCurrentTime()]"+exception.getMessage());
		}
	}

}
