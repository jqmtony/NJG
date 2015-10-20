package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * @author Cassiel_peng
 * @date 2009-11-24  
 * @work ���ྭ��һ�������Ż��ִ�����R090927-138�ᵥ�Ѿ���ù����Ӵ��ˣ��ֳ�ȡ�������߼���װ��������
 */
public class ContractFullListUtils {
	
	public ContractFullListUtils() {
		
	}
	/**
	 * �Ƿ����óɱ�����һ�廯
	 */
	private static boolean isSimpleFinacialMode() {
//			if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL.toString())!=null){
//				isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue();
//			}
//	       return isFinacial;
		boolean isSimpleFinacialMode=true;
		CompanyOrgUnitInfo	company =  SysContext.getSysContext().getCurrentFIUnit();
		try {
			isSimpleFinacialMode=FDCUtils.getDefaultFDCParamByKey(null, company.getId().toString(),FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return isSimpleFinacialMode;
	}
	/**
	 * �Ƿ����óɱ�����һ�廯
	 */
	private static boolean isFinacialModel() {
		boolean isFinacial=false;
		CompanyOrgUnitInfo	company =  SysContext.getSysContext().getCurrentFIUnit();
		try {
			isFinacial = FDCUtils.IsFinacial(null, company.getId().toString());
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		} catch (BOSException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		return isFinacial;
	}
	 /**
	 * ��ͬ���:��Ҫ������ͬ�����ʱ����������ְ�ť���е��߼���"���۲��"��Ϊ��������������ͻ�ʹ�ùʲ��ڿ���֮��
	 * 1���ɱ��½��Ѿ����ñ����ݣ������޸ģ������޸ģ��������ϱ���ͬ��֣�
	 * 2������Ѿ������������ã����ܽ��д˲���!
	 * 3������Ѿ�����������ֻ򸶿������ã����ܽ��д˲���!
	 * 4���Ǹ���ģʽ����£���ͬ��ֱ����������ã������Ҫ����ʱ����ʾ���ã������û�ѡ���Ƿ�ֱ��ɾ���������
	 *  by Cassiel_peng  2009-11-17
	 */
	public static boolean  verifyCanSplit(KDTable tblMain,boolean isCostSplit) throws EASBizException, BOSException {
		
		boolean canSplit=true;
		
		Object billId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(new FilterItemInfo("costBillId", billId));
		// ���ϵ��ݲ���
		filtertemp.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		
		boolean existSettlecoll = false;
		boolean existcollPay = false;
		boolean existcollNoCost = false;
		boolean existcollPayNoCost = false;
		if(isCostSplit){//�ɱ����ͬ���
			existSettlecoll = SettlementCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPay = PaymentSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			if((existSettlecoll||existcollPay)){		// �ɱ���֣�����/������/�����ֱ�����  
				if(isSimpleFinacialMode()){
					int value = MsgBox.showConfirm2("��ͬ��ֱ�����������ã��Ƿ�ֱ��ɾ���������");
					if(value == MsgBox.YES){
						if(existSettlecoll){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							SettlementCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
						if(existcollPay){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));					
							PaymentSplitFactory.getRemoteInstance().delete(filtertemp);
						}
					}else{
						canSplit=false;
						SysUtil.abort();
					}
				}else{
					if (existSettlecoll) {			//������������
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impBySett"));
						SysUtil.abort();
					}
					if (existcollPay) {				//��ֱ���������ֻ򸶿�������
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impByPay"));
						SysUtil.abort();
					}
				}
			}
		}else{//�ǳɱ����ͬ���
			existcollNoCost = SettNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			existcollPayNoCost = PaymentNoCostSplitEntryFactory.getRemoteInstance().exists(filtertemp);
			if((existcollNoCost||existcollPayNoCost)){		//�ǳɱ���֣�����/������/�����ֱ�����
				if(!isFinacialModel()){
					int value = MsgBox.showConfirm2("��ͬ��ֱ�����������ã��Ƿ�ֱ��ɾ���������");
					if(value == MsgBox.YES){
						if(existcollNoCost){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							SettNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
						if(existcollPayNoCost){
							filtertemp = new FilterInfo();
							filtertemp.getFilterItems().add(
									new FilterItemInfo("contractBill.id",billId,CompareType.EQUALS));
							PaymentNoCostSplitFactory.getRemoteInstance().delete(filtertemp);
						}
					}else{
						canSplit=false;
						SysUtil.abort();
					}
				}else{
					if (existcollNoCost) {			//������������
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impBySett"));
						SysUtil.abort();
					}
					if (existcollPayNoCost) {				//��ֱ���������ֻ򸶿�������
						canSplit=false;
						MsgBox.showError(FDCSplitClientHelper.getRes("impByPay"));
						SysUtil.abort();
					}
				}
			}
		}
		return canSplit;
	}
	/**
	 * �жϺ�ͬ����ǳɱ����ֻ��Ƿǳɱ�����
	 *   by Cassiel_peng  2009-11-17
	 */
	public static boolean isCostSplit(KDTable tblMain) throws EASBizException, BOSException, UuidException{
		boolean isCostSplit=true;
		//���ں�ͬ����������ǳɱ����ֻ��Ƿǳɱ�����
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("isCoseSplit");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())),selector);
		if(!contractBill.isIsCoseSplit()){
			isCostSplit=false;
		}
		return isCostSplit;
	}
	/**
	 * �ж��Ƿ���ĳһ��Ȩ��
	 */
	public static boolean verifyPermission(String UI,String action_permission,String currentUserInfoId,String currentOrgUnitId) throws EASBizException, BOSException {
		boolean hasPermission=false;
		hasPermission=PermissionFactory.getRemoteInstance().hasFunctionPermission(
				new ObjectUuidPK(currentUserInfoId),
				new ObjectUuidPK(currentOrgUnitId),
				new MetaDataPK(UI),
				new MetaDataPK(action_permission) );
		return hasPermission;
	}
	/**
	 * ����ͬ��δ��������ť����ͬ��֡������������㵥���� �������������뵥������������²�����������
	 *  by Cassiel_peng 2009-11-18
	 */
	public static void verifyAudited(KDTable tblMain) throws EASBizException, BOSException, UuidException {
		/*// TODO ֱ�������жϵĻ����ܻ���ں�ͬ�Ѿ����������ݿ���״̬�ֶ��Ѿ��ı䣬���Ǻ�ͬ��ѯ����ÿˢ�¹�����״̬�ֶλ�û�仯�����
		Object state=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("state").getValue();
		if(state!=null&&("����".equals(state.toString())||"���ύ".equals(state.toString())||"������".equals(state.toString()))){
			FDCMsgBox.showError(this,"�ú�ͬ��δ��������������иò�����");
			SysUtil.abort();
		}*/
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("����ѡ��һ�е��ݣ�");
			SysUtil.abort();
		};
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())));
		if(contractBill!=null&&contractBill.getState()!=null&&(FDCBillStateEnum.SAVED.equals(contractBill.getState())||FDCBillStateEnum.SUBMITTED.equals(contractBill.getState())||FDCBillStateEnum.AUDITTING.equals(contractBill.getState()))){
			FDCMsgBox.showError("�ú�ͬ��δ��������������иò�����");
			SysUtil.abort();
		}
	
	}
	/**
	 * ����ͬ�ѽ��㣬��ť ���������ǩ֤���롱 �����������㵥����������
	 *  by Cassiel_peng 2009-11-18
	 */
	public static void verifyHasSettled(KDTable tblMain) throws EASBizException, BOSException, UuidException {
		if(tblMain.getRowCount()==0){
			FDCMsgBox.showWarning("����ѡ��һ�е��ݣ�");
			SysUtil.abort();
		};
		Object contractId=tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("hasSettled");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId==null?"":contractId.toString())));
		if(contractBill.isHasSettled()){
			FDCMsgBox.showError("�ú�ͬ�Ѿ����㣬���ܽ��д˲�����");
			SysUtil.abort();
		}
	}
	
}
