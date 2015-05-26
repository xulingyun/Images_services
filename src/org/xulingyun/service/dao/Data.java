package org.xulingyun.service.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Data {

	int id;
	String user;
	String area;
	int status;
	int kind;
	String content;
	Date date;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = new Date();
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", user=" + user + ", area=" + area
				+ ", status=" + status + ", kind=" + kind + ", content="
				+ content + ", date=" + date + "]";
	}

	public Data(String user, String area, int status, int kind,
			String content, Date date) {
		super();
		this.user = user;
		this.area = area;
		this.status = status;
		this.kind = kind;
		this.content = content;
		this.date = date;
	}

	public Data() {
	}
	
	
}
