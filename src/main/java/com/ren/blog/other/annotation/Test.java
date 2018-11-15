package com.ren.blog.other.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解使用方式测试
 * @author RYF
 *
 */
public class Test {
	
	public static void main(String[] args) {
		UserBean user1 = new UserBean();
		user1.setName("zhangsan");
		String result = query(user1);
		System.out.println(result);
		/**
		 * Output
		 * SELECT * FROM user where 1=1  and name=zhangsan
		 * 
		 */
		UserBean user2 = new UserBean();
		user2.setId(12);
		String result2 = query(user2);
		System.out.println(result2);
		/**
		 * Output
		 * SELECT * FROM user where 1=1  and id=12
		 * 
		 */
		
	}
	
	/**
	 * 组合查询语句并返回
	 * @param user 用户对象
	 * @return 返回查询语句
	 */
	public static String query(UserBean user) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ");
		
		//获取类上的表名注解
		MyTypeAnnotation table = user.getClass().getAnnotation(MyTypeAnnotation.class);
		if(table != null) {
			String tableName = table.value();
			sb.append(tableName +" where 1=1 " );
		}
		
		//获取所有的字段
		Field[] fields = user.getClass().getDeclaredFields();
		for (Field field : fields) {
			
			//获取字段上列名注解
			MyFieldAnnotation column = field.getAnnotation(MyFieldAnnotation.class);
			if(column != null) {
				//列名
				String columnName = column.value();
				//字段名
				String fieldName = field.getName();
				//通过字段名组合get方法名称
				String fieldGetMethodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
				try {
					//反射获取方法的值
					Method fieldMethod = user.getClass().getMethod(fieldGetMethodName);
					Object fieldValue =  fieldMethod.invoke(user);
					if(fieldValue == null) {
						continue;
					}
					//值不为空则作为条件
					sb.append(" and "+columnName+"="+ fieldValue);
				}  catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
