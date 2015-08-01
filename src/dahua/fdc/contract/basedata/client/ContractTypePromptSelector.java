/*
 * @(#)ContractTypePromptSelector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.client;

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
 * 
 * 描述:合同类型F7 Selector
 * @author liupd  date:2006-8-16 <p>
 * @version EAS5.1.3
 */
public class ContractTypePromptSelector implements KDPromptSelector {
	
	protected IUIWindow f7UI;
	
	protected CoreUIObject ui;
	
	public ContractTypePromptSelector(CoreUIObject ui) {
		super();
		this.ui = ui;
	}

	public void show() {		
		UIContext context = new UIContext(ui);
		context.put("CANRESIZE", Boolean.FALSE);		
		IUIFactory uiFactory = null;
        try {
            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
            f7UI = uiFactory.create(ContractTypeF7UI.class.getName(), context, null, OprtState.VIEW);
            f7UI.show();            
        } catch (BOSException ex) {
        	// @AbortException
           ExceptionHandler.handle(ui, ex);
        }
	}

	public boolean isCanceled() {
		ContractTypeF7UI f7UIObject = (ContractTypeF7UI) f7UI.getUIObject();

        return f7UIObject.isCancel();
	}

	public Object getData() {
		try {
			ContractTypeF7UI f7UIObject = (ContractTypeF7UI) f7UI.getUIObject();
			return f7UIObject.getData();
		} catch (Exception e) {
			// @AbortException
			e.printStackTrace();
			ExceptionHandler.handle(ui, e);
		}		
		return  null;
	}
}
