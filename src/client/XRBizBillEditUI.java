/**
 * output package name
 */
package com.kingdee.eas.xr.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.xr.XRBizBillFactory;
import com.kingdee.eas.xr.app.XRBillStatusEnum;

/**
 * output class name
 */
public class XRBizBillEditUI extends AbstractXRBizBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(XRBizBillEditUI.class);
    
    /**
     * output class constructor
     */
    public XRBizBillEditUI() throws Exception
    {
        super();
        this.contCreator.setEnabled(false);
    	this.contCreateTime.setEnabled(false);
    	this.contLastUpdateUser.setEnabled(false);
    	this.contLastUpdateTime.setEnabled(false);
    	this.contAuditor.setEnabled(false);
    	this.contAuditTime.setEnabled(false);
    	this.pkCreateTime.setEnabled(false);
    	this.pkLastUpdateTime.setEnabled(false);
    	this.pkAuditTime.setEnabled(false);
    	this.prmtAuditor.setEnabled(false);
    	this.prmtCreator.setEnabled(false);
    	this.prmtLastUpdateUser.setEnabled(false);
    	this.actionRemove.setVisible(false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void loadFields() {
    	super.loadFields();
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionSubmit_actionPerformed(arg0);
    }
    
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	protected ICoreBase getBizInterface()
    throws Exception
	{
	   return XRBizBillFactory.getRemoteInstance();
	}

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
}