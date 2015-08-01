/**
 * 
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.scheme.FdcUpdateGeneralFacadeFactory;
import com.kingdee.eas.fdc.basedata.scheme.IFdcUpdateGeneralFacade;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * 描述：UI工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2015-5-21
 * @version 1.0, 2015-5-21
 * @see
 * @since JDK1.4
 */
public class UIUtils {

    public static void openUI(String uiName,CoreUI ui)throws Exception{
        UIContext uiContext = new UIContext(ui);
        IUIWindow uiWindow = null;
    
        uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.EDIT);
        uiWindow.show();   
    }
    
    public static IFdcUpdateGeneralFacade getRemoteInterFace()throws Exception{
    	return FdcUpdateGeneralFacadeFactory.getRemoteInstance();
    }
}
