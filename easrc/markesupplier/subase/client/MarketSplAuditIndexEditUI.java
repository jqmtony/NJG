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
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;

/**
 * output class name
 */
public class MarketSplAuditIndexEditUI extends AbstractMarketSplAuditIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSplAuditIndexEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSplAuditIndexEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	chkMenuItemSubmitAndAddNew.setSelected(true);
		chkMenuItemSubmitAndAddNew.setVisible(true);
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		this.kDLabelContainer4.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		this.actionFirst.setVisible(false);//��һ
		this.actionPre.setVisible(false);//ǰһ
		this.actionNext.setVisible(false);//��һ
		this.actionLast.setVisible(false);//���һ��
		this.contAccreditationType.setVisible(false);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){return;}
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
    	MarketSplAuditIndexInfo Info = (MarketSplAuditIndexInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("�����ã�����ִ�д˲�����");SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){return;}
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
    	MarketSplAuditIndexInfo Info = (MarketSplAuditIndexInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("�����ã�����ִ�д˲�����");SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo objectValue = new com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		if(getUIContext().get("supplierFileType")!=null){
			objectValue.setAccreditationType((MarketAccreditationTypeInfo) getUIContext().get("supplierFileType"));
		}else{
			MsgBox.showWarning("��ѡ���������ͽ���������");SysUtil.abort();
		}
        return objectValue;
    }

}