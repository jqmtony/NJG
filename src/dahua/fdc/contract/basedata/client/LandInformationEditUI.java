/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.LandInfomationFactory;
import com.kingdee.eas.fdc.basedata.LandInfomationInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 土地信息 编辑界面
 */
public class LandInformationEditUI extends AbstractLandInformationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(LandInformationEditUI.class);

    protected void initWorkButton() {
		super.initWorkButton();
		//数字类控件，数字应居右对齐
		txtPlotRatio.setHorizontalAlignment(JTextField.RIGHT);
		txtTotalLandPrice.setHorizontalAlignment(JTextField.RIGHT);
		txtUnitPrice.setHorizontalAlignment(JTextField.RIGHT);
	}
    
    public LandInformationEditUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		LandInfomationInfo landInfo = new LandInfomationInfo();
		landInfo.setCompany((CompanyOrgUnitInfo) (SysContext.getSysContext().getCurrentFIUnit()));
		return landInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LandInfomationFactory.getRemoteInstance();
	}
}