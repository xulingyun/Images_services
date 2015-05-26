package org.xulingyun.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class ThreadSafeOfConnectionManager implements Runnable{
	
	/**
	 * 请求超时10秒钟
	 */
	//private static int REQUEST_TIMEOUT = 30000;		
	
	/**
	 * 等待数据超时时间10秒钟
	 */
	//private static int SO_TIMEOUT = 60000;			
	
	/**
	 * 客户端总并行链接最大数
	 */
	private static int MAXTOTAL = 400;				
	
	/**
	 * 每个主机的最大并行链接数 
	 */
	private static int MAXPERROUTE = 200;		
	
	private static int EXPIRE_CHECK_PERIOD = 60000;
	
	private static boolean shutdown;
	
	private static PoolingHttpClientConnectionManager pccm;
	//private static HttpClientBuilder client;
	private static CloseableHttpClient httpClient = null;
	private static ThreadSafeOfConnectionManager instance;
	
	static{
		instance = new ThreadSafeOfConnectionManager();
		pccm = new PoolingHttpClientConnectionManager();
		pccm.setDefaultMaxPerRoute(MAXPERROUTE);	
		pccm.setMaxTotal(MAXTOTAL);	
		new Thread(instance).start();
	}
	
	//从连接池中获取一个httpclient对象
	public static CloseableHttpClient buildCloseableHttpClient(){
		httpClient = HttpClients.custom().setConnectionManager(pccm).build();
		return httpClient;
	}

	public static ResponseHandler<String> stringHandler = new ResponseHandler<String>() {
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			} else {
				return null;
			}
		}
	};
	
	public static ResponseHandler<byte[]> byteArrayHandler = new ResponseHandler<byte[]>() {
		@Override
		public byte[] handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toByteArray(entity);
			} else {
				return null;
			}
		}
	};
	
	@Override
    protected void finalize() throws Throwable {
    	super.finalize();
    	shutdown = true;
    }
	
	public static <T> T execute(
			final HttpClient httpClient,
            final HttpUriRequest request,
            final ResponseHandler<? extends T> responseHandler)
                throws Exception {
		try {
			return httpClient.execute(request, responseHandler);
		}
		catch (Exception e) {
    		if (request != null) {
    			request.abort();
    		}
			throw e;
		}
	}
	
	public static String executeForBodyString(
			final HttpClient httpClient,
            final HttpUriRequest request)
                throws Exception {
		return execute(httpClient, request, stringHandler);
	}
	
	public static byte[] executeForBodyByteArray(
			final HttpClient httpClient,
            final HttpUriRequest request)
                throws Exception {
		return execute(httpClient, request, byteArrayHandler);
	}
    
	@Override
	public void run() {
		long lastTime = System.currentTimeMillis();
		while(!shutdown){
			try{
				long currTime = System.currentTimeMillis();
				if((currTime - lastTime) >= EXPIRE_CHECK_PERIOD){
					pccm.closeExpiredConnections();
					pccm.closeIdleConnections(30, TimeUnit.SECONDS);
					lastTime = currTime;
					Thread.sleep(EXPIRE_CHECK_PERIOD);
					//log.info("关闭过期连接");
					//UseLog4j.info(this.getClass(), "关闭过期链接");
				}else{
					Thread.sleep(EXPIRE_CHECK_PERIOD - (currTime - lastTime));
				}
			}catch(Throwable t){
				//log.error("Error occured when loop for httpclient connection expire check", t);
				UseLog4j.error(this.getClass(), "HttpClient连接池异常："+t.getLocalizedMessage());
			}
		}
		pccm.shutdown();
	}
    
}
