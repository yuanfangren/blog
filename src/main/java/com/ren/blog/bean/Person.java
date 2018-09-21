package com.ren.blog.bean;

/**
 * 测试SSM联通性bean
 * @author RYF
 *
 */
public class Person {
  
	private int id;
	
	private String password;
	
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name="
				+ name + "]";

	}
	
}
