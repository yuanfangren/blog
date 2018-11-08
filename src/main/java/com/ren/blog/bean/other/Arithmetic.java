package com.ren.blog.bean.other;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class Arithmetic {
	
    //操作符stack
    private Stack<String> operator = new Stack<String>();
    //后缀表达式
    private List<String> postFix = new ArrayList<String>();

    private Pattern operatorPattern = Pattern.compile("[0-9]");
    private Pattern arithmeticPattern = Pattern.compile("[\\(\\)\\+\\-/\\*]");

    //将中缀表达式换为后缀表达式
    public void parse(String s) {
        s = replace(s);
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            String temp = s.substring(i, i + 1);
            if (operatorPattern.matcher(temp).matches()) {
                continue;
            }
            if (arithmeticPattern.matcher(temp).matches()) {
                j = process(j, i, s, temp);
            } else if ("".equals(temp)) {
                return;
            }
        }
        
        //最后一个数字
        if (j < s.length()) {
            postFix.add(s.substring(j, s.length()));
        }
        //操作符栈中剩余内容
        while (!this.operator.isEmpty()) {
            postFix.add(operator.pop());
        }
    }

    /**
     * 去除空白字符
     * @param s
     * @return
     */
    private String replace(String s) {
        return s.replaceAll("\\s", "");
    }

    private int process(int startIndex, int currentIndex, String str, String word) {
        if (startIndex != currentIndex) {
            postFix.add(str.substring(startIndex, currentIndex));
        }
        addOperator(word);
        startIndex = currentIndex + 1;
        if (startIndex > str.length()) {
            startIndex = str.length();
        }
        return startIndex;
    }

    public void addOperator(String operator) {
        if ("(".equals(operator)) {

        } else if (")".equals(operator)) {
            while (!this.operator.isEmpty()) {
                String temp = this.operator.pop();
                if ("(".equals(temp)) {
                    break;
                } else {
                    postFix.add(temp);
                }

            }
            return;
        } else if (!this.operator.isEmpty()) {
            while (!this.operator.isEmpty()) {
                String temp = this.operator.peek();
                if (needPop(temp, operator)) {
                    this.postFix.add(this.operator.pop());
                } else {
                    break;
                }
            }
        }
        this.operator.add(operator);
    }

    public boolean needPop(String inStackTop, String current) {
        return getLevel(current.charAt(0)) <= getLevel(inStackTop.charAt(0));
    }

    public int getLevel(char operator) {
        switch (operator) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    public BigDecimal compute() {
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        for (int i = 0; i < this.postFix.size(); i++) {
            if (arithmeticPattern.matcher(postFix.get(i)).matches()) {
                BigDecimal bd2 = stack.pop();
                BigDecimal bd1 = stack.pop();
                BigDecimal temp = compute(postFix.get(i).charAt(0), bd1, bd2);
                stack.add(temp);
            } else {
                stack.add(new BigDecimal(postFix.get(i)));
            }
        }
        return stack.pop();
    }

    private BigDecimal compute(char operator, BigDecimal bd1, BigDecimal bd2) {
        switch (operator) {
            case '+':
                return bd1.add(bd2);
            case '-':
                return bd1.subtract(bd2);
            case '*':
                return bd1.multiply(bd2);
            case '/':
                return bd1.divide(bd2);//应当使用bd1.divide(divisor, scale, roundingMode);
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Arithmetic arithmetic = new Arithmetic();
        arithmetic.parse("9 + (3 - 1-1+1) * 3 + 10 / 2");
        System.out.println(arithmetic.postFix);
        System.out.println(arithmetic.compute());

       /* arithmetic = new Arithmetic();
        arithmetic.parse("9 + (3 - 1) * 3 + 10 / 2 + 1000 - 200 - (100 + 5 - 9 / 3)");
        System.out.println(arithmetic.postFix);
        System.out.println(arithmetic.compute());

        arithmetic = new Arithmetic();
        arithmetic.parse("9 + (3 - 1) * 3 + 10 / 2 + 1000 - 200 - (100 + 5 - 9 / 3)");
        System.out.println(arithmetic.postFix);
        System.out.println(arithmetic.compute());*/
    }
}