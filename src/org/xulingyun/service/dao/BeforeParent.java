package org.xulingyun.service.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BeforeParent {

	int id;
	String week;
	String outline;
	String mamiRead;
	String parentKnow;
	String focus;
	String cookbook;
	String book;
	String taegyo;
	String pic;

	public BeforeParent() {
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getMamiRead() {
		return mamiRead;
	}

	public void setMamiRead(String mamiRead) {
		this.mamiRead = mamiRead;
	}

	public String getParentKnow() {
		return parentKnow;
	}

	public void setParentKnow(String parentKnow) {
		this.parentKnow = parentKnow;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getCookbook() {
		return cookbook;
	}

	public void setCookbook(String cookbook) {
		this.cookbook = cookbook;
	}

	public String getTaegyo() {
		return taegyo;
	}

	public void setTaegyo(String taegyo) {
		this.taegyo = taegyo;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "BeforeParent [id=" + id + ", week=" + week + ", outline=" + outline + ", mamiRead=" + mamiRead + ", parentKnow=" + parentKnow + ", focus=" + focus + ", cookbook=" + cookbook
				+ ", book=" + book + ", taegyo=" + taegyo + ", pic=" + pic + "]";
	}

}
