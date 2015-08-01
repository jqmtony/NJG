/*
 * @(#)IJobMonitor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

/**
 * 为长时间任务提供进度条
 * 
 * @author allenhe  date:2007-1-19 <p>
 * @version EAS5.1.3
 */
public interface IFDCProgressMonitor {

	/**
	 * 开始一个任务
	 * 
	 * @param name
	 * @param totalWork
	 *            如为-1,表示不能预测任务数，进度条将会来回晃动
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void beginTask(String name, int totalWork);

	/**
	 * 任务完成。
	 * <p>
	 * 可能是用户手工结束。
	 * 
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void done();

	/**
	 * 是否取消
	 * 
	 * @return
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public boolean isCanceled();

	/**
	 * 设置取消与否，用户强制取消该长时间操作。
	 * @param value
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void setCanceled(boolean value);

	/**
	 * 设置任务名称
	 * 
	 * @param name
	 * @author:allenhe 2007-1-18
	 *                 <p>
	 */
	public void setTaskName(String name);
	
	/**
	 * 通知子任务开始
	 * @param name
	 * @author:allenhe 2007-1-18 <p>
	 */
	public void subTaskBegin(String name);
	
	/**
	 * 完成的工作数
	 * 
	 * @param work
	 * @author:allenhe 2007-1-18 <p>
	 */
	public void worked(int work);
	
	public void stopTimer();
}
