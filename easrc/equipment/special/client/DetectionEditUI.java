/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.fa.basedata.FaCatFactory;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DetectionEditUI extends AbstractDetectionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DetectionEditUI.class);
    
    /**
     * output class constructor
     */
    public DetectionEditUI() throws Exception
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
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.special.DetectionFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.special.DetectionInfo objectValue = new com.kingdee.eas.port.equipment.special.DetectionInfo();
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
	
	String cleName[] = { "厂车", "起重", " 电梯" , "压力管道", "压力容器" };
	
	public void onLoad() throws Exception {
		 this.kdtE1.getColumn("seq").getStyleAttributes().setHided(true);
		 this.kdtE2.getColumn("seq").getStyleAttributes().setHided(true);
		super.onLoad();
		if(getOprtState().equals(OprtState.ADDNEW)){
			this.pkBizDate.setValue(new Date());
			prmtCU.setValue(SysContext.getSysContext().getCurrentCtrlUnit());
		 }
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		this.kDContainer1.setTitle("实际检测明细");
		this.kDContainer1.getContentPane().add(kdtE2, BorderLayout.CENTER);
		
		this.kdtE1.getColumn("qualifiedRate1").getStyleAttributes().setNumberFormat("#,##0.00 ");
		this.kdtE1.getColumn("qualifiedRate2").getStyleAttributes().setNumberFormat("#,##0.00 ");
		
		kdtE1_detailPanel.setVisible(false);
		kdtE1.setBounds(new Rectangle(33, 66, 944, 215));
		kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel) com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.operate.ComproductionEntryInfo(),null, false);
		this.add(kdtE1, new KDLayout.Constraints(33, 66, 944, 215,KDLayout.Constraints.ANCHOR_TOP| KDLayout.Constraints.ANCHOR_BOTTOM| KDLayout.Constraints.ANCHOR_LEFT| KDLayout.Constraints.ANCHOR_RIGHT));
		
		Set<String> catMap = new HashSet<String>();
		IRowSet rowset = new XRSQLBuilder().appendSql(getCatType()).executeQuery();
		while(rowset.next())
		{
			catMap.add(rowset.getString("catId"));
		}
		
		this.kdtE1.removeRows();
		
		IRowSet newrowset = null;
		Iterator itertor = catMap.iterator();
		
		while(itertor.hasNext())
		{
			String id = (String) itertor.next();
			//市检
			String oql10 = getsql("10",editData.getId().toString(),id);
			//港检
			String oql20 = getsql("20",editData.getId().toString(),id);
			IRow row = this.kdtE1.addRow();
			String oql = "select id,name,number where id='"+id+"'";
			row.getCell("deviceType1").setValue(FaCatFactory.getRemoteInstance().getFaCatCollection(oql).get(0).getName());
			//市检
			newrowset = new XRSQLBuilder().appendSql(oql10).executeQuery();
			while(newrowset.next())
			{
				row.getCell("planNumber1").setValue(newrowset.getString("计划数"));
				row.getCell("actualNumber1").setValue(newrowset.getString("实际数"));
				row.getCell("qualifiedNumber1").setValue(newrowset.getString("合格数"));
				row.getCell("qualifiedRate1").setValue(newrowset.getString("合格率"));
			}

			//港检
			newrowset = new XRSQLBuilder().appendSql(oql20).executeQuery();
			while(newrowset.next())
			{
				row.getCell("planNumber2").setValue(newrowset.getString("计划数"));
				row.getCell("actualNumber2").setValue(newrowset.getString("实际数"));
				row.getCell("qualifiedNumber2").setValue(newrowset.getString("合格数"));
				row.getCell("qualifiedRate2").setValue(newrowset.getString("合格率"));
			}
		}
	}
	
	
	 /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    	Set<String> catMap = new HashSet<String>();
		IRowSet rowset = new XRSQLBuilder().appendSql(getCatType()).executeQuery();
		while(rowset.next())
		{
			catMap.add(rowset.getString("catId"));
		}
		
		this.kdtE1.removeRows();
		
		IRowSet newrowset = null;
		Iterator itertor = catMap.iterator();
		
		while(itertor.hasNext())
		{
			String id = (String) itertor.next();
			//市检
			String oql10 = getsql("10",editData.getId().toString(),id);
			//港检
			String oql20 = getsql("20",editData.getId().toString(),id);
			IRow row = this.kdtE1.addRow();
			String oql = "select id,name,number where id='"+id+"'";
			row.getCell("deviceType1").setValue(FaCatFactory.getRemoteInstance().getFaCatCollection(oql).get(0).getName());
			//市检
			newrowset = new XRSQLBuilder().appendSql(oql10).executeQuery();
			while(newrowset.next())
			{
				row.getCell("planNumber1").setValue(newrowset.getString("计划数"));
				row.getCell("actualNumber1").setValue(newrowset.getString("实际数"));
				row.getCell("qualifiedNumber1").setValue(newrowset.getString("合格数"));
				row.getCell("qualifiedRate1").setValue(newrowset.getString("合格率"));
			}

			//港检
			newrowset = new XRSQLBuilder().appendSql(oql20).executeQuery();
			while(newrowset.next())
			{
				row.getCell("planNumber2").setValue(newrowset.getString("计划数"));
				row.getCell("actualNumber2").setValue(newrowset.getString("实际数"));
				row.getCell("qualifiedNumber2").setValue(newrowset.getString("合格数"));
				row.getCell("qualifiedRate2").setValue(newrowset.getString("合格率"));
			}
		}
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	
	/**
	 * 
	 * @param type
	 * type =10 是市检，20是港检
	 * @return
	 */
	private String getsql(String type,String editId,String typeID){
		StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/select d.fname_l2 设备类型,nvl(count(b.fid), 0) 计划数, nvl(sj.实际数, 0) 实际数,nvl(hg.合格数, 0) 合格数, ");
		sb.append("  (case when nvl(sj.实际数, 0) = '0' then 0 when nvl(sj.实际数, 0) <> '0' then nvl(hg.合格数, 0) / nvl(sj.实际数, 0) * 100 end )合格率");
		sb.append("  from CT_SPE_Detection a");
		sb.append("  left join CT_SPE_DetectionE2 b on a.fid = b.fparentid");
		sb.append("  left join CT_REC_EquId c on b.cfzdanumberid = c.fid");
		sb.append("  left join T_FA_Cat d on d.fid = c.cfeqmtypeid");
		sb.append("  left join (select d.fid catID, count(b.fid) 实际数");
		sb.append("  from CT_SPE_Detection a");
		sb.append("  left join CT_SPE_DetectionE2 b on a.fid = b.fparentid");
		sb.append("  left join CT_REC_EquId c on b.cfzdanumberid = c.fid");
		sb.append("  left join T_FA_Cat d on d.fid = c.cfeqmtypeid");
		sb.append("  where CFCheck = '1'");
		sb.append("  and CFTestCategory = '"+type+"'");
		sb.append("  and a.fid = '"+editId+"'");
		sb.append("  group by d.fid) sj on sj.catID = d.fid");
		sb.append("  left join (select d.fid catID, count(b.fid) 合格数");
		sb.append("  from CT_SPE_Detection a");
		sb.append("  left join CT_SPE_DetectionE2 b on a.fid = b.fparentid");
		sb.append("  left join CT_REC_EquId c on b.cfzdanumberid = c.fid");
		sb.append("  left join T_FA_Cat d on d.fid = c.cfeqmtypeid");
		sb.append("  where CFTestResults = '10'");
		sb.append("  and a.fid =  '"+editId+"'");
		sb.append("  and CFTestCategory = '"+type+"'");
		sb.append("  group by d.fid) hg on hg.catID = d.fid");
		sb.append("  where a.fid =  '"+editId+"'");
		sb.append("  and CFTestCategory = '"+type+"'");
		sb.append("  and d.fid = '"+typeID+"'");
		sb.append("  group by d.fid, d.fname_l2, sj.实际数, hg.合格数");
		return sb.toString();
	}
	
	private String getCatType(){
		String sql = "select d.fid catId,d.fname_l2 catName from CT_SPE_Detection a  left join CT_SPE_DetectionE2 b on a.fid = b.fparentid  left join CT_REC_EquId c on b.cfzdanumberid = c.fid  left join T_FA_Cat d on d.fid = c.cfeqmtypeid  " +
				" where a.fid='"+editData.getId()+"' group by d.fid,d.fname_l2";
		return sql;
	}

}