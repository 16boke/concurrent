package com.log;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Log���С��̰߳�ȫ�ġ�
 * */
class LogQueue{
	
	//���ڴ��Log����Ķ���
	private ArrayList<LogTask> queue = new ArrayList<LogTask>(LoggerManager.logPoolSize);
	/**
	 * ������в���һ��Log���񣨶�β������������������ȴ�
	 * @param task Log����
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
	 * �Ӷ�����ȡ��һ��Log���񣨶��ף�
	 * @return ȡ����Log���������Ϊ�գ��ȴ�
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
	 * ������в���һ��Log���񣨶�β������������������ȴ�
	 * @param task Log����
	 * */
	public synchronized int getCount() {
		return queue.size();
	}
	
	public String Name = ""; 
}

/**
 * ��ӡһ��Log��Ҫ��Logger����Ϣ��������Ϣ���Լ�ʹ�õļ���Debug��Info��Warn��Error��Fatal��
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