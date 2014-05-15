/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.common.CtrlUtil.Obj;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.pm.base.EvaluationTemplateInfo;
import com.kingdee.eas.port.pm.base.EvaluationTemplateTreeInfo;
import com.kingdee.eas.port.pm.base.IEvaluationTemplate;
import com.kingdee.eas.port.pm.base.JudgesTreeInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * output class name
 */
public class EvaluationTemplateListUI extends AbstractEvaluationTemplateListUI
{
    private static final Logger logger = CoreUIObject.getLogger(EvaluationTemplateListUI.class);
    
    /**
     * output class constructor
     */
    public EvaluationTemplateListUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	btnCancel.setVisible(true);
    	btnCancel.setEnabled(true);
    	btnCancelCancel.setVisible(true);
    	btnCancelCancel.setEnabled(true);
    }
    @Override
    protected String getEditUIModal() {
    	// TODO Auto-generated method stub
    	return UIFactoryName.MODEL;
    }
    @Override
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	// TODO Auto-generated method stub
    	super.prepareUIContext(uiContext, e);
    	if(getActionFromActionEvent(e).equals(actionAddNew)) {
	    	DefaultKingdeeTreeNode treeNote = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
			if (treeNote.getUserObject() instanceof EvaluationTemplateTreeInfo) {
				getUIContext().put("treeInfo", treeNote.getUserObject());
			} else {
				MsgBox.showWarning("非子节点无法新增！");
				SysUtil.abort();
			}
    	}	
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

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
    }

    /**
     * output chkIncludeChild_itemStateChanged method
     */
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.chkIncludeChild_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	String id = getSelectedKeyValue();
    	IEvaluationTemplate ieva = (IEvaluationTemplate) getBizInterface();
    	EvaluationTemplateInfo info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(id));
    	if(info.isUse()) {
    		MsgBox.showWarning("已启用数据不能修改");
    		SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	boolean flag = true;
    	ArrayList idList = getSelectedIdValues();
    	IEvaluationTemplate ieva = (IEvaluationTemplate) getBizInterface();
    	for(Object Id : idList) {
    		String id = Id.toString();
    		EvaluationTemplateInfo info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(id));
    		if(info.isUse()) {
    			flag = false;
    			continue;
    		}
    		ieva.delete(new ObjectUuidPK(id));
    	}
    	if(flag) {
    		MsgBox.showInfo("删除成功");
    	} else {
    		MsgBox.showWarning("已启用的数据无法删除");
    	}
    	actionRefresh_actionPerformed(e);
//        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	boolean flag = true;
    	ArrayList idList = getSelectedIdValues();
    	IEvaluationTemplate ieva = (IEvaluationTemplate) getBizInterface();
    	for(Object Id : idList) {
    		String id = Id.toString();
    		EvaluationTemplateInfo info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(id));
    		if(!info.isUse()) {
    			flag = false;
    			continue;
    		}
    		ieva.cancel(new ObjectUuidPK(id), info);
    	}
    	if(flag) {
    		MsgBox.showInfo("禁用成功");
    	} else {
    		MsgBox.showWarning("未启用的数据无需禁用");
    	}
    	actionRefresh_actionPerformed(e);
//        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	boolean flag = true;
    	ArrayList idList = getSelectedIdValues();
    	IEvaluationTemplate ieva = (IEvaluationTemplate) getBizInterface();
    	for(Object Id : idList) {
    		String id = Id.toString();
    		EvaluationTemplateInfo info = ieva.getEvaluationTemplateInfo(new ObjectUuidPK(id));
    		if(info.isUse()) {
    			flag = false;
    			continue;
    		}
    		ieva.cancelCancel(new ObjectUuidPK(id), info);
    	}
    	if(flag) {
    		MsgBox.showInfo("启用成功");
    	} else {
    		MsgBox.showWarning("已启用的数据无需启用");
    	}
    	actionRefresh_actionPerformed(e);
//        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupView_actionPerformed
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupView_actionPerformed(e);
    }

    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupRemove_actionPerformed(e);
    }

    /**
     * output actionGroupMoveTree_actionPerformed
     */
    public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupMoveTree_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.base.EvaluationTemplateFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.base.EvaluationTemplateTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.port.pm.base.client.EvaluationTemplateTreeEditUI.class.getName();
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
        return "评标模板";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.base.EvaluationTemplateInfo objectValue = new com.kingdee.eas.port.pm.base.EvaluationTemplateInfo();
		
        return objectValue;
    }

}