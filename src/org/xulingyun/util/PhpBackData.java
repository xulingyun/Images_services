package org.xulingyun.util;

public class PhpBackData {
	int status = 20;
	String info;
	
	@Override
	public String toString() {
		return "PhpBackData [status=" + status + ", info=" + info + "]";
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
