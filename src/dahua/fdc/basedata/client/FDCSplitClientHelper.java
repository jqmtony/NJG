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
	 * ��ȫ�����ɫ
	 */
	public final static Color COLOR_ALLSPLIT=new Color(0xD2E3CA);
	/**
	 * ���ֲ����ɫ
	 */
	public final static Color COLOR_PARTSPLIT=new Color(0xF6F6BF);
	
	public final static Color COLOR_NOSPLIT=new Color(0xFFEA67);
	/**
	 * ��������ɫ
	 */
	public final static Color COLOR_AUDITTED=new Color(0xE9E2B8);
	/**
	 * δ������ɫ,�������ύ�������еĵ���
	 */
	public final static Color COLOR_UNAUDITTED=new Color(0xF5F5E6);
	/**
	 * ������ɫ,��δ�ύ�ĵ�����ʾ����ɫ
	 */
	public final static Color COLOR_SAVED=Color.WHITE;
	
	/**
	 *�õ���Դ�ļ� 
	 * @return ��Դ�ַ���
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
	
	/** ֧�ָ������
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
	 * ֧�ָ������
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
	 * ���ݵ���ID�ж��Ƿ��Ѳ��
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
	
	// �����������������
	public static void autoChangeSettle2(String settleId) throws Exception {
		// �����Ӧ�ĺ�ͬ�Ƿ��б����
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
		if (!rowSet.getBoolean("FIsFinalSettle")) {// ���ս��㵥������˴���
			return;
		}
		FDCBillFacadeFactory.getRemoteInstance().dealSaveAboutConChange(settleId);
		FDCBillFacadeFactory.getRemoteInstance().autoChangeSettle(settleId, true);
	}
	// �������������㲻��
	public static void autoChangeSettle4(String settleId) throws Exception {
		// �����Ӧ�ĺ�ͬ�Ƿ��б����
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
		if (!rowSet.getBoolean("FIsFinalSettle")) {// ���ս��㵥������˴���
			return;
		}
		String title = EASResource.getString("promotInfoBox");
		String msg = "���������ͬ���+������Ĳ������������������ԭ��ͬ�������ֱ������з�̯����������ֹ��޸ġ���ȷ���Ƿ����";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame = false;
		if (win instanceof Frame) {
			isFrame = true;
		}
		AdvMsgBox msgBox = null;
		if (isFrame) {
			msgBox = new AdvMsgBox((Frame) win, title, msg, null, new String[] { "ȷ��", "����", "" },
					AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);
		} else {
			msgBox = new AdvMsgBox((JDialog) win, title, msg, null, new String[] { "ȷ��", "����", "" },
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

	// �����������������
	public static void autoChangeSettle3(String settleId) throws Exception {
		// �����Ӧ�ĺ�ͬ�Ƿ��б����
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
		if (!rowSet.getBoolean("FIsFinalSettle")) {// ���ս��㵥������˴���
			return;
		}
		String title = EASResource.getString("promotInfoBox");
		String msg = "��ȷ���Ƿ񰴽��������ͬ����ֵ���¼����������ǲ�֣�";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame = false;
		if (win instanceof Frame) {
			isFrame = true;
		}
		AdvMsgBox msgBox = null;
		if (isFrame) {
			msgBox = new AdvMsgBox((Frame) win, title, msg, null, new String[] { "��", "��", "����" }, AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
		} else {
			msgBox = new AdvMsgBox((JDialog) win, title, msg, null, new String[] { "��", "��", "����" }, AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);

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
		//�����Ӧ�ĺ�ͬ�Ƿ��б����
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
		if(!rowSet.getBoolean("FIsFinalSettle")){//���ս��㵥������˴���
			return;
		}
		String title=EASResource.getString("promotInfoBox");
		String msg="��ȷ���Ƿ񰴽��������ͬ����ֵ���¼����������ǲ�֣�";
		Window win = FDCClientHelper.getCurrentActiveWindow();
		boolean isFrame=false;
		if(win instanceof Frame){
			isFrame=true;
		}
		AdvMsgBox msgBox=null;
		AdvMsgBox msgBox2=null;
		if(isFrame){
			msgBox=new AdvMsgBox((Frame)win, title, msg, null,
	            new String[] {"��","��","����"},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
			msgBox2=new AdvMsgBox((Frame)win, title, "�뵽���ǩ֤ȷ�ϲ˵���д���������", null,
		            new String[]{"ȷ��","����",""},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);
		}else{
			msgBox=new AdvMsgBox((JDialog)win, title, msg, null,
		            new String[] {"��","��","����"},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_CANCEL_OPTION);
			msgBox2=new AdvMsgBox((JDialog)win, title, "�뵽���ǩ֤ȷ�ϲ˵���д���������", null,
		            new String[]{"ȷ��","����",""},AdvMsgBox.QUESTION_MESSAGE, AdvMsgBox.YES_NO_OPTION);

		}
		
		msgBox.show();
		int v=msgBox.getResult();
		if(v==MsgBox.YES){
			v=MsgBox.showConfirm2New(win, "��ȷ���Ƿ����¼��㲢�������б�������?");
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
	 * ����ͬ����Ƿ�����
	 * @author owen_wen 2010-12-03
	 * @param conBillId ��ͬID
	 * @param isCostSplit �Ƿ��ǳɱ����
	 * @return true ����δ�����ı�����
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
	 * ����������Ƿ��Ѿ�����
	 * @param contractBillId ��ͬID
	 * @param isCostSplit �Ƿ��ǳɱ����
	 * @return true ����δ�����ı�����
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
	
	/** ��ȡFDC5014_NEXTSPLITISBASEONPREAUDITED�Ĳ���ֵ�������ݲ���ֵ��ʾ��Ϣ����ʾ��Ϣ�У�
	 * conSplitNotAudit��changeSpliteNotAudit��conAndChangeSpltNotAudited
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
	 * ����ͬ��֡��������Ƿ�δ�����������Ƿ����ã�
	 * ���ݲ����������Ͳ���������񣬸�����ʾ��Ϣ��
	 * @param contractBillId ��ͬID
	 * @param comp �����ڣ����Դ���null
	 * @param isCostSplit �Ƿ�ɱ����
	 * @author owen_wen 2010-12-03
	 */
	public static void checkAndShowMsgBeforeSplit(String contractBillId, Component comp, boolean isCostSplit) throws EASBizException, BOSException{
		boolean conSplitIsAudited = checkContractSplitIsAudited(contractBillId, isCostSplit); 
		boolean changeSplitIsAudited = checkConChangeIsAudited(contractBillId, isCostSplit);
		if (conSplitIsAudited && changeSplitIsAudited) //��ͬ��ֺͱ����δ����
			getParamValueAndShowMsg("conAndChangeSpltNotAudited", comp);
		if (conSplitIsAudited)// ��ͬ���δ����
			getParamValueAndShowMsg("conSplitNotAudit", comp);
		if (changeSplitIsAudited)// ������δ����
			getParamValueAndShowMsg("changeSpliteNotAudit", comp);
	}
	/**
	 * ���ݵ���ID�ж��Ƿ��Ѳ��
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
		// ����в����ڴ˵��ݻ���ڵ��õ���״̬Ϊȫ����ַ���true
		if (state.equals("") || state.equals(CostSplitStateEnum.ALLSPLIT_VALUE)) {
			return true;
		}

		return false;
	}

	/**
	 * ���ݺ�ͬ����ID�ж��Ƿ��Ѳ�֡�ConChangeSplitQuery�ǡ������֡��б����ʹ�õ�query����������һ�¡�<p>
	 * 
	 * ע�⣺���桢�ύ�������еĵ��ݲ��ò����ж�
	 * @param id ��ͬid
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
			MsgBox.showInfo(comp, "����δ��ֵı��ǩ֤ȷ�ϣ���������к�ͬ�����֣�");
			SysUtil.abort();
		}
	}
}
