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
	 * 该合同是否存在变更签证申请
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
	 * 合同是否存在变更签证申请
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
		//如果存在变更签证申请，则不允许反审批
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
    	if (ctx == null) {
    		return ChangeSupplierEntryFactory.getRemoteInstance().exists(filter);
    	} else {
    		return ChangeSupplierEntryFactory.getLocalInstance(ctx).exists(filter);
    	}
	}
	
	/**
	 * 合同是否存在变更签证确认
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
	 * 合同是否存在变更签证确认
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
		//如果存在变更签证申请，则不允许反审批
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
    	if (ctx == null) {
    		return ContractChangeBillFactory.getRemoteInstance().exists(filter);
    	} else {
    		return ContractChangeBillFactory.getLocalInstance(ctx).exists(filter);
    	}
	}
	
	/**
	 * 合同是否存在工程量确认单
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
	 * 合同是否存在工程量确认单
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
		//如果存在工程量确认单，则不允许反审批
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractIds, CompareType.INCLUDE));
		if (ctx == null) {
			return WorkLoadConfirmBillFactory.getRemoteInstance().exists(filter);
		} else {
			return WorkLoadConfirmBillFactory.getLocalInstance(ctx).exists(filter);
		}
	}
	
	public static void checkHasChangeAuditBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {
		//如果存在变更签证申请，则不允许反审批
    	if (hasChangeAuditBill(ctx, contractBillId)) {
    		throw new  ContractException(ContractException.HASCHANGEAUDITBILL);
    	}
	}
	
	public static void checkHasContractChangeBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {

    	//如果存在变更签证确认，则不允许反审批
    	if (hasContractChangeBill(ctx, contractBillId)) {
    		throw new  ContractException(ContractException.HASCONTRACTCHANGEBILL);
    	}
	}
	
	public static void checkhasWorkLoadConfirmBill(Context ctx, BOSUuid contractBillId) throws EASBizException, BOSException {

		//如果存在工程量确认单，则不允许反审批
		if (hasWorkLoadConfirmBill(ctx, contractBillId)) {
			throw new ContractException(ContractException.HASWORKLOADCONFIRMBILL);
		}
	}
	
	/**
	 * 描述：获取组织树的所有父组织ID，包括自己的ID
	 * @param ctx
	 * @param orgID
	 * @param arr
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：jian_cao
	 * @CreateTime：2013-1-23
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
	 * 描述：从合同同步附件
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param contractBillReviseInfo
	 *            合同修订对象;非空
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

		// 删除目标单据所有附件
		// FDCAttachmentUtil.deleteAllAtt(ctx, destBoID);

		// 根据业务单据ID拷贝“附件与业务对象关联”列表
		FDCAttachmentUtil.copyBoAttchAssosByBoID(ctx, srcBoID, destBoID);
	}

	/**
	 * 描述：同步附件到合同
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param contractBillReviseInfo
	 *            合同修订对象;非空
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

		// 删除目标单据所有附件
		FDCAttachmentUtil.deleteAllAtt(ctx, destBoID);

		// 根据业务单据ID拷贝“附件与业务对象关联”列表
		FDCAttachmentUtil.copyBoAttchAssosByBoID(ctx, srcBoID, destBoID);
	}
	
}
