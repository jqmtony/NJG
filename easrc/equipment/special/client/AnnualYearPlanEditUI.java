/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailFactory;
import com.kingdee.eas.port.equipment.special.AnnualYearDetailInfo;
import com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryInfo;
import com.kingdee.eas.port.equipment.special.DetectionE2Factory;
import com.kingdee.eas.port.equipment.special.IAnnualYearDetail;
import com.kingdee.eas.port.equipment.special.IDetectionE2;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.Tool;

/**
 * output class name
 */
public class AnnualYearPlanEditUI extends AbstractAnnualYearPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AnnualYearPlanEditUI.class);
    
    /**
     * 已生成检查明细
     */
    private Color a = new Color(109,169,235);
    /**
     * 已下达
     */
    private Color b = new Color(220,124,172);
    /**
     * 已确认
     */
    private Color c = new Color(101,243,122);
    /**
     * 已执行
     */
    private Color d = new Color(211,145,44);
    /**
     * 已生成检测小结
     */
    private Color e = new Color(189,200,145);
    /**
     * output class constructor
     */
    public AnnualYearPlanEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        try {
			setRowColor();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
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
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    
    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
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
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.AnnualYearPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.special.AnnualYearPlanInfo objectValue = new com.kingdee.eas.port.equipment.special.AnnualYearPlanInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    	Tool.checkGroupAddNew();
        return objectValue;
    }
	protected void attachListeners() {
		
	}
	protected void detachListeners() {
		
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}

	public void onLoad() throws Exception {
		 this.kdtEntry.getColumn("seq").getStyleAttributes().setHided(true);
		 this.kdtEntry.getColumn("checkType").getStyleAttributes().setLocked(true);
//		 this.kdtEntry.getColumn("planDate").getStyleAttributes().setLocked(true);
		 this.kdtEntry.getColumn("endDate").getStyleAttributes().setLocked(true);
		super.onLoad();
		InitButtonColor();
		if(getOprtState().equals(OprtState.ADDNEW)){
		  pkBizDate.setValue(new Date());
		  prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		InitWorkButtons();
		
		
		KDPopupMenu menu = this.getMenuManager(this.kdtEntry).getMenu();
		JMenuItem jme = new JMenuItem("批量设置计划检验日期");
		menu.add(jme);
		jme.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!OprtState.EDIT.equals(getOprtState())&&!OprtState.ADDNEW.equals(getOprtState())){
					MsgBox.showInfo("当前状态不是修改状态，不能设置时间！");
					SysUtil.abort();
				}
				KDTSelectBlock sb;
	        	int size = kdtEntry.getSelectManager().size(); 
	        	for (int i = 0; i < size; i++){
	        		sb = kdtEntry.getSelectManager().get(i);
	        		int left = sb.getLeft(); // 选择块最左边列索引
	        		int right = sb.getRight(); // 选择块最右边列索引
	        		for (int j2 = left; j2 <=right; j2++) {
						String cellName = kdtEntry.getColumnKey(j2);
						if(!cellName.equals("planDate")){
							MsgBox.showInfo("请选择计划检验日期列进行批量设置时间！");
							SysUtil.abort();
						}
					}
	        	}
				
				IUIWindow uiWindow = null;
				UIContext context = new UIContext(this);
				try {
					 context.put("kdtable", kdtEntry);
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(UpdatePlanDate.class.getName(), context, null, OprtState.VIEW);
				} catch (UIException e1) {
					e1.printStackTrace();
				}
				uiWindow.show();
			}
		});
	}
	
	private void InitWorkButtons()
	{
		this.kDContainer1.getContentPane().add(kdtEntry, BorderLayout.CENTER);
		KDWorkButton  addnewButton =kdtEntry_detailPanel.getAddNewLineButton();
		addnewButton.setText("批量选择特种设备");
		KDWorkButton RemoveButton =kdtEntry_detailPanel.getRemoveLinesButton();
		RemoveButton.setText("删除特种设备");
		this.kDContainer1.addButton(addnewButton);
		this.kDContainer1.addButton(RemoveButton);
		
		if(addnewButton.getActionListeners()[0]!=null)
			addnewButton.removeActionListener(addnewButton.getActionListeners()[0]);
		
		addnewButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					
					IUIWindow uiWindow = null;
					UIContext context = new UIContext(this);
					
					Set<String>  set = new HashSet<String>();
					for (int i = 0; i <kdtEntry.getRowCount(); i++) 
					{
						if(UIRuleUtil.isNotNull(kdtEntry.getCell(i, "zdaNumber").getValue()))
							set.add(((EquIdInfo)kdtEntry.getCell(i, "zdaNumber").getValue()).getId().toString());
					}
					context.put("kdtable", kdtEntry);
					context.put("equID", set);
					context.put("yearPlan", set);
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ImportEquIdUI.class.getName(), context, null, OprtState.ADDNEW);
					uiWindow.show(); 
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}
	
		public void kdtEntry_Changed(int rowIndex, int colIndex) throws Exception {
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"equipmentName").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"name")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"code").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"code")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"useUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"ssOrgUnit.name")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"state").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"tzsbStatus"));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"address").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"address.name")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"companyNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"innerNumber")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"NO").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"model")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"engineNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"engineNumber")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"carNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"carNumber")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"weight").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getBigDecimal(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"ratedWeight")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"useDate").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getDateValue(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"qyDate")));
				
				}
				
				if ("zdaNumber".equalsIgnoreCase(kdtEntry.getColumn(colIndex).getKey())) {
					kdtEntry.getCell(rowIndex,"createUnit").setValue(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)kdtEntry.getCell(rowIndex,"zdaNumber").getValue(),"mader")));
				
				}
				
				
				
				    }
		
		
	private void InitButtonColor()
	{
		this.kDLabel1.setOpaque(true);
		this.kDLabel2.setOpaque(true);
		this.kDLabel3.setOpaque(true);
		this.kDLabel4.setOpaque(true);
		this.kDLabel5.setOpaque(true);
		this.kDLabel1.setBackground(a);
		this.kDLabel2.setBackground(b);
		this.kDLabel3.setBackground(c);
		this.kDLabel4.setBackground(d);
		this.kDLabel5.setBackground(e);
	}
	
	private void setRowColor() throws EASBizException, BOSException
	{
		if(editData.getId()==null)
			return;
		IAnnualYearDetail iAnnualYearDetail = AnnualYearDetailFactory.getRemoteInstance();
		IDetectionE2 iDetectionE2 = DetectionE2Factory.getRemoteInstance();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filInfo = new FilterInfo();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("status");
		sic.add("isConfirmation");
		
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) 
		{
			IRow row = this.kdtEntry.getRow(i);
			AnnualYearPlanEntryInfo annEntryPlanEntryInfo = (AnnualYearPlanEntryInfo)row.getUserObject();
			EquIdInfo equIdInfo = annEntryPlanEntryInfo.getZdaNumber();
			if(equIdInfo==null)
				continue;
			String equId = equIdInfo.getId().toString();
			
			String sql = "select a.fid from CT_SPE_AnnualYearDetail a left join CT_SPE_AnnualYearDetailEntry b on b.fparentid=a.fid " +
					" where a.fsourceBillId='"+editData.getId()+"' and b.CFZdaNumberID='"+equId+"'";
			view = new EntityViewInfo();
			filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
			view.setFilter(filInfo);
			view.setSelector(sic);
			if(!iAnnualYearDetail.exists(filInfo))
				continue;
			row.getStyleAttributes().setBackground(a);
			AnnualYearDetailInfo detailInfo = iAnnualYearDetail.getAnnualYearDetailCollection(view).get(0);
			
			XRBillStatusEnum status = detailInfo.getStatus();
			//下达
			if(status.equals(XRBillStatusEnum.RELEASED))
				row.getStyleAttributes().setBackground(b);
			//确认
			if(detailInfo.isIsConfirmation())
				row.getStyleAttributes().setBackground(c);
			//执行 
			if(status.equals(XRBillStatusEnum.EXECUTION))
				row.getStyleAttributes().setBackground(d);
				
			//当前设备是否已经有小结
			if(iDetectionE2.exists("select id where parent.sourceBillId='"+detailInfo.getId()+"' and zdaNumber.id='"+equId+"'"))
			{
				row.getStyleAttributes().setBackground(e);
			}
		}
	}
	
	
	
}