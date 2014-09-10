/**
 * output package name
 */
package com.kingdee.eas.port.equipment.insurance.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.port.equipment.base.IInsurance;
import com.kingdee.eas.port.equipment.base.InsuranceFactory;
import com.kingdee.eas.port.equipment.base.InsuranceInfo;
import com.kingdee.eas.port.equipment.insurance.InsuranceCoverageE1Info;
import com.kingdee.eas.port.equipment.record.EquIdCollection;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.IEquId;
import com.kingdee.eas.port.equipment.uitl.ToolHelp;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.Tool;

/**
 * output class name
 */
public class InsuranceCoverageEditUI extends AbstractInsuranceCoverageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InsuranceCoverageEditUI.class);
    
    /**
     * output class constructor
     */
    public InsuranceCoverageEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	txtxianzhongID.setEnabled(false);
    	txtxianzhongID.setVisible(false);
        super.loadFields();
        
this.prmtinsurance.setEnabledMultiSelection(true);
		
		
		if(UIRuleUtil.isNotNull(this.txtxianzhongID.getText()))
        {
        	String spicId[] = (this.txtxianzhongID.getText().trim()).split("&");
        	try {
        		IInsurance IInsurance = InsuranceFactory.getRemoteInstance();
        		
        		InsuranceInfo objValue[] = new InsuranceInfo[spicId.length];
        		
				for (int i = 0; i < spicId.length; i++) 
				{
					String oql ="select id,name,number where id='"+spicId[i]+"'";
					objValue[i] = IInsurance.getInsuranceInfo(oql);
				}
				this.prmtinsurance.setValue(objValue);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	if(this.prmtinsurance.getValue()!=null)
    	{
    		StringBuffer sb = new StringBuffer();
    		if(this.prmtinsurance.getValue() instanceof Object[])
    		{
    			Object obj[] = (Object[]) this.prmtinsurance.getValue();
    			for (int i = 0; i < obj.length; i++) 
    			{
    				if((InsuranceInfo)obj[i]==null)
    					continue;
					if(sb!=null&&!"".equals(sb.toString().trim()))
						sb.append("&").append(((InsuranceInfo)obj[i]).getId().toString());
					else
						sb.append(((InsuranceInfo)obj[i]).getId().toString());
				}
    		}
    		else
    		{
    			sb = new StringBuffer();
    			sb.append(((InsuranceInfo)this.prmtinsurance.getValue()).getId().toString());
    		}
    		
    		this.txtxianzhongID.setText(sb.toString());
    	}
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
        return com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo objectValue = new com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        Tool.checkGroupAddNew();
        return objectValue;
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}


	public void onLoad() throws Exception {
		 this.kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		 this.kdtE1.getColumn("useUnit").getStyleAttributes().setLocked(true);
		 txtNumber.setEnabled(false);
		super.onLoad();
		
		this.kDContainer1.setTitle("保险投保明细");
		this.kDContainer1.getContentPane().add(kdtE1, BorderLayout.CENTER);
		KDWorkButton  addnewButton =kdtE1_detailPanel.getAddNewLineButton();
		addnewButton.setText("新增行");
		KDWorkButton  InsertButton =kdtE1_detailPanel.getInsertLineButton();
		InsertButton.setText("插入行");
		KDWorkButton RemoveButton =kdtE1_detailPanel.getRemoveLinesButton();
		RemoveButton.setText("删除行");
		this.kDContainer1.addButton(addnewButton);
		this.kDContainer1.addButton(InsertButton);
		this.kDContainer1.addButton(RemoveButton);
		this.kDContainer1.addButton(this.btnImportExcel);
		this.kDContainer1.addButton(this.btnExcel);
		
		btnImportExcel	.setIcon(EASResource.getIcon("imgTbtn_input"));
		btnExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		
		this.btnExportExcel.setVisible(false);
		this.kDInsuranceDetail.setVisible(false);
		this.prmtinsurance.setEnabledMultiSelection(true);
		
		 KDBizPromptBox kdtE1_equNumber_PromptBox = new KDBizPromptBox();
	        kdtE1_equNumber_PromptBox.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");
	        kdtE1_equNumber_PromptBox.setVisible(true);
	        kdtE1_equNumber_PromptBox.setEditable(true);
	        kdtE1_equNumber_PromptBox.setDisplayFormat("$number$");
	        kdtE1_equNumber_PromptBox.setEditFormat("$number$");
	        kdtE1_equNumber_PromptBox.setCommitFormat("$number$");
	   	 EntityViewInfo evi = new EntityViewInfo();
			 FilterInfo filter = new FilterInfo();
//			 filter.getFilterItems().add(new FilterItemInfo("sbStatus","1",CompareType.EQUALS));
			 String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
	 		 filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id",id ,CompareType.EQUALS));
			 evi.setFilter(filter);
//			kdtE1_equNumber_PromptBox.setEntityViewInfo(evi);
			kdtE1_equNumber_PromptBox.setSelector(ToolHelp.initPrmtEquIdByF7Color(evi, false));
			 KDTDefaultCellEditor kdtEntry_feeType_CellEditor = new KDTDefaultCellEditor(kdtE1_equNumber_PromptBox);
			 kdtE1.getColumn("equNumber").setEditor(kdtEntry_feeType_CellEditor);
			 
				if(getOprtState().equals(OprtState.ADDNEW)){
					Calendar  calendar  =  Calendar.getInstance();  
		            calendar.setTime(SysUtil.getAppServerTime(null));  
		            pkBizDate.setValue(calendar.getTime());
		            String year =  Integer.toString(calendar.get(Calendar.YEAR));
		            txtyear.setText(year);
		            prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
				}
				
				txtNumber.setRequired(true);
				pkBizDate.setRequired(true);
				pkeffectDate.setRequired(true);
				pkendDate.setRequired(true);
				prmtinsuranceCompany.setRequired(true);
				prmtinsurance.setRequired(true);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(txtNumber.getText())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"单据编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkBizDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"签单日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkeffectDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"起保日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(pkendDate.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"终保日期"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtinsuranceCompany.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"保险公司"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtinsurance.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"险种"});
		}
		super.verifyInput(e);
	}
	
	protected void btnExcel_actionPerformed(ActionEvent e) throws Exception {
		super.btnExcel_actionPerformed(e);
		btnExportExcel();
	}
	
	protected void btnImportExcel_actionPerformed(ActionEvent e)throws Exception {
		super.btnImportExcel_actionPerformed(e);
		actionImportExcel();
	}
	
	private String lockCell[] = {"useUnit","originalValue","presentValue","equType","specModel","factoryUseDate","makeUnit","tonnage"};
	String path ="";
	
	    public void actionImportExcel()  {
			path = showExcelSelectDlg(this);
			if (path == null) {
				return;
			}
			Window win = SwingUtilities.getWindowAncestor(this);
	        LongTimeDialog dialog = null;
	        if(win instanceof Frame){
	        	dialog = new LongTimeDialog((Frame)win);
	        }else if(win instanceof Dialog){
	        	dialog = new LongTimeDialog((Dialog)win);
	        }
	        if(dialog==null){
	        	dialog = new LongTimeDialog(new Frame());
	        }
	        dialog.setLongTimeTask(new ILongTimeTask() {
				public void afterExec(Object arg0) throws Exception {
					Boolean bol=(Boolean)arg0;
					if(bol){
						MsgBox.showInfo("导入成功！");
					}
				}
				public Object exec() throws Exception {
					boolean bol=importExcelToTable(path,kdtE1);
					return bol;
				}
	    	}
		    );
		    dialog.show();
		}
		private boolean importExcelToTable(String fileName, KDTable table) throws Exception {
			KDSBook kdsbook = null;
			try {
				kdsbook = POIXlsReader.parse2(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				MsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
				return false;
			}
			if (kdsbook == null) {
				return false;
			}
			if(KDSBookToBook.traslate(kdsbook).getSheetCount()>1){
				MsgBox.showWarning(this,"读EXCEL出错,EXCEl Sheet数量不匹配！");
				return false;
			}
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
	    	Map e_colNameMap = new HashMap();
			int e_maxRow = excelSheet.getMaxRowIndex();
			int e_maxColumn = excelSheet.getMaxColIndex();
			for (int col = 0; col <= e_maxColumn; col++) {
				String excelColName = excelSheet.getCell(0, col, true).getText();
				e_colNameMap.put(excelColName, new Integer(col));
			}
			Map kdtableHidedCell = new HashMap();
			for (int i = 0; i < lockCell.length; i++) 
			{
				kdtableHidedCell.put(lockCell[i], lockCell[i]);
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int col = 0; col< table.getColumnCount(); col++) {
				if (table.getColumn(col).getStyleAttributes().isHided()||kdtableHidedCell.get(table.getColumnKey(col))!=null) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(col).getValue();
				Integer colInt = (Integer) e_colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showWarning(this,"表头结构不一致！表格上的关键列:" + colName + "在EXCEL中没有出现！");
					return false;
				}
				
			}
			for (int col = 0; col< table.getRowCount(); col++) {
				IRow row = table.getRow(col);
				map.put(((EquIdInfo)row.getCell("equNumber").getValue()).getNumber(), col);
			}
			for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) 
			{
				Integer colInt = (Integer) e_colNameMap.get("设备编号");
				com.kingdee.bos.ctrl.common.variant.Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (!com.kingdee.bos.ctrl.common.variant.Variant.isNull(cellRawVal)) 
				{
					Integer equInt = (Integer) e_colNameMap.get("签单保额");
					Integer equInt1 = (Integer) e_colNameMap.get("签单保费");
					
					com.kingdee.bos.ctrl.common.variant.Variant equV = excelSheet.getCell(rowIndex, equInt.intValue(), true).getValue();
					if (!com.kingdee.bos.ctrl.common.variant.Variant.isNull(equV)) {
						if(map.get(cellRawVal.toString())!=null&&table.getRow(map.get(cellRawVal.toString()))!=null)
						{
							table.getRow(map.get(cellRawVal.toString())).getCell("insuranceAmount").setValue(equV.toString());
						}
					}
					com.kingdee.bos.ctrl.common.variant.Variant equV1 = excelSheet.getCell(rowIndex, equInt1.intValue(), true).getValue();
					if (!com.kingdee.bos.ctrl.common.variant.Variant.isNull(equV1)) {
						if(map.get(cellRawVal.toString())!=null&&table.getRow(map.get(cellRawVal.toString()))!=null)
						{
							table.getRow(map.get(cellRawVal.toString())).getCell("premium").setValue(equV1.toString());
						}
					}
				}
			}
			return true;
		}
		
		 public static String showExcelSelectDlg(CoreUIObject ui)
         {
			 KDFileChooser chsFile = new KDFileChooser();
			 String XLS = "xls";
			 String Key_File = "Key_File";
			 SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, (new StringBuilder("MS Excel")).append(LanguageManager.getLangMessage(Key_File, WizzardIO.class.getName(), "\u64CD\u4F5C\u5931\u8D25")).toString());
			 chsFile.addChoosableFileFilter(Filter_Excel);
			 int ret = chsFile.showOpenDialog(ui);
			 if(ret != 0)
				 SysUtil.abort();

			 File file = chsFile.getSelectedFile();
			 String fileName = file.getAbsolutePath();
			 return fileName;
         }
		public void setOprtState(String oprtType) {
			super.setOprtState(oprtType);
			if (oprtType.equals(OprtState.VIEW)) {
				this.lockUIForViewStatus();
				this.btnImportExcel.setEnabled(false);
				this.btnExcel.setEnabled(false);
			} else {
				this.unLockUI();
				this.btnExcel.setEnabled(true);
				this.btnImportExcel.setEnabled(true);
			}
		}
		private File js;
		protected void btnExportExcel() throws Exception {
			ExportManager exportM = new ExportManager();
	        String path = null;
	        File tempFile = File.createTempFile("eastemp",".xls");
	        path = tempFile.getCanonicalPath();

	        for (int i = 0; i < lockCell.length; i++) 
	        {
	        	this.kdtE1.getColumn(lockCell[i]).getStyleAttributes().setHided(true);
			}
	        
	        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
	        tablesVO[0]=new KDTables2KDSBookVO(this.kdtE1);
			tablesVO[0].setTableName("保险投保明细");
	        KDSBook book = null;
	        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
	        exportM.exportToExcel(book, path);
	        for (int i = 0; i < lockCell.length; i++) 
	        {
	        	this.kdtE1.getColumn(lockCell[i]).getStyleAttributes().setHided(false);
			}
			KDFileChooser fileChooser = new KDFileChooser();
			fileChooser.setFileSelectionMode(0);
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setSelectedFile(new File("保险投保明细.xls"));
			int result = fileChooser.showSaveDialog(this);
			if (result == KDFileChooser.APPROVE_OPTION){
				File dest = fileChooser.getSelectedFile();
				try{
					File src = new File(path);
					if (dest.exists())
						dest.delete();
					src.renameTo(dest);
					MsgBox.showInfo("导出成功！");
					KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
				}
				catch (Exception e3)
				{
					handUIException(e3);
				}
			}
			tempFile.delete();
		}
	
		public void kdtE1_Changed(int rowIndex, int colIndex) throws Exception {
			super.kdtE1_Changed(rowIndex, colIndex);
			if(kdtE1.getCell(rowIndex, "equNumber").getValue() != null){
				String id = ((EquIdInfo)kdtE1.getCell(rowIndex, "equNumber").getValue()).getId().toString();
				EquIdInfo eqInfo = EquIdFactory.getRemoteInstance().getEquIdInfo(new ObjectUuidPK(id));
				if(eqInfo.getAssetValue() != null){
					kdtE1.getCell(rowIndex, "originalValue").setValue(eqInfo.getAssetValue());
				}
				if(eqInfo.getNowAmount() != null){
					kdtE1.getCell(rowIndex, "presentValue").setValue(eqInfo.getNowAmount());
				}
				if(eqInfo.getSsOrgUnit() != null){
					String id2 = ((AdminOrgUnitInfo)eqInfo.getSsOrgUnit()).getId().toString();
					AdminOrgUnitInfo aoInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(id2));
					kdtE1.getCell(rowIndex, "useUnit").setValue(aoInfo);
				}
			}else{
				kdtE1.getCell(rowIndex, "originalValue").setValue(null);
				kdtE1.getCell(rowIndex, "useUnit").setValue(null);
				kdtE1.getCell(rowIndex, "presentValue").setValue(null);
			}
		}
		
		   /**
	     * output getSelectors method
	     */
	    public SelectorItemCollection getSelectors()
	    {
	        SelectorItemCollection sic = new SelectorItemCollection();
			String selectorAll = System.getProperty("selector.all");
			if(StringUtils.isEmpty(selectorAll)){
				selectorAll = "true";
			}
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("creator.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("creator.id"));
	        	sic.add(new SelectorItemInfo("creator.number"));
	        	sic.add(new SelectorItemInfo("creator.name"));
			}
	        sic.add(new SelectorItemInfo("createTime"));
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("lastUpdateUser.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
	        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
	        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
			}
	        sic.add(new SelectorItemInfo("lastUpdateTime"));
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("CU.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("CU.id"));
	        	sic.add(new SelectorItemInfo("CU.number"));
	        	sic.add(new SelectorItemInfo("CU.name"));
			}
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("bizDate"));
	        sic.add(new SelectorItemInfo("description"));
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("auditor.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("auditor.id"));
	        	sic.add(new SelectorItemInfo("auditor.number"));
	        	sic.add(new SelectorItemInfo("auditor.name"));
			}
	        sic.add(new SelectorItemInfo("status"));
	        sic.add(new SelectorItemInfo("bizStatus"));
	        sic.add(new SelectorItemInfo("auditTime"));
	        sic.add(new SelectorItemInfo("xzdm"));
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("insurance.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("insurance.id"));
	        	sic.add(new SelectorItemInfo("insurance.number"));
	        	sic.add(new SelectorItemInfo("insurance.name"));
			}
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("insuranceCompany.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("insuranceCompany.id"));
	        	sic.add(new SelectorItemInfo("insuranceCompany.number"));
	        	sic.add(new SelectorItemInfo("insuranceCompany.name"));
			}
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("tbrmc.*"));
			}
			else{
	        	sic.add(new SelectorItemInfo("tbrmc.id"));
	        	sic.add(new SelectorItemInfo("tbrmc.number"));
	        	sic.add(new SelectorItemInfo("tbrmc.name"));
			}
	    	sic.add(new SelectorItemInfo("E1.seq"));
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("E1.*"));
			}
			else{
			}
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("E1.useUnit.*"));
			}
			else{
		    	sic.add(new SelectorItemInfo("E1.useUnit.id"));
				sic.add(new SelectorItemInfo("E1.useUnit.name"));
	        	sic.add(new SelectorItemInfo("E1.useUnit.number"));
			}
			if(selectorAll.equalsIgnoreCase("true"))
			{
				sic.add(new SelectorItemInfo("E1.equNumber.*"));
			}
			else{
		    	sic.add(new SelectorItemInfo("E1.equNumber.id"));
				sic.add(new SelectorItemInfo("E1.equNumber.number"));
				sic.add(new SelectorItemInfo("E1.equNumber.name"));
			}
	    	sic.add(new SelectorItemInfo("E1.equType"));
	    	sic.add(new SelectorItemInfo("E1.equName"));
	    	sic.add(new SelectorItemInfo("E1.specModel"));
	    	sic.add(new SelectorItemInfo("E1.factoryUseDate"));
	    	sic.add(new SelectorItemInfo("E1.makeUnit"));
	    	sic.add(new SelectorItemInfo("E1.tonnage"));
	    	sic.add(new SelectorItemInfo("E1.originalValue"));
	    	sic.add(new SelectorItemInfo("E1.presentValue"));
	    	sic.add(new SelectorItemInfo("E1.insuranceAmount"));
	    	sic.add(new SelectorItemInfo("E1.remark"));
	    	sic.add(new SelectorItemInfo("E1.premium"));
	        sic.add(new SelectorItemInfo("xianzhongID"));
	        sic.add(new SelectorItemInfo("coverNumber"));
	        sic.add(new SelectorItemInfo("contNumber"));
	        sic.add(new SelectorItemInfo("effectDate"));
	        sic.add(new SelectorItemInfo("endDate"));
	        sic.add(new SelectorItemInfo("year"));
	        return sic;
	    }        
}