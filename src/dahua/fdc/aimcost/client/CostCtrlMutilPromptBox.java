package com.kingdee.eas.fdc.aimcost.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.CostAccountImportUI;
import com.kingdee.eas.fdc.basedata.client.CostAccountMutilF7UI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class CostCtrlMutilPromptBox extends KDCommonPromptDialog {

	
	protected IUIWindow costAccountDialog = null;
	Map ctx = new HashMap();
	private IUIObject uiObj;
	
	
    public CostCtrlMutilPromptBox(IUIObject uiObj) {
		this.uiObj = uiObj;
	}
	public Object getData() {
		CostAccountMutilF7UI accountMutilUI = (CostAccountMutilF7UI) costAccountDialog.getUIObject();
		try {
			return accountMutilUI.getData();
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		return null;
	}
	
	
	public void show() {
		IUIFactory uiFactory = null;
		ctx.put("query", this.getQueryInfo());
		ctx.put("view", this.getEntityViewInfo());
		ctx.put("project", uiObj.getUIContext().get("project"));
		try  {
	        uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        costAccountDialog = uiFactory.create(CostAccountMutilF7UI.class.getName(),ctx,null,OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        costAccountDialog.show();
		}
		catch (BOSException ex) {
	        ExceptionHandler.handle(ex);
	        SysUtil.abort();
		}
	}
	

	public boolean isCanceled() {
		if (costAccountDialog != null) {
			return ((CostAccountMutilF7UI) costAccountDialog.getUIObject()).isCanceled();
			// return false;
		} else {
			return true;
		}
	}
}
