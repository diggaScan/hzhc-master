package com.sunland.hzhc.map_config;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class QueryCommon {
    private static final int REQUEST_TIMEOUT = 20 * 1000;
    private static final int SO_TIMEOUT = 20 * 1000;
    private static final String METHOD_NAME = "unifiedCall";
    private static String SOAP_ACTION = METHOD_NAME;

//	public static String _doInBackground(String sendData) {
//		// TODO Auto-generated method stub
//		try {
//			SoapObject rpc = new SoapObject(Config.IP + ":" + Config.PortAr, METHOD_NAME);
//			rpc.addProperty("arg0", sendData);
//
//			HttpTransportSE he = new HttpTransportSE(
//					Config.IP + ":" + Config.PortAr + "/user_webservice/services/UserInterBean", 100000);
//			he.debug = true;
//
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//			envelope.encodingStyle = "UTF-8";
//			envelope.bodyOut = rpc;
//			envelope.setOutputSoapObject(rpc);
//			he.call(SOAP_ACTION, envelope);
//			String resp = envelope.getResponse().toString();
//			return resp;
//		} catch (Exception e) {
//			return "";
//		}
//	}

    public static boolean write2file(String path, String filename, String sb) {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        if (!dir.exists())
            return false;

        File file = new File(path + filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file, true);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

//	public static String _doInBackground(String sendData, int timeout) {
//		// TODO Auto-generated method stub
//		try {
//			SoapObject rpc = new SoapObject(Config.IP + ":" + Config.PortAr, METHOD_NAME);
//			rpc.addProperty("arg0", sendData);
//
//			HttpTransportSE he = new HttpTransportSE(
//					Config.IP + ":" + Config.PortAr + "/user_webservice/services/UserInterBean", timeout);
//			he.debug = true;
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//			envelope.encodingStyle = "UTF-8";
//			envelope.bodyOut = rpc;
//			envelope.setOutputSoapObject(rpc);
//
//			he.call(SOAP_ACTION, envelope);
//			return envelope.getResponse().toString();
//		} catch (Exception e) {
//			return "";
//		}
//	}

//	/**
//	 * 交警数据 请求方法 匡信提供接口
//	 */
//	public static String QueryDataPeopeleInfo(String url, String josnParam)
//			throws ClientProtocolException, IOException {
//
//		BasicHttpParams httpParams = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
//		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
//		HttpPost method = new HttpPost(url);
//		method.addHeader("ContentType", "multipart/form-data");
//		method.addHeader("Charset", "UTF-8");
//		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
//		httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
//		StringEntity entity = new StringEntity(josnParam, "UTF-8");// 解决中文乱码问题
//		entity.setContentEncoding("UTF-8");
//		entity.setContentType("application/json");
//		method.setEntity(entity);
//		HttpResponse result = httpClient.execute(method);
//
//		// 请求结束，返回结果
//		String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
//
//		return resData;
//
//	}

    /**
     * 交警数据 请求方法 匡信提供接口
     */
    // public static String QueryData(String url, String josnParam) throws
    // ClientProtocolException, IOException {
    //
    // BasicHttpParams httpParams = new BasicHttpParams();
    // HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
    // HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
    // HttpPost method = new HttpPost(url);
    // method.addHeader("ContentType", "multipart/form-data");
    // method.addHeader("Charset", "UTF-8");
    // DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
    // StringEntity entity = new StringEntity(josnParam, "UTF-8");// 解决中文乱码问题
    // entity.setContentEncoding("UTF-8");
    // entity.setContentType("application/json");
    // method.setEntity(entity);
    // HttpResponse result = httpClient.execute(method);
    //
    // // 请求结束，返回结果
    // String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
    //
    // return resData;
    //
    // }

//	// http 请求 json
//	public static String sendRequest(String url, LinkedList<BasicNameValuePair> params, String token) {
//		try {
//			HttpPost httpPost = new HttpPost(url);
//			if (null != params)
//				httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
//			httpPost.addHeader("ContentType", "multipart/form-data");
//			httpPost.addHeader("Charset", "UTF-8");
//			BasicHttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
//			HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
//			HttpClient httpClient = new DefaultHttpClient(httpParams);
//			HttpResponse response = httpClient.execute(httpPost);
//			response.getStatusLine().getStatusCode();
//			HttpEntity httpEntity = response.getEntity();
//			InputStream inputStream = httpEntity.getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//			StringBuffer result = new StringBuffer();
//			String line = "";
//			while (null != (line = reader.readLine())) {
//				result.append(line);
//			}
//			return result.toString();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return "";
//		}
//
//	}
    public static String doGET(String path) {
        String tex = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setRequestMethod("GET");
            Log.d(TAG, "doGET: "+conn.getResponseMessage()+conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                tex = gettextFromInputStream(is, null);

            } else {
                tex = "";
            }

        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
        return tex;
    }

    public static InputStream doGET(String path, String filename) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                return is;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
//
//	public static String sendGET(String url) throws Exception {
//		HttpGet httpGet = new HttpGet(url);
//		try {
//			BasicHttpParams httpParams = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParams, 5 * 1000);
//			HttpConnectionParams.setSoTimeout(httpParams, 5 * 1000);
//			HttpClient httpClient = new DefaultHttpClient(httpParams);
//			HttpResponse response = httpClient.execute(httpGet);
//			response.getStatusLine().getStatusCode();
//			HttpEntity httpEntity = response.getEntity();
//			InputStream inputStream = httpEntity.getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//			StringBuffer result = new StringBuffer();
//			String line = "";
//			while (null != (line = reader.readLine())) {
//				result.append(line);
//			}
//			return result.toString();
//		} catch (Exception e) {
//			throw e;
//		}
//	}

    public static String sendGET(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("tag", "sendGET: "+response.code());
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String gettextFromInputStream(InputStream is, String charset) {
        String text = null;

        if (charset == null) {
            charset = "utf-8";
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            baos.close();

            text = new String(baos.toByteArray(), charset);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

//	/**
//	 * 交警数据 请求方法 匡信提供接口
//	 */
//	public static String QueryData(String url, String josnParam) throws ClientProtocolException, IOException {
//
//		BasicHttpParams httpParams = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
//		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
//		HttpPost method = new HttpPost(url);
//		method.addHeader("ContentType", "multipart/form-data");
//		method.addHeader("Charset", "UTF-8");
//		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
//
//		StringEntity entity = new StringEntity(josnParam, "UTF-8");// 解决中文乱码问题
//		entity.setContentEncoding("UTF-8");
//		entity.setContentType("application/json");
//		method.setEntity(entity);
//		HttpResponse result = httpClient.execute(method);
//		// 请求结束，返回结果
//		String resData = EntityUtils.toString(result.getEntity(), "UTF-8");
//		return resData;
//
//	}

    // /**
    // * 匡信新接口 匡信增加了审计
    // */
    // public static String QueryData(String urlstring, String paramstring) {
    //
    // HttpURLConnection connection = null;
    // try {
    // URL url = new URL(urlstring);
    // connection = (HttpURLConnection) url.openConnection();
    // connection.setRequestMethod("POST");
    // connection.setRequestProperty("Content-Type", "application/json;
    // charset=utf-8");
    //
    // Content user = MobilePoliceApp.USER;
    // if (null == user || null == user.getLoginID() || null ==
    // user.getUserName() || null == user.getDeptID()
    // || user.getLoginID().equals("") || user.getUserName().equals("") |
    // user.getDeptID().equals("")) {
    // return null;
    // }
    //
    // connection.setRequestProperty("X-BUS-Context-HZGAUserKey",
    // "406ce5a7-0ef5-4027-8917-68a610234912");
    // connection.setRequestProperty("X-BUS-Context-HZGAUserCardId",
    // user.getLoginID());
    // connection.setRequestProperty("X-BUS-Context-HZGAUserName",
    // user.getUserName());
    // connection.setRequestProperty("X-BUS-Context-HZGAUserDept",
    // user.getDeptID());
    // connection.setRequestProperty("X-BUS-Context-HZGAUserCompanyName",
    // Config.CompanyName);
    // connection.setRequestProperty("X-BUS-Context-HZGAUserCompanyAddress",
    // Config.CompanyAddress);
    // connection.setRequestProperty("charset", "UTF-8");
    // connection.setUseCaches(false);
    // connection.setDoOutput(true);
    // connection.setConnectTimeout(1000);
    // // send request
    // DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
    // wr.writeBytes(paramstring);
    // wr.close();
    // // get response
    // InputStream is = connection.getInputStream();
    // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    // StringBuffer response = new StringBuffer();
    // String line;
    // while ((line = reader.readLine()) != null) {
    // response.append(line);
    // response.append("\t");
    // }
    // reader.close();
    // return response.toString();
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // return null;
    // }
    // }
    //
}
