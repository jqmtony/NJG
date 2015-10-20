/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;

/**
 * output class name
 */
public class ChangeAuditAheadEditUI extends AbstractChangeAuditAheadEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeAuditAheadEditUI.class);
    private boolean isOk = false;
    /**
     * output class constructor
     */
    public ChangeAuditAheadEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	txtAheadReason.setMaxLength(80);
    	txtConnectType.setMaxLength(80);
    	txtValidator.setMaxLength(80);
    	txtAheadReason.setRequired(true);
    	txtConnectType.setRequired(true);
    	txtValidator.setRequired(true);
    }

	protected void btnCan_actionPerformed(ActionEvent e) throws Exception {
		setConfirm(false);
    	disposeUIWindow();
		super.btnCan_actionPerformed(e);
	}

	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeConfirm();
		if (getUIContext().get("ID") != null) {
    		String id = getUIContext().get("ID").toString();
    		ChangeAuditBillInfo info = ChangeAuditBillFactory.getRemoteInstance().
    		getChangeAuditBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
    		info.setAheadReason(txtAheadReason.getText());
    		info.setConnectType(txtConnectType.getText());
    		info.setValidator(txtValidator.getText());
    		info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
    		SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("aheadReason");
			selector.add("connectType");
			selector.add("validator");
			selector.add("changeState");
			Set idSet = new HashSet();
			idSet.add(id);
			ChangeAuditBillFactory.getRemoteInstance().aheadDisPatch(idSet);
			ChangeAuditBillFactory.getRemoteInstance().updatePartial(info,selector);	
    	}
		setConfirm(true);
		super.btnConfirm_actionPerformed(e);
	}
    
	public void setConfirm(boolean isOk) {
		this.isOk = isOk;

		disposeUIWindow();
	}
    
	private void checkBeforeConfirm() throws Exception{
		FDCClientVerifyHelper.verifyRequire(this);
	}
}