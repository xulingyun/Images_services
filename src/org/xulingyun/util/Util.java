package org.xulingyun.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.xulingyun.service.dao.PicText;

public class Util {
	public final static String secret = "isdj&@#)#*dsid&#(idslNDiie*#";
	public final static String  path = "/home/tomcat7/webapps/weixinImage";
	
	/**
	 * 对数据进行MD5加密并以前的字符串进行对比，确定解密是否正确
	 * 
	 * @param str
	 * @param first
	 * @param last
	 * @param start
	 * @param end
	 * @return 
	 */
	public static boolean MD5JudgeData(String str, int first,int last, int start, int end) {
		String mingPassword = str.substring(first, last);
		String md5String = str.substring(str.length() - 2);
		String s = MD5Data(mingPassword).substring(start, end);
		if (md5String.equals(s)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Data("80027210001"));
	}
	
	/**
	 * 对str数据加密后与secret进行对比
	 * @param str 原始数据
	 * @param secret 传过来的加密数据
	 * @param start 取对str加密的开始的第几位
	 * @param end 	取对str加密的结束的第几位
	 * @return true表示数据正确，错误说明数据来源错误
	 */
	public static boolean MD5DataAndCompare(String str, String secret, int start, int end) {
		String s = MD5Data(str).substring(start, end);
		if (secret.equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";
		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}

	/**
	 * 对数据进行MD5加密
	 * 
	 * @param str 要加密的字符串
	 * @return  返回加密的字符串
	 */
	public static String MD5Data(String str) {
		MessageDigest md = null;
		String data = str + secret;
		byte[] judgeString;
		try {
			md = MessageDigest.getInstance("MD5");
			judgeString = md.digest(data.getBytes());
			String s = bytetoString(judgeString);
			return s;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return "error123456";
			// e.printStackTrace();
		}
	}
	
	public static boolean  creatDirectory(String area,String account){
		int year = getYear();
		String month = getMonth();
		String are = area;
		String unt = account.substring(account.length()-3);
		File f0 = new File(path + "/"+are);
		if (!f0.exists()) {
			f0.mkdir();
		}
		File f1 = new File(path + "/"+are+"/"+year+month);
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f2 = new File(path + "/"+are+"/"+year+month+"/"+unt);
		if (!f2.exists()) {
			f2.mkdir();
		}
		return true;
	}
	
	public static Calendar getCalendar(){
		Calendar  c = Calendar.getInstance();
		return c;
	}
	
	public static String getMonth(){
		Calendar  c = getCalendar();
		int month = c.get(Calendar.MONTH);
		String monthS="";
		if(month+1<10){
			monthS="0"+(month+1);
		}else{
			monthS=(month+1)+"";
		}
		return monthS;
	}
	
	public static int getYear(){
		Calendar  c = getCalendar();
		int year = c.get(Calendar.YEAR);
		return year;
	}
	
	public static int getDay(){
		Calendar  c = getCalendar();
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 删除单个文件
     * @param   file    被删除文件的文件
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
        boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
        	file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 删除多个文件
     * @param f
     */
    public static void deleteFiles(File[] f){
    	for (File f0 : f) {
			Util.deleteFile(f0);
		}
    }
    
    public static int weixinHash(String weixin){
    	int weixinInt = Math.abs(weixin.substring(7).hashCode());
    	int weixinTable = weixinInt%16;
		return weixinTable;
    }
    
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static boolean isQQ(String qq) {
		Pattern p = Pattern.compile("\\d{5,12}");
		Matcher m = p.matcher(qq);
		return m.matches();
	}
	
	public static boolean check() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		List<PicText> q = s.createQuery("from PicText as p where p.kind=1 order by date desc").list();
		for(PicText pt:q){
			System.out.println(pt.toString());
		}
		s.getTransaction().commit();
		return false;
	}
	
	public static String DateFormat(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(d);
		return date;
	}
	

	public static int[] random(int range ) {
        int number = 0;
        Set set = new HashSet();
        int[] array = new int[2];
        while (number < 2) {
            int temp = (int)(Math.random() * (range-2)+2);
            if (set.add(temp)) {
                array[number++] = temp;
            }
        }
        return array;
    }
}
