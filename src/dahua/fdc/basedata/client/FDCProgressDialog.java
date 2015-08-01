/*
 * @(#)ProgressDialog.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.CtrlUIEnv;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDProgressBar;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 长时间操作的进度条对话框。<p>
 * 
 * 使用方法:<p>
 * 	<pre>
		ProgressDialog dialog = ProgressDialog.createProgressDialog(this, true);
		dialog.run(false, new IRunnableWithProgress(){
			public void run(IProgressMonitor monitor) {
				try{
					monitor.beginTask("操作XX", 10);
					monitor.setTaskName("正在操作XX，请稍候 ...");
					for(int i<=0; i<10; i++){
						monitor.subTaskBegin("操作1 ...");
						....
						monitor.worked(1);
					}
				}finally{
					monitor.done();
				}
			}
		});
 *  </pre>
 * @author allenhe  date:2007-1-19 <p>
 * @version EAS5.1.3
 */
public class FDCProgressDialog extends KDDialog {
    private static final Logger logger = CoreUIObject.getLogger(FDCProgressDialog.class);
    private KDLabel labelTaskName;
    private KDLabel labelIcon;
    private KDProgressBar progressBar;
    private KDLabel subTaskName;
    private KDLabel labelTimer;
    private KDButton btnCancel;

    private IFDCProgressMonitor monitor;
    
    private static final String COMMAND_CANCEL = "CANCEL";
	private boolean cancelable;
	private boolean enableTimer;
	private Timer timer;
    /**
     * output class constructor
     */
    public FDCProgressDialog(){
        super();
        initUI();
    }
    
    public FDCProgressDialog(Frame owner, boolean modal){
    	super(owner, modal);
    	initUI();
    }
    
    public FDCProgressDialog(Dialog owner, boolean modal){
    	super(owner, modal);
    	initUI();
    }
    
    private void initUI(){
        labelTaskName = new KDLabel();
        progressBar = new KDProgressBar();
        subTaskName = new KDLabel();
        btnCancel = new KDButton();
        labelIcon = new KDLabel();
        labelTimer = new KDLabel();
        labelTaskName.setName("calculating");
        progressBar.setName("calcProgress");
        labelIcon.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
        initUIContentLayout();
        
        monitor = new ProgressMonitor();
        this.setDefaultCloseOperation(KDFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e) {
        		if(!cancelable){
        			monitor.setCanceled(true);
        		}
        	}
        	public void windowClosed(WindowEvent e) {
        		super.windowClosed(e);
        		if(timer!=null){
    				timer.stop();
    			}
        	}
        });
    }
    
    private void initUIContentLayout() {
        setBounds(new Rectangle(10, 10, 350, 163));
        setResizable(false);
        getContentPane().setLayout(null);
        labelIcon.setBounds(new Rectangle(10, 8, 20, 20));
        getContentPane().add(labelIcon, null);
        labelTaskName.setBounds(new Rectangle(30, 10, 250, 19));
        getContentPane().add(labelTaskName, null);
        progressBar.setBounds(new Rectangle(10, 35, 329, 19));
        getContentPane().add(progressBar, null);
        subTaskName.setBounds(new Rectangle(10, 62, 329, 19));
        getContentPane().add(subTaskName, null);
        labelTimer.setBounds(new Rectangle(10, 85, 329, 19));
        getContentPane().add(labelTimer, null);
        
        btnCancel.setBounds(new Rectangle(263, 109, 75, 19));
		btnCancel.setAction(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				if(COMMAND_CANCEL.equals(e.getActionCommand())){
					cancelPressed(e);
				}
			}
	    });
		btnCancel.setActionCommand(COMMAND_CANCEL);
		btnCancel.setText("取消(C)");
		btnCancel.setMnemonic('C');
		getContentPane().add(btnCancel, null);
    }
    
    public static FDCProgressDialog createProgressDialog(Component owner, boolean model){
    	Window ance = null;
    	if(owner != null){
    		ance = SwingUtilities.getWindowAncestor(owner);
    	}
    	FDCProgressDialog dialog = null;
    	if(ance == null){
    		dialog = new FDCProgressDialog();
    	}else if(ance instanceof Dialog){
    		dialog = new FDCProgressDialog((Dialog)ance, model);	
    	}else if(ance instanceof Frame){
    		dialog = new FDCProgressDialog((Frame)ance, model);	
    	}
    	return dialog;
    }
    
    public void setIcon(Icon icon){
    	labelIcon.setIcon(icon);
    }
    
    public void run(boolean cancelable, final IFDCRunnableWithProgress runable){
    	run(cancelable, false, runable);
    }
    
    /**
     * 执行任务
     * 
     * @param cancelable 可否取消 
     * @param runable 任务
     * @author:allenhe 2007-1-19 <p>
     */
    public void run(boolean cancelable, final boolean enableTimer, final IFDCRunnableWithProgress runable){
    	this.cancelable = cancelable;
		btnCancel.setEnabled(cancelable);
		btnCancel.setVisible(cancelable);
		if(enableTimer){
			labelTimer.setVisible(true);
			startTimer();
		}else{
			labelTimer.setVisible(false);
		}
		setToScreenCenter(this);
		
		final Thread runner = new Thread(new Runnable(){
			public void run() {
				runable.run(monitor);			
			}});
		
		//显示玩才运行后台线程
		this.addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e) {
				runner.start();
			}
		});
		
		this.setVisible(true);
    }
    
    class ProgressMonitor implements IFDCProgressMonitor{
    	private boolean isCancel = false;
		private int totalWork;
		public void beginTask(String name, int totalWork) {
			this.totalWork = totalWork;
			FDCProgressDialog.this.setTitle(name);
			labelTaskName.setText(name);
			if(totalWork<0){
				progressBar.setIndeterminate(true);
			}
			progressBar.setMaximum(totalWork);
			progressBar.setMinimum(0);
		}

		public void done() {
			if(totalWork<0){
				progressBar.setIndeterminate(false);
			}
			stopTimer();
			dispose();
		}

		public boolean isCanceled() {
			return isCancel;
		}

		public void setCanceled(boolean value) {
			isCancel = value;
		}

		public void setTaskName(String name) {
			labelTaskName.setText(name);
		}

		public void subTaskBegin(String name) {
			subTaskName.setText(name);
		}

		public void worked(int work) {
			if(totalWork>=0){
				progressBar.setValue(progressBar.getValue()+work);
			}
		}
		
		public void stopTimer() {
			if(timer!=null){
				timer.stop();
			}
		}
    }
    
	protected void cancelPressed(ActionEvent e) {
		btnCancel.setEnabled(false);
		monitor.setCanceled(true);
		SysUtil.abort();
	}
	public static void main(String[] args){
		CtrlUIEnv.setKingdeeLAF();
		FDCProgressDialog dialog = FDCProgressDialog.createProgressDialog(null, false);
		dialog.run(true, true, new IFDCRunnableWithProgress(){
			public void run(IFDCProgressMonitor monitor) {
				monitor.beginTask("ABXC", 1000);
				try{
					for(int i=0; i<1000; i++){
						//MsgBox.showError("Step " + i);
						Thread.currentThread().sleep(1);
						monitor.setTaskName("sumName:"+i);
						monitor.worked(1);
					}
				} catch (InterruptedException e) {
					// @AbortException
					logger.error(e.getMessage(), e);
				}finally{
					monitor.done();
				}
			}
		});
	}
	
	private void startTimer(){
		labelTimer.setText("已耗时：00:00:00");
		timer = new Timer(1000, new ActionListener(){
			long start = System.currentTimeMillis();
			public void actionPerformed(ActionEvent e) {
				long end = System.currentTimeMillis()-start;
				String str="已耗时: " + formatTime(end);
				if(!progressBar.isIndeterminate()&&progressBar.getValue()!=0){
					long total=progressBar.getMaximum()*end/progressBar.getValue();
					str+="  计划总耗时:"+formatTime(total);
				}
				labelTimer.setText(str);
			}});
		timer.setRepeats(true);
		timer.start();
	}
	
	private String formatTime(long time){
		time = time / 1000;
		StringBuffer sb = new StringBuffer();
		sb.append(formatNumber((time/60/60)%60))
		  .append(':')
		  .append(formatNumber((time/60)%60))
		  .append(':')
		  .append(formatNumber(time%60));
		return sb.toString();
	}
	
	private String formatNumber(long t){
		return (t>9?(""+t):("0"+t));
	}
	
    public static void setToScreenCenter(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        if (windowSize.height > screenSize.height)
            windowSize.height = screenSize.height;
        if (windowSize.width > screenSize.width)
            windowSize.width = screenSize.width;
        window.setLocation((screenSize.width - windowSize.width) / 2,
                (screenSize.height - windowSize.height) / 2);
    }
}
