package com.log;

public class MutiLogTest 
{
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		Time.setStart(start);
		for(int i=0;i<20;i++){
			new ThreadLog(i);
			try {
				//Thread.sleep(10);
			} catch (Exception e) {}
		}
	}
}
