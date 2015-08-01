package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.rptclient.newrpt.util.AdvMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCSplitClientHelper
{
    private static final String RESOURCE_FILE="com.kingdee.eas.fdc.basedata.client.FDCSplitBillResource";
	private FDCSplitClientHelper(){};
	
	/**
	 * 完全拆分颜色
	 */
	public final static Color COLOR_ALLSPLIT=new Color(0xD2E3CA);
	/**
	 * 部分拆分颜色
	 */
	public final static Color COLOR_PARTSPLIT=new Color(0xF6F6BF);
	
	public final static Color COLOR_NOSPLIT=new Color(0xFFEA67);
	/**
	 * 已审批颜色
	 */
	public final static Color COLOR_AUDITTED=new Color(0xE9E2B8);
	/**
	 * 未审批颜色,包括已提交及审批中的单据
	 */
	public final static Color COLOR_UNAUDITTED=new Color(0xF5F5E6);
	/**
	 * 保存颜色,即未提交的单据显示的颜色
	 */
	public final static Color COLOR_SAVED=Color.WHITE;
	
	/**
	 *得到资源文件 
	 * @return 资源字符串
	 */
	public static String getRes(String resName){
		return EASResource.getString(RESOURCE_FILE,resName);
	}
	
	public static KDTDefaultCellEditor getTotalCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);// MIN_TOTAL_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.ZERO);//MIN_TOTAL_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public static KDTDefaultCellEditor getChangeCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/** 支持负数拆分
	 * @return
	 */
	public static KDTDefaultCellEditor _getTotalCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/**
	 * 支持负数拆分
	 * @return
	 */
	public static KDTDefaultCellEditor _getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	

	/**
	 * 根据单据ID判断是否已拆分
	 * @param tabName
	 * @param billFieldName
	 * @return
	 * @throws Exception
	 */
	public static boolean isBillSplited(String id, String tabName, String billFieldName) throws Exception {
		List params = new ArrayList();
		params.add(id);
		StringBuffer sql = new StringBuffer();
		boolean b = false;
		
		sql.append("select fid from ");
		sql.append(tabName);
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");
		
		IRowSet set = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), params);
		
		if(set.next()) {
			b = true;
		}
		
		return b;
		
	}
	
	public static void checkSplitedForAmtChange(CoreUIObject ui, String id, String tabName, String billFieldName) throws Exception {
		boolean isSplited = isBillSplited(id, tabName, billFieldName);
		if(isSplited) {
			MsgBox.showConfirm2(ui, ContractClientUtils.getRes("spltedWarningForAmtChg"));
		}
	}
	
	// 当最新造价与结算相等
	public static void autoChangeSettle2(String settleId) throws Exception {
		// 检查相应的合同是否有变更单
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select top 1 change.fid,settle.FIsFinalSettle from T_Con_ContractChangeBill change ");
		builder.appendSql("inner join T_Con_ContractSettlementbill settle on change.fcontractBillId=settle.fcontractBillId ");
		builder.appendSql("where settle.fid=?");
		builder.addParam(settleId);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() == 0) {
			return;
		}
		rowSet.next();
		if (!rowSet.getBoolean("FIsFinalSettle")) {// 最终结算单才做如此处理
			return;
		}
		FDCBillFacadeFactory.getRemoteInstance().dealSaveAboutConChange(settleId);
		FDCBillFacadeFactory.getRemoteInstance().autoChangeSettle(settleId, true);
	}
	// 当最新造价与结算不等
	public static void autoChangeSettle4(String settleId) throws Exception {
		// 检查相应的合同是否有变更单
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select top 1 change.fid,settle.FIsFinalSettle from T_Con_ContractChangeBill change ");
		builder.appendSql("inner join T_Con_ContractSettlementbill settle on change.fcontractBillId=settle.fcontractBillId ");
		builder.appendSql("where settle.fid=?");
		builder.addParam(settleId);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() == 0) {
			return;
		}
		rowSet.next();
		if (!rowSet.getBoolean("FIsFinalSettle")) {// 最终结算单才做如此处理
			return;
		}
		String title = EASResource.getString("promotInfoBox");
		String msg = "结算金额与合同金额+变更金额的差额（结算调整），将按照原合同、变更拆分比例进行分摊，如调整请手工修改。请确认是否继续";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame = false;
		if (win instanceof Frame) {
			isFrame = true;
		}
		AdvMsgBox msgBox = null;
		if (isFrame) {
			msgBox = new AdvMsgBox((Frame) win, title, msg, null, new String[] { "确定", "返回", "" },
					AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);
		} else {
			msgBox = new AdvMsgBox((JDialog) win, title, msg, null, new String[] { "确定", "返回", "" },
					AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);

		}

		msgBox.show();
		
		
		int v = msgBox.getResult();
		if (v == MsgBox.OK) {
			return;
		} else {
			SysUtil.abort();
		}
		
	}

	// 当最新造价与结算相等
	public static void autoChangeSettle3(String settleId) throws Exception {
		// 检查相应的合同是否有变更单
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select top 1 change.fid,settle.FIsFinalSettle from T_Con_ContractChangeBill change ");
		builder.appendSql("inner join T_Con_ContractSettlementbill settle on change.fcontractBillId=settle.fcontractBillId ");
		builder.appendSql("where settle.fid=?");
		builder.addParam(settleId);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() == 0) {
			return;
		}
		rowSet.next();
		if (!rowSet.getBoolean("FIsFinalSettle")) {// 最终结算单才做如此处理
			return;
		}
		String title = EASResource.getString("promotInfoBox");
		String msg = "请确认是否按结算金额与合同金额差值重新计算变更金额并覆盖拆分？";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame = false;
		if (win instanceof Frame) {
			isFrame = true;
		}
		AdvMsgBox msgBox = null;
		if (isFrame) {
			msgBox = new AdvMsgBox((Frame) win, title, msg, null, new String[] { "是", "否", "忽略" }, AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
		} else {
			msgBox = new AdvMsgBox((JDialog) win, title, msg, null, new String[] { "是", "否", "忽略" }, AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);

		}

		msgBox.show();
		int v = msgBox.getResult();
		if (v == MsgBox.YES) {
			FDCBillFacadeFactory.getRemoteInstance().dealSaveAboutConChange(settleId);
			FDCBillFacadeFactory.getRemoteInstance().autoChangeSettle(settleId, true);
		} else if (v == MsgBox.CANCEL) {
			return;
		} else {
			if (v == MsgBox.OK) {
				SysUtil.abort();
			} else {
				autoChangeSettle(settleId);
				
			}
		}
	}
	
	public static void autoChangeSettle(String settleId) throws Exception{
		//检查相应的合同是否有变更单
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select top 1 change.fid,settle.FIsFinalSettle from T_Con_ContractChangeBill change ");
		builder.appendSql("inner join T_Con_ContractSettlementbill settle on change.fcontractBillId=settle.fcontractBillId ");
		builder.appendSql("where settle.fid=?");
		builder.addParam(settleId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet.size()==0){
			return;
		}
		rowSet.next();
		if(!rowSet.getBoolean("FIsFinalSettle")){//最终结算单才做如此处理
			return;
		}
		String title=EASResource.getString("promotInfoBox");
		String msg="请确认是否按结算金额与合同金额差值重新计算变更金额并覆盖拆分？";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame=false;
		if(win instanceof Frame){
			isFrame=true;
		}
		AdvMsgBox msgBox=null;
		AdvMsgBox msgBox2=null;
		if(isFrame){
			msgBox=new AdvMsgBox((Frame)win, title, msg, null,
	            new String[] {"是","否","忽略"},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
			msgBox2=new AdvMsgBox((Frame)win, title, "请到变更签证确认菜单填写变更结算金额", null,
		            new String[]{"确定","返回",""},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);
		}else{
			msgBox=new AdvMsgBox((JDialog)win, title, msg, null,
		            new String[] {"是","否","忽略"},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
			msgBox2=new AdvMsgBox((JDialog)win, title, "请到变更签证确认菜单填写变更结算金额", null,
		            new String[]{"确定","返回",""},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);

		}
		
		msgBox.show();
		int v=msgBox.getResult();
		if(v==MsgBox.YES){
			v=MsgBox.showConfirm2New(win, "请确认是否重新计算并覆盖所有变更金额及拆分?");
			if(v==MsgBox.YES){
				FDCBillFacadeFactory.getRemoteInstance().dealSaveAboutConChange(settleId);
				FDCBillFacadeFactory.getRemoteInstance().autoChangeSettle(settleId, true);
			}else{
				FDCBillFacadeFactory.getRemoteInstance().dealSaveAboutConChange(settleId);
				FDCBillFacadeFactory.getRemoteInstance().autoChangeSettle(settleId, false);
			}
		}else if(v==MsgBox.CANCEL){
			return;
		}else{
//			msgBox2.show();
//			v=msgBox2.getResult();
//			if(v==MsgBox.OK){
//				SysUtil.abort();
//			}else{
//				autoChangeSettle(settleId);
//			}
		}
	}
	
	/**
	 * 检查合同拆分是否审批
	 * @author owen_wen 2010-12-03
	 * @param conBillId 合同ID
	 * @param isCostSplit 是否是成本拆分
	 * @return true 存在未审批的变更拆分
	 * @throws BOSException  
	 * @throws EASBizException 
	 */
	public static boolean checkContractSplitIsAudited(String conBillId, boolean isCostSplit) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();	
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", conBillId));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
    	    	
    	boolean isConSplitNotAudited ;
    	if(isCostSplit)
    		isConSplitNotAudited = ContractCostSplitFactory.getRemoteInstance().exists(filter);
    	else
    		isConSplitNotAudited = ConNoCostSplitFactory.getRemoteInstance().exists(filter);

    	return isConSplitNotAudited;
	}
	 
	/**
	 * 检查拆变更拆分是否已经审批
	 * @param contractBillId 合同ID
	 * @param isCostSplit 是否是成本拆分
	 * @return true 存在未审批的变更拆分
	 * @author owen_wen 2010-12-02
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static boolean checkConChangeIsAudited(String contractBillId, boolean isCostSplit) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractbill.id", contractBillId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		
    	boolean isConChangeSplitNotAudited;
    	if(isCostSplit)
    		isConChangeSplitNotAudited = ConChangeSplitFactory.getRemoteInstance().exists(filter);
    	else
    		isConChangeSplitNotAudited = ConChangeNoCostSplitFactory.getRemoteInstance().exists(filter);
    	
    	return isConChangeSplitNotAudited;
	}
	
	/** 获取FDC5014_NEXTSPLITISBASEONPREAUDITED的参数值，并根据参数值提示信息，提示信息有：
	 * conSplitNotAudit，changeSpliteNotAudit，conAndChangeSpltNotAudited
	 * @throws EASBizException
	 * @throws BOSException
	 * @author owen_wen 2010-11-30
	 */
	public static void getParamValueAndShowMsg(String resName, Component comp) throws EASBizException, BOSException {
		boolean paramValue = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED);
		if (paramValue){
			MsgBox.showInfo(comp, FDCSplitClientHelper.getRes(resName));
			SysUtil.abort();
		}
	}
	
	/**
	 * 检查合同拆分、变更拆分是否都未审批，参数是否启用，
	 * 根据拆分审批情况和参数启用与否，给出提示信息。
	 * @param contractBillId 合同ID
	 * @param comp 父窗口，可以传入null
	 * @param isCostSplit 是否成本拆分
	 * @author owen_wen 2010-12-03
	 */
	public static void checkAndShowMsgBeforeSplit(String contractBillId, Component comp, boolean isCostSplit) throws EASBizException, BOSException{
		boolean conSplitIsAudited = checkContractSplitIsAudited(contractBillId, isCostSplit); 
		boolean changeSplitIsAudited = checkConChangeIsAudited(contractBillId, isCostSplit);
		if (conSplitIsAudited && changeSplitIsAudited) //合同拆分和变更都未审批
			getParamValueAndShowMsg("conAndChangeSpltNotAudited", comp);
		if (conSplitIsAudited)// 合同拆分未审批
			getParamValueAndShowMsg("conSplitNotAudit", comp);
		if (changeSplitIsAudited)// 变更拆分未审批
			getParamValueAndShowMsg("changeSpliteNotAudit", comp);
	}
	/**
	 * 根据单据ID判断是否已拆分
	 * 
	 * @param tabName
	 * @param billFieldName
	 * @return
	 * @throws Exception
	 */
	public static boolean isBillSplitedByContractBillId(String id, String tabName,
			String billFieldName)
			throws Exception {
		List params = new ArrayList();
		params.add(id);
		StringBuffer sql = new StringBuffer();
		String state = "";

		sql.append("select fsplitstate from ");
		sql.append(tabName);
		sql.append(" where ");
		sql.append(billFieldName);
		sql.append(" = ? ");

		IRowSet set = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(sql.toString(), params);

		if (set.next()) {
			state = set.getString(1);
		}
		// 拆分中不存在此单据或存在但该单据状态为全部拆分返回true
		if (state.equals("") || state.equals(CostSplitStateEnum.ALLSPLIT_VALUE)) {
			return true;
		}

		return false;
	}

	/**
	 * 根据合同单据ID判断是否已拆分。ConChangeSplitQuery是“变更拆分”列表界面使用的query，与它保持一致。<p>
	 * 
	 * 注意：保存、提交、审批中的单据不用参与判断
	 * @param id 合同id
	 */
	public static void isBillSplitedByContractBillId(String id, Component comp) throws Exception {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", id));
		filter.getFilterItems().add(new FilterItemInfo("state not in('1SAVED', '2SUBMITTED', '3AUDITTING')"));
		filter.getFilterItems().add(new FilterItemInfo("costSplit.splitState", CostSplitStateEnum.NOSPLIT_VALUE, CompareType.EQUALS));
		ev.setFilter(filter);
		IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(MetaDataPK
				.create("com.kingdee.eas.fdc.contract.app.ConChangeSplitQuery"));
		exec.setObjectView(ev);
		IRowSet iRowSet = exec.executeQuery();
		if (iRowSet.size() > 0) {
			MsgBox.showInfo(comp, "存在未拆分的变更签证确认，不允许进行合同付款拆分！");
			SysUtil.abort();
		}
	}
}
