package com.app.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class QuickArticleClientJdk {

	public void readArticle() {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL restAPIUrl = new URL("http://localhost:8080/v1/Articles/1");
			connection = (HttpURLConnection) restAPIUrl.openConnection();
			connection.setRequestMethod("GET");
			
			// Read the response
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder jsonData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}
			
			System.out.println(jsonData.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// Clean up 
			IOUtils.closeQuietly(reader);
			if(connection != null)
				connection.disconnect();
		}
	}

	public static void main(String[] args) throws Exception {
		QuickArticleClientJdk client = new QuickArticleClientJdk();
		client.readArticle();
	}
	
}
