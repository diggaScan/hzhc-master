package com.sunland.hzhc.modules.lmhc_module;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

/***
 * 请求参数
 * @author NXP
 *
 */
public class MyTaskParams {
	private List<NameValuePair> entityParams = null;
	private List<Header> headParams = null;

	public MyTaskParams() {
		entityParams = new ArrayList<NameValuePair>();
		headParams = new ArrayList<Header>();
	}

	public void putEntity(String key, String value) {
		entityParams.add(new BasicNameValuePair(key, value));
	}

	public void putEntity(List<NameValuePair> values) {
		entityParams.addAll(values);
	}

	public List<NameValuePair> getEntityParams() {
		return entityParams;
	}

	public void putHead(String key, String value) {
		headParams.add(new BasicHeader(key, value));
	}

	public void putHead(List<BasicHeader> values) {
		headParams.addAll(values);
	}

	public List<Header> getHeadParams() {
		return headParams;
	}
}
