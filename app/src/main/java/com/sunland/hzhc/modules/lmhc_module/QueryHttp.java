package com.sunland.hzhc.modules.lmhc_module;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/***
 * 请求类
 * @author NXP
 *
 */
public class QueryHttp {
	private static final int REQUEST_TIMEOUT = 20 * 1000;
	private static final int SO_TIMEOUT = 20 * 1000;

	public static String post(String url, MyTaskParams params) throws Exception {
		return sendRequest(url, params);
	}

	public static Bitmap download(String url, MyTaskParams params)
			throws Exception {
		return downloadRequest(url, params);
	}

	private static String sendRequest(String url, MyTaskParams params)
			throws Exception {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (params.getHeadParams().size() != 0) {
				httpPost.setHeaders(params.getHeadParams().toArray(
						new Header[0]));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params
					.getEntityParams(), "utf-8"));
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse response = httpClient.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode >= 300) {
				throw new HttpResponseException(statusCode,
						statusLine.getReasonPhrase());
			}

			HttpEntity httpEntity = response.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuffer result = new StringBuffer();
			String line = "";
			while (null != (line = reader.readLine())) {
				result.append(line);
			}

			return result.toString();
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	private static Bitmap downloadRequest(String url, MyTaskParams params
			) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		Bitmap mBitmap=null;
		try {
			if (params.getHeadParams().size() != 0) {
				httpPost.setHeaders(params.getHeadParams().toArray(
						new Header[0]));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(params
					.getEntityParams(), "utf-8"));
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					REQUEST_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);

			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse response = httpClient.execute(httpPost);

			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
						
			if (statusCode == 200) {
				InputStream instream = response.getEntity().getContent();				
				mBitmap = BitmapFactory.decodeStream(instream); 
			}
								
			return mBitmap;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}

