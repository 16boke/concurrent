package com.log;

public class Time {
	private static long start=0;
	private static long end=0;
	
	public static long getEnd() {
		return end;
	}

	public static void setEnd(long end) {
		Time.end = end;
	}

	public static long getStart() {
		return start;
	}

	public static void setStart(long start) {
		Time.start = start;
	}
	

}
