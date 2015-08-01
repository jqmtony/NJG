package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;


/**
 * 进度计划任务选择f7
 * 
 * @author weiqiang_chen
 *
 */
public class F7ScheduleTaskPromptBox extends KDCommonPromptDialog{
	
	private ScheduleTaskF7UI ui = null; 
	private String projectId;
	private boolean isMultiSelect = false;

	public F7ScheduleTaskPromptBox(String projectId) {
		this.projectId = projectId;
	}
	
	public F7ScheduleTaskPromptBox(String projectId,boolean isMultiSelect) {
		this.projectId = projectId;
		this.isMultiSelect = isMultiSelect;
	}
	
	public void show() {
		try {
			UIContext context = new UIContext(this);
			IUIFactory factory = (IUIFactory) UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow window = factory.create(ScheduleTaskF7UI.class.getName(),context, null,null);
			ui = (ScheduleTaskF7UI) window.getUIObject();
			ui.setUITitle("节点支付名称");
			ui.setProjectId(projectId);
			ui.setMutilSelect(isMultiSelect);
			window.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isCanceled() {
		return ui.isCancel();
	} 

	public Object getData() {
		return ui.getData();
	}

}
