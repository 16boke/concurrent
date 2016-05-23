package com.log;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Log队列。线程安全的。
 * */
class LogQueue{
	
	//用于存放Log任务的队列
	private ArrayList<LogTask> queue = new ArrayList<LogTask>(LoggerManager.logPoolSize);
	/**
	 * 向队列中插入一个Log任务（队尾），如果队列已满，等待
	 * @param task Log任务
	 * */
	public synchronized void push(LogTask task) {
		while(queue.size()>=LoggerManager.logPoolSize) {
			try {
				this.wait();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		queue.add(task);
		this.notify();
	}
	
	/**
	 * 从队列中取出一个Log任务（队首）
	 * @return 取出的Log，如果队列为空，等待
	 * */
	public synchronized LogTask pull() {
		while(queue.size()<=0) {
			try {
				this.wait();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		LogTask task = queue.get(0);
		queue.remove(0);
		this.notify();
		return task;
	}
	
	/**
	 * 向队列中插入一个Log任务（队尾），如果队列已满，等待
	 * @param task Log任务
	 * */
	public synchronized int getCount() {
		return queue.size();
	}
	
	public String Name = ""; 
}

/**
 * 打印一次Log需要的Logger，消息，错误信息，以及使用的级别（Debug，Info，Warn，Error，Fatal）
 * */
class LogTask{
	private String comCode = "";
	private Logger logger = null;
	private Object objMessage = null;
	private Throwable exception = null;
	private Level level = null;
	private String locationInfo = "";
	private String traceInfo = "";
	
	public LogTask(String comCode,Logger logger,Object Message,Throwable t,Level level) {
		this.comCode = comCode;
		this.objMessage = Message;
		this.exception = t;
		this.logger = logger;
		this.level = level;
	}
	public Object getObjMessage() {
		return objMessage;
	}
	public void setObjMessage(Object objMessage) {
		this.objMessage = objMessage;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public String getLocationInfo() {
		return locationInfo;
	}
	
	public String getTraceInfo() {
	
		return traceInfo;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
}

class ArrayUtils{
	public static boolean contains(Object[] objs,Object obj){
		for(Object o:objs){
			if(o.equals(obj)){
				return true;
			}
		}
		return false;	
	}
}