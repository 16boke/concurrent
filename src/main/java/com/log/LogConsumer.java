package com.log;

import org.apache.log4j.NDC;
import org.apache.log4j.Priority;

/**
 * Log任务的执行者，真正向输出流打印Log的执行者。<br>
 * 从系统Log队列中获取Log任务，并执行打印。
 * */
public class LogConsumer extends Thread{
	private LogQueue logQueue = null;
	private int logCount = 0;
	//构造一个任务执行器
	public LogConsumer(LogQueue logQueue,String name){
		this.logQueue = logQueue;
		this.logQueue.Name = name;
	}

	@Override
	public void run() {
		while (true) {
			logCount++;
			try {
				//从队列中取得一个任务，如果没有，本线程会自动睡眠
				LogTask task = this.logQueue.pull();
				NDC.push(task.getLocationInfo());
				int level = ((Priority)task.getLevel()).toInt();
				LogUtils.asynLogPrint(level,task);
			}catch(Exception e) {e.printStackTrace();}finally {
				NDC.clear();
			}
			//当打印256条Log以后，进行垃圾回收。没有用的LogTask应该被系统回收。
			if(logCount>256) {
				logCount=0;
				//System.gc();
			}
		}
	}
}
