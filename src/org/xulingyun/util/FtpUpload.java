package org.xulingyun.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUpload {

	private FTPClient ftp;
	private String area;
	
	public FtpUpload(String area) {
		super();
		this.area = area;
	}

	public boolean connect(HashMap<String, String> hm)
			throws Exception {
		login(hm);
		boolean result = false;
		int reply;
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(hm.get("path"));
		result = true;
		return result;
	}

	/**
	 * @param file上传的文件或文件夹
	 * @throws Exception
	 */
	public void upload(File[] files) {
		try {
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			for (File file : files) {
				FileInputStream input = new FileInputStream(file);
				
				boolean b = ftp.storeFile(file.getName(), input);
				UseLog4j.info(this.getClass(),"文件保存成功："+b);
				input.close();
			}
			ftp.logout();
		} catch (Exception e) {
			UseLog4j.error(this.getClass(), "ftp上传失败，原因==》"+e.getStackTrace());
//			e.printStackTrace();
		}
	}

	public void makeDirectory(HashMap<String, String> hm, String iptvAccount) {
		try {
			login(hm);
			String dir = area;
			ftp.makeDirectory(dir);
			ftp.changeWorkingDirectory("/"+dir + "/");
			String dir1 = "" + Util.getYear() + Util.getMonth();
			ftp.makeDirectory(dir1);
			ftp.changeWorkingDirectory("/"+dir + "/" + dir1 + "/");
			String dir2 = iptvAccount.substring(iptvAccount.length() - 3);
			ftp.makeDirectory(dir2);
			ftp.changeWorkingDirectory("/"+dir + "/" + dir1 + "/"+dir2+"/");
		} catch (Exception e) {
			UseLog4j.error(this.getClass(), "创建目录失败，原因==》"+e.getStackTrace());
//			e.printStackTrace();
		}
	}

	public void login(HashMap<String, String> hm) {
		ftp = new FTPClient();
		try {
			ftp.connect(hm.get("url"), Integer.parseInt(hm.get("port")));
			boolean b = ftp.login(hm.get("username"), hm.get("password"));
			UseLog4j.info(this.getClass(),"**登录状态：**"+b);
		} catch (Exception e) {
			UseLog4j.error(this.getClass(), "ftp登入失败，原因==》"+e.getStackTrace());
//			e.printStackTrace();
		}
	}
	
}