package org.xulingyun.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xulingyun.CustomerServices.CustomerServices;
import org.xulingyun.message.resp.Article;
import org.xulingyun.message.resp.Music;
import org.xulingyun.message.resp.MusicMessage;
import org.xulingyun.message.resp.NewsMessage;
import org.xulingyun.message.resp.TextMessage;
import org.xulingyun.service.dao.Data;
import org.xulingyun.service.dao.ImageInfo;
import org.xulingyun.service.dao.PicText;
import org.xulingyun.test.BaiduMusicService;
import org.xulingyun.util.FtpUpload;
import org.xulingyun.util.HibernateUtil;
import org.xulingyun.util.MessageUtil;
import org.xulingyun.util.ImageHandle;
import org.xulingyun.util.PhpBackData;
import org.xulingyun.util.SingletonThreadPool;
import org.xulingyun.util.UseLog4j;
import org.xulingyun.util.Util;
import org.xulingyun.util.WeixinIptv;

import com.alibaba.fastjson.JSON;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

@SuppressWarnings("deprecation")
public class CoreService {
	String weixin_table_weixin_iptv = "WeixinIptv";
	static int SIZE;
	ServletContext servletContext;
	HttpServletRequest request;
	String respContent;
	int width;
	int height;
	PrintWriter out;
	final static String TIANJIN = "800272";
	final static String XINJIANG = "800292";
	public final static int IMAGE = 0;
	final static int USER = 1;
	final static int BABY = 2;
	final static String BOBY = "小帅哥";
	final static String GIRL = "小美女";
	String msg_respContent="";

	public CoreService(ServletContext servletContext, HttpServletRequest request, PrintWriter out) {
		this.servletContext = servletContext;
		this.request = request;
		this.out = out;
	}

	public CoreService() {
	}

	public String processRequest(String signature,String timestamp,String nonce) {
		String respMessage = null;
		respContent = "如果您是iTV用户，可以在电视上观看专家育儿专题视频及早教课程；完成微信与机顶盒绑定，还可以传照片到电视，外地工作也可轻松往家里机顶盒传，与家人分享，更有精彩相册活动，等你来领奖。" + System.getProperty("line.separator", "/n")
				+ "如果您还不是iTV用户，也可以在这里获取育儿知识、提交育儿问题、分享育儿经验。" + System.getProperty("line.separator", "/n") + "回复“1”:查看微信如何绑定机顶盒。" + System.getProperty("line.separator", "/n") + "回复“2”： 查看如何往电视传照片。"
				+ System.getProperty("line.separator", "/n") + "回复“3”：查看如何完善资料。" + System.getProperty("line.separator", "/n") + "回复“?”：查看帮助。" + System.getProperty("line.separator", "/n")
				+ "欢迎猛戳左下【妈咪宝典】" + System.getProperty("line.separator", "/n") + "海量育儿知识“每日”定时更新！" + System.getProperty("line.separator", "/n") + "智慧妈咪，与宝宝共成长。";
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt("weixinCourse", "Z5j8NCubGtKFPBWHGr8VpVmyM8BsNpKh37s306QEHYn", "wxee41d66b7309e352");
			Map<String, String> requestMap = MessageUtil.parseXml(request,signature,timestamp,nonce,pc);
			final String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");
			String content = requestMap.get("Content");
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				//UseLog4j.info(CoreService.class, "content---------------:"+content);
				if (content.startsWith("N80") || content.startsWith("N90") || content.startsWith("n80") || content.startsWith("n90")) {// 发送的信息是iptv的绑定号
					if (Util.MD5JudgeData(content, 1, content.length() - 2, 4, 6)) {
						addIPTVAccountToWeixin(weixin_table_weixin_iptv, content.substring(1), fromUserName);
						respContent = "恭喜！你的微信已绑定机顶盒。更多操作请留意“使用指南”！";
					} else {
						respContent = "字符不符，请重新输入!";
					}
				} else if (content.startsWith("歌曲")) {
					// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
					String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
					// 如果歌曲名称为空
					if ("".equals(keyWord)) {
						respContent = "歌曲名不能为空！";
					} else {
						String[] kwArr = keyWord.split("@");
						String musicTitle = kwArr[0];
						String musicAuthor = "";
						if (2 == kwArr.length)
							musicAuthor = kwArr[1];
						Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
						if (null == music) {
							respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
						} else {
							long msg_time = new Date().getTime();
							MusicMessage musicMessage = new MusicMessage();
							musicMessage.setToUserName(fromUserName);
							musicMessage.setFromUserName(toUserName);
							musicMessage.setCreateTime(new Date().getTime());
							musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
							musicMessage.setMusic(music);
							respMessage = MessageUtil.musicMessageToXml(musicMessage);
							msg_respContent = pc.encryptMsg(respMessage, msg_time+"", nonce);
							//UseLog4j.info(CoreService.class, "加密后的音乐消息："+msg_respContent);
							return msg_respContent;
						}
					}
				} else if (content.trim().length()>=3&&content.trim().substring(0, 3).equalsIgnoreCase("yes")) {
					respContent = "感谢您使用我们的产品。快乐生活，轻松育儿！完成微信与机顶盒绑定，还可以时时传照片到电视，与家人分享精彩生活照片。\ue418"+System.getProperty("line.separator", "/n")+"请回复“?”，查看如何绑定上传照片";
				} else if (content.trim().length()>=2&&content.trim().substring(0, 2).equalsIgnoreCase("no")) {
					respContent = "快乐生活，轻松育儿！智慧妈咪期待与您携手再造美丽！\ue022获取精选育儿知识、提交育儿问题、分享育儿经验...敬请关注【智慧妈咪】下拉菜单栏及每周定期推送消息！";
				} else if (content.length()==1&&content.startsWith("h")) {
					respContent = picTextMessage(toUserName, fromUserName, 8,nonce,pc);
					return respContent;
				} else if (content.length()==1&&content.startsWith("1")) {
					respContent = "将电视上的绑定码（用户中心－>用户资料－>绑定码）在这里输入即可绑定，1个绑定码可绑定多个微信号；1微信号对应1个绑定码，要绑定新机顶盒，输入新的绑定码即可。";
				} else if (content.length()==1&&content.startsWith("2")) {
					respContent = "完成微信与机顶盒绑定后（回复“1”查看如何绑定），直接给智慧妈咪发送照片，就可以在电视上看到啦。（上传到宝宝相册的照片仅本机可见）";
				} else if (content.length()==1&&content.startsWith("3")) {
					respContent = "iTV用户绑定机顶盒后，按以下格式向公众号发信息，即可快捷完善个人信息及宝宝资料。" + "回复“P手机号码”更新手机信息" + "回复“QQ你的QQ号”更新QQ信息" + "回复“A地址”更新联系地址" + "回复“N1#昵称”更新第一个宝宝昵称" + "回复“S1#女/男”更新第一个宝宝性别"
							+ "回复“B1#生日日期”更新第一个宝宝生日（格式：2014-04-12）" + "添加或编辑第二个宝宝资料，相应1改成2即可。";
				} else if (content.startsWith("?") || content.startsWith("？")) {
					respContent = "亲爱的iTV/互动电视用户，请您"+System.getProperty("line.separator", "/n")+
							"回复“1”：查看微信如何绑定机顶盒"+System.getProperty("line.separator", "/n")+
							"回复“2”：查看如何往电视传照片"+System.getProperty("line.separator", "/n")+
							"回复“3”：查看如何完善资料"+System.getProperty("line.separator", "/n")+
							"回复“h”：查看使用指南"+System.getProperty("line.separator", "/n");
				} else {
					String iptvAccount = judgeBinding(weixin_table_weixin_iptv, fromUserName);
					if (iptvAccount != null) {
						String[] splice = split(iptvAccount);
						// if
						// (!content.startsWith("QQ")&&content.startsWith("Q"))
						// {// 发送的是用户的提问
						// SaveFileTxt(content.substring(1).trim());
						// respContent =
						// "你的问题已收到，我们会对妈咪们提出的热门问题作解答，并更新在“每天一问”里，请留意查看哦。";
						// } else
						if (content.startsWith("N")) {// 发送的是宝宝的资料
							StringBuffer sb = new StringBuffer(content);
							if (content.substring(1, 2).equals("1") || content.substring(1, 2).equals("2")) {
								if (content.substring(1, 2).equals("1")) {
									sb.append(",R:0");
								} else if (content.substring(1, 2).equals("2")) {
									sb.append(",R:1");
								}
								if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
									Session s = HibernateUtil.getSessionFactory().getCurrentSession();
									s.beginTransaction();
									Data d = new Data(splice[1], splice[0], 0, BABY, "N:" + sb.substring(3).trim(), new Date());
									s.save(d);
									s.getTransaction().commit();
									respContent = "稍等一会您宝宝的大名就会出现了。";
								} else {
									String state = httpCon(servletContext, splice[0], splice[1], "N:" + sb.substring(3).trim(), "0");
									PhpBackData backData = parseJson(state);
									if (backData.getStatus() == 0) {
										respContent = backData.getInfo();
									} else {
										respContent = "宝宝响亮的大名已更新o(∩_∩)o";
									}
								}
							} else {
								respContent = "超过了系统规定的宝宝数目，系统规定只能设置两个宝宝的资料！";
							}
						} else if (content.startsWith("S")) {// 发送的是宝宝的资料
							StringBuffer sb = new StringBuffer(content);
							if (content.substring(1, 2).equals("1") || content.substring(1, 2).equals("2")) {
								if (content.substring(1, 2).equals("1")) {
									sb.append(",R:0");
								} else if (content.substring(1, 2).equals("2")) {
									sb.append(",R:1");
								}
								if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
									if (content.substring(3).equals("男")) {
										sb.replace(3, 4, "1");
										respContent = "信息已收到，小帅哥好！";
										Session s = HibernateUtil.getSessionFactory().getCurrentSession();
										s.beginTransaction();
										Data d = new Data(splice[1], splice[0], 0, BABY, "S:" + sb.substring(3).trim(), new Date());
										s.save(d);
										s.getTransaction().commit();
									} else if (content.substring(3).equals("女")) {
										sb.replace(3, 4, "2");
										respContent = "信息已收到，小美女好！";
										Session s = HibernateUtil.getSessionFactory().getCurrentSession();
										s.beginTransaction();
										Data d = new Data(splice[1], splice[0], 0, BABY, "S:" + sb.substring(3).trim(), new Date());
										s.save(d);
										s.getTransaction().commit();
									} else {
										respContent = "性别不能超出人类的范围！（男或女）,想要了解怎么设置用户资料，宝宝信息，请回复数字3。";
									}
								} else {
									String temp = null;
									if (content.substring(3).equals("男")) {
										sb.replace(3, 4, "1");
										temp = BOBY;
										String state = httpCon(servletContext, splice[0], splice[1], "S:" + sb.substring(3).trim(), "0");
										PhpBackData backData = parseJson(state);
										if (backData.getStatus() == 0) {
											respContent = backData.getInfo();
										} else {
											respContent = "信息已收到，" + temp + "好！";
										}
									} else if (content.substring(3).equals("女")) {
										sb.replace(3, 4, "2");
										temp = GIRL;
										String state = httpCon(servletContext, splice[0], splice[1], "S:" + sb.substring(3).trim(), "0");
										PhpBackData backData = parseJson(state);
										if (backData.getStatus() == 0) {
											respContent = backData.getInfo();
										} else {
											respContent = "信息已收到，" + temp + "好！";
										}
									} else {
										respContent = "性别不能超出人类的范围！（男或女）,想要了解怎么设置用户资料，宝宝信息，请回复数字3。";
									}
								}
							} else {
								respContent = "超过了系统规定的宝宝数目，系统规定只能设置两个宝宝的资料！";
							}
						} else if (content.startsWith("B")) {// 发送的是宝宝的资料
							String[] spl = content.split("-");
							if (spl.length != 3) {
								respContent = "发送宝宝的生日格式错误，请回复数字3，看如何设置用户资料，宝宝信息。";
							} else {
								if (spl[1].length() == 1) {
									spl[1] = "0" + spl[1];
								}
								if (spl[2].length() == 1) {
									spl[2] = "0" + spl[2];
								}
								StringBuffer sb = new StringBuffer(content.substring(0, 8) + spl[1] + "-" + spl[2]);
								if (content.substring(1, 2).equals("1") || content.substring(1, 2).equals("2")) {
									if (content.substring(1, 2).equals("1")) {
										sb.append(",R:0");
									} else if (content.substring(1, 2).equals("2")) {
										sb.append(",R:1");
									}
									if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
										Session s = HibernateUtil.getSessionFactory().getCurrentSession();
										s.beginTransaction();
										Data d = new Data(splice[1], splice[0], 0, BABY, "B:" + sb.substring(3).trim(), new Date());
										s.save(d);
										s.getTransaction().commit();
										respContent = "您宝贝的生日稍后就会显示在你的电视上面了。";
									} else {
										String state = httpCon(servletContext, splice[0], splice[1], "B:" + sb.substring(3).trim(), "0");
										PhpBackData backData = parseJson(state);
										if (backData.getStatus() == 0) {
											respContent = backData.getInfo();
										} else {
											respContent = "这一美好日子，小智会和您一样，铭记在心，期待惊喜！";
										}
									}
								} else {
									respContent = "超过了系统规定的宝宝数目，系统规定只能设置两个宝宝的资料！";
								}
							}
						} else if (content.startsWith("P")) {
							if (Util.isMobileNO(content.substring(1).trim())) {
								if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
									Session s = HibernateUtil.getSessionFactory().getCurrentSession();
									s.beginTransaction();
									Data d = new Data(splice[1], splice[0], 0, USER, "P:" + content.substring(1).trim(), new Date());
									s.save(d);
									s.getTransaction().commit();
									respContent = "您的手机号码稍后就会显示在电视上面了。";
								} else {
									String state = httpCon(servletContext, splice[0], splice[1], "P:" + content.substring(1).trim(), "1");
									PhpBackData backData = parseJson(state);
									if (backData.getStatus() == 1) {
										respContent = "你的手机号码已更新，留意精彩活动，顺便把奖品领回家:-)";
									} else {
										respContent = "手机号码更新有误！";
									}
								}
							} else {
								respContent = "亲，这不是一个有效的手机号码!";
							}
						} else if (content.startsWith("QQ")) {
							if (Util.isQQ(content.substring(2).trim())) {
								if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
									Session s = HibernateUtil.getSessionFactory().getCurrentSession();
									s.beginTransaction();
									Data d = new Data(splice[1], splice[0], 0, USER, "qq:" + content.substring(2).trim(), new Date());
									s.save(d);
									s.getTransaction().commit();
									respContent = "您的QQ号码稍后就会显示在电视上面了。";
								} else {
									String state = httpCon(servletContext, splice[0], splice[1], "qq:" + content.substring(2).trim(), "1");
									PhpBackData backData = parseJson(state);
									if (backData.getStatus() == 1) {
										respContent = "您的QQ号码已更新，参加了活动的话，要多留意下信息，说不定哪天就中奖了!";
									} else {
										// UseLog4j.info(this.getClass(),
										// backData.getInfo());
										respContent = "QQ号码必须是5到12位的数字。";
									}
								}
							} else {
								respContent = "这帐号不是腾讯企鹅家的吧，外星来的？";
							}
						} else if (content.startsWith("A")) {
							if (splice[0].equals(TIANJIN)||splice[0].equals(XINJIANG)) {
								Session s = HibernateUtil.getSessionFactory().getCurrentSession();
								s.beginTransaction();
								Data d = new Data(splice[1], splice[0], 0, USER, "A:" + content.substring(1).trim(), new Date());
								s.save(d);
								s.getTransaction().commit();
								respContent = "您的地址稍后就会显示在电视上面了。";
							} else {
								String state = httpCon(servletContext, splice[0], splice[1], "A:" + content.substring(1).trim(), "1");
								PhpBackData backData = parseJson(state);
								if (backData.getStatus() == 1) {
									respContent = "请确认地址填写是否正确，错了可重新输入该指令!";
								} else {
									// UseLog4j.info(this.getClass(),
									// backData.getInfo());
									respContent = "提交地址错误，请按指令输入！";
								}
							}
						} else {
							respContent = "如果您是iTV用户，可以在电视上观看专家育儿专题视频及早教课程；完成微信与机顶盒绑定，还可以传照片到电视，外地工作也可轻松往家里机顶盒传，与家人分享，更有精彩相册活动，等你来领奖。" + System.getProperty("line.separator", "/n")
									+ "如果您还不是iTV用户，也可以在这里获取育儿知识、提交育儿问题、分享育儿经验。" + System.getProperty("line.separator", "/n") + "回复“1”:查看微信如何绑定机顶盒。" + System.getProperty("line.separator", "/n")
									+ "回复“2”： 查看如何往电视传照片。" + System.getProperty("line.separator", "/n") + "回复“3”：查看如何完善资料。" + System.getProperty("line.separator", "/n") + "回复“?”：查看帮助。"
									+ System.getProperty("line.separator", "/n") + "欢迎猛戳左下【妈咪宝典】" + System.getProperty("line.separator", "/n") + "海量育儿知识“每日”定时更新！"
									+ System.getProperty("line.separator", "/n") + "智慧妈咪，与宝宝共成长。 ";
						}
					} else {
						respContent = "";
					}
				}
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				String iptvAccount = judgeBinding(weixin_table_weixin_iptv, fromUserName);
				if (iptvAccount != null) {
					final String imgUrl = requestMap.get("PicUrl");
					final String[] strArray = split(iptvAccount);
					final String spliceUser = strArray[1].substring(strArray[1].length() - 3);
					Util.creatDirectory(strArray[0], strArray[1]);
					final String path = Util.path + "/" + strArray[0] + "/" + Util.getYear() + Util.getMonth() + "/" + spliceUser;
					final int id = Integer.parseInt(strArray[0].substring(strArray[0].length() - 1));
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							long time = System.currentTimeMillis();
							boolean issave = saveImage(imgUrl, time, path, id);// 保存图片
							if (strArray[0].equals(TIANJIN)||strArray[0].equals(XINJIANG)) {
								Session s = HibernateUtil.getSessionFactory().getCurrentSession();
								s.beginTransaction();
								String path = "/" + strArray[0] + "/" + Util.getYear() + Util.getMonth() + "/" + spliceUser + "/" + time + ".jpg";
								String strSecret = strArray[1] + path;// 10001地址:将计就计看看吧
								String strMask = Util.MD5Data(strSecret);
								String mask = strMask.substring(10, 16);
								ImageInfo ii = new ImageInfo(path, SIZE, width + "|" + height, mask);
								Data d = new Data(strArray[1], strArray[0], 0, IMAGE, JSON.toJSONString(ii), new Date());
								s.save(d);
								s.getTransaction().commit();
								respContent = "您的照片稍后就会显示在电视上面了。";
							} else {
								if (issave) {
									String weixinImagePath = path + "/" + time;
									File[] f = new File[3];
									f[0] = new File(weixinImagePath + "_l.jpg");
									f[1] = new File(weixinImagePath + "_m.jpg");
									f[2] = new File(weixinImagePath + "_s.jpg");
									String state = httpCon(servletContext, strArray[0], strArray[1], "/" + strArray[0] + "/" + Util.getYear() + Util.getMonth() + "/" + spliceUser + "/" + time
											+ ".jpg", SIZE + "_" + width + "_" + height);
									if (state.equals("500")) {
										respContent = "网络不佳，请重试！";
										UseLog4j.error(this.getClass(), "服务器未能正常运行500");
									} else if (state.equals("")) {
										respContent = "网络不佳，请重试！";
										UseLog4j.error(this.getClass(), "服务器未能正常运行，空");
									} else {
										PhpBackData backData = parseJson(state);
										int states = backData.getStatus();
										String info = backData.getInfo();
										if (states == 1) {
											FtpUpload ftpUpload = new FtpUpload(strArray[0]);
											ftpUpload.makeDirectory(getFTPConfig(servletContext, strArray[0]), strArray[1]);
											ftpUpload.upload(f);
											//UseLog4j.info(CoreService.class, "ftp文件上传完成！");
										}
										//UseLog4j.info(CoreService.class, info);
										if (info.equals("你的空间已不足，请升级会员等级")) {
											respContent = "你的相册空间已满，请在电视上开通会员再来上传吧！";
										} else if (info.equals("图片上传成功")) {
											// respContent =
											// "照片收到，已投递到电视上了，快去看看好棒的照片！";
											respContent = "照片已经收到了哟！";
										}
									}
									for (File f0 : f) {
										Util.deleteFile(f0);
									}
									CustomerServices cs = new CustomerServices(servletContext);
									cs.readPropAndSendCSMessage(fromUserName, "text", respContent);
								}
							}
						}
					});
					t.setDaemon(true);
					SingletonThreadPool.getInstance().execute(t);
					respContent = "已成功上传，系统会对提交的照片做审核，审核通过会在24小时内显示，请耐心等待！";
				} else {
					respContent = "您还未绑定机顶盒帐号，绑定方法请看“使用指南”。";
				}
			} else {
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					respContent = "你发送的是位置信息！";
				} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					respContent = "你发送的是链接信息！";
				} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					respContent = "你发送的是音频信息！";
				} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					String eventType = requestMap.get("Event");
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						respContent = "欢迎加入智慧妈咪大家庭！\ue032请问，您是iTV/互动电视用户吗？是请回复“yes”，否请回复“no”。 ";
					} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

					} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						String key = requestMap.get("EventKey");
						if (Integer.parseInt(key) == 11) {
							respContent = picTextMessage(toUserName, fromUserName, 1,nonce,pc);
							return respContent;
						} else if (Integer.parseInt(key) == 12) {
							respContent = picTextMessage(toUserName, fromUserName, 10,nonce,pc);
							return respContent;
						} else if (Integer.parseInt(key) == 31) {
							respContent = picTextMessage(toUserName, fromUserName, 8,nonce,pc);
							return respContent;
						} else if (Integer.parseInt(key) == 32) {
							respContent = picTextMessage(toUserName, fromUserName, 9,nonce,pc);
							return respContent;
						}
					} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
						String key = requestMap.get("EventKey");
						if (Integer.parseInt(key) == 11280) {
							respContent = "带入场景值:" + key;
						}
					}
				}
			}
			long msg_time = new Date().getTime();
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(msg_time);
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			msg_respContent = pc.encryptMsg(respMessage, msg_time+"", nonce);
			//UseLog4j.info(CoreService.class, "加密后的文字正经的文字图片："+msg_respContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg_respContent;
	}

	private String picTextMessage(String toUserName, String fromUserName, int kind,String nonce,WXBizMsgCrypt pc) {
		String context = "";
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		String sql = "from PicText as p where p.kind=? order by date desc";
		Query query = s.createQuery(sql);
		query.setParameter(0, kind);
		List<PicText> list = query.list();
		int length = list.size();
		s.getTransaction().commit();
		NewsMessage nm = new NewsMessage();
		nm.setFromUserName(toUserName);
		nm.setToUserName(fromUserName);
		nm.setCreateTime(new Date().getTime());
		nm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		nm.setFuncFlag(0);
		if (length != 0) {
			List<Article> article = new ArrayList<Article>();
			Article article1 = new Article();
			// int random = new Random().nextInt(length);
			int random = 0;
			PicText pt1 = list.get(random);
			article1.setTitle(pt1.getTitle());
			article1.setDescription(pt1.getDigest());
			article1.setPicUrl("http://183.57.41.222:9100/weixinImage/" + pt1.getCover());
			article1.setUrl(pt1.getUrl());
			article.add(article1);
			if (length > 1) {
				Article article2 = new Article();
				while(random==0){
					random = new Random().nextInt(length);
				}
				PicText pt2 = list.get(random);
				article2.setTitle(pt2.getTitle());
				article2.setDescription(pt2.getDigest());
				article2.setPicUrl("http://183.57.41.222:9100/weixinImage/" + pt2.getCover());
				article2.setUrl(pt2.getUrl());
				article.add(article2);
			}
			nm.setArticleCount(article.size());
			nm.setArticles(article);
			context = MessageUtil.newsMessageToXml(nm);
			long msg_time = new Date().getTime();
			try {
				msg_respContent = pc.encryptMsg(context, msg_time+"", nonce);
				//UseLog4j.info(CoreService.class, "加密后的图文消息："+msg_respContent);
			} catch (AesException e) {
				e.printStackTrace();
			}
		}
		return msg_respContent;
	}

	private static String[] split(String iptvAccount) {
		String[] s = new String[3];
		s[0] = iptvAccount.substring(0, 6);
		s[1] = iptvAccount.substring(6, iptvAccount.length() - 2);
		s[2] = iptvAccount.substring(iptvAccount.length() - 2);
		return s;
	}

	public static void SaveFileTxt(String content) {
		// File f = new File("D:/aa.txt");
		File f = new File(Util.path + "/" + Util.getYear() + Util.getMonth() + Util.getDay() + ".txt");
		FileReader fr;
		BufferedReader br;
		FileWriter fw;
		BufferedWriter bw;
		StringBuffer sb = new StringBuffer();
		try {
			fw = new FileWriter(f, true);// 初始化输出流
			bw = new BufferedWriter(fw);// 初始化输出字符流
			String lineSeparator = System.getProperty("line.separator", "/n");
			bw.write(content + lineSeparator);// 写文件
			bw.flush();
			bw.close();
			fw.close();
			//
			// fr = new FileReader(f);// 初始化输入流
			// br = new BufferedReader(fr);// 初始化输入字符流
			// sb.append(br.readLine().toString());// 按行读文件
			// System.out.println("文件内容是：" + sb);
			// br.close();
			// fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPreparedStatementArg(java.sql.PreparedStatement ps, String[][] data, String iptvAccount, int kind) {
		try {
			if (kind != 1) {
				for (int i = 0; i < data.length; i++) {
					if (i == 2 || i == 4) {
						ps.setInt(i + 1, Integer.parseInt(data[i][1]));
					} else {
						ps.setString(i + 1, data[i][1]);
					}
				}
				ps.setString(6, iptvAccount);
			} else {
				ps.setInt(1, Integer.parseInt(data[4][1]));
				ps.setString(2, iptvAccount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加机顶盒账号到微信端的数据库
	 * 
	 * @param table
	 *            保存机顶盒帐号的表
	 * @param iptvAccount
	 *            机顶盒帐号
	 * @param weixinAccount
	 *            微信帐号
	 * @return true表示保存成功，false表示保存失败
	 */
	private boolean addIPTVAccountToWeixin(String table, String iptvAccount, String weixinAccount) {
		// String sql = "select * from " + table +
		// " where weixin="+"'"+weixinAccount+"'";
		String sql = "from WeixinIptv as w where w.weixin=" + "'" + weixinAccount + "'";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(sql);
		List list = query.list();
		if (list.size() == 1) {
			WeixinIptv wi = (WeixinIptv) list.get(0);
			//UseLog4j.info(this.getClass(), "update-------------" + wi.toString());
			wi.setIptv(iptvAccount);
			wi.setWeixin(weixinAccount);
			session.update(wi);
		} else {
			//UseLog4j.info(this.getClass(), "add----------------");
			session.save(new WeixinIptv(weixinAccount, iptvAccount));
		}
		session.getTransaction().commit();
		return true;
	}

	/**
	 * 判断机顶盒是否与微信绑定
	 * 
	 * @param table
	 *            保存机顶盒帐号的数据库表
	 * @param weixinAccount
	 *            微信帐号
	 * @return 机顶盒帐号或者空
	 */
	private String judgeBinding(String table, String weixinAccount) {
		String iptvAccount = null;
		String sql = "from WeixinIptv as w where w.weixin=" + "'" + weixinAccount + "'";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(sql);
		List list = query.list();
		if (list.size() == 1) {
			iptvAccount = ((WeixinIptv) list.get(0)).getIptv();
		}
		session.getTransaction().commit();
		return iptvAccount;
	}

	/**
	 * 建一个与电视机服务端的连接，并等待返回数据
	 * 
	 * @param area
	 *            代表哪个地区
	 * @param user
	 *            用户ID
	 * @param param
	 *            要发送的参数
	 * @param size
	 *            如果param是图片地址，size表示图片的尺寸大小，否则，0代表宝贝信息，1代表用户信息
	 */
	public String httpCon(ServletContext servletContext, String area, String user, String params, String sizes) {
		String destStr = "";
		String[] sizeSting = sizes.split("_");
		int[] size = new int[sizeSting.length];
		for (int i = 0; i < sizeSting.length; i++) {
			size[i] = Integer.parseInt(sizeSting[i]);
		}
		String strSecret = user + params;// 10001地址:将计就计看看吧
		String strMask = Util.MD5Data(strSecret);
		String mask = strMask.substring(10, 16);
		String strUrl = "";
		if (size[0] == 0) {
			strUrl = getLocalhost(servletContext, area, "babyInfo");// .replace("USER",
																	// user).replace("PARAMS",
																	// params).replace("MASK",
																	// mask);

		} else if (size[0] == 1) {
			strUrl = getLocalhost(servletContext, area, "userInfo");// .replace("USER",
																	// user).replace("PARAMS",
																	// params).replace("MASK",
																	// mask);

		} else {
			strUrl = getLocalhost(servletContext, area, "sendImage");// .replace("USER",
																		// user).replace("IMGPATH",
																		// params).replace("SIZE",
																		// size[0]+"").replace("MASK",
																		// mask).replace("WIDTH",
																		// size[1]+"").replace("HEIGHT",
																		// size[2]+"");

		}
		//UseLog4j.info(this.getClass(), "strUrl:" + strUrl);
		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(10000);
			con.setDoOutput(true);
			con.connect();
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			// UseLog4j.info(this.getClass(),
			// "ResponseMessage==》"+con.getResponseMessage());
			// UseLog4j.info(this.getClass(),
			// "错误信息==》"+con.getContent().toString());

			String content = null;
			if (size[0] == 0) {
				String content1 = "user=" + URLEncoder.encode(user, "utf-8");
				String content2 = "params=" + URLEncoder.encode(params, "utf-8");
				String content3 = "mask=" + URLEncoder.encode(mask, "utf-8");
				content = content1 + "&" + content2 + "&" + content3;
			} else if (size[0] == 1) {
				String content1 = "user=" + URLEncoder.encode(user, "utf-8");
				String content2 = "params=" + URLEncoder.encode(params, "utf-8");
				String content3 = "mask=" + URLEncoder.encode(mask, "utf-8");
				content = content1 + "&" + content2 + "&" + content3;
			} else {
				String content1 = "user=" + URLEncoder.encode(user, "utf-8");
				String content2 = "imgPath=" + URLEncoder.encode(params, "utf-8");
				String content3 = "size=" + URLEncoder.encode(size[0] + "", "utf-8");
				String content4 = "mask=" + URLEncoder.encode(mask, "utf-8");
				String content5 = "widthHeight=" + URLEncoder.encode(size[1] + "|" + size[2], "utf-8");
				content = content1 + "&" + content2 + "&" + content3 + "&" + content4 + "&" + content5;
			}
			// UseLog4j.info(this.getClass(), "content:"+strUrl+"?"+content);
			out.writeBytes(content);
			out.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			int status = con.getResponseCode();
			//UseLog4j.info(this.getClass(), "ResponseCode==》" + status);
			if (status != 200) {
				return "500";
			}
			String inputLin = "";
			while ((inputLin = in.readLine()) != null) {
				destStr += inputLin;
			}
			con.disconnect();
			return destStr;
		} catch (Exception e) {
			//UseLog4j.info(this.getClass(), "异常错误信息==》" + e.getStackTrace());
			return destStr;
		}
	}

	public String getLocalhost(ServletContext servletContext, String area, String operation) {
		String s = "";
		// InputStream in =
		// ClassLoader.getSystemResourceAsStream("address.properties");//本地读取properties文件
		InputStream in = servletContext.getResourceAsStream("/WEB-INF/classes/address.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			s = p.getProperty(area + operation);
			in.close();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	public HashMap<String, String> getFTPConfig(ServletContext servletContext, String area) {
		HashMap<String, String> hm = new HashMap<String, String>();
		InputStream in = servletContext.getResourceAsStream("/WEB-INF/classes/ftp.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			hm.put("url", p.getProperty(area + "url"));
			hm.put("port", p.getProperty(area + "port"));
			hm.put("username", p.getProperty(area + "username"));
			hm.put("password", p.getProperty(area + "password"));
			hm.put("path", p.getProperty(area + "path"));
			in.close();
			return hm;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hm;
	}

	@SuppressWarnings("deprecation")
	private boolean saveImage(String imgUrl, long time, String path, int id) {
		File f = new File(path + "/" + time + ".jpg");
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(imgUrl);
			HttpResponse response = httpclient.execute(httpget);
			FileOutputStream output = new FileOutputStream(f);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				byte b[] = new byte[1024];
				int j = 0;
				while ((j = instream.read(b)) != -1) {
					output.write(b, 0, j);
				}
				output.flush();
				output.close();
				instream.close();
			}
			httpget.releaseConnection();
			httpclient.getConnectionManager().shutdown();
			ImageHandle ih = new ImageHandle();
			if (id == 1) {
				ih.saveImageAsJpg(path + "/" + time + ".jpg", path + "/" + time + "_l.jpg", 640, 530, 1);
				height = ih.getHeight();
				width = ih.getWidth();
				new File(path + "/" + time + ".jpg").delete();
				ih.saveImageAsJpg(path + "/" + time + "_l.jpg", path + "/" + time + "_m.jpg", 240, 180, 0);
				ih.saveImageAsJpg(path + "/" + time + "_l.jpg", path + "/" + time + "_s.jpg", 160, 120, 0);
			} else if (id == 2) {
				ih.saveImageAsJpg(path + "/" + time + ".jpg", path + "/" + time + "_l.jpg", 1280, 720, 1);
				height = ih.getHeight();
				width = ih.getWidth();
				new File(path + "/" + time + ".jpg").delete();
				ih.saveImageAsJpg(path + "/" + time + "_l.jpg", path + "/" + time + "_m.jpg", 360, 225, 0);
				ih.saveImageAsJpg(path + "/" + time + "_l.jpg", path + "/" + time + "_s.jpg", 280, 215, 0);
			}
			SIZE = (int) (new File(path + "/" + time + "_l.jpg").length() / 1024);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int ImgSize(long size) {
		int length = (int) (size / 1024);
		return length;
	}

	public PhpBackData parseJson(String jsonData) {
		//UseLog4j.info(this.getClass(), jsonData);
		PhpBackData phpBackData = JSON.parseObject(jsonData, PhpBackData.class);
		return phpBackData;
	}
	
	public static void main(String[] args) {
		ImageInfo ii = new ImageInfo("path", SIZE, "width" + "|" + "height", "mask");
		System.out.println(JSON.toJSONString(ii));
	}

}
