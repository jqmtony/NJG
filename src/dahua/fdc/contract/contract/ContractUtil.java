package com.kingdee.eas.fdc.contract;

import java.util.ArrayList;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;

public class ContractUtil {

	/**
	 * �ú�ͬ�Ƿ���ڱ��ǩ֤����
	 * @param ctx
	 * @param contractBillId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasChangeAuditBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {
		if (contractBillId == null) {
			return false;
		}
		ArrayList idList = new ArrayList();
		idList.add(contractBillId.toString());
		return hasChangeAuditBill(ctx, idList);
	}
	
	/**
	 * ��ͬ�Ƿ���ڱ��ǩ֤����
	 * @param ctx
	 * @param contractBillIds
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasChangeAuditBill(Context ctx, ArrayList contractBillIds) throws EASBizException, BOSException {
		Set contractIds = FDCHelper.getSetByList(contractBillIds);
		if (contractIds == null || contractIds.size() <= 0) {
			return false;
		}
		//������ڱ��ǩ֤���룬����������
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
    	if (ctx == null) {
    		return ChangeSupplierEntryFactory.getRemoteInstance().exists(filter);
    	} else {
    		return ChangeSupplierEntryFactory.getLocalInstance(ctx).exists(filter);
    	}
	}
	
	/**
	 * ��ͬ�Ƿ���ڱ��ǩ֤ȷ��
	 * @param ctx
	 * @param contractBillId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasContractChangeBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {
		if (contractBillId == null) {
			return false;
		}
		ArrayList idList = new ArrayList();
		idList.add(contractBillId.toString());
		return hasContractChangeBill(ctx, idList);
	}
	
	/**
	 * ��ͬ�Ƿ���ڱ��ǩ֤ȷ��
	 * @param ctx
	 * @param contractBillId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasContractChangeBill(Context ctx, ArrayList contractBillIds) throws EASBizException, BOSException {
		Set contractIds = FDCHelper.getSetByList(contractBillIds);
		if (contractIds == null || contractIds.size() <= 0) {
			return false;
		}
		//������ڱ��ǩ֤���룬����������
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
    	if (ctx == null) {
    		return ContractChangeBillFactory.getRemoteInstance().exists(filter);
    	} else {
    		return ContractChangeBillFactory.getLocalInstance(ctx).exists(filter);
    	}
	}
	
	/**
	 * ��ͬ�Ƿ���ڹ�����ȷ�ϵ�
	 * @param ctx
	 * @param contractBillId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasWorkLoadConfirmBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {
		if (contractBillId == null) {
			return false;
		}
		ArrayList idList = new ArrayList();
		idList.add(contractBillId.toString());
		return hasWorkLoadConfirmBill(ctx, idList);
	}

	/**
	 * ��ͬ�Ƿ���ڹ�����ȷ�ϵ�
	 * @param ctx
	 * @param contractBillId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean hasWorkLoadConfirmBill(Context ctx, ArrayList contractBillIds) throws EASBizException, BOSException {
		Set contractIds = FDCHelper.getSetByList(contractBillIds);
		if (contractIds == null || contractIds.size() <= 0) {
			return false;
		}
		//������ڹ�����ȷ�ϵ�������������
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
		if (ctx == null) {
			return WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter);
		} else {
			return WorkLoadConfirmBillFactory.getLocalInstance(ctx).exists(filter);
		}
	}
	
	public static void checkHasChangeAuditBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {
		//������ڱ��ǩ֤���룬����������
    	if (hasChangeAuditBill(ctx, contractBillId)) {
    		throw new  ContractException(ContractException.HASCHANGEAUDITBILL);
    	}
	}
	
	public static void checkHasContractChangeBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {

    	//������ڱ��ǩ֤ȷ�ϣ�����������
    	if (hasContractChangeBill(ctx, contractBillId)) {
    		throw new  ContractException(ContractException.HASCONTRACTCHANGEBILL);
    	}
	}
	
	public static void checkhasWorkLoadConfirmBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {

		//������ڹ�����ȷ�ϵ�������������
		if (hasWorkLoadConfirmBill(ctx, contractBillId)) {
			throw new ContractException(ContractException.HASWORKLOADCONFIRMBILL);
		}
	}
	
	/**
	 * ��������ȡ��֯�������и���֯ID�������Լ���ID
	 * @param ctx
	 * @param orgID
	 * @param arr
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author��jian_cao
	 * @CreateTime��2013-1-23
	 */
	public static void findParentOrgUnitIdToList(Context ctx, String orgID, ArrayList arr) throws EASBizException, BOSException {

		if (orgID == null)
			return;
		arr.add(orgID);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("parent"));
		FullOrgUnitInfo parentOrgUnit = null;
		if (ctx != null) {
			parentOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(orgID), coll);
		} else {
			parentOrgUnit = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgID), coll);
		}
		
		if (parentOrgUnit != null && parentOrgUnit.getParent() != null) {
			findParentOrgUnitIdToList(ctx, parentOrgUnit.getParent().getId().toString(), arr);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ӻ�ͬͬ������
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param contractBillReviseInfo
	 *            ��ͬ�޶�����;�ǿ�
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void synAttachmentFromContract(Context ctx, ContractBillReviseInfo contractBillReviseInfo)
			throws EASBizException, BOSException {
		ContractBillInfo contractBillInfo = contractBillReviseInfo.getContractBill();

		String srcBoID = contractBillInfo.getId().toString();
		String destBoID = contractBillReviseInfo.getId().toString();

		// ɾ��Ŀ�굥�����и���
		// FDCAttachmentUtil.deleteAllAtt(ctx, destBoID);

		// ����ҵ�񵥾�ID������������ҵ�����������б�
		FDCAttachmentUtil.copyBoAttchAssosByBoID(ctx, srcBoID, destBoID);
	}

	/**
	 * ������ͬ����������ͬ
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param contractBillReviseInfo
	 *            ��ͬ�޶�����;�ǿ�
	 * @author skyiter_wang
	 * @createDate 2013-12-19
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void synAttachmentToContract(Context ctx, ContractBillReviseInfo contractBillReviseInfo)
			throws EASBizException, BOSException {
		ContractBillInfo contractBillInfo = contractBillReviseInfo.getContractBill();

		String srcBoID = contractBillReviseInfo.getId().toString();
		String destBoID = contractBillInfo.getId().toString();

		// ɾ��Ŀ�굥�����и���
		FDCAttachmentUtil.deleteAllAtt(ctx, destBoID);

		// ����ҵ�񵥾�ID������������ҵ�����������б�
		FDCAttachmentUtil.copyBoAttchAssosByBoID(ctx, srcBoID, destBoID);
	}
	
}
