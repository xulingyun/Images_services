package org.xulingyun.CustomerServices;

public class BackMessageBase {

	private String touser;
	private String msgtype;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public BackMessageBase(String touser, String msgtype) {
		super();
		this.touser = touser;
		this.msgtype = msgtype;
	}
	
}
