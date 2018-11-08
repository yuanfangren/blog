package com.ren.blog.bean.other;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 栈方式计算加减乘除
 * @author RYF
 *
 */
public class StackCalc {
	
	public static void main(String[] args) {
		
		String s = " 9+(3-1)*3+10/2";//12*3+6-97*+
		s = s.replaceAll("\\s", "");
		//String s = "9+(3-1)3+10/2";
		
		//转换为后缀表达式
		String suffix = suffixExpr(s);
		System.out.println(suffix);
		//后缀表达式计算
		String result = calc(suffix);
		System.out.println(result);
		
	}
	
	/**
	 * 转换为后缀表达式(主要是去掉() )
	 * 	
	 * 	从左到右遍历中缀表达式中的每个数字和符号，若是数字就输出，即称为后缀表达式的一部分
		若是符号，则判断其与栈顶符号的优先级，右括号或优先级不高于栈顶符号(乘除优先加减)则栈顶元素依次弹栈并放入后缀表达式中,并将当前符号压栈，直到遍历结束，栈中元素依次弹栈加入后缀表达式中。
	 * @param s
	 * @return
	 */
	public static String suffixExpr(String s){
		
		StringBuffer sb = new StringBuffer();//String s = "1*2+3-6+9*7";//12*3+6-97*+
		
		//操作符栈
		Stack<String> stack = new Stack<>();
		
		//数字操作符 
		Stack<String> intStack = new Stack<>();
		boolean flag = false;
		
		for(int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if(isNum(c)) {
				if(flag) {
					String tmp =  intStack.pop();
					int a = Integer.valueOf(tmp)*10+Integer.valueOf(String.valueOf(c));
					int last = sb.lastIndexOf(tmp);
					sb.delete(last, last+tmp.length()+1);
					sb.append(String.valueOf(a)+"#");
				}else {
					intStack.push(String.valueOf(c));
					sb.append(String.valueOf(c)+"#");
					flag = true;
				}
			}else if(isOpe(c)){
				flag = false;
				if(!stack.empty()) {
					if(c == '(') {
						stack.push(String.valueOf(c));
					}else if(c == ')') {
						while(!stack.empty()) {
							String tmp = stack.pop();
							if("(".equals(tmp)) {
								break;
							}else {
								sb.append(tmp+"#");
							}
						}
					}else {
						if(priority(stack.peek(),String.valueOf(c))) {
							sb.append(String.valueOf(stack.pop())+"#");
						}
						stack.push(String.valueOf(c));
					} 
					
				}else {
					stack.push(String.valueOf(c));
				}
			}else {
				return "error";//有不符合要求字符
			}
		}
		
		//将栈内元素依次弹出
		while(!stack.empty()) {
			sb.append(String.valueOf(stack.pop())+"#");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	/**
	 * 利用栈的方式计算后缀表达式
	 * 	遇到数字，将数字压栈
		遇到字符，将栈中最顶的两个元素弹栈，然后用该字符计算出结果(栈中第二个元素对栈顶元素操作),然后再将结果压栈，重复上述过程直到结束
		最终栈中的唯一一个元素就是结果，弹栈，结束计算
	 * @param s
	 * @return
	 */
	public static String calc(String s) {
		
		Stack<String> stack = new Stack<>();//12*3+6-97*+
		String[] ss = s.split("#");
		for(int i=0;i<ss.length;i++) {
			String c = ss[i];
			if(isNum(c)) {
				stack.push(String.valueOf(c));
			}else if(isOpe(c)){
				String c1 = stack.pop();
				String c2 = stack.pop();
				String c3 = calc_true(c,c1,c2);
				stack.push(c3);
			}
		}
		return String.valueOf(stack.pop());
	}
	
	/**
	 * 真正的计算操作
	 * @param op 操作符 【+ - * /】
	 * @param c1 
	 * @param c2
	 * @return
	 */
	public static String calc_true(String op,String c1,String c2) {
		int i1 = Integer.parseInt(c1);
		int i2 = Integer.parseInt(c2);
		int rs = 0;
		switch(op) {
			case "+":
				rs = i2+i1;break;
			case "-":
				rs = i2-i1;break;
			case "*":
				rs = i2*i1;break;
			case "/":
				rs = i2/i1;break;
		}
		return String.valueOf(rs);
	}
	
	/**
	 * 栈顶操作符和要对比的操作符比较优先级(目前只对比 + - * / )
	 * c2 优先级不高于c1的话就返回true
	 * @param c1 栈顶操作符
	 * @param c2 要对比操作符
	 * @return
	 */
	public static boolean priority(String c1 ,String c2) {
		boolean flag = false;
		if("*".equals(c1) || "/".equals(c1)) {
			flag = true;
		}else if("+".equals(c1) || "-".equals(c1)){
			if("+".equals(c2) || "-".equals(c2)) {
				flag = true;
			}else if("*".equals(c2) || "/".equals(c2)){
				flag = false;
			}
		} 
		return flag;
	}
	
	/**
	 * 判断是否是数字
	 * @param c 
	 * @return
	 */
	public static boolean isNum(char c) {
		if(c>='0' && c<='9') {
			return true;
		}
		return false;
	}
	
	public static boolean isNum(String c) {
		 Pattern pattern = Pattern.compile("[0-9]*");
		 if(pattern.matcher(c).matches()) {
			 return true;
		 }
		 return false;
	}
	
	/**
	 * 判断是否是操作符 目前只实现  【 + - * /】
	 * @param c
	 * @return
	 */
	public static boolean isOpe(char c) {
		if(c == '+' || c == '-' 
				|| c== '*' || c == '/' || c == '(' || c==')' ){
			return true;
		}
		return false;
	}
	
	public static boolean isOpe(String c) {
		if("+".equals(c)||"-".equals(c)
				||"*".equals(c)||"/".equals(c) || "(".equals(c) ||")".equals(c)) {
			return true;
		}
		return false;
	}
}
