package com.kingdee.eas.port.pm.JavaDataSet;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import com.kingdee.bos.ctrl.ext.fulfil.ExtGuiExecutor;
import com.kingdee.eas.rpts.ctrlreport.client.ExtReportRunUI;

public class MyExtReportRunUI extends ExtReportRunUI{

	public MyExtReportRunUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		Dimension windowSize = new Dimension(936,669);
		this.setPreferredSize(windowSize);	
		this.setMaximumSize(windowSize);
		this.setMinimumSize(windowSize);
		this.setSize(windowSize);
	}
	
	public void onShow() throws Exception {
		super.onShow();
	}

	public void showBizData(ExtGuiExecutor re) {
		super.showBizData(re);
	
	}
}
