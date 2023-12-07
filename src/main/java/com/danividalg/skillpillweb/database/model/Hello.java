package com.danividalg.skillpillweb.database.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hellodata")
public class Hello implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    private String id;
	
    private int num;
    private String text;
    
    public Hello(String id, int num, String text) {
    	super();
    	this.id = id;
    	this.num = num;
    	this.text = text;
    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
