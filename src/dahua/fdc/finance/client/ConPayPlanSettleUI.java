/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.finance.ConPayPlanInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：月结界面
 */
public class ConPayPlanSettleUI extends AbstractConPayPlanSettleUI {
	
	private static final Logger logger = CoreUIObject.getLogger(ConPayPlanSettleUI.class);

	protected  ConPayPlanInfo editData;

	protected ConPayPlanUI ui;
	
	public ConPayPlanSettleUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		editData = (ConPayPlanInfo) getUIContext().get("PayPlan");
		ui = (ConPayPlanUI) getUIContext().get(UIContext.OWNER);
		this.dpSettleMonth.setValue(editData.getSettleMonth());
	}
	
	protected void btnComfirm_actionPerformed(ActionEvent e) throws Exception {
		super.btnComfirm_actionPerformed(e);
		if(dpSettleMonth.getValue() == null){
			MsgBox.showInfo("月结日期不能为空！");
			return;
		}
		editData.setSettleMonth((Date) dpSettleMonth.getValue());
		ui.actionSubmit_actionPerformed(e);
		this.destroyWindow();
	}
	
	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		super.btnCancel_actionPerformed(e);
		
		this.destroyWindow();
	}

}