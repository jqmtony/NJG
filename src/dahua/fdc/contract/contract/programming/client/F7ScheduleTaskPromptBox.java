package com.kingdee.eas.fdc.contract.programming.client;


import javax.swing.JDialog;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.SysUtil;

/**
 * 相关任务项F7控件
 * @author shangjing
 *
 */
public class F7ScheduleTaskPromptBox extends KDCommonPromptDialog {
	private FilterInfo filter = null;
	private IUIWindow dlg = null; 
	private UIContext context ;
	private Object  Data;
	
	public void setData(Object data) {
		Data = data;
	}

	public F7ScheduleTaskPromptBox() {
		// TODO Auto-generated constructor stub
	}
	
	public F7ScheduleTaskPromptBox(FilterInfo filter) {
		this.filter = new FilterInfo();
		context = new UIContext(this);
		context.put("filter", filter);
//		isHistoryRecordEnabled = false;
	}
	
	public FilterInfo getFilter() {
		return filter;
	}
	public void show() {
		
		
		try {
			IUIFactory factory = (IUIFactory) UIFactory.createUIFactory(UIFactoryName.MODEL);
			dlg = factory.create(getF7UIClassName(),context, null,null,WinStyle.SHOW_TOOLBAR);
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		if(dlg instanceof JDialog){
			((JDialog) dlg).setResizable(false);
		}
		if(dlg!=null){
			((JDialog) dlg).setTitle("F7相关任务项");
			dlg.show();
		}
		
	}
	
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		boolean isCancel = true;
		if(getScheduleTaskF7UI() != null){
			isCancel = getScheduleTaskF7UI().isCancel();
		}
		return isCancel;
	} 
	
	public UIContext getMyUIContext(){
		UIContext context = new UIContext(this);
		return context;
	}
	
	public ScheduleTaskF7UI getScheduleTaskF7UI(){
		if(dlg == null){
			return null;
		}else{
			return (ScheduleTaskF7UI) dlg.getUIObject();
		}
	}
	
	public Object getData() {
		Object o = new Object[]{getScheduleTaskF7UI().getData()};
		return o;
	}
	public String getF7UIClassName(){
		return "com.kingdee.eas.fdc.contract.programming.client.ScheduleTaskF7UI";
	}
}
