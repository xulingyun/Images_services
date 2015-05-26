package org.xulingyun.CustomerServices;

/**
 * 微信通用接口凭证
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class AccessToken {
	// 获取到的凭证
	private String access_token;
	// 凭证有效时间，单位：秒
	private long expires_in;

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

	@Override
	public String toString() {
		return "AccessToken [access_token=" + access_token + ", expires_in="
				+ expires_in + "]";
	}

}