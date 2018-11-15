package com.ren.blog.other.annotation;

/**
 * 模拟用户对象
 * @author RYF
 *
 */
@MyTypeAnnotation("user")
public class UserBean {

	@MyFieldAnnotation("id")
	private Integer id;
	
	@MyFieldAnnotation("name")
	private String name;
	
	@MyFieldAnnotation("sex")
	private Integer sex;

	 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
