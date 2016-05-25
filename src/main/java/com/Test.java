package com;

public class Test {
	public static void main(String[] args) {
		System.out.println(Integer.SIZE);
		//Integer.SIZE表示Integer数据类型的长度为32位
		int COUNT_BITS = Integer.SIZE - 3;
		System.out.println(COUNT_BITS);
		//1 << COUNT_BITS 表示2进制的1向左移COUNT_BITS位数，后面补0
		System.out.println(1 << COUNT_BITS);
		System.out.println(Integer.toBinaryString(1 << COUNT_BITS));
		int CAPACITY   = (1 << COUNT_BITS) - 1;
		System.out.println(CAPACITY);
		System.out.println(Integer.toBinaryString(CAPACITY));
		
	}
}
