package org.xulingyun.CustomerServices;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.xulingyun.util.UseLog4j;

import com.alibaba.fastjson.JSON;

public class CustomerServices {

	final static String synchronizedprop = "synchronized";
	ServletContext servletContext;
	String access_token;
	String appId;
	String appSecret;
	String access_tokenUrl;
	String cs;
	long expires_in;

	public CustomerServices(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public synchronized void readPropAndSendCSMessage(String touser,String msgtype, String text) {
		synchronized (synchronizedprop) {
			String time = "";
			InputStream in = servletContext.getResourceAsStream("/WEB-INF/classes/weixinInterface.properties");
			Properties p = new Properties();
			try {
				p.load(in);
				time = p.getProperty("expires_in");
				if (time.equals("") || System.currentTimeMillis() > Long.parseLong(time)) {
					appId = p.getProperty("appId");
					appSecret = p.getProperty("appSecret");
					cs = p.getProperty("cs");
					access_tokenUrl = p.getProperty("access_tokenUrl");
					AccessToken at = WeixinUtil
							.getAccessToken(appId, appSecret);
					access_token = at.getAccess_token();
					expires_in = System.currentTimeMillis()
							+ at.getExpires_in() * 1000 - 200 * 1000;
					p.setProperty("expires_in", expires_in + "");
					p.setProperty("access_token", access_token);
					p.setProperty("appId", appId);
					p.setProperty("appSecret", appSecret);
					p.setProperty("access_tokenUrl", access_tokenUrl);
					p.setProperty("cs", cs);
					OutputStream fos = new FileOutputStream(
							"/home/tomcat7/webapps/weixin/WEB-INF/classes/weixinInterface.properties");
					p.store(fos, "");
					fos.close();
				} else {
					cs = p.getProperty("cs");
					access_token = p.getProperty("access_token");
					UseLog4j.info(this.getClass(), cs);
				}
				in.close();
				sendCSMessage(cs, access_token, touser, msgtype, text);
			} catch (IOException e) {
				UseLog4j.info(CustomerServices.class, "发送客服信息失败！");
//				e.printStackTrace();
			}
		}

	}

	public void sendCSMessage(String cs, String access_token, String touser,
			String msgtype, String text) {
		try {
			URL url = new URL(cs + access_token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStream out = con.getOutputStream();
			TextBackMessage tbm = new TextBackMessage(touser, msgtype,
					new Text(text));
			String addPara = JSON.toJSON(tbm).toString();
			out.write(addPara.getBytes());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLin = "";
			String destStr = "";
			while ((inputLin = in.readLine()) != null) {
				destStr += inputLin;
			}
			UseLog4j.info(this.getClass(), destStr);
			out.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getAccess_tokenUrl() {
		return access_tokenUrl;
	}

	public String getCs() {
		return cs;
	}

}
