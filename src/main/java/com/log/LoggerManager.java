package com.log;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class LoggerManager {
	private static int count = 4;
	private static List<LogQueue>logQueueList = new ArrayList<LogQueue>(count);
	private static List<Thread> logConsumerList = new ArrayList<Thread>(count);
//	//log�أ����У���ϵͳΨһ
//	private static LogQueue logQueue1 = null;
//	
//	//log�أ����У���ϵͳΨһ
//	private static LogQueue logQueue2 = null;
//	
//	//log�أ����У���ϵͳΨһ
//	private static LogQueue logQueue3 = null;
//	
//	//log�أ����У���ϵͳΨһ
//	private static LogQueue logQueue4 = null;
//	
//	//��ӡLog��ִ���ߣ�������ģʽ�е������ߣ����߳���Ϊ������
//	private static Thread logConsumer1 = null;
//	
//	//��ӡLog��ִ���ߣ�������ģʽ�е������ߣ����߳���Ϊ������
//	private static Thread logConsumer2 = null;
//	
//	//��ӡLog��ִ���ߣ�������ģʽ�е������ߣ����߳���Ϊ������
//	private static Thread logConsumer3 = null;
//	
//	//��ӡLog��ִ���ߣ�������ģʽ�е������ߣ����߳���Ϊ������
//	private static Thread logConsumer4 = null;
	
	//���MarsorLog�Ƿ��Ѿ����ù�
	private static boolean isConfigured = false;
	//���MarsorLog�Ƿ���Ҫ��ӡ��ջ��Ϣ
	protected static boolean isTraceEnabled = false;
	//�����Ҫ��ӡ��ջ��Ϣ�����Log����
	protected static Level traceLevel = Level.ALL;
	//Log�صĴ�С������Log�����ڸ���ֵʱ��Ч�ʵ�ͬ��Log4j
	protected static int logPoolSize = 1024;
	
	//��ʼ��logConsumer����ʼ����ϵͳLog��
	static {
		for(int i=0;i<count;i++){
			logConsumerList.add(i,null);
			logQueueList.add(i,null);
			if(logConsumerList.get(i) == null){
				if(logQueueList.get(i) == null){
					logQueueList.set(i, new LogQueue());
				}
				logConsumerList.set(i, new Thread(new LogConsumer(logQueueList.get(i),"logQueue"+i),"LoggingThread"));
				//logConsumerList.get(i).setDaemon(true);
				//logConsumerList.get(i).setContextClassLoader(null);
			}
			logConsumerList.get(i).start();
		}
//		if(logConsumer1==null) {
//			//Log��
//			if(logQueue1==null) {
//				logQueue1 = new LogQueue();
//			}
//			//logִ���ߣ�������
//			logConsumer1 = new Thread(new LogConsumer(logQueue1,"logQueue1"),"LoggingThread");
//			//logConsumer1.setDaemon(true);
//			//logConsumer1.setContextClassLoader(null);
//		}
//		//�߳̿�ʼ����
//		logConsumer1.start();
//		
//		if(logConsumer2==null) {
//			//Log��
//			if(logQueue2==null) {
//				logQueue2 = new LogQueue();
//			}
//			//logִ���ߣ�������
//			logConsumer2 = new Thread(new LogConsumer(logQueue2,"logQueue2"),"LoggingThread");
//			//logConsumer2.setDaemon(true);
//			//logConsumer2.setContextClassLoader(null);
//		}
//		//�߳̿�ʼ����
//		logConsumer2.start();
//		
//		if(logConsumer3==null) {
//			//Log��
//			if(logQueue3==null) {
//				logQueue3 = new LogQueue();
//			}
//			//logִ���ߣ�������
//			logConsumer3 = new Thread(new LogConsumer(logQueue3,"logQueue3"),"LoggingThread");
//			//logConsumer3.setDaemon(true);
//			//logConsumer3.setContextClassLoader(null);
//		}
//		//�߳̿�ʼ����
//		logConsumer3.start();
//		
//		if(logConsumer4==null) {
//			//Log��
//			if(logQueue4==null) {
//				logQueue4 = new LogQueue();
//			}
//			//logִ���ߣ�������
//			logConsumer4 = new Thread(new LogConsumer(logQueue4,"logQueue4"),"LoggingThread");
//			//logConsumer4.setDaemon(true);
//			//logConsumer4.setContextClassLoader(null);
//		}
//		//�߳̿�ʼ����
//		logConsumer4.start();
	}
	//�������ⲿ����ʵ��
	private LoggerManager() 
	{
		
	}
	
	//ͬ������
	public static class Synchronous
	{
		public static void debug(String comCode,Object message,Throwable t) throws Exception {
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,t,Level.DEBUG);
		}
		public static void debug(String comCode,Object message) throws Exception {
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,null,Level.DEBUG);
		}
		public static void info(String comCode,Object message,Throwable t) throws Exception {
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,t,Level.INFO);
		}
		public static void info(String comCode,Object message)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,null,Level.INFO);
		}
		public static void warn(String comCode,Object message,Throwable t)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,t,Level.WARN);
		}
		public static void warn(String comCode,Object message)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,null,Level.WARN);
		}
		public static void error(String comCode,Object message,Throwable t)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,t,Level.ERROR);
		}
		public static void error(String comCode,Object message)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,null,Level.ERROR);
		}
		public static void fatal(String comCode,Object message,Throwable t)  throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,t,Level.FATAL);
		}
		public static void fatal(String comCode,Object message) throws Exception{
			Logger log = Logger.getLogger(LoggerManager.class);
			LogUtils.synLogPrint(comCode,log,message,null,Level.FATAL);
		}
	}
	///�첽����
	public static class Asynchronous
	{
		public static void debug(String comCode,String objName,Object message,Throwable t) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,t,Level.DEBUG));
		} 
		public static void debug(String comCode,String objName,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,null,Level.DEBUG));
		} 
		
		public static void debug(String comCode,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(),message,null,Level.DEBUG));
		} 
		public static void info(String comCode,String objName,Object message,Throwable t) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,t,Level.INFO));
		} 
		public static void info(String comCode,String objName,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,null,Level.INFO));
		} 
		
		public static void info(String comCode,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(),message,null,Level.INFO));
		}
		public static void warn(String comCode,String objName,Object message,Throwable t) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,t,Level.WARN));
		} 
		public static void warn(String comCode,String objName,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,null,Level.WARN));
		} 
		
		public static void warn(String comCode,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(),message,null,Level.WARN));
		} 
		
		public static void error(String comCode,String objName,Object message,Throwable t) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,t,Level.ERROR));
		}
		public static void error(String comCode,String objName,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,null,Level.ERROR));
		}
		
		public static void error(String comCode,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(),message,null,Level.ERROR));
		}
		
		public static void fatal(String comCode,String objName,Object message,Throwable t) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,t,Level.FATAL));
		} 
		public static void fatal(String comCode,String objName,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(objName),message,null,Level.FATAL));
		} 
		
		public static void fatal(String comCode,Object message) {
			LogQueue logQ = getMinQueue();
			logQ.push(new LogTask(comCode,getLogger(),message,null,Level.FATAL));
		} 
		
	}

	private static LogQueue getMinQueue()
	{
		LogQueue logQ = logQueueList.get(0);
		int min = logQ.getCount();
		int temp = 0;
		for(int i=1;i<count;i++){
			temp = logQueueList.get(i).getCount();
			if(temp < min){
				min = temp;
				logQ = logQueueList.get(i);
			}
		}
		return logQ;
		
		
//		LogQueue logQ = logQueue1;
//		int min = logQ.getCount();
//		int tempNum = logQueue2.getCount();
//		
//		if(tempNum < min)
//		{
//			min = tempNum;
//			logQ = logQueue2;
//		}
//		
//		tempNum = logQueue3.getCount();
//		if(tempNum < min)
//		{
//			min = tempNum;
//			logQ = logQueue3;
//		}
//		tempNum = logQueue4.getCount();
//		if(tempNum < min)
//		{
//			min = tempNum;
//			logQ = logQueue4;
//		}
//		return logQ;
	}

	private static Logger getLogger() {
		return getLogger(LoggerManager.class.getName());
	}
	
	//���ݸ��������ļ�����Loggerʵ��.
	private static Logger getLogger(String objName) {
		Logger logger = null;
		if(!isConfigured)
		{
		isConfigured=true;
		ClassLoader crClassLoader  = LoggerManager.class.getClassLoader();
		//DOMConfigurator.configure(LoggerManager.class.getResource("/log4j.xml"));
		
		//String strClassPath= "";
		//��ȡ��ǰ���ļ����ڵ�Ŀ¼
		String configFilePath = null;
		
		//Ĭ���ļ���ʽ 1
		if(crClassLoader.getResource("log4j.properties")==null) {
			//Ĭ���ļ���ʽ2
			if(crClassLoader.getResource("log4j.xml")==null) {
			
			}else {
				
				//Ĭ��log�����ļ���ʽ2�Ѵ���
				configFilePath = crClassLoader.getResource("log4j.xml").getPath();
			}
		}else {
			//Ĭ��log�����ļ���ʽ1�Ѵ���
			configFilePath = crClassLoader.getResource("log4j.properties").getPath();
		}
		
		//��������ļ���һ��Xml�ļ�
		if(configFilePath.endsWith("xml")) {
			DOMConfigurator.configureAndWatch(configFilePath);
		}else {
			//�����properties�ļ�
			PropertyConfigurator.configureAndWatch(configFilePath);
			
		}
		
		if(objName == null || objName == "")
		{
			//ȡ�õ��ö�ջ��Ϣ
			StackTraceElement[] traces = Thread.currentThread().getStackTrace(); 
			//ȡ�õ�һ�����Ǳ���ķ��������������Դ�Ϊ���ݴ���Log4j��Logger
			for(StackTraceElement element :traces) {
				String[] exceptClasses=new String[] {LoggerManager.class.getName(),Thread.class.getName(),LogConsumer.class.getName(),LogTask.class.getName()};
				if(!ArrayUtils.contains(exceptClasses, element.getClassName())) {
					logger= LogManager.getLogger(element.getClassName());
					break;
				}
			}
			
		}
		}
		//���δ�ܴ���Logger������Ĭ�ϵ�Logger
		if(logger==null) {
			logger = LogManager.getLogger(objName);
		}
		return logger;
	}
	
	///��ȡ������Ϣ
	protected static String getTraceInfo() {
		StringBuffer sbResult = new StringBuffer("Traces: \r\n");
		//ȡ�õ��ö�ջ��Ϣ
		StackTraceElement[] traces = Thread.currentThread().getStackTrace(); 
		//ȡ�÷Ǳ�����õ����ж�ջ��Ϣ
		StringBuffer sbPrefix = null;
		for(int i=0,j=0;i<traces.length;i++,j++) {
			StackTraceElement element = traces[i];
			String[] exceptClasses=new String[] {LoggerManager.class.getName(),Thread.class.getName(),LogConsumer.class.getName(),LogTask.class.getName()};
			if(ArrayUtils.contains(exceptClasses, element.getClassName())) {
				j--;
				continue;
			}
			sbPrefix = new StringBuffer("|-");
			for(int k=j;k>0;k--) {
				sbPrefix.append("-");
			}
			sbResult.append(sbPrefix.toString()+element.toString()+"\r\n");
		}
		if(sbPrefix==null||sbPrefix.length()==2) {
			return null;
		}
		return sbResult.toString();
	}
}
