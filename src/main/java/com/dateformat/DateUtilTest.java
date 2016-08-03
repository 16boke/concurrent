package com.dateformat;

import java.text.ParseException;

public class DateUtilTest {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							//当前线程中断2秒
							Thread.currentThread().join(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						try {
							System.out.println(Thread.currentThread().getName() + ":" + DateUtil.parse("2016-08-01 06:02:20"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
}
