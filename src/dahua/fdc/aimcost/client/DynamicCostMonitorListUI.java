/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.aimcost.DynamicCostMonitorFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * ��̬�ɱ���� �б���� 
 */
public class DynamicCostMonitorListUI extends AbstractDynamicCostMonitorListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DynamicCostMonitorListUI.class);
    
    public DynamicCostMonitorListUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return DynamicCostMonitorFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return DynamicCostMonitorEditUI.class.getName();
	}

}