/**
 * output package name
 */
package com.kingdee.eas.port.equipment.insurance.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.port.equipment.base.IInsurance;
import com.kingdee.eas.port.equipment.base.InsuranceCompanyFactory;
import com.kingdee.eas.port.equipment.base.InsuranceCompanyInfo;
import com.kingdee.eas.port.equipment.base.InsuranceFactory;
import com.kingdee.eas.port.equipment.base.InsuranceInfo;
import com.kingdee.eas.port.equipment.insurance.InsuranceCoverageFactory;
import com.kingdee.eas.port.equipment.insurance.InsuranceCoverageInfo;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;

/**
 * output class name
 */
public class EquInsuranceAccidentEditUI extends AbstractEquInsuranceAccidentEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EquInsuranceAccidentEditUI.class);
    
    /**
     * output class constructor
     */
    public EquInsuranceAccidentEditUI() throws Exception
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
        return com.kingdee.eas.port.equipment.insurance.EquInsuranceAccidentFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.insurance.EquInsuranceAccidentInfo objectValue = new com.kingdee.eas.port.equipment.insurance.EquInsuranceAccidentInfo();
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
//		prmtpolicyNumber.setEnabled(false);
		prmtinsuranceCompany.setEnabled(false);
		prmtinsurance.setEnabled(false);
		txtequName.setEnabled(false);
		super.onLoad();
		 EntityViewInfo evi = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		 filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit.id",id ,CompareType.EQUALS));
//		 filter.getFilterItems().add(new FilterItemInfo("sbStatus","1",CompareType.EQUALS));
		 evi.setFilter(filter);
		 prmtequNumber.setEntityViewInfo(evi);
		 if(getOprtState().equals(OprtState.ADDNEW)){
			 pkBizDate.setValue(new Date());
			 pklossDate.setValue(new Date());
			 prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
		 }
		 prmtequNumber.setRequired(true);
		 prmtpolicyNumber.setRequired(true);
	}
	
	public void prmtequNumber_Changed() throws Exception {
		super.prmtequNumber_Changed();
		if(prmtequNumber.getValue() != null){
			String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			String equID =((EquIdInfo)prmtequNumber.getData()).getId().toString() ;	
			 StringBuffer sb = new StringBuffer();
			 sb.append("/*dialect*/select * from(");
			 sb.append(" select b.faudittime,b.fid,a.CFEquNumberID,b.CFInsurancecompany,b.fnumber,b.cfxianzhongid");
			 sb.append("  from CT_INS_InsuranceCoverageE1 a");
			 sb.append("  left join CT_INS_InsuranceCoverage b on a.FParentID = b.fid");
			 sb.append("  where b.FStatus = '4'");
			 sb.append("  and a.CFEquNumberID = '"+equID+"'");
			 sb.append("  and b.fcontrolunitid = '"+id+"'");
			 sb.append("  order by b.faudittime DESC)");
			 sb.append("  where ROWNUM = '1'");
			 IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			 while (rowSet.next()) {
				 String id1 = rowSet.getString("fid");//保险投保明细表ID
//				 if(id1 != null){
//					 InsuranceCoverageInfo icInfo = InsuranceCoverageFactory.getRemoteInstance().getInsuranceCoverageInfo(new ObjectUuidPK(id1));
//					 prmtpolicyNumber.setValue(icInfo);
//				 }else{
//					 prmtpolicyNumber.setValue(null);
//				 }
				 if(id1 != null){
					 EntityViewInfo evi = new EntityViewInfo();
					 FilterInfo filter = new FilterInfo();
					 filter.getFilterItems().add(new FilterItemInfo("id",id1 ,CompareType.EQUALS));
					 evi.setFilter(filter);
					 prmtpolicyNumber.setEntityViewInfo(evi);
				 }
//					String id2 = rowSet.getString("CFInsurancecompany");//保险公司ID
//				 if(id2 != null){
//					 InsuranceCompanyInfo iscInfo = InsuranceCompanyFactory.getRemoteInstance().getInsuranceCompanyInfo(new ObjectUuidPK(id2));
//					 prmtinsuranceCompany.setValue(iscInfo);
//				 }else{
//					 prmtinsuranceCompany.setValue(null);
//				 }
//				 String id3 = rowSet.getString("cfxianzhongid");//险种
//				 if(id3 != null){
//					 txtxianzhongID.setText(id3);
//				 }
//				
//					if(UIRuleUtil.isNotNull(this.txtxianzhongID.getText()))
//			        {
//			        	String spicId[] = (this.txtxianzhongID.getText().trim()).split("&");
//			        	try {
//			        		IInsurance IInsurance = InsuranceFactory.getRemoteInstance();
//			        		
//			        		InsuranceInfo objValue[] = new InsuranceInfo[spicId.length];
//			        		
//							for (int i = 0; i < spicId.length; i++) 
//							{
//								String oql ="select id,name,number where id='"+spicId[i]+"'";
//								objValue[i] = IInsurance.getInsuranceInfo(oql);
//							}
//							this.prmtinsurance.setValue(objValue);
//						} catch (BOSException e) {
//							e.printStackTrace();
//						} catch (EASBizException e) {
//							e.printStackTrace();
//						}
//			        }else{
//			        	this.prmtinsurance.setValue(null);
//			        }
				 
			 }
		}else{
			 prmtpolicyNumber.setValue(null);
			 prmtinsuranceCompany.setValue(null);
			 this.prmtinsurance.setValue(null);
		}
	}
	
	protected void prmtpolicyNumber_dataChanged(DataChangeEvent e)throws Exception {
		super.prmtpolicyNumber_dataChanged(e);
			if(prmtpolicyNumber.getValue() != null){
				String idpo = ((InsuranceCoverageInfo)prmtpolicyNumber.getData()).getId().toString();
				InsuranceCoverageInfo isInfo = InsuranceCoverageFactory.getRemoteInstance().getInsuranceCoverageInfo(new ObjectUuidPK(idpo));
				if(isInfo.getXianzhongID() != null){
					String spicId[] = (isInfo.getXianzhongID().trim()).split("&");
		        		IInsurance IInsurance = InsuranceFactory.getRemoteInstance();
		        		InsuranceInfo objValue[] = new InsuranceInfo[spicId.length];
						for (int i = 0; i < spicId.length; i++) 
						{
							String oql ="select id,name,number where id='"+spicId[i]+"'";
							objValue[i] = IInsurance.getInsuranceInfo(oql);
						}
						this.prmtinsurance.setValue(objValue);
				}else{
					prmtinsurance.setValue(null);
				}
				
				if(isInfo.getInsuranceCompany() != null){
					String idbx = ((InsuranceCompanyInfo)isInfo.getInsuranceCompany()).getId().toString();
					InsuranceCompanyInfo incomInfo = InsuranceCompanyFactory.getRemoteInstance().getInsuranceCompanyInfo(new ObjectUuidPK(idbx));
					prmtinsuranceCompany.setValue(incomInfo);
				}else{
					prmtinsuranceCompany.setValue(null);
				}
			}else{
				prmtinsurance.setValue(null);
				prmtinsuranceCompany.setValue(null);
			}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtequNumber.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"设备编号"});
		}
		if (com.kingdee.bos.ui.face.UIRuleUtil.isNull(prmtpolicyNumber.getValue())) {
			throw new com.kingdee.eas.common.EASBizException(com.kingdee.eas.common.EASBizException.CHECKBLANK,new Object[] {"保单号"});
		}
		super.verifyInput(e);
	}
}