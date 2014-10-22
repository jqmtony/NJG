/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;

/**
 * output class name
 */
public class ShipFuelEditUI extends AbstractShipFuelEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ShipFuelEditUI.class);
    
    /**
     * output class constructor
     */
    public ShipFuelEditUI() throws Exception
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
        return com.kingdee.eas.port.equipment.operate.ShipFuelFactory.getRemoteInstance();
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
        com.kingdee.eas.port.equipment.operate.ShipFuelInfo objectValue = new com.kingdee.eas.port.equipment.operate.ShipFuelInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
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
		contDescription.setVisible(false);
		contBizDate.setVisible(false);
		contBizStatus.setVisible(false);
		txtintoTotal.setEnabled(false);
		txttotalConsum.setEnabled(false);
		txtmonthBalance.setEnabled(false);
		txtgzdel.setEnabled(false);
		txtxyzdel.setEnabled(false);
		txtmonthBalance.setEnabled(false);
		txtTotal.setEnabled(false);
		txthjde.setEnabled(false);
		txthjdel.setEnabled(false);
		txtzhdel.setEnabled(false);
		txtshiyongliang.setEnabled(false);
		txtjieyou.setEnabled(false);
		txtchaohao.setEnabled(false);
		txtrunhuayouben.setEnabled(false);
		txtchilunyouben.setEnabled(false);
		txtyeyayouben.setEnabled(false);
		txtshijirunranbi.setEnabled(false);
		txtshiyongliangone.setEnabled(false);
		txtjieone.setEnabled(false);
		txtchaoone.setEnabled(false);
		txtjishubenyue.setEnabled(false);
		txtchanzhibenyue.setEnabled(false);
		txtjishuleiji.setEnabled(false);
		txtchanzhileiji.setEnabled(false);
		txtzuoshangyue.setEnabled(false);
		txtyoujishangyue.setEnabled(false);
		txtfujishangyue.setEnabled(false);
		txtdianbiaoshangyue.setEnabled(false);
		txtzuoheji.setEnabled(false);
		txtyoujiheji.setEnabled(false);
		txtfujiheji.setEnabled(false);
		txtdianbiaoheji.setEnabled(false);
		txtleijiyongdian.setEnabled(false);
		txtleijiranyou.setEnabled(false);
		txtleijiyunshi.setEnabled(false);
		txtleijichanzhi.setEnabled(false);
		super.onLoad();
		prmtreportMonth.setRequired(true);
		if(prmtreportMonth.getValue() != null){
			String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
			PeriodInfo pdInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
	//查询本年累计数据
		//累计用电量（度），累计燃油耗量（t），累计运时（h），累计产值（万元）
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String month = String.valueOf(pdInfo.getNumber());
		String year = (df.format(month)).substring(0, 4);
		  StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/ select  sum(a.CFLeijiyongdian) 累计用电, sum(a.CFLeijiranyou) 累计燃油,sum(a.CFLeijiyunshi) 累计运时 ,sum(a.CFLeijichanzhi) 累计产值");
		sb.append(" from CT_OPE_ShipFuel a");
		sb.append(" left join T_BD_Period b on a.CFReportMonthID = b.fid");
		sb.append(" where b.fnumber ='"+year+"' and a.FStatus = '4'");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		while (rowSet.next()) {
			BigDecimal bigleijidian = getBigDecimal(rowSet.getString("累计用电"));
			BigDecimal bigleijiyou = getBigDecimal(rowSet.getString("累计燃油"));
			BigDecimal bigleijishi = getBigDecimal(rowSet.getString("累计运时"));
			BigDecimal bigleijizhi = getBigDecimal(rowSet.getString("累计产值"));
			txtleijiyongdian.setText(String.valueOf(bigleijidian));
			txtleijiranyou.setText(String.valueOf(bigleijiyou));
			txtleijiyunshi.setText(String.valueOf(bigleijishi));
			txtleijichanzhi.setText(String.valueOf(bigleijizhi));
		  }
		}
	}
	
	
	    public void actionAudit_actionPerformed(ActionEvent e) throws Exception{
	        super.actionAudit_actionPerformed(e);
	    	if(prmtreportMonth.getValue() != null){
				String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
				PeriodInfo pdInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
		//查询本年累计数据
			//累计用电量（度），累计燃油耗量（t），累计运时（h），累计产值（万元）
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String month = String.valueOf(pdInfo.getNumber());
			String year = (df.format(month)).substring(0, 4);
			  StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/ select  sum(a.CFLeijiyongdian) 累计用电, sum(a.CFLeijiranyou) 累计燃油,sum(a.CFLeijiyunshi) 累计运时 ,sum(a.CFLeijichanzhi) 累计产值");
			sb.append(" from CT_OPE_ShipFuel a");
			sb.append(" left join T_BD_Period b on a.CFReportMonthID = b.fid");
			sb.append(" where b.fnumber ='"+year+"' and a.FStatus = '4'");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			while (rowSet.next()) {
				BigDecimal bigleijidian = getBigDecimal(rowSet.getString("累计用电"));
				BigDecimal bigleijiyou = getBigDecimal(rowSet.getString("累计燃油"));
				BigDecimal bigleijishi = getBigDecimal(rowSet.getString("累计运时"));
				BigDecimal bigleijizhi = getBigDecimal(rowSet.getString("累计产值"));
				txtleijiyongdian.setText(String.valueOf(bigleijidian));
				txtleijiranyou.setText(String.valueOf(bigleijiyou));
				txtleijiyunshi.setText(String.valueOf(bigleijishi));
				txtleijichanzhi.setText(String.valueOf(bigleijizhi));
			  }
			}
	    }
	
	protected void prmtreportMonth_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtreportMonth_dataChanged(e);
//		if(prmtreportMonth.getValue() != null){
//			String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
//			PeriodInfo pdInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
//			String date1 = String.valueOf(pdInfo.getNumber());
//			Calendar cal = Calendar.getInstance();
////			cal.setTime(date1);
//		    cal.add(Calendar.MONTH, -1);
//		  
////		    StringBuffer sb = new StringBuffer();
////			sb.append("/*dialect*/ select A.FSEQ,nvl(sum(a.CFStagePerformance),0) bqsj,nvl(sum(a.CFProEnergy),0) zxscnh,");
////			sb.append(" nvl(sum(a.CFFzproEnergy),0) fzscnh,nvl(sum(a.CFLifeEnergy),0) shnh,nvl(sum(a.CFOtherEnergy),0) qtnh,nvl(sum(a.CFSamePerformance),0) spfa,nvl(sum(a.CFIncreaseDecrease),0) icdc,'0',nvl(sum(a.CFPeriodCon),0) bqdh,nvl(sum(a.CFSamePeriod),0) spod,'0',nvl(sum(a.CFExcessSection),0) estn");
////			sb.append(" from CT_OPE_ComproductionEntry a");
////			sb.append(" left join CT_OPE_Comproduction b on a.fparentid = b.fid");
////			sb.append(" where a.CFProject = '本月' and b.CFReportingUnitID = '"+ ((AdminOrgUnitInfo) prmtreportingUnit.getData()).getId().toString() + "' AND to_char(b.fbizdate,'YYYY')='"+ year + "' AND b.cfstate = '4'");
////			sb.append(" group by A.FSEQ  ORDER BY A.FSEQ");
////			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
////			int rowindex = 10;
////			while (rowSet.next()) {
////				int column = 2;
////				for (int j = 3; j < this.kdtEntrys.getColumnCount(); j++) {
////					this.kdtEntrys.getCell(rowindex, j).setValue(rowSet.getBigDecimal(column));
////					column++;
////				}
//		}
		
		if(prmtreportMonth.getValue() != null){
			String id = ((PeriodInfo)prmtreportMonth.getData()).getId().toString();
			PeriodInfo pdInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(id));
	//查询本年累计数据
		//累计用电量（度），累计燃油耗量（t），累计运时（h），累计产值（万元）
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String month = String.valueOf(pdInfo.getNumber());
		String year = (df.format(month)).substring(0, 4);
		  StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/ select  sum(a.CFLeijiyongdian) 累计用电, sum(a.CFLeijiranyou) 累计燃油,sum(a.CFLeijiyunshi) 累计运时 ,sum(a.CFLeijichanzhi) 累计产值");
		sb.append(" from CT_OPE_ShipFuel a");
		sb.append(" left join T_BD_Period b on a.CFReportMonthID = b.fid");
		sb.append(" where b.fnumber ='"+year+"' and a.FStatus = '4'");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		while (rowSet.next()) {
			BigDecimal bigleijidian = getBigDecimal(rowSet.getString("累计用电"));
			BigDecimal bigleijiyou = getBigDecimal(rowSet.getString("累计燃油"));
			BigDecimal bigleijishi = getBigDecimal(rowSet.getString("累计运时"));
			BigDecimal bigleijizhi = getBigDecimal(rowSet.getString("累计产值"));
			txtleijiyongdian.setText(String.valueOf(bigleijidian));
			txtleijiranyou.setText(String.valueOf(bigleijiyou));
			txtleijiyunshi.setText(String.valueOf(bigleijishi));
			txtleijichanzhi.setText(String.valueOf(bigleijizhi));
		  }
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		
		//燃料
		BigDecimal bigzhione = getBigDecimal(txtzhione.getText());
		BigDecimal bigqione = getBigDecimal(txtqione.getText());
		BigDecimal bigmiduone = getBigDecimal(txtmiduone.getText());
		BigDecimal bigzhitwo = getBigDecimal(txtzhitwo.getText());
		BigDecimal bigqitwo = getBigDecimal(txtqitwo.getText());
		BigDecimal bigmidutwo = getBigDecimal(txtmidutwo.getText());
		BigDecimal bigintoTotal = (bigzhione.subtract(bigqione)).multiply(bigmiduone).add((bigzhitwo.subtract(bigqitwo)).multiply(bigmidutwo));
		txtintoTotal.setText(String.valueOf(bigintoTotal));//领入合计=(止-起)*密度+(止-起)*密度
		
		BigDecimal biglastMonth = getBigDecimal(txtlastMonth.getText());
		BigDecimal bigzhithree = getBigDecimal(txtzhithree.getText());
		BigDecimal bigqithree = getBigDecimal(txtqithree.getText());
		BigDecimal bigmiduthree = getBigDecimal(txtmiduthree.getText());
		BigDecimal bigzhifour = getBigDecimal(txtzhifour.getText());
		BigDecimal bigqifour = getBigDecimal(txtqifour.getText());
		BigDecimal bigmidufour = getBigDecimal(txtmidufour.getText());
		BigDecimal bigtotalConsum = (bigzhithree.subtract(bigqithree)).multiply(bigmiduthree).add((bigzhifour.subtract(bigqifour)).multiply(bigmidufour));
		txttotalConsum.setText(String.valueOf(bigtotalConsum));//消耗合计=(止-起)*密度+(止-起)*密度
		
		txttotalConsum.setText(String.valueOf(biglastMonth.add(bigintoTotal).subtract(bigtotalConsum)));//本月结存=上月结存+领入合计-消耗合计
		
		
		BigDecimal bigportShipment = getBigDecimal(txtportShipment.getText());
		BigDecimal biggzde = getBigDecimal(txtgzde.getText());
		BigDecimal biggzdel = bigportShipment.multiply(biggzde);
		txtgzdel.setText(String.valueOf(biggzdel));//港作运时定额量=港作运时实绩*港作运时定额
		
		BigDecimal bigsmallTransport = getBigDecimal(txtsmallTransport.getText());
		BigDecimal bigxyzde = getBigDecimal(txtxyzde.getText());
		BigDecimal bigxyzdel = bigsmallTransport.multiply(bigxyzde);
		txtxyzdel.setText(String.valueOf(bigxyzdel));//小运转运时定额量=小运转运时实绩*小运转运时定额
		
		BigDecimal bigTotal = bigportShipment.add(bigsmallTransport);
		BigDecimal bighjde = biggzde.add(bigxyzde);
		txtTotal.setText(String.valueOf(bigTotal));
		txthjde.setText(String.valueOf(bighjde));
		txthjdel.setText(String.valueOf(bigTotal.multiply(bighjde)));//合计定额量=合计实绩*合计定额
		
		txtzhdel.setText(String.valueOf(bigTotal.multiply(bighjde)));//综合定额量=合计定额量
		
		txtshiyongliang.setText(String.valueOf(bigtotalConsum));//实用量 = 消耗合计
		
		BigDecimal bigzhdel = getBigDecimal(txtzhdel.getText());
		BigDecimal bigshiyongliang = getBigDecimal(txtshiyongliang.getText());
		BigDecimal abc = bigzhdel.subtract(bigshiyongliang);//节超=定额量-实用量
		if(abc.compareTo(BigDecimal.ZERO) == 1){
			txtjieyou.setText(String.valueOf(abc));
		}
		if(abc.compareTo(BigDecimal.ZERO) == -1){
			txtchaohao.setText(String.valueOf(abc));
		}
		
		//润料
		BigDecimal bigrunhuayoujiecun = getBigDecimal(txtrunhuayoujiecun.getText());//润滑油上月结存
		BigDecimal biglingrurhy = getBigDecimal(txtlingrurhy.getText());//润滑油本月领入
		BigDecimal bigrunhuayougangzuo = getBigDecimal(txtrunhuayougangzuo.getText());//润滑油港作消耗
		BigDecimal bigrunhuayouxiao = getBigDecimal(txtrunhuayouxiao.getText());//润滑油小运转消耗
		BigDecimal bigrunhuayouben = bigrunhuayoujiecun.add(biglingrurhy).subtract(bigrunhuayougangzuo).subtract(bigrunhuayouxiao);
		txtrunhuayouben.setText(String.valueOf(bigrunhuayouben));//润滑油本月结存= 润滑油上月结存+润滑油本月领入-润滑油港作消耗-润滑油小运转消耗
		
		BigDecimal bigchilunyoushang = getBigDecimal(txtchilunyoushang.getText());//齿轮油上月结存
		BigDecimal biglingrucly = getBigDecimal(txtlingrucly.getText());//齿轮油本月领入
		BigDecimal bigchilunyougang = getBigDecimal(txtchilunyougang.getText());//齿轮油港作消耗
		BigDecimal bigchilunyouxiao = getBigDecimal(txtchilunyouxiao.getText());//齿轮油小运转消耗
		BigDecimal bigchilunyouben = bigchilunyoushang.add(biglingrucly).subtract(bigchilunyougang).subtract(bigchilunyouxiao);
		txtchilunyouben.setText(String.valueOf(bigchilunyouben));//齿轮油本月结存= 齿轮油上月结存+齿轮油本月领入-齿轮油港作消耗-齿轮油小运转消耗
		
		BigDecimal bigyeyayoushang = getBigDecimal(txtyeyayoushang.getText());//液压油上月结存
		BigDecimal biglingruyyy = getBigDecimal(txtlingruyyy.getText());//液压油本月领入
		BigDecimal bigyeyayougang = getBigDecimal(txtyeyayougang.getText());//液压油港作消耗
		BigDecimal bigyeyayouxiao = getBigDecimal(txtyeyayouxiao.getText());//液压油小运转消耗
		BigDecimal bigyeyayouben = bigyeyayoushang.add(biglingruyyy).subtract(bigyeyayougang).subtract(bigyeyayouxiao);
		txtyeyayouben.setText(String.valueOf(bigyeyayouben));//液压油本月结存= 液压油上月结存+液压油本月领入-液压油港作消耗-液压油小运转消耗
		
		BigDecimal bigshiyongliangone = bigrunhuayougangzuo.add(bigrunhuayouxiao).add(bigchilunyougang).add(bigchilunyouxiao).add(bigyeyayougang).add(bigyeyayouxiao);
		txtshiyongliangone.setText(String.valueOf(bigshiyongliangone));//实用量=润滑油港作消耗+润滑油小运转消耗+齿轮油港作消耗+齿轮油小运转消耗+液压油港作消耗+液压油小运转消耗
		
		double bigshijirunranbi = Double.parseDouble(String.valueOf((bigtotalConsum.compareTo(BigDecimal.ZERO))!=0?bigshiyongliangone.divide(bigtotalConsum,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("0.01")):BigDecimal.ZERO));
		txtshijirunranbi.setText(String.valueOf(bigshijirunranbi));//实际润燃比=润料消耗合计/燃油消耗合计*100%
		
		BigDecimal bigdingeliangxx = getBigDecimal(txtdingeliangxx.getText());//定额量
		BigDecimal aaa = bigdingeliangxx.subtract(bigshiyongliangone);//节超=定额量-实用量
		if(aaa.compareTo(BigDecimal.ZERO) == 1){
			txtjieone.setText(String.valueOf(aaa));
		}
		if(aaa.compareTo(BigDecimal.ZERO) == -1){
			txtchaoone.setText(String.valueOf(aaa));
		}
		
		double bigjishubenyue = Double.parseDouble(String.valueOf((bighjde.compareTo(BigDecimal.ZERO))!=0?bigtotalConsum.divide(bighjde,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("1")):BigDecimal.ZERO));
		txtjishubenyue.setText(String.valueOf(bigjishubenyue));//本月技术单耗=柴油消耗合计/实绩合计
		
		BigDecimal bigoutputValue = getBigDecimal(txtoutputValue.getText());//液压油上月结存
		double bigchanzhibenyue = Double.parseDouble(String.valueOf((bigoutputValue.compareTo(BigDecimal.ZERO))!=0?bigtotalConsum.divide(bigoutputValue,  4, BigDecimal.ROUND_UP).multiply(new BigDecimal("1")):BigDecimal.ZERO));
		txtchanzhibenyue.setText(String.valueOf(bigchanzhibenyue));//本月产值单耗 = 柴油消耗合计/产值实绩
		
		BigDecimal bigzuobenyue = getBigDecimal(txtzuobenyue.getText());//本月左主机计时
		BigDecimal bigzuoshangyue = getBigDecimal(txtzuoshangyue.getText());//上月左主机计时
		BigDecimal bigzuoheji = bigzuobenyue.subtract(bigzuoshangyue);
		txtzuoheji.setText(String.valueOf(bigzuoheji));//左主机计时小计=本月左主机计时-上月左主机计时
		BigDecimal bigyoujibenyue = getBigDecimal(txtyoujibenyue.getText());//本月右主机计时
		BigDecimal bigyoujishangyue = getBigDecimal(txtyoujishangyue.getText());//上月右主机计时
		BigDecimal bigyoujiheji = bigyoujibenyue.subtract(bigyoujishangyue);
		txtyoujiheji.setText(String.valueOf(bigyoujiheji));//右主机计时小计 = 本月右主机计时-上月右主机计时
		BigDecimal bigfujibenyue = getBigDecimal(txtfujibenyue.getText());//本月付机运时
		BigDecimal bigfujishangyue = getBigDecimal(txtfujishangyue.getText());//上月付机运时
		BigDecimal bigfujiheji = bigfujibenyue.subtract(bigfujishangyue);
		txtfujiheji.setText(String.valueOf(bigfujiheji));//付机运时小计 = 本月付机运时 - 上月付机运时
		BigDecimal bigdianbiaobenyue = getBigDecimal(txtdianbiaobenyue.getText());//本月电表读数
		BigDecimal bigdianbiaoshangyue = getBigDecimal(txtdianbiaoshangyue.getText());//上月电表读数
		BigDecimal bigdianbiaoheji = bigdianbiaobenyue.subtract(bigdianbiaoshangyue);
		txtdianbiaoheji.setText(String.valueOf(bigdianbiaoheji));//电表读数小计 = 本月电表读数-上月电表读数
		super.verifyInput(e);
	}

	

	public static BigDecimal getBigDecimal(String value) 
	{
		if (value == null||"".equals(value.trim()))
			return new BigDecimal(0.0D);
		else
			return new BigDecimal(value);
	}

}