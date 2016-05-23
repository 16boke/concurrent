/**
 * ��־������
 */
package com.log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;

public class LogUtils {
	/**
     * ���ݴ�����ļ���ÿ��һ��
     * @param content  ��������
     * @param areaCode ���ݵĵ���
     * @throws IOException
     */
	public static synchronized void output(String content,String areaCode) throws IOException, Exception {
		//�����ļ�·�����ļ���
		java.util.Date date = new java.util.Date();
		StringBuffer filePath = new StringBuffer();
		StringBuffer dirPath = new StringBuffer();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String datetime = simpleDateFormat.format(date);
			dirPath.append(datetime);
			filePath = new StringBuffer(dirPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(filePath.toString());
		if(!file.exists()){
			File fileDir = new File(dirPath.toString());
			fileDir.mkdir();
			file = new File(filePath.toString());
		}
		BufferedWriter bufferWriter = new BufferedWriter(
				new OutputStreamWriter(
						new BufferedOutputStream(
								new FileOutputStream(file,true)), "GBK"));
		bufferWriter.write(content.concat("\r\n"));
		bufferWriter.flush();
		bufferWriter.close();
	}
	/**
	 * �첽��ӡ��־
	 * @param level
	 * @param task
	 * @throws Exception
	 */
	public static synchronized void asynLogPrint(int level,LogTask task) throws Exception{
		Logger log = task.getLogger();
		log.addAppender(getAppender(task.getComCode()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("<").append(dateTime).append(">").append(" ").append(task.getObjMessage().toString().replaceAll("\r\n", ""));
		switch (level) {
			case Level.INFO_INT:
				log.info(str,task.getException());
				break;
			case Level.DEBUG_INT:
				log.debug(str,task.getException());
				break;
			case Level.WARN_INT:
				log.warn(str,task.getException());
				break;
			case Level.ERROR_INT:
				log.error(str,task.getException());
				break;
			case Level.FATAL_INT:
				log.fatal(str,task.getException());
				break;
		}
		log.removeAllAppenders();
	}
	/**
	 * ͬ����ӡ��־
	 * @param comCode
	 * @param log
	 * @param objName
	 * @param message
	 * @param t
	 * @param debug
	 */
	public static synchronized void synLogPrint(String comCode, Logger log, 
			Object message, Throwable t, Level lev) throws Exception {
		int level = ((Priority)lev).toInt();
		log.addAppender(getAppender(comCode));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("<").append(dateTime).append(">").append(" ").append(message.toString().replaceAll("\r\n", ""));
		switch (level) {
			case Level.INFO_INT:
				log.info(str,t);
				break;
			case Level.DEBUG_INT:
				log.debug(str,t);
				break;
			case Level.WARN_INT:
				log.warn(str,t);
				break;
			case Level.ERROR_INT:
				log.error(str,t);
				break;
			case Level.FATAL_INT:
				log.fatal(str,t);
				break;
		}
		log.removeAllAppenders();
	}
	
	public static Appender getAppender(String areaCode) throws Exception{
		FileAppender appender = null;
		SimpleLayout layout = new SimpleLayout();
		//�����ļ�·�����ļ���
		java.util.Date date = new java.util.Date();
		StringBuffer filePath = new StringBuffer();
		StringBuffer dirPath = new StringBuffer();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String datetime = simpleDateFormat.format(date);
			dirPath.append(datetime);
			filePath = new StringBuffer(dirPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file = new File(filePath.toString());
		if(!file.exists()){
			File fileDir = new File(dirPath.toString());
			fileDir.mkdir();
			file = new File(filePath.toString());
		}
		try {
			appender = new FileAppender(layout, filePath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appender;
	}
}
