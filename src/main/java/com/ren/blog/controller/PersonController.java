package com.ren.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 测试用controller
 * @author RYF
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.ren.blog.bean.PersonBean;
import com.ren.blog.service.PersonService;
@Controller
public class PersonController {

	@Autowired
	PersonService personService;
	
	@RequestMapping("/test/findAll")
	public String  test(){
		List<PersonBean> list = personService.findAll();
		for (PersonBean person : list) {
			System.out.println(person.toString());
		}
		
		return "test";
	}
}
