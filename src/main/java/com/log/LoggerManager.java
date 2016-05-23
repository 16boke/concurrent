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
//	//log池（队列），系统唯一
//	private static LogQueue logQueue1 = null;
//	
//	//log池（队列），系统唯一
//	private static LogQueue logQueue2 = null;
//	
//	//log池（队列），系统唯一
//	private static LogQueue logQueue3 = null;
//	
//	//log池（队列），系统唯一
//	private static LogQueue logQueue4 = null;
//	
//	//打印Log的执行者，消费者模式中的消费者，主线程作为生产者
//	private static Thread logConsumer1 = null;
//	
//	//打印Log的执行者，消费者模式中的消费者，主线程作为生产者
//	private static Thread logConsumer2 = null;
//	
//	//打印Log的执行者，消费者模式中的消费者，主线程作为生产者
//	private static Thread logConsumer3 = null;
//	
//	//打印Log的执行者，消费者模式中的消费者，主线程作为生产者
//	private static Thread logConsumer4 = null;
	
	//标记MarsorLog是否已经配置过
	private static boolean isConfigured = false;
	//标记MarsorLog是否需要打印堆栈信息
	protected static boolean isTraceEnabled = false;
	//标记需要打印堆栈信息的最低Log级别
	protected static Level traceLevel = Level.ALL;
	//Log池的大小，并发Log数大于该数值时，效率等同于Log4j
	protected static int logPoolSize = 1024;
	
	//初始化logConsumer，开始监听系统Log池
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
//			//Log池
//			if(logQueue1==null) {
//				logQueue1 = new LogQueue();
//			}
//			//log执行者，消费者
//			logConsumer1 = new Thread(new LogConsumer(logQueue1,"logQueue1"),"LoggingThread");
//			//logConsumer1.setDaemon(true);
//			//logConsumer1.setContextClassLoader(null);
//		}
//		//线程开始监听
//		logConsumer1.start();
//		
//		if(logConsumer2==null) {
//			//Log池
//			if(logQueue2==null) {
//				logQueue2 = new LogQueue();
//			}
//			//log执行者，消费者
//			logConsumer2 = new Thread(new LogConsumer(logQueue2,"logQueue2"),"LoggingThread");
//			//logConsumer2.setDaemon(true);
//			//logConsumer2.setContextClassLoader(null);
//		}
//		//线程开始监听
//		logConsumer2.start();
//		
//		if(logConsumer3==null) {
//			//Log池
//			if(logQueue3==null) {
//				logQueue3 = new LogQueue();
//			}
//			//log执行者，消费者
//			logConsumer3 = new Thread(new LogConsumer(logQueue3,"logQueue3"),"LoggingThread");
//			//logConsumer3.setDaemon(true);
//			//logConsumer3.setContextClassLoader(null);
//		}
//		//线程开始监听
//		logConsumer3.start();
//		
//		if(logConsumer4==null) {
//			//Log池
//			if(logQueue4==null) {
//				logQueue4 = new LogQueue();
//			}
//			//log执行者，消费者
//			logConsumer4 = new Thread(new LogConsumer(logQueue4,"logQueue4"),"LoggingThread");
//			//logConsumer4.setDaemon(true);
//			//logConsumer4.setContextClassLoader(null);
//		}
//		//线程开始监听
//		logConsumer4.start();
	}
	//不允许外部访问实例
	private LoggerManager() 
	{
		
	}
	
	//同步对象
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
	///异步对象
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
	
	//根据根据配置文件生成Logger实例.
	private static Logger getLogger(String objName) {
		Logger logger = null;
		if(!isConfigured)
		{
		isConfigured=true;
		ClassLoader crClassLoader  = LoggerManager.class.getClassLoader();
		//DOMConfigurator.configure(LoggerManager.class.getResource("/log4j.xml"));
		
		//String strClassPath= "";
		//获取当前类文件所在的目录
		String configFilePath = null;
		
		//默认文件形式 1
		if(crClassLoader.getResource("log4j.properties")==null) {
			//默认文件形式2
			if(crClassLoader.getResource("log4j.xml")==null) {
			
			}else {
				
				//默认log配置文件形式2已存在
				configFilePath = crClassLoader.getResource("log4j.xml").getPath();
			}
		}else {
			//默认log配置文件形式1已存在
			configFilePath = crClassLoader.getResource("log4j.properties").getPath();
		}
		
		//如果配置文件是一个Xml文件
		if(configFilePath.endsWith("xml")) {
			DOMConfigurator.configureAndWatch(configFilePath);
		}else {
			//如果是properties文件
			PropertyConfigurator.configureAndWatch(configFilePath);
			
		}
		
		if(objName == null || objName == "")
		{
			//取得调用堆栈信息
			StackTraceElement[] traces = Thread.currentThread().getStackTrace(); 
			//取得第一个不是本类的方法的类名，并以此为依据创建Log4j的Logger
			for(StackTraceElement element :traces) {
				String[] exceptClasses=new String[] {LoggerManager.class.getName(),Thread.class.getName(),LogConsumer.class.getName(),LogTask.class.getName()};
				if(!ArrayUtils.contains(exceptClasses, element.getClassName())) {
					logger= LogManager.getLogger(element.getClassName());
					break;
				}
			}
			
		}
		}
		//如果未能创建Logger，创建默认的Logger
		if(logger==null) {
			logger = LogManager.getLogger(objName);
		}
		return logger;
	}
	
	///获取队列信息
	protected static String getTraceInfo() {
		StringBuffer sbResult = new StringBuffer("Traces: \r\n");
		//取得调用堆栈信息
		StackTraceElement[] traces = Thread.currentThread().getStackTrace(); 
		//取得非本类调用的所有堆栈信息
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
