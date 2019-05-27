package com.project2.stockConsumerResources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

/*
 * taken from
 * 
 * https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 * 
 * these are all the helper methods
 * 
 * */
 
public class Stock {
	private final static String USER_AGENT = "Mozilla/5.0";

	// HTTP GET request
		public static String sendGet(String ticker) throws Exception {

			String url = ticker;
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			return response.toString();

		}
		
		// HTTP POST request
		public void sendPost(String ticker) throws Exception {

			String url = "https://financialmodelingprep.com/api/company/price/"
					+ ticker
					+ "?datatype=json";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println(response.toString());

		}
		
		public static String Evaluate(double check)
		{
			
			if(check >= 0 && check <= .05)
			{
				return "an okay day";
			}
			else if (check <= 0)
			{
				return "not a good day";
				
			}
			else if (check >= .05 && check <= .10)
			{
				return "a great day";
				
			}
			else if (check > .10 && check <= .15)
			{
				return "a fantastic day";
				
			}
			else if (check > .15)
			{
				return "a crazy day, consider donating to my venmo @nomoodfunds";
				
			}
			
			return "a day";
		}
}
