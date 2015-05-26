package org.xulingyun.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WeixinIptv {
	String weixin;
	String iptv;
	int id;
	public WeixinIptv() {
	}

	public WeixinIptv(String weixin, String iptv) {
		this.weixin = weixin;
		this.iptv = iptv;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getIptv() {
		return iptv;
	}

	public void setIptv(String iptv) {
		this.iptv = iptv;
	}

	@Override
	public String toString() {
		return "WeixinIptv [weixin=" + weixin + ", iptv=" + iptv + ", id=" + id
				+ "]";
	}
	
	
}
