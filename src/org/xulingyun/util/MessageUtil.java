package org.xulingyun.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xulingyun.message.resp.Article;
import org.xulingyun.message.resp.MusicMessage;
import org.xulingyun.message.resp.NewsMessage;
import org.xulingyun.message.resp.TextMessage;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	public static final String EVENT_TYPE_VIEW = "view";
	
	public static final String EVENT_TYPE_SCAN = "SCAN";

	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request,String signature,String timestamp,String nonce,WXBizMsgCrypt pc){
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			document = reader.read(br);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			boolean isJiami = false;
			String jiami="";
			for (Element e : elementList){
				//UseLog4j.info(MessageUtil.class, e.getName()+"===="+e.getText());
				//map.put(e.getName(), e.getText());
				if(e.getName().equals("Encrypt")){
					isJiami = true;
					jiami = e.getText();
				}
			}
			if(isJiami){
				try {
					String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
					String fromXML = String.format(format, jiami);
					String sss = pc.decryptMsg(signature, timestamp, nonce, fromXML);
					//UseLog4j.info(MessageUtil.class,"解密后的字符串："+sss);
					
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					StringReader sr = new StringReader(sss);
					InputSource is = new InputSource(sr);
					org.w3c.dom.Document documents = db.parse(is);
					org.w3c.dom.Element roots = documents.getDocumentElement();
					NodeList list = roots.getChildNodes();
					for(int i=0;i<list.getLength();){
						map.put(list.item(i).getNodeName(), list.item(i).getTextContent());
						//UseLog4j.info(MessageUtil.class,list.item(i).getNodeName()+"___4___"+list.item(i).getTextContent()+"_____length:"+list.getLength());
						i+=2;
					}
				} catch (Exception e1) {
					UseLog4j.info(MessageUtil.class,"_____4_____神奇的分割线："+e1.getMessage());
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}finally{
			try {
				br.close();
				br = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	private static XStream xstream =new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}