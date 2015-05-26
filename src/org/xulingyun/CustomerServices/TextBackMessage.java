package org.xulingyun.CustomerServices;

public class TextBackMessage extends BackMessageBase {
	Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public TextBackMessage(String touser, String msgtype, Text text) {
		super(touser, msgtype);
		this.text = text;
	}

}
