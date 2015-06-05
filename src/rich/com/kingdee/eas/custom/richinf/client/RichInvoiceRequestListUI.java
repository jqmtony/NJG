/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.richinf.BillState;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory;
import com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RichInvoiceRequestListUI extends AbstractRichInvoiceRequestListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RichInvoiceRequestListUI.class);
    
    /**
     * output class constructor
     */
    public RichInvoiceRequestListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    @Override
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedKeyValue();
    	if(id != null){
    		RichInvoiceRequestInfo info = null;
    		info = RichInvoiceRequestFactory.getRemoteInstance().getRichInvoiceRequestInfo(new ObjectUuidPK(id));
    		if(!info.getBillState().equals(BillState.SUBMIT)) {
    			MsgBox.showInfo("提交状态下的单据才能进行审核操作！");
        		SysUtil.abort();
    		}
    		
    	}else {
    		MsgBox.showInfo("请选择您要进行审核的记录！");
    		SysUtil.abort();
    	}
    	super.actionAudit_actionPerformed(e);
    	MsgBox.showInfo("审核成功！");
    	actionRefresh_actionPerformed(e);
    }
    
    @Override
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnAudit_actionPerformed(e);
    	MsgBox.showInfo("反审核成功！");
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.richinf.RichInvoiceRequestFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo objectValue = new com.kingdee.eas.custom.richinf.RichInvoiceRequestInfo();
		
        return objectValue;
    }

}