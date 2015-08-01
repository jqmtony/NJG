/*
 * @(#)AimCostPromptSelector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.aimcost.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 描述:
 * @author keyan_zhao  date:2012-6-16
 * @version EAS6.1
 */
public class AimCostPromptSelector implements KDPromptSelector {

	protected IUIWindow f7UI;

	protected CoreUIObject ui;

	public AimCostPromptSelector(CoreUIObject ui) {
		super();
		this.ui = ui;
	}

	public void show() {
		UIContext context = new UIContext(ui);
		context.put("CANRESIZE", Boolean.FALSE);

		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			f7UI = uiFactory.create(AimCostZkyF7UI.class.getName(), context, null, OprtState.VIEW);
			f7UI.show();

		} catch (BOSException ex) {
			ExceptionHandler.handle(ui, ex);
		}
	}

	public Object getData() {
		try {
			AimCostZkyF7UI f7UIObject = (AimCostZkyF7UI) f7UI.getUIObject();

			return f7UIObject.getData();
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionHandler.handle(ui, e);
		}
		return null;
	}

	public boolean isCanceled() {
		return false;
	}
}
