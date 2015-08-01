package com.kingdee.eas.fdc.basedata.client;

import javax.swing.JDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 物料选择F7 Selector
 * @author owen_wen 2010-08-18
 *
 */
public class MaterialPromptSelector extends KDCommonPromptDialog {
	
	private IUIWindow f7UI = null;
	protected CoreUIObject ui;
	
	private FilterInfo filter = null;
	private UIContext context ;
	private Object data;
	
	public void setData(Object data) {
		this.data = data;
	}

	public MaterialPromptSelector(CoreUIObject ui) {
		super();
		this.ui = ui;
	}
	
	public MaterialPromptSelector(FilterInfo filter) {
		this.filter = new FilterInfo();
		context = new UIContext(this);
		context.put("filter", filter);
	}
	
	public FilterInfo getFilter() {
		return filter;
	}
	
	public void show() {
		if(context==null)
			context = new UIContext(this);
		
		context.put("CANRESIZE", Boolean.FALSE);
		
		IUIFactory uiFactory = null;
		
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			f7UI = uiFactory.create(FDCF7MaterialTreeListUI.class.getName(),context, null, OprtState.VIEW);
			f7UI.show();
		} catch (BOSException ex) {
			// @AbortException
           ExceptionHandler.handle(ui, ex);
        }
		
		if(f7UI instanceof JDialog){
			((JDialog) f7UI).setResizable(false);
		}
		if(f7UI!=null){
			((JDialog) f7UI).setTitle("物料选择");
		}		
	}
	
	public boolean isCanceled() {
		FDCF7MaterialTreeListUI f7UIObject = (FDCF7MaterialTreeListUI) f7UI.getUIObject();

        return f7UIObject.isCancel();
	}
	
	public UIContext getMyUIContext(){
		UIContext context = new UIContext(this);
		return context;
	}
	
	public FDCF7MaterialTreeListUI getF7Material(){
		if(f7UI == null){
			return null;
		}else{
			return (FDCF7MaterialTreeListUI) f7UI.getUIObject();
		}
	}
	
	public Object getData() {
		try {
			return ((FDCF7MaterialTreeListUI)f7UI.getUIObject()).getData();
		} catch (BOSException e) {
			// @AbortException
			e.printStackTrace();
		}
		
		return null;
	}
	
	//added by ken_liu...以便在调用不带filter的构造参数时传入filter
	public UIContext getContext() {
		if (context == null)
			context = new UIContext(this);
		return this.context;
	}
}
