package org.xulingyun.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xulingyun.CustomerServices.AccessToken;
import org.xulingyun.CustomerServices.MyX509TrustManager;
import org.xulingyun.CustomerServices.WeixinUtil;
import org.xulingyun.util.ImageHandle;
import org.xulingyun.util.UseLog4j;

import com.alibaba.fastjson.JSON;

public class MakeQrcode {
	static String ticketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	static String getQrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	// 第三方用户唯一凭证
	static String appId = "wxee41d66b7309e352";
	// 第三方用户唯一凭证密钥
	static String appSecret = "d2b8965eb0f1603b388ffd31a9b2735c";
	static String tick = "gQHL7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL19rd0hUVHZsUFQxRVQ5U083R0JuAAIEOnkyUwMEAAAAAA==";
	
	public static void main(String[] args) {
//		getTicket();
//		getQrCode();
		String s= getQrcodeUrl+URLEncoder.encode(tick);
		System.out.println(s);
		httpRequest(s,"GET",null);
		System.out.println("gggggggg");
	}
	
	public static String getTicket(){
//		Ticket t = new Ticket();
		try {
			AccessToken at = WeixinUtil.getAccessToken(appId,appSecret);
			URL url = new URL(ticketUrl+at.getAccess_token());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			OutputStream out = con.getOutputStream();
			SendData sd = new SendData(1800, "QR_SCENE", new ActionInfo(new Scene(100+"")));
			String addPara = JSON.toJSON(sd).toString();
			out.write(addPara.getBytes());
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLin = "";
			String destStr = "";
			while ((inputLin = in.readLine()) != null) {
				destStr += inputLin;
			}
//			UseLog4j.info(getClass(), destStr);
//			System.out.println(destStr);
			out.close();
			con.disconnect();
			return destStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "meiyou!";
	}
	
	public static void getQrCode(){
		try {
			File f = new File("F:/qrCode/qwert.jpg");
			URL url = new URL(getQrcodeUrl+tick);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
//			OutputStream out = con.getOutputStream();
			FileOutputStream out = new FileOutputStream(f);
			InputStream fis = con.getInputStream();
			int j=0;
			byte b[] = new byte[1024];
			while ((j=fis.read(b)) != -1) {
				out.write(b, 0, j);
			}
			out.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void httpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			System.out.println("sadb");
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			ce.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
