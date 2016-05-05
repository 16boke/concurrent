package com.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	public static void main(String[] args) {
		class MyTimerTask extends TimerTask{
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis());
			}
		}
		new Timer().schedule(new MyTimerTask(), 5000,1000);
	}
}
