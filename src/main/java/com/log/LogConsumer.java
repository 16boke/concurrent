package com.log;

import org.apache.log4j.NDC;
import org.apache.log4j.Priority;

/**
 * Log�����ִ���ߣ��������������ӡLog��ִ���ߡ�<br>
 * ��ϵͳLog�����л�ȡLog���񣬲�ִ�д�ӡ��
 * */
public class LogConsumer extends Thread{
	private LogQueue logQueue = null;
	private int logCount = 0;
	//����һ������ִ����
	public LogConsumer(LogQueue logQueue,String name){
		this.logQueue = logQueue;
		this.logQueue.Name = name;
	}

	@Override
	public void run() {
		while (true) {
			logCount++;
			try {
				//�Ӷ�����ȡ��һ���������û�У����̻߳��Զ�˯��
				LogTask task = this.logQueue.pull();
				NDC.push(task.getLocationInfo());
				int level = ((Priority)task.getLevel()).toInt();
				LogUtils.asynLogPrint(level,task);
			}catch(Exception e) {e.printStackTrace();}finally {
				NDC.clear();
			}
			//����ӡ256��Log�Ժ󣬽����������ա�û���õ�LogTaskӦ�ñ�ϵͳ���ա�
			if(logCount>256) {
				logCount=0;
				//System.gc();
			}
		}
	}
}
