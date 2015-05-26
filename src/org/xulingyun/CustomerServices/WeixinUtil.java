package org.xulingyun.CustomerServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.xulingyun.CustomerServices.Text;
import org.xulingyun.CustomerServices.TextBackMessage;
import org.xulingyun.util.MyThread;
import org.xulingyun.util.SingletonThreadPool;
import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSON;


/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WeixinUtil {
//	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static AccessToken httpRequest(String requestUrl, String requestMethod, String outputStr) {
		AccessToken at = null;
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
			at = JSON.parseObject(buffer.toString(),AccessToken.class);
		} catch (ConnectException ce) {
//			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
//			log.error("https request error:{}", e);
		}
		return at;
	}
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		accessToken = httpRequest(requestUrl, "GET", null);
//		 //如果请求成功
//		if (null != jsonObject) {
////			try {
//				accessToken = new AccessToken();
//				accessToken.setToken(jsonObject.getString("access_token"));
//				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
////			} catch (JSONException e) {
////				accessToken = null;
//				// 获取token失败
////				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
////			}
//		}
		return accessToken;
	}
	
	public static void main(String[] args) {
//		// 第三方用户唯一凭证
//		String appId = "wxee41d66b7309e352";
//		// 第三方用户唯一凭证密钥
//		String appSecret = "d2b8965eb0f1603b388ffd31a9b2735c";
//		//第三方用户获取access_token 的url地址
//		String access_tokenUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
//		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
//		System.out.println(at);
//		String token = at.getAccess_token();
//
//		try {
//			URL url = new URL(access_tokenUrl+token);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("PUT");
//			con.setDoOutput(true);
//			OutputStream out = con.getOutputStream();
//			TextBackMessage tbm = new TextBackMessage("789", "text",new Text("heheh!"));
//			String addPara = JSON.toJSON(tbm).toString();
//			out.write(addPara.getBytes());
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String inputLin = "";
//			String destStr = "";
//			while ((inputLin = in.readLine()) != null) {
//				destStr += inputLin;
//			}
//			out.close();
//			con.disconnect();
//			System.out.println("destStr----->"+destStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		File f = new File("D:/123.txt");
//		System.out.println(f.length());
		
//		ExecutorService es =SingletonThreadPool.getInstance();
//		Thread t1 = new MyThread();
//		Thread t2 = new MyThread();
//		Thread t3 = new MyThread();
//		Thread t4 = new MyThread();
//		Thread t5 = new MyThread();
//		Thread t6 = new MyThread();
//		Thread t7 = new MyThread();
//		Thread t8 = new MyThread();
//		Thread t9 = new MyThread();
//		Thread t10 = new MyThread();
//		Thread t11 = new MyThread();
//		Thread t12 = new MyThread();
//		es.execute(t1);
//		es.execute(t2);
//		es.execute(t3);
//		es.execute(t4);
//		es.execute(t5);
//		es.execute(t6);
//		es.execute(t7);
//		es.execute(t8);
//		es.execute(t9);
//		es.execute(t10);
//		es.execute(t11);
//		es.execute(t12);
//		Map map = Thread.getAllStackTraces();
//		System.out.println(map.size());
//	
//		writePropertiesFile("D:/1.properties");
		System.out.println(Util.weixinHash("ozWS5uGiOGoGqi4SThz-q0x0wbgo"));
		System.out.println(Util.weixinHash("ozWS5uFvGqZA1aid-ZrJ6bWhE_JU")+"@");
	}
	
	 public static void writePropertiesFile(String filename)  
	    {  
	        Properties properties = new Properties();  
	        try  
	        {  
	            OutputStream outputStream = new FileOutputStream(filename);  
	            properties.setProperty("username", "myname");  
	            properties.setProperty("password", "mypassword111");  
//	            properties.setProperty("chinese", "中文");  
	            properties.store(outputStream, "author: shixing_11@sina.com");  
	            outputStream.close();  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
	    }
}
