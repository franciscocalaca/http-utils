package com.franciscocalaca.http.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;



public class UtilHttp {

	static {
		Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", easyhttps);
	}

	public static String getAuthHeaderBase64(String clientId, String clientSecret) {
		String auth = clientId + ":" + clientSecret;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeader = "Basic " + new String(encodedAuth);
		return authHeader;
	}
	
	public static RespHttp sendPut(String url, Map<String, String> headers, String contentType, String body, String charset) {
		try {
			StringRequestEntity stringEntity = new StringRequestEntity(body, contentType, charset);

			PutMethod httpPost = new PutMethod(url);
			try {
				httpPost.setRequestEntity(stringEntity);

				for(Entry<String, String> entry: headers.entrySet()) {
					httpPost.addRequestHeader(entry.getKey(), entry.getValue());
				}
				HttpClient httpClient = new HttpClient();
				int result = httpClient.executeMethod(httpPost);

				RespHttp resp = new RespHttp(result, httpPost);
				resp.updateContentAsString();
				return resp;
			} finally {
				httpPost.releaseConnection();
			}

		} catch (IOException e) {
			return new RespHttp(e);
		}
	}

	public static RespHttp sendPost(String url, Map<String, String> headers, Map<String, String> parameters, String contentType, String body, String charset) {
		try {
			StringRequestEntity stringEntity = new StringRequestEntity(body, contentType, charset);

			PostMethod httpPost = new PostMethod(url);
			try {

				httpPost.setRequestEntity(stringEntity);

				for(Entry<String, String> entry: parameters.entrySet()) {
					httpPost.addParameter(entry.getKey(), entry.getValue());
				}

				for(Entry<String, String> entry: headers.entrySet()) {
					httpPost.addRequestHeader(entry.getKey(), entry.getValue());
				}
				HttpClient httpClient = new HttpClient();
				int result = httpClient.executeMethod(httpPost);

				RespHttp resp = new RespHttp(result, httpPost);
				resp.updateContentAsString();
				return resp;
			}finally {
				httpPost.releaseConnection();
			}


		} catch (Exception e) {
			return new RespHttp(e);
		}
	}

	public static Token oauth(String url, String username, String password, Map<String, String> headers, Map<String, String> parameters) {
		try {
			return new Token(url, username, password, headers, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static RespHttp sendGet(String url, Map<String, String> headers, String charset) {
		try {
			GetMethod httpGet = new GetMethod(url);

			for(Entry<String, String> entry: headers.entrySet()) {
				httpGet.addRequestHeader(entry.getKey(), entry.getValue());
			}
			HttpClient httpClient = new HttpClient();
			try {
				int result = httpClient.executeMethod(httpGet);

				RespHttp resp = new RespHttp(result, httpGet);
				resp.updateContentAsString();
				return resp;
			} finally {
				httpGet.releaseConnection();
			}
		} catch (Exception e) {
			return new RespHttp(e);
		}
	}

}
