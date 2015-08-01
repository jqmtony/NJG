package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class AimAimCostAdjustControllerBean extends AbstractAimAimCostAdjustControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.AimAimCostAdjustControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

		String id = billId.toString();
		AimAimCostAdjustInfo aimCostAdjust = fetchAimAimCostAdjustInfo(ctx,id);
		String projectID = aimCostAdjust.getCurProject().getId().toString();
		try {
			// ����һ���µİ汾����Ŀ��ɱ��������ķ�¼�ӵ��°汾��¼�ϣ��Ұ�״̬��Ϊ������
			AimCostInfo aimCostInfo = fetchAimCost(ctx, projectID);
			BigDecimal newVersionNumber = new BigDecimal(aimCostInfo.getVersionNumber()).add(FDCHelper.ONE);
			aimCostInfo.setVersionNumber(newVersionNumber.toString());// �汾��
			updateOldAimVersionNumber(ctx, projectID);
			BOSUuid bosId = BOSUuid.create(aimCostInfo.getBOSType());
			aimCostInfo.setId(bosId);
			aimCostInfo.setGenByAdjust(id);
			aimCostInfo.setRecenseDate(new Timestamp(System.currentTimeMillis()));// �޶�ʱ��
			aimCostInfo.setState(FDCBillStateEnum.AUDITTED);// ״̬
			aimCostInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));// ������
			aimCostInfo.setAuditDate(new Date());// ����ʱ��
			aimCostInfo.setIsLastVersion(true);// ���°�
			CostEntryCollection costEntry = aimCostInfo.getCostEntry();
			AimAimCostAdjustEntryCollection costAdjustEntry = aimCostAdjust.getEntrys();
			for (int i = 0; i < costAdjustEntry.size(); i++) {
				AimAimCostAdjustEntryInfo aimCostAdjustEntryInfo = costAdjustEntry.get(i);
				CostAccountInfo costAccount = aimCostAdjustEntryInfo.getCostAccount();
				CostEntryInfo addEntry = new CostEntryInfo();
				addEntry.setId(BOSUuid.create(addEntry.getBOSType()));
				addEntry.setCostAccount(costAccount);
				addEntry.setEntryName(costAccount.getName());
				addEntry.setWorkload(aimCostAdjustEntryInfo.getWorkload());
				addEntry.setPrice(aimCostAdjustEntryInfo.getPrice());
				if (aimCostAdjustEntryInfo.getUnit() != null) {
					addEntry.setUnit(aimCostAdjustEntryInfo.getUnit().getName());
				}
				addEntry.setCostAmount(aimCostAdjustEntryInfo.getAdjustAmt());
				addEntry.setProduct(aimCostAdjustEntryInfo.getProduct());
				
				/* modified by zhaoqin for R131022-0402 on 2013/12/31 start */
				addEntry.setDesc(aimCostAdjustEntryInfo.getDescription());	//��ע
				addEntry.setChangeReason(aimCostAdjustEntryInfo.getChangeReason()); //�仯ԭ��
				/* modified by zhaoqin for R131022-0402 on 2013/12/31 end */
				
				addEntry.setHead(aimCostInfo);
				costEntry.add(addEntry);
			}
			Map oldAndNewID = new HashMap();
			Set oldID= new HashSet();
			for (int i = 0; i < costEntry.size(); i++) {
				CostEntryInfo costEntryInfo = costEntry.get(i);
				BOSUuid oldId= costEntryInfo.getId();
				BOSUuid uuid = BOSUuid.create(costEntryInfo.getBOSType());
				costEntryInfo.setId(uuid);
				oldAndNewID.put(oldId.toString(), uuid.toString());
				oldID.add(oldId.toString());
			}
			AimCostFactory.getLocalInstance(ctx).addnew(aimCostInfo);
			
			/**
			 * ���¸���Ʒ���Ͷ�̬Ŀ��ɱ��е� ��¼ID
			 */
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("costEntryId",oldID,CompareType.INCLUDE));
			view.setFilter(filter);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("costEntryId");
			view.setSelector(sic);
			AimCostProductSplitEntryCollection splitEntryCollection = AimCostProductSplitEntryFactory.getLocalInstance(ctx).getAimCostProductSplitEntryCollection(view );
			for(int i=0;i<splitEntryCollection.size();i++){
				AimCostProductSplitEntryInfo splitEntryInfo = splitEntryCollection.get(i);
				splitEntryInfo.setCostEntryId(oldAndNewID.get(splitEntryInfo.getCostEntryId()).toString());
				AimCostProductSplitEntryFactory.getLocalInstance(ctx).updatePartial(splitEntryInfo, sic);
			}
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
		}
		super._audit(ctx, billId);
	}

	/**
	 * ��ǰһ�汾�С��Ƿ����°汾����Ϊfalse
	 * 
	 * @param ctx
	 * @param projectID
	 */
	private void updateOldAimVersionNumber(Context ctx, String projectID) {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_AIM_AimCost set FIsLastVersion=0 where FOrgOrProId =? and FIsLastVersion = 1 ");
		builder.addParam(projectID);
		try {
			builder.execute();
		} catch (BOSException e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡĿ��ɱ���������Ϣ
	 * 
	 * @param ctx
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private AimAimCostAdjustInfo fetchAimAimCostAdjustInfo(Context ctx, String id) throws EASBizException, BOSException {
		SelectorItemCollection selects = new SelectorItemCollection();
		selects.add("*");
		selects.add("entrys.*");
		selects.add("entrys.costAccount.*");
		selects.add("entrys.unit.*");
		AimAimCostAdjustInfo aimCostAdjust = AimAimCostAdjustFactory.getLocalInstance(ctx).getAimAimCostAdjustInfo(new ObjectUuidPK(id),
				selects);
		return aimCostAdjust;
	}

	/**
	 * ��ȡ���°�����������Ŀ��ɱ�������Ϣ
	 * 
	 * @param projectID
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private AimCostInfo fetchAimCost(Context ctx, String projectID) throws BOSException, SQLException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("CU.id");
		view.getSelector().add("costEntry.*");
		view.getSelector().add("costEntry.costAccount.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", projectID));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", Boolean.TRUE));
		view.setFilter(filter);
		AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
		if (aimCostCollection.size() != 0) {
			AimCostInfo aimCostInfo = aimCostCollection.get(0);
			return aimCostInfo;
		}
		return null;
	}

	/**
	 * ��ȡ���°�Ŀ��ɱ�ͷ��Ϣ
	 * 
	 * @param ctx
	 * @param projectID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private AimCostInfo fetchAimCostHead(Context ctx, String projectID) throws BOSException, SQLException {
		String id = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select acc.fid fid from T_AIM_AimCost acc");
		builder.appendSql(" where acc.fversionnumber =");
		builder.appendSql(" (");
		builder.appendSql(" select max(fversionnumber) from T_AIM_AimCost ac");
		builder.appendSql(" where ac.ForgOrProId = '" + projectID + "'");
		builder.appendSql(" )");
		builder.appendSql(" and acc.forgOrProid = '" + projectID + "'");
		IRowSet rowSet = builder.executeQuery(ctx);
		if (rowSet.next()) {
			id = rowSet.getString("fid");
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
		if (aimCostCollection.size() != 0) {
			AimCostInfo aimCostInfo = aimCostCollection.get(0);
			return aimCostInfo;
		}
		return null;
	}

	protected void _setAuttingForWF(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		AimAimCostAdjustInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		billInfo = (AimAimCostAdjustInfo) super.getValue(ctx, new ObjectUuidPK(billId), selector);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		AimAimCostAdjustFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
	}

	protected void _setSubmitState(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		AimAimCostAdjustInfo billInfo = AimAimCostAdjustFactory.getLocalInstance(ctx).getAimAimCostAdjustInfo(new ObjectUuidPK(billId));
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}
	
	
	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	/**
	 * �����������ֶβ������������ֵ�����ı䣬����Ҫ���»�ȡ����
	 * 
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * �������Ƿ�����ϼ���֯���ݵı������
	 * 
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * ������ȡ�û��ձ���Seletor
	 * 
	 * @return
	 * @author��skyiter_wang
	 * @createDate��2013-10-16
	 */
	protected SelectorItemCollection getSeletorForRecycleNumber() {
		SelectorItemCollection sic = super.getSeletorForRecycleNumber();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));

		FdcObjectCollectionUtil.clearDuplicate(sic);

		return sic;
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException,
			EASBizException {

		FilterInfo filter = null;
		int i = 0;
		do {
			handleIntermitNumber1(ctx, info, i);

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (_exists(ctx, filter) && i < 100);
	}

	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info, int count) throws BOSException,
			CodingRuleException, EASBizException {
		String currentOrgId = getOrgUnitId(ctx, info);
		if (FdcStringUtil.isBlank(currentOrgId)) {
			return;
		}

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// ��ǰ��֯ID����ѭ���б���ǰ��
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// ����������е��ظ�ֵ��Nullֵ
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		String bindingProperty = getBindingProperty();
		String orgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		// ��������ȡҪ֧����֯�ݹ顣���磺����Ҫ�ҵ�ǰ��֯�����û���ҵ�����Ҫ���ҵ��ϼ���֯�������û���ҵ�����Ҫ�����ϼ���֯����������
		for (int j = 0; j < orgIdList.size(); j++) {
			orgId = orgIdList.get(j).toString();
			if (setNumber(ctx, info, orgId, bindingProperty, iCodingRuleManager, count)) {
				// ����ɹ�ƥ�䵽�����������ֹѭ����ֱ�ӷ��أ���ͬ��������Ϊ���һ�����õ���ʱֵ��
				return;
			}
		}
	}

	/**
	 * ������ƥ�������򣬻�ȡ���룬���ñ���
	 * 
	 * @param ctx
	 * @param info
	 * @param orgId
	 * @param bindingProperty
	 * @param iCodingRuleManager
	 * @param count
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-11-20
	 */
	protected boolean setNumber(Context ctx, FDCBillInfo info, String orgId, String bindingProperty,
			ICodingRuleManager iCodingRuleManager, int count) throws BOSException, EASBizException {

		if (FdcCodingRuleUtil.isExist(iCodingRuleManager, info, orgId, bindingProperty)) {
			// ��ȡ����������
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, info, orgId,
					bindingProperty);
			// ���֧���޸ľ�Ҫ�������Ƿ���ϵ�ǰ���õı������Ĺ���

			// ��ȡ��������Ƿ�֧���޸�
			boolean flag = FdcCodingRuleUtil.isAllowModifyNumber(iCodingRuleManager, info, orgId, bindingProperty);
			// �ж��Ƿ��޸��˱���,�Ƿ�Ĵ���˳���
			if (flag) {
				iCodingRuleManager.checkModifiedNumber(info, orgId, info.getNumber());
			} else {
				// �����������ʾ
				if (codingRuleInfo.isIsAddView()) {
					// ����Ϊ�յ�ʱ��Ҫ��ȡ����
					if (FdcStringUtil.isNotBlank(info.getNumber())) {
						String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
						info.setNumber(number);
					} else {
						// �����벻Ϊ�յ�ʱ��count=0��ʱ����Ҫ���»�ȡ���룬������>0��ʱ��֤�������ظ�Ҫ���»�ȡ�µı���
						if (count > 0) {
							String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId,
									bindingProperty);
							info.setNumber(number);
						}
					}
				} else {
					// ������ǲ�����Ϻţ�ÿ�ζ�Ҫ���ֻ�ȡ
					String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
					info.setNumber(number);
				}
			}

			return true;
		}

		return false;
	}

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		logger.info("===============================================================================");
		logger.info("recycleNumber(),start");
		logger.info("===============================================================================");

		SelectorItemCollection sic = getSeletorForRecycleNumber();
		FDCBillInfo info = (FDCBillInfo) _getValue(ctx, pk, sic);
		String currentOrgId = getOrgUnitId(ctx, info);

		logger.info("sic:" + sic);
		logger.info("info:" + info);
		logger.info("info.number:" + info.getNumber());

		boolean isRecycleParentOrgNumber = isRecycleParentOrgNumber();
		logger.info("isRecycleParentOrgNumber:" + isRecycleParentOrgNumber);
		ArrayList orgIdList = new ArrayList();
		// ��ǰ��֯ID����ѭ���б���ǰ��
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// ����������е��ظ�ֵ��Nullֵ
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		String bindingProperty = getBindingProperty();
		String curOrgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			curOrgId = orgIdList.get(i).toString();
			logger.info("������֯Ϊ��" + curOrgId + "�ı������");

			boolean flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, info, curOrgId, bindingProperty);
			if (flag) {
				// ������ճɹ��˾�����ѭ��
				if (FdcCodingRuleUtil.recycleNumber(iCodingRuleManager, info, curOrgId, bindingProperty, "", info
						.getNumber())) {
					break;
				}
			}
		}

		logger.info("===============================================================================");
		logger.info("recycleNumber(),end");
		logger.info("===============================================================================");
	}

	// ////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////

}