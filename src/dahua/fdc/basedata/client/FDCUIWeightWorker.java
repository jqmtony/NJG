package com.kingdee.eas.fdc.basedata.client;

import EDU.oswego.cs.dl.util.concurrent.BoundedLinkedQueue;

import com.kingdee.bos.ctrl.kdf.data.logging.Logger;

/**
 * �����ӵĺ�̨�����ӽ���ķַ��߳̽�ų�����������ø��õĽ�����Ӧ
 * @author xiaohong_shi
 *
 */
public class FDCUIWeightWorker {
	private final static int cap=100;
	private final BoundedLinkedQueue taskQueue=new BoundedLinkedQueue(cap);
//	private final List taskQueue=Collections.synchronizedList(new LinkedList());
	private Thread workThread; 
	/**
	 * ����Ҫͬ���Ķ��Գ�ʼ��holder��
	 * @author xiaohong_shi
	 *
	 */
	private static class FDCUIWeightWorkerHolder{
		private static final FDCUIWeightWorker worker=new FDCUIWeightWorker(); 
	}
	private FDCUIWeightWorker(){
		init();
	}
	private void init(){
		workThread=new Thread(new Runnable(){
			public void run() {
				while(true){
					try {
						getWork().run();
					} catch (InterruptedException e) {
						// @AbortException
						Logger.error(e, e.getMessage());
					}
				}
			}
		});
		workThread.start();
	}
	
	public static FDCUIWeightWorker getInstance(){
		return FDCUIWeightWorkerHolder.worker;
	}

	public void addWork(IFDCWork work){
		try{
			taskQueue.put(work);
		}catch (InterruptedException e){
			// @AbortException
			handleInterruptedException(e);
		}
	}
	private IFDCWork getWork() throws InterruptedException{
		return (IFDCWork) taskQueue.take();
	}
	
	void run(){
		try {
			if(workThread==null||!workThread.isAlive()){
				init();
			}
			getWork().run();
		} catch (InterruptedException e) {
			// @AbortException
			handleInterruptedException(e);
		}
	}
	
	public boolean isWork() {
		return !taskQueue.isEmpty();
	}
	
	private void handleInterruptedException(InterruptedException e){
		e.printStackTrace();
	}
	public void reset(){
		init();
	}
}
