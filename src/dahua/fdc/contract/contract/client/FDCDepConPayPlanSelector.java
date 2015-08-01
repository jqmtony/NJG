package com.kingdee.eas.fdc.contract.client;

import java.util.Date;

import javax.swing.JDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class FDCDepConPayPlanSelector extends KDCommonPromptDialog {
	private IUIWindow f7UI = null;
	protected CoreUIObject ui;

	private FilterInfo filter = null;
	private UIContext context;
	private Object data;

	public void setData(Object data) {
		this.data = data;
	}

	public FDCDepConPayPlanSelector(CoreUIObject ui) {
		super();
		this.ui = ui;
	}

	public FDCDepConPayPlanSelector(FilterInfo filter) {
		this.filter = new FilterInfo();
		context = new UIContext(this);
		context.put("filter", filter);
	}

	public FilterInfo getFilter() {
		return filter;
	}

	public void show() {
		context = new UIContext(ui);
		context.put("CANRESIZE", Boolean.FALSE);
		Date bizDate = (Date) ui.getUIContext().get("bookedDate");
		// CurProjectInfo curProject
		// =(CurProjectInfo)ui.getUIContext().get("curProject");
		// context.put("curProject", curProject);
		if (ui instanceof ContractWithoutTextEditUI) {
			Date bookedDate = (Date) ((ContractWithoutTextEditUI) ui).pkbookedDate.getValue();
			if(bookedDate!=null){
				bizDate = bookedDate;
			}
			AdminOrgUnitInfo dpt = (AdminOrgUnitInfo) ((ContractWithoutTextEditUI) ui).prmtuseDepartment
					.getValue();
			if (dpt != null) {
				context.put("dpt", dpt);
			}
		}
		context.put("bizDate", bizDate);
		IUIFactory uiFactory = null;

		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			f7UI = uiFactory.create(F7FDCDepConPayPlanUI.class.getName(),
					context, null, OprtState.VIEW);
			f7UI.show();
		} catch (BOSException ex) {
			ExceptionHandler.handle(ui, ex);
			SysUtil.abort();
		}

		if (f7UI instanceof JDialog) {
			((JDialog) f7UI).setResizable(false);
		}
		if (f7UI != null) {
			((JDialog) f7UI).setTitle("计划项目选择");
		}
	}

	public boolean isCanceled() {
		if (f7UI == null) {
			return false;
		}
		F7FDCDepConPayPlanUI f7UIObject = (F7FDCDepConPayPlanUI) f7UI
				.getUIObject();

		return f7UIObject.isCancel();
	}

	public F7FDCDepConPayPlanUI getF7FDCDepConPayPlan() {
		if (f7UI == null) {
			return null;
		} else {
			return (F7FDCDepConPayPlanUI) f7UI.getUIObject();
		}
	}

	public UIContext getMyUIContext() {
		UIContext context = new UIContext(this);
		return context;
	}

	public Object getData() {
		F7FDCDepConPayPlanUI iUIWindow = (F7FDCDepConPayPlanUI) f7UI
				.getUIObject();
		// ui.getUIContext().put("fdcDepConPayPlanMonth",
		// iUIWindow.getUIContext().get("fdcDepConPayPlanMonth"));
		return iUIWindow.getData();
	}
}
