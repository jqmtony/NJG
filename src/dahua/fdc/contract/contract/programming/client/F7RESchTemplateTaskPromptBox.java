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

/**
 * 描述：计划模板任务f7
 *
 */
public class F7RESchTemplateTaskPromptBox extends KDCommonPromptDialog{
	
	private FilterInfo filter = null;
	private IUIWindow dlg = null; 
	private UIContext context ;
	private Object  Data;
	
	public void setData(Object data) {
		Data = data;
	}

	public F7RESchTemplateTaskPromptBox() {
		context = new UIContext(this);
	}
	
	
	public FilterInfo getFilter() {
		return filter;
	}
	public boolean isEnabledMultipleSelect() {
		return false;
	}
	public void show() {
		
		
		try {
			IUIFactory factory = (IUIFactory) UIFactory.createUIFactory(UIFactoryName.MODEL);
			dlg = factory.create(getF7UIClassName(),context, null,null,WinStyle.SHOW_TOOLBAR);
		} catch (UIException e) {
			e.printStackTrace();
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
		boolean isCancel = true;
		if(getRESchTemplateTaskF7UI() != null){
			isCancel = getRESchTemplateTaskF7UI().isCancel();
		}
		return isCancel;
	} 
	
	public UIContext getMyUIContext(){
		UIContext context = new UIContext(this);
		return context;
	}
	
	public RESchTemplateTaskF7UI getRESchTemplateTaskF7UI(){
		if(dlg == null){
			return null;
		}else{
			return (RESchTemplateTaskF7UI) dlg.getUIObject();
		}
	}
	
	public Object getData() {
		Object o = new Object[]{getRESchTemplateTaskF7UI().getData()};
		return o;
	}
	public String getF7UIClassName(){
		return RESchTemplateTaskF7UI.class.getName();
	}
}
