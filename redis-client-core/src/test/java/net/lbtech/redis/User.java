package net.lbtech.redis;

import java.io.Serializable;

public class User implements Serializable{

	private String id;
	private Integer age;
	
	public User(String id, Integer age) {
		super();
		this.id = id;
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
