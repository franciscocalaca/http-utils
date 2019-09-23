package com.franciscocalaca.http.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franciscocalaca.http.utils.RespHttp;

public class UtilManager {

	public Map<String, Object> createUser(String urlAuthManager, String userStr, String passStr, User user) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String body = mapper.writeValueAsString(user);
			
			String auth = userStr + ":" + passStr;
			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
			String authHeader = "Basic " + new String(encodedAuth);
			PostMethod httpPost = new PostMethod(urlAuthManager);
			StringRequestEntity stringEntity = new StringRequestEntity(body, "application/json", "utf-8");
			httpPost.setRequestEntity(stringEntity);
			httpPost.addRequestHeader("Authorization", authHeader);

			HttpClient httpClient = new HttpClient();
			int result = httpClient.executeMethod(httpPost);
			RespHttp resp = new RespHttp(result, httpPost);
			return resp.getContentAsMap();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
}
