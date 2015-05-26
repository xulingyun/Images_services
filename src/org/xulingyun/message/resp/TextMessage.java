package org.xulingyun.message.resp;

public class TextMessage extends BaseMessage {
	private String Content;
	
	private Music Music;
	
	private boolean isMusic;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
	public boolean isMusic() {
		return isMusic;
	}

	public void setMusic(boolean isMusic) {
		this.isMusic = isMusic;
	}
}