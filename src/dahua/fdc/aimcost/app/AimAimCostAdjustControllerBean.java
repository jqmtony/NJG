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
			// 新增一版新的版本，把目标成本调整单的分录加到新版本分录上；且把状态置为已审批
			AimCostInfo aimCostInfo = fetchAimCost(ctx, projectID);
			BigDecimal newVersionNumber = new BigDecimal(aimCostInfo.getVersionNumber()).add(FDCHelper.ONE);
			aimCostInfo.setVersionNumber(newVersionNumber.toString());// 版本号
			updateOldAimVersionNumber(ctx, projectID);
			BOSUuid bosId = BOSUuid.create(aimCostInfo.getBOSType());
			aimCostInfo.setId(bosId);
			aimCostInfo.setGenByAdjust(id);
			aimCostInfo.setRecenseDate(new Timestamp(System.currentTimeMillis()));// 修订时间
			aimCostInfo.setState(FDCBillStateEnum.AUDITTED);// 状态
			aimCostInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));// 审批人
			aimCostInfo.setAuditDate(new Date());// 审批时间
			aimCostInfo.setIsLastVersion(true);// 最新版
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
				addEntry.setDesc(aimCostAdjustEntryInfo.getDescription());	//备注
				addEntry.setChangeReason(aimCostAdjustEntryInfo.getChangeReason()); //变化原因
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
			 * 更新各产品类型动态目标成本中的 分录ID
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
	 * 把前一版本中“是否最新版本”置为false
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
	 * 获取目标成本调整单信息
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
	 * 获取最新版且已审批的目标成本所有信息
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
	 * 获取最新版目标成本头信息
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
	 * 描述：属性字段参与编码规则，如果值发生改变，则需要重新获取编码
	 * 
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isNeedReHandleIntermitNumberByProperty() {
		return true;
	}

	/**
	 * 描述：是否回收上级组织传递的编码规则
	 * 
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-10-10
	 */
	protected boolean isRecycleParentOrgNumber() {
		return true;
	}

	/**
	 * 描述：取得回收编码Seletor
	 * 
	 * @return
	 * @author：skyiter_wang
	 * @createDate：2013-10-16
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
		// 当前组织ID放在循环列表最前面
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		String bindingProperty = getBindingProperty();
		String orgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		// 编码规则获取要支持组织递归。例如：首先要找当前组织，如果没有找到，则要能找到上级组织，如果还没有找到，则要找上上级组织，依次类推
		for (int j = 0; j < orgIdList.size(); j++) {
			orgId = orgIdList.get(j).toString();
			if (setNumber(ctx, info, orgId, bindingProperty, iCodingRuleManager, count)) {
				// 如果成功匹配到编码规则，则终止循环，直接返回（合同编码类型为最后一次设置的临时值）
				return;
			}
		}
	}

	/**
	 * 描述：匹配编码规则，获取编码，设置编码
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
			// 获取编码规则对象
			CodingRuleInfo codingRuleInfo = FdcCodingRuleUtil.getCodingRule(iCodingRuleManager, info, orgId,
					bindingProperty);
			// 如果支持修改就要检查编码是否符合当前启用的编码规则的规则

			// 获取编码规则是否支持修改
			boolean flag = FdcCodingRuleUtil.isAllowModifyNumber(iCodingRuleManager, info, orgId, bindingProperty);
			// 判断是否修改了编码,是否改大了顺序号
			if (flag) {
				iCodingRuleManager.checkModifiedNumber(info, orgId, info.getNumber());
			} else {
				// 如果是新增显示
				if (codingRuleInfo.isIsAddView()) {
					// 编码为空的时候要获取编码
					if (FdcStringUtil.isNotBlank(info.getNumber())) {
						String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId, bindingProperty);
						info.setNumber(number);
					} else {
						// 当编码不为空的时候，count=0的时候不需要重新获取编码，当编码>0的时候证明编码重复要重新获取新的编码
						if (count > 0) {
							String number = FdcCodingRuleUtil.getNumber(iCodingRuleManager, info, orgId,
									bindingProperty);
							info.setNumber(number);
						}
					}
				} else {
					// 否则就是不允许断号，每次都要重现获取
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
		// 当前组织ID放在循环列表最前面
		orgIdList.add(currentOrgId);
		if (isRecycleParentOrgNumber) {
			ContractUtil.findParentOrgUnitIdToList(ctx, currentOrgId, orgIdList);
		}
		// 清除掉集合中的重复值和Null值
		FdcCollectionUtil.clearDuplicateAndNull(orgIdList);
		logger.info("orgIdList:" + orgIdList);

		String bindingProperty = getBindingProperty();
		String curOrgId = null;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		for (int i = 0, size = orgIdList.size(); i < size; i++) {
			curOrgId = orgIdList.get(i).toString();
			logger.info("回收组织为：" + curOrgId + "的编码规则。");

			boolean flag = FdcCodingRuleUtil.isRecycleNumber(iCodingRuleManager, info, curOrgId, bindingProperty);
			if (flag) {
				// 如果回收成功了就跳出循环
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