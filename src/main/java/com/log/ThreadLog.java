package com.log;



public class ThreadLog implements Runnable
{
	String string="<?xml version=\"1.0\" encoding=\"GBK\"?><Platform><PlatUrl>http://192.168.153.105/circauto/servlet/insurance/interface</PlatUrl><PACKET type=\"REQUEST\" version=\"1.0\"><HEAD><REQUEST_TYPE>01</REQUEST_TYPE><USER>picc_test</USER><PASSWORD>111111</PASSWORD></HEAD><BODY><BASE_PART><QUERY_SEQUENCE_NO/><DISTRICT_CODE/><CAR_MARK>京65437</CAR_MARK><VEHICLE_TYPE>02</VEHICLE_TYPE><VEHICLE_CATEGORY>11</VEHICLE_CATEGORY><USE_TYPE>210</USE_TYPE><ENGINE_NO>7543654</ENGINE_NO><RACK_NO>75436543636543655</RACK_NO><USE_AGES>2</USE_AGES><MILEAGES>10000</MILEAGES><NEW_VEHICLE_FLAG>0</NEW_VEHICLE_FLAG><ECDEMIC_VEHICLE_FLAG>0</ECDEMIC_VEHICLE_FLAG><PC_VEHICLE_CATEGORY>K11</PC_VEHICLE_CATEGORY><OWNER>test</OWNER><LIMIT_LOAD_PERSON>5</LIMIT_LOAD_PERSON><PO_WEIGHT>1050</PO_WEIGHT><EXHAUST_CAPACITY>1297</EXHAUST_CAPACITY><VEHICLE_REGISTER_DATE>20100101</VEHICLE_REGISTER_DATE><CERTIFICATE_DATE>20100101</CERTIFICATE_DATE><VEHICLE_BRAND>奇瑞</VEHICLE_BRAND><VEHICLE_MODEL>SQR7130S167</VEHICLE_MODEL><DRIVER_NUM>0</DRIVER_NUM><PAY_TAX_FLAG>0</PAY_TAX_FLAG><RESTRIC_FLAG/><POLICY_FLAG>02</POLICY_FLAG><MADE_DATE/><VEHICLE_STYLE_DESC>轿车,手动档 标准型 国IV</VEHICLE_STYLE_DESC><LIMIT_LOAD>0</LIMIT_LOAD><SEARCH_SEQUENCE_NO>39PICC02120000000000009448221D</SEARCH_SEQUENCE_NO><BUSINESS_CHANNEL>11</BUSINESS_CHANNEL><COMPUTER_IP>12.1.37.216</COMPUTER_IP><USBKEY/><POS_NO/><FUEL_TYPE>A</FUEL_TYPE><PAY_NO/><DEPARTMENT_NONLOCAL/><AGENT_NAME>921000</AGENT_NAME><CERTI_TYPE>99</CERTI_TYPE><CERTI_CODE>654377543753</CERTI_CODE><INSURED_NAME>test</INSURED_NAME></BASE_PART><DRIVER_LIST/><COVERAGE_LIST><COVERAGE/></COVERAGE_LIST></BODY></PACKET></Platform>";
	int index=0;
	public ThreadLog(int index)
	{
		this.index = index;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run()
	{
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 200; i++) {
			//同步
			try {
				//LoggerManager.Synchronous.debug("1100",Thread.currentThread().getName()+" "+string+" 【"+i+"】");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//异步
			LoggerManager.Asynchronous.debug("1100"," 【"+i+"】"+Thread.currentThread().getName()+" "+string);
			//原来写日志
//			try {
//				LogUtils.output(string, "1100");
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		long end = System.currentTimeMillis();
		if(Time.getEnd() < end){
			Time.setEnd(end);
			System.out.println("总时间 = "+(Time.getEnd() - Time.getStart()));
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("线程："+Thread.currentThread().getName()+"写完50次所需要的时间："+time);
	}
}
