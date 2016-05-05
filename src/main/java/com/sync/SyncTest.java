package com.sync;

/**
 * �߳�ͬ�� ���߳�ѭ��10�Σ��������߳�ѭ��100�Σ������ֻص����߳�ѭ��10�Σ������ٻص����߳�ѭ��100�Σ����ѭ��50��
 * 
 * @author wyc
 * 
 */
public class SyncTest {
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}

	}
}
/**
 * ���ⷽ����������ͬһ�����У�������ʹ����ͬ����ֽ��룬�ſ���ʹ��ͬ����
 * Ҫ�õ���ͬ���ݣ�����ͬ�����������ɷ���Ӧ��д��ͬһ������
 * @author wyc
 *
 */
class Business {
	boolean subShould = true;
	public synchronized void sub(int i) {
		//����������������̣�����ͣ
		if(!subShould){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//������������ӽ��̣������ִ��
		for (int j = 1; j <= 10; j++) {
			System.out.println("sub thread:" + j + ",loop:" + i);
		}
		//ע�⣺ִ����֮��һ��Ҫ�ǵý���ͣ���̻߳��ѣ�����˽�����Զ�ᴦ����ͣ״̬
		subShould = false;
		this.notify();
	}

	public synchronized void main(int i) {
		//��������������̣߳�����ͣ
		if(subShould){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//��������������̣߳������ִ��
		for (int j = 1; j <= 100; j++) {
			System.out.println("main thread:" + j + ",loop:" + i);
		}
		//ִ����֮��һ��Ҫ�ǵý����̻߳���
		subShould = true;
		this.notify();
	}
}
