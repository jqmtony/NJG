/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeInfo;
import com.kingdee.eas.port.markesupplier.subase.SupplierTypeInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * output class name
 */
public class SupplierInvoiceTypeListUI extends AbstractSupplierInvoiceTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierInvoiceTypeListUI.class);
    
    /**
     * output class constructor
     */
    public SupplierInvoiceTypeListUI() throws Exception
    {
        super();
    }

    protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
    public void onLoad() throws Exception {
    	super.onLoad();
//    	btnCancel.setVisible(true);
//    	btnCancelCancel.setVisible(true);
    	
    	if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
    		this.actionEdit.setEnabled(true);
    		this.actionAddNew.setEnabled(true);
    		this.actionRemove.setEnabled(true);
    	} else {
    		this.actionEdit.setEnabled(false);
    		this.actionAddNew.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    	}
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionGroupAddNew_actionPerformed(e);
    }
    

    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
        refresh(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	ObjectUuidPK pk = new ObjectUuidPK(this.getSelectedKeyValue());
    	getBizInterface().cancelCancel(pk, getBizInterface().getValue(pk));
        refresh(e);
    }


    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupRemove_actionPerformed(e);
    }
    
    public void actionView_actionPerformed(ActionEvent e) throws Exception {
    	super.actionGroupView_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.port.markesupplier.subase.client.SupplierInvoiceTypeTreeEditUI.class.getName();
    }

    /**
     * output getQueryFieldName method
     */
    protected String getQueryFieldName()
    {
        return "treeid.id";
    }

    /**
     * output getKeyFieldName method
     */
    protected String getKeyFieldName()
    {
        return "id";
    }

    /**
     * output getRootName method
     */
    protected String getRootName()
    {
        return "供应商类别（新）";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeInfo objectValue = new com.kingdee.eas.port.markesupplier.subase.SupplierInvoiceTypeInfo();
		
        return objectValue;
    }

}