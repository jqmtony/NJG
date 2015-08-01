/*
 * @(#)IJobMonitor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.client;

/**
 * Ϊ��ʱ�������ṩ������
 * 
 * @author allenhe  date:2007-1-19 <p>
 * @version EAS5.1.3
 */
public interface IFDCProgressMonitor {

	/**
	 * ��ʼһ������
	 * 
	 * @param name
	 * @param totalWork
	 *            ��Ϊ-1,��ʾ����Ԥ�����������������������ػζ�
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void beginTask(String name, int totalWork);

	/**
	 * ������ɡ�
	 * <p>
	 * �������û��ֹ�������
	 * 
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void done();

	/**
	 * �Ƿ�ȡ��
	 * 
	 * @return
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public boolean isCanceled();

	/**
	 * ����ȡ������û�ǿ��ȡ���ó�ʱ�������
	 * @param value
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void setCanceled(boolean value);

	/**
	 * ������������
	 * 
	 * @param name
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void setTaskName(String name);
	
	/**
	 * ֪ͨ������ʼ
	 * @param name
	 * @author:allenhe 2007-1-18 <p>
	 */
	public void subTaskBegin(String name);
	
	/**
	 * ��ɵĹ�����
	 * 
	 * @param work
	 * @author:allenhe 2007-1-18 <p>
	 */
	public void worked(int work);
	
	public void stopTimer();
}
