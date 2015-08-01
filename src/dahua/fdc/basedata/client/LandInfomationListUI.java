/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.LandInfomationFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 土地信息 列表界面
 */
public class LandInfomationListUI extends AbstractLandInfomationListUI
{
    private static final Logger logger = CoreUIObject.getLogger(LandInfomationListUI.class);
    
    public LandInfomationListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return LandInfomationFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return LandInformationEditUI.class.getName();
	}
	
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}