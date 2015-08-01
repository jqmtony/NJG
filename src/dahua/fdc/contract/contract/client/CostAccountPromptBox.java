package com.kingdee.eas.fdc.contract.client;

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
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class CostAccountPromptBox extends KDCommonPromptDialog {
	protected IUIWindow costAccountDialog = null;
	Map ctx = new HashMap();
	private IUIObject uiObj;
	
	public CostAccountPromptBox(IUIObject uiObj) {
		
		this.uiObj = uiObj;
	}
	
	public Object getData() {
		CostAccountF7UI caF7UI = (CostAccountF7UI) costAccountDialog.getUIObject();
		try {
			return new Object[] {caF7UI.getData()};
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		return null;
	}
	
	public boolean isCanceled() {
		CostAccountF7UI caF7UI = (CostAccountF7UI) costAccountDialog.getUIObject();
		return caF7UI.isCancel();
	}
	
	public void show() {
		IUIFactory uiFactory = null;
		ctx.put("query", this.getQueryInfo());
		ctx.put("view", this.getEntityViewInfo());
		try  {
	        uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        costAccountDialog = uiFactory.create(CostAccountF7UI.class.getName(),ctx,null,OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        costAccountDialog.show();
		}
		catch (BOSException ex) {
	        ExceptionHandler.handle(ex);
	        SysUtil.abort();
		}
	}
}
