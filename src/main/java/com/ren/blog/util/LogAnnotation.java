package com.ren.blog.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author RYF
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
	String desc(); //操作描述信息
	int operType() ;//操作类型 默认未分类 
	int operModule() ;//操作模块 默认未分类
	int operUserId() default 0;//操作用户ID
	String operDate() default "";//操作时间
	String operIp() default "";//操作ip
	int operResult() default 0;// 1 正常 0 异常
	String exceptionDesc() default ""; //异常描述
	String note() default "";//备注
}
