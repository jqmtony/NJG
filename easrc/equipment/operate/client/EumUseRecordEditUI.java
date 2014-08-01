/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.data.engine.script.beanshell.function.datetime.MONTH;
import com.kingdee.bos.ctrl.freechart.data.time.Month;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fi.fa.basedata.FaCatFactory;
import com.kingdee.eas.fi.fa.basedata.IFaCat;
import com.kingdee.eas.port.equipment.base.CzUnitFactory;
import com.kingdee.eas.port.equipment.base.CzUnitInfo;
import com.kingdee.eas.port.equipment.base.PowerUnitFactory;
import com.kingdee.eas.port.equipment.base.PowerUnitInfo;
import com.kingdee.eas.port.equipment.base.PuUnitFactory;
import com.kingdee.eas.port.equipment.base.PuUnitInfo;
import com.kingdee.eas.port.equipment.base.enumbase.CostType;
import com.kingdee.eas.port.equipment.base.enumbase.sbStatusType;
import com.kingdee.eas.port.equipment.record.EquIdCollection;
import com.kingdee.eas.port.equipment.record.EquIdFactory;
import com.kingdee.eas.port.equipment.record.EquIdInfo;
import com.kingdee.eas.port.equipment.record.IEquId;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.helper.Tool;
import com.kingdee.eas.xr.helper.XRSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class EumUseRecordEditUI extends AbstractEumUseRecordEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(EumUseRecordEditUI.class);

	/**
	 * output class constructor
	 */
	public EumUseRecordEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		 this.kdtEqmUse.getColumn("seq").getStyleAttributes().setHided(true);
		 this.kdtEqmUse.getColumn("startTime").getStyleAttributes().setHided(true);
		 this.kdtEqmUse.getColumn("endTime").getStyleAttributes().setHided(true);
		 prmtUseOrgUnit.setEnabled(false);
		super.onLoad();
		if(getOprtState().equals(OprtState.ADDNEW)){
			pkBizDate.setValue(new Date());
			//获取当前的登录人，除了User用户之外。
			prmtstaPerson.setValue(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo().getPerson());
//			FilterInfo filter = new FilterInfo();
//			EntityViewInfo view = new EntityViewInfo();
//			view.setFilter(filter);
//			filter.getFilterItems().add(new FilterItemInfo("tzsbStatus",sbStatusType.INUSE_VALUE));
//			filter.getFilterItems().add(new FilterItemInfo("ssOrgUnit",SysContext.getSysContext().getCurrentCtrlUnit().getId(),CompareType.EQUALS));
//			EquIdCollection coll = EquIdFactory.getRemoteInstance().getEquIdCollection(view);
//			IFaCat ifacat = FaCatFactory.getRemoteInstance();
//			for (int i = 0; i < coll.size(); i++) {
//				IRow row = kdtEqmUse.addRow(i);
//				EquIdInfo info = coll.get(i);
//				String eqmcat = ifacat.getFaCatInfo(new ObjectUuidPK(info.getEqmType().getId())).getName();
//				row.getCell("seq").setValue(i+1);
//				row.getCell("eqmName").setValue(info.getName());
//				row.getCell("modelType").setValue(info.getSize());
//				row.getCell("eqmType").setValue(eqmcat);
//				row.getCell("eqmCategory").setValue(info.getEqmCategory());
////				System.out.println(info.getName()+"=============>"+eqmcat);
//			}
			this.prmtUseOrgUnit.setValue(SysContext.getSysContext().getCurrentAdminUnit());
			
			//引入在用的主要设备以及相关信息
			String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			StringBuffer sb = new StringBuffer();
			sb.append("/*dialect*/select");
			sb.append(" a.fid 设备名称,b.fname_l2 设备类型,a.CFEqmCategory 设备类别,a.CFModel 规格型号");
			sb.append(" from CT_REC_EquId a");
			sb.append(" left join T_FA_Cat b on a.CFEqmTypeID = b.fid");
			sb.append(" where a.cfsbstatus = '1'");
			sb.append(" and a.CFIsMainEqm = '1'");
			sb.append(" and a.CFSsOrgUnitID = '"+id+"'");
			IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
			this.kdtEqmUse.removeRows();
			IEquId iEquId = EquIdFactory.getRemoteInstance();
			while (rowSet.next()) {
				IRow row = this.kdtEqmUse.addRow();
				EquIdInfo equInfo = iEquId.getEquIdInfo(new ObjectUuidPK(rowSet.getString("设备名称")));
				row.getCell("eqmName").setValue(equInfo);
				row.getCell("modelType").setValue(rowSet.getString("规格型号"));
				row.getCell("eqmCategory").setValue(rowSet.getString("设备类别"));
				row.getCell("eqmType").setValue(rowSet.getString("设备类型"));
				getRepairOrder(row, equInfo);
				getMetialAmount(row,equInfo);
//				getLastmonth(row,equInfo);
			}
		}
		
		if(chkinitialiRecord.isSelected()){
			 this.kdtEqmUse.getColumn("startTime").getStyleAttributes().setHided(false);
			 this.kdtEqmUse.getColumn("endTime").getStyleAttributes().setHided(false);
		}else{
			 this.kdtEqmUse.getColumn("startTime").getStyleAttributes().setHided(true);
			 this.kdtEqmUse.getColumn("endTime").getStyleAttributes().setHided(true);
		}
		
	}

	
		protected void chkinitialiRecord_stateChanged(ChangeEvent e)throws Exception {
			super.chkinitialiRecord_stateChanged(e);
			if(chkinitialiRecord.isSelected()){
				 this.kdtEqmUse.getColumn("startTime").getStyleAttributes().setHided(false);
				 this.kdtEqmUse.getColumn("endTime").getStyleAttributes().setHided(false);
			}else{
				 this.kdtEqmUse.getColumn("startTime").getStyleAttributes().setHided(true);
				 this.kdtEqmUse.getColumn("endTime").getStyleAttributes().setHided(true);
				   for(int i = 0; i <kdtEqmUse.getRowCount(); i++){
					   kdtEqmUse.getCell(i, "startTime").setValue(null);
					   kdtEqmUse.getCell(i, "endTime").setValue(null);
				   }
			}
		}
	
	
	protected void verifyInput(ActionEvent e) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = (df.format(this.pkBizDate.getSqlDate())).substring(0, 7);
		String id = ((AdminOrgUnitInfo)prmtUseOrgUnit.getData()).getId().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("/*dialect*/select to_char(fbizdate,'yyyy-mm') datetime");
		sb.append(" from CT_OPE_EumUseRecord");
		sb.append(" where CFUseOrgUnitID = '"+id+"'");
		sb.append(" and to_char(fbizdate,'yyyy-mm')='"+time+"'and fnumber <> '"+editData.getNumber()+"'");
		IRowSet rowSet = new XRSQLBuilder().appendSql(sb.toString()).executeQuery();
		if(rowSet.size()>0){
			MsgBox.showInfo("本单位本月已有设备使用记录，不允许再新增!");
  			SysUtil.abort();
		}
		for (int i = 0; i <kdtEqmUse.getRowCount(); i++) {
			if(chkinitialiRecord.isSelected()){
				if(UIRuleUtil.isNull(kdtEqmUse.getCell(i, "startTime").getValue()))
				{
					MsgBox.showInfo("分录第{"+(i+1)+"}行的开始日期不能为空！");
					SysUtil.abort();
				}
				if(UIRuleUtil.isNull(kdtEqmUse.getCell(i, "endTime").getValue()))
				{
					MsgBox.showInfo("分录第{"+(i+1)+"}行的截止日期不能为空！");
					SysUtil.abort();
				}
				Date a = (Date) kdtEqmUse.getCell(i, "endTime").getValue();
				Date b = (Date) kdtEqmUse.getCell(i, "startTime").getValue();
				if(a.before(b)){
					MsgBox.showInfo("分录第{"+(i+1)+"}行截止日期不能晚于开始日期！");
					SysUtil.abort();
				}
			}
		}
		
	
		
		super.verifyInput(e);
	}
	
	private void getRepairOrder(IRow row,EquIdInfo info) throws BOSException, SQLException
	{
		String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String month = (df.format(this.pkBizDate.getSqlDate())).substring(0, 7);
		//修理费取当月维修单的总维修费用
		StringBuffer sb1 = new StringBuffer();
		sb1.append("/*dialect*/select");
		sb1.append(" CFEquNameID fid,");
		sb1.append(" nvl(sum(CFSelfAmount),0) samount,");
		sb1.append(" nvl(sum(CFOutAmount),0) outmount");
		sb1.append(" from CT_MAI_RepairOrder");
		sb1.append(" where to_char(fbizdate,'YYYY-MM')='"+month+"'");
		sb1.append(" and  CFEquNameID = '"+info.getId()+"'");
		sb1.append(" and  FStatus = '4'");
		sb1.append(" and FControlUnitID = '"+id+"' group by CFEquNameID");
		IRowSet rowSet1 = new XRSQLBuilder().appendSql(sb1.toString()).executeQuery();
		while (rowSet1.next()) {
				row.getCell("selfAmount").setValue(rowSet1.getBigDecimal("samount"));
				row.getCell("outAmount").setValue(rowSet1.getBigDecimal("outmount"));
	     	}
	}
	
	private void getMetialAmount(IRow row,EquIdInfo info) throws BOSException, SQLException
	{
		String id = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		//材料费取维修领料单分录的金额
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		String month1 = (df1.format(this.pkBizDate.getSqlDate())).substring(0, 7);
		StringBuffer sb2 = new StringBuffer();
		sb2.append("/*dialect*/select e.fid fid,");
		sb2.append(" nvl(sum(a.famount),0) amount");
		sb2.append(" from T_IM_MaterialReqBillEntry a");
		sb2.append(" left join T_IM_MaterialReqBill b on a.fparentid = b.fid");
		sb2.append(" left join CT_FI_AssetCard c on c.fid = a.cfassetid");
		sb2.append(" left join CT_BAS_InitialConnectionE1 d on d.CFInitialID = c.fid");
		sb2.append(" left join CT_REC_EquId e on e.fid = d.CFEqupmentID");
		sb2.append(" left join T_ORG_Storage f on b.FStorageOrgUnitID = f.fid");
		sb2.append(" where f.fid = '"+id+"'");
		sb2.append(" and e.fid = '"+info.getId()+"'");
		sb2.append(" and to_char(b.fbizdate,'YYYY-MM')='"+month1+"'");
		sb2.append(" and b.FBaseStatus = '4'");
		sb2.append(" group by e.fid");
		IRowSet rowSet2 = new XRSQLBuilder().appendSql(sb2.toString()).executeQuery();
		while (rowSet2.next()) {
			row.getCell("materialAmount").setValue(rowSet2.getBigDecimal("amount"));
     	}
	}
	
//	private void getLastmonth(IRow row,EquIdInfo info) throws BOSException, SQLException, EASBizException{
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(this.pkBizDate.getSqlDate());
//	    ca.add(Calendar.MONTH, -1);  
//	    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//		String month1 = (df1.format(ca.getTime())).substring(0, 7);
//	    StringBuffer sb2 = new StringBuffer();
//		sb2.append("/*dialect*/select a.CFEqmNameID 设备ID,");
//		sb2.append(" a.CFPowerCost 能耗量,a.CFCzCost 操作量,a.CFPowerUnitCost 能源单耗,");
//		sb2.append(" a.CFEqmTime 日历台时,a.CFEventTime 故障台时,a.CFUsageRate 使用率,");
//		sb2.append(" a.CFUseTime 使用台时,a.CFFaultRate 故障率,a.CFPowerUnitID 能耗量单位,");
//		sb2.append(" a.CFCzUnitID 操作量单位,a.CFPuUnitID 能源单耗单位,a.CFCostType 能耗类别");
//		sb2.append(" from CT_OPE_EumUseRecordEqmUse a");
//		sb2.append(" left join CT_OPE_EumUseRecord b on a.fparentid = b.fid");
//		sb2.append(" where a.CFEqmNameID = '"+info.getId()+"'");
//		sb2.append(" and to_char(b.fbizdate,'YYYY-MM')='"+month1+"'");
//		IRowSet rowSet2 = new XRSQLBuilder().appendSql(sb2.toString()).executeQuery();
//		while (rowSet2.next()) {
//			String id = rowSet2.getString("能耗量单位");
//			PowerUnitInfo puInfo = PowerUnitFactory.getRemoteInstance().getPowerUnitInfo(new ObjectUuidPK(id));
//			row.getCell("powerUnit").setValue(puInfo);
//			String id2 = rowSet2.getString("操作量单位");
//			CzUnitInfo czInfo = CzUnitFactory.getRemoteInstance().getCzUnitInfo(new ObjectUuidPK(id2));
//			row.getCell("czUnit").setValue(czInfo);
//			String id3 = rowSet2.getString("能源单耗单位");
//			PuUnitInfo punInfo = PuUnitFactory.getRemoteInstance().getPuUnitInfo(new ObjectUuidPK(id3));
//			row.getCell("puUnit").setValue(punInfo);
//			row.getCell("powerCost").setValue(rowSet2.getBigDecimal("能耗量"));
//			row.getCell("czCost").setValue(rowSet2.getBigDecimal("操作量"));
//			row.getCell("powerUnitCost").setValue(rowSet2.getBigDecimal("能源单耗"));
//			row.getCell("eqmTime").setValue(rowSet2.getBigDecimal("日历台时"));
//			row.getCell("EventTime").setValue(rowSet2.getBigDecimal("故障台时"));
//			row.getCell("usageRate").setValue(rowSet2.getBigDecimal("使用率"));
//			row.getCell("UseTime").setValue(rowSet2.getBigDecimal("使用台时"));
//			row.getCell("faultRate").setValue(rowSet2.getBigDecimal("故障率"));
//			if(rowSet2.getString("能耗类别").equals("1") ){
//				row.getCell("CostType").setValue(CostType.powerDriven);//电动机械
//			}else{
//				row.getCell("CostType").setValue(CostType.ICDriven);//内燃机械
//			}
//			
//     	}
//	}
	
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		contreportTime.setEnabled(false);
		contreportTime.setVisible(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionToolBarCustom_actionPerformed
	 */
	public void actionToolBarCustom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionToolBarCustom_actionPerformed(e);
	}

	/**
	 * output actionCloudFeed_actionPerformed
	 */
	public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloudFeed_actionPerformed(e);
	}

	/**
	 * output actionCloudShare_actionPerformed
	 */
	public void actionCloudShare_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudShare_actionPerformed(e);
	}

	/**
	 * output actionCloudScreen_actionPerformed
	 */
	public void actionCloudScreen_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudScreen_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		onLoad();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.port.equipment.operate.EumUseRecordFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.port.equipment.operate.EumUseRecordInfo objectValue = new com.kingdee.eas.port.equipment.operate.EumUseRecordInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
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
		return txtNumber;
	}
	
	
	

}