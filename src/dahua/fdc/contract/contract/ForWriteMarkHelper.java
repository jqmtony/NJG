package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractContentUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.ListUI;

public class ForWriteMarkHelper {
	public static void isUseWriteMarkForListUI(FDCBillInfo billInfo,String oprtState,ListUI listUI) throws EASBizException, BOSException{
		UIContext uiContext = new UIContext();
		uiContext.put("billInfo",billInfo);
		uiContext.put("optState", oprtState);
		uiContext.put(UIContext.OWNER,listUI);
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractContentUI.class.getName(), uiContext);
		window.show();
	}
	public static void isUseWriteMarkForEditUI(FDCBillInfo billInfo,String oprtState,EditUI editUI) throws EASBizException, BOSException{
		UIContext uiContext = new UIContext();
		uiContext.put("billInfo",billInfo);
		uiContext.put("optState", oprtState);
		uiContext.put(UIContext.OWNER,editUI);
		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractContentUI.class.getName(), uiContext);
		window.show();
	}
	
}
