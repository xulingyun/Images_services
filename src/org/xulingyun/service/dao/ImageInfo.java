package org.xulingyun.service.dao;

import org.xulingyun.util.Util;

import com.alibaba.fastjson.JSON;

public class ImageInfo {

	String imgUrl;
	int size;
	String widthHeight;
	String mask;

	public ImageInfo(String imgUrl, int size, String widthHeight,String mask) {
		this.imgUrl = imgUrl;
		this.size = size;
		this.widthHeight = widthHeight;
		this.mask = mask;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getWidthHeight() {
		return widthHeight;
	}

	public void setWidthHeight(String widthHeight) {
		this.widthHeight = widthHeight;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}
	
	public static void main(String[] args) {
		String path = "/80027/201409/001/1409707025166.jpg";
		String strSecret = "10001" + path;// 10001地址:将计就计看看吧
		String strMask = Util.MD5Data(strSecret);
		String mask = strMask.substring(10, 16);
		ImageInfo ii = new ImageInfo(path, 50, "50|100", mask);
		System.out.println(JSON.toJSON(ii));
	}

}
