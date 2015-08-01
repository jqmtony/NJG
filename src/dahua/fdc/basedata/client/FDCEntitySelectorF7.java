/*
 * @(#)FDCEntitySelectorF7.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.Map;

import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * 描述: 房地产的entity选择F7
 * @author owen_wen  date:2012-12-25
 * @version EAS6.1
 */
public class FDCEntitySelectorF7 implements KDPromptSelector {
	private boolean isCanceled = false;
	private IUIWindow f7UI = null;

	public Object getData() {
		if (f7UI != null) {
			SelectFDCEntityUI selectFDCEntityUI = (SelectFDCEntityUI) f7UI.getUIObject();
			return selectFDCEntityUI.getMetaDataBrief();
		}
		return null;
	}

	public boolean isCanceled() {
		SelectFDCEntityUI selectFDCEntityUI = (SelectFDCEntityUI) f7UI.getUIObject();
		return selectFDCEntityUI.isCanceled();
	}

	public void show() {
		Map uiContext = new UIContext(this);
		uiContext.put("Owner", this);
		try {
			f7UI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectFDCEntityUI.class.getName(), uiContext);
			f7UI.show();
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

}
