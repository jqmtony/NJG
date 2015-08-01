package com.kingdee.eas.fdc.basedata.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexVerTypeEnum;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ProjectIndexDataControllerBean extends AbstractProjectIndexDataControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectIndexDataControllerBean");
	private final BOSObjectType prjBosType = new CurProjectInfo().getBOSType();

	protected IRowSet _sum(Context ctx, List projIdList, String productTypeId) throws BOSException,
			EASBizException {

		StringBuffer sql = new StringBuffer();
		sql.append("select b.FApportionTypeID, c.FTargetTypeID, sum(b.FIndexValue) totalValue ");
		sql.append("from t_fdc_projectindexdata a inner join t_fdc_projectindexdataentry b on a.fid = b.fparentid " +
				"inner join t_fdc_apportiontype c on b.FApportionTypeID = c.Fid " +
				"inner join t_fdc_curproject d on a.fprojororgid = d.fid " +
				"where c.FIsForGather = 1 and d.FIsEnabled = 1 and a.FIsLatestVer = 1 and a.FProjOrOrgId in(");

		int size = projIdList.size();
		StringBuffer c = new StringBuffer();
		for (int i = 0; i < size - 1; i++) {
			c.append("?,");
		}
		c.append("?");

		sql.append(c);
		sql.append(") and a.FProductTypeId ");

		if (productTypeId == null) {
			sql.append(" is null ");
		} else {
			sql.append(" = ? ");
			projIdList.add(productTypeId);
		}

		sql.append(" group by b.FApportionTypeID, c.FTargetTypeID");

		return DbUtil.executeQuery(ctx, sql.toString(), projIdList.toArray());

	}

	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProjectIndexDataInfo dataInfo = (ProjectIndexDataInfo) model;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_fdc_projectindexdata set FIsLatestVer = 0 where FProjOrOrgID =");
		builder.appendParam(dataInfo.getProjOrOrgID().toString());
		builder.appendSql(" and FProjectStage =");
		builder.appendParam(dataInfo.getProjectStage().getValue());
		builder.appendSql(" and FProductTypeID ");
		if (dataInfo.getProductType() != null) {
			builder.appendSql("= ");
			builder.appendParam(dataInfo.getProductType().getId().toString());
		} else {
			builder.appendSql("is null ");
		}
		builder.executeUpdate();
		// update isLastsubver field
		builder.clear();
		builder.appendSql("update t_fdc_projectindexdata set FisLatestSubVer = 0 where FProjOrOrgID =");
		builder.appendParam(dataInfo.getProjOrOrgID().toString());
		builder.appendSql(" and FProjectStage =");
		builder.appendParam(dataInfo.getProjectStage().getValue());
		builder.appendSql(" and FProductTypeID ");
		if (dataInfo.getProductType() != null) {
			builder.appendSql("= ");
			builder.appendParam(dataInfo.getProductType().getId().toString());
		} else {
			builder.appendSql("is null ");
		}
		builder.appendSql(" and FverName=");
		builder.appendParam(dataInfo.getVerName());
		builder.executeUpdate();
	}

	/*
	 * 维护版本号的一致
	 * 
	 * @see
	 * com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#handleIntermitNumber
	 * (com.kingdee.bos.Context, com.kingdee.eas.fdc.basedata.FDCBillInfo)
	 */
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException,
			EASBizException {
		ProjectIndexDataInfo dataInfo = (ProjectIndexDataInfo) info;
		String verNo = dataInfo.getVerNo();
		if (FDCHelper.isEmpty(verNo)) {
			verNo = "V1.0";
			dataInfo.setVerNo(verNo);
		}
		// 状态默认为审批
		if (!verNo.equals("V1.0")) {
			dataInfo.setState(FDCBillStateEnum.AUDITTED);
		}
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("projOrOrgId", dataInfo.getProjOrOrgID().toString());
		filter.appendFilterItem("projectStage", dataInfo.getProjectStage().getName());
		filter.appendFilterItem("productType.id", dataInfo.getProductType() != null ? dataInfo.getProductType()
				.getId() : null);
		filter.appendFilterItem("verNo", dataInfo.getVerNo());
		if (_exists(ctx, filter)) {
			String v = verNo.substring(1, verNo.indexOf("."));
			int i = Integer.parseInt(v);
			i++;

			verNo = "V" + i + ".0";
			dataInfo.setVerNo(verNo);
			handleIntermitNumber(ctx, info);
		}
	}

	protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return null;
	}

	protected Map _submitAreaIndex(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
		TargetTypeInfo targe = new TargetTypeInfo();
		targe.setId(BOSUuid.read("WhiXXQEOEADgAAFiwKgTu0gq1N0="));
		for (Iterator iter = colls.iterator(); iter.hasNext();) {
			ProjectIndexDataInfo info = (ProjectIndexDataInfo) iter.next();
			info.setState(FDCBillStateEnum.SUBMITTED);
			info.setIsLatestSubVer(true);
			info.setIsLatestVer(true);

			for (int i = 0; i < info.getEntries().size(); i++) {
				info.getEntries().get(i).setTargetType(targe);
			}

			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("productType.*");
			view.getSelector().add("entries.*");
			view.getSelector().add("entries.apportionType.*");
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("projOrOrgID", info.getProjOrOrgID().toString());
			filter.appendFilterItem("isLatestVer", Boolean.TRUE);
			filter.appendFilterItem("projectStage", info.getProjectStage().getValue());
			filter.appendFilterItem("productType", info.getProductType() == null ? null : info.getProductType()
					.getId().toString());
			view.setFilter(filter);

			ProjectIndexDataCollection projectIndexDataCollection = getProjectIndexDataCollection(ctx, view);
			if (projectIndexDataCollection.size() > 1) {
				throw new EASBizException(new NumericExceptionSubItem("1000", "存在多条最新数据"));
			}
			if (projectIndexDataCollection.size() == 0) {
				_submit(ctx, info);
			}

			if (projectIndexDataCollection.size() == 1) {
				// 做数据合并
				if (info.getVerName() != null
						&& info.getVerName().equals(ProjectIndexVerTypeEnum.PRESELLAREA_VALUE)
						&& projectIndexDataCollection.get(0).getVerName() != null
						&& projectIndexDataCollection.get(0).getVerName().equals(
								ProjectIndexVerTypeEnum.COMPLETEAREA_VALUE)) {
					throw new EASBizException(new NumericExceptionSubItem("1000", "已经有峻工查丈数据，不允许新增预售查丈数据"));
				}
				for (Iterator iter2 = projectIndexDataCollection.get(0).getEntries().iterator(); iter2.hasNext();) {
					ProjectIndexDataEntryInfo entry = (ProjectIndexDataEntryInfo) iter2.next();
					boolean hasContain = false;
					for (int i = 0; i < info.getEntries().size(); i++) {
						if (info.getEntries().get(i).getApportionType().getId().toString().equals(
								entry.getApportionType().getId().toString())) {
							hasContain = true;
							break;
						}

					}

					if (!hasContain) {
						entry.setParent(info);
						entry.setId(null);
						info.getEntries().add(entry);
					}
				}
			}

			_submit(ctx, info);
			// TODO 更新最否最版本等
			// FDCSQLBuilder builder=
		}

		return null;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProjectIndexDataInfo info = (ProjectIndexDataInfo) model;
		if (info.getProjOrOrgID().getType().equals(prjBosType)) {
			String projectID = info.getProjOrOrgID().toString();
			SelectorItemCollection selector = new SelectorItemCollection();
			String companyID = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(
					new ObjectUuidPK(info.getProjOrOrgID()), selector).getFullOrgUnit().getId().toString();
			if (FDCUtils.IsInCorporation(ctx, companyID)) {
				PeriodInfo currenctCostPeriod = FDCUtils.getCurrentPeriod(ctx, projectID, true);
				info.setPeriod(currenctCostPeriod);
			}
		}
		return super._submit(ctx, info);
	}

	/*
	 * param格式：param内List,List内放置为子map： 1.projId 工程项目在ID 2.productId 产品ID
	 * 如果没有产品则为空 3.apportionsId 指标IDList
	 * 
	 * @see
	 * com.kingdee.eas.fdc.basedata.app.AbstractProjectIndexDataControllerBean
	 * #_idxRefresh(com.kingdee.bos.Context, java.util.Map)
	 */

	protected Map _idxRefresh(Context ctx, Map param) throws BOSException, EASBizException {
		// 被观察者对象
		String companyId = (String) param.get("companyId");
		// 启用了成本月结也应该是新的版本，因为老的版本可能被引用了
		boolean isFCAll = FDCUtils.IsFinacial(ctx, companyId) || FDCUtils.IsInCorporation(ctx, companyId);
		initIdxData(ctx, param);
		try {
			// 发出更新通知
			ProjectObservable.getInstance(ctx, isFCAll).notifyObservers(param);
		} catch (Exception e) {
			throw new BOSException(e);
		}
		return null;
	}

	private void initIdxData(Context ctx, Map param) throws BOSException {
		// 按项目做一次过滤
		String prjId = (String) param.get("projId");
		Set set = new HashSet();
		if (prjId == null) {
			List refreshSrcList = (List) param.get("refreshSrcList");
			for (Iterator iter = refreshSrcList.iterator(); iter.hasNext();) {
				Map map = (Map) iter.next();
				if (map.get("projId") != null) {
					set.add(map.get("projId"));
				}
			}
		} else {
			set.add(prjId);
		}
		if (set.size() == 0) {
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		initIdxContractSplit(ctx, builder, set);
		initIdxChangeSplit(ctx, builder, set);
		initIdxSettleSplit(ctx, builder, set);
		initIdxPaymentSplit(ctx, builder, set);
	}	
	private void initIdxContractSplit(Context ctx, FDCSQLBuilder builder, Set set) throws BOSException {
		// 合同拆分
		ContractCostSplitCollection c = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(getInitIdxView());
		String parentUpdateSql = "update T_Con_ContractCostSplit set fhasInitIdx=1 where fid=?";
		String entryUpdateSql = "update T_Con_ContractCostSplitEntry set fidxApportionId=? where fid=?";
		initIdx(builder, c, parentUpdateSql, entryUpdateSql);
	}
	private void initIdxChangeSplit(Context ctx, FDCSQLBuilder builder, Set set) throws BOSException {
		// 变更拆分
		ConChangeSplitCollection c = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(getInitIdxView());
		String parentUpdateSql = "update T_CON_ConChangeSplit set fhasInitIdx=1 where fid=?";
		String entryUpdateSql = "update T_CON_ConChangeSplitEntry set fidxApportionId=? where fid=?";
		initIdx(builder, c, parentUpdateSql, entryUpdateSql);
	}
	private void initIdxSettleSplit(Context ctx, FDCSQLBuilder builder, Set set) throws BOSException {
		// 结算单拆分
		SettlementCostSplitCollection c = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitCollection(getInitIdxView());
		String parentUpdateSql = "update T_CON_SettlementCostSplit set fhasInitIdx=1 where  fid=?";
		String entryUpdateSql = "update T_CON_SettlementCostSplitEntry set fidxApportionId=? where fid=?";
		initIdx(builder, c, parentUpdateSql, entryUpdateSql);
	}	
	private void initIdxPaymentSplit(Context ctx, FDCSQLBuilder builder, Set set) throws BOSException {
		// 付款拆分
		PaymentSplitCollection c = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(getInitIdxView());
		String parentUpdateSql = "update T_FNC_Paymentsplit set fhasInitIdx=1 where fid=?";
		String entryUpdateSql = "update T_FNC_PaymentsplitEntry set fidxApportionId=? where fid=?";
		initIdx(builder, c, parentUpdateSql, entryUpdateSql);
	}
	private void initIdx(FDCSQLBuilder builder, IObjectCollection bills, String parentSql, String entrySql) throws BOSException {
		List parentList = new ArrayList();
		List entryList = new ArrayList();
		fillInitIdxParams(bills, parentList, entryList);
		builder.clear();
		builder.executeBatch(parentSql, parentList);
		builder.clear();
		builder.executeBatch(entrySql, entryList);
	}
	private void fillInitIdxParams(IObjectCollection bills, List parentList, List entryList) {
		if (bills == null || bills.size() == 0) {
			return;
		}
		for (Iterator iter = bills.iterator(); iter.hasNext();) {
			FDCSplitBillInfo bill = (FDCSplitBillInfo) iter.next();
			List param = new ArrayList();
			param.add(bill.getId().toString());
			parentList.add(param);
			fillEntryList((IObjectCollection) bill.get("entrys"), entryList);
		}

	}	
	private void fillEntryList(IObjectCollection entries, List entryList) {
		for (int i = 0; i < entries.size(); i++) {
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) entries.getObject(i);
			for (int j = i + 1; j < entries.size(); j++) {
				FDCSplitBillEntryInfo subEntry = (FDCSplitBillEntryInfo) entries.getObject(j);
				if (entry.getLevel() > subEntry.getLevel() - 1) {
					break;
				}
				if (entry.getLevel() == subEntry.getLevel() - 1) {
					String apportId = (entry.getApportionType() != null && entry.getApportionType().getId() != null) ? entry.getApportionType()
							.getId().toString() : null;
					if (apportId == null) {
						continue;
					}
					entryList.add(Arrays.asList(new Object[] { apportId, subEntry.getId().toString() }));
				}
			}
		}
	}
	private EntityViewInfo getInitIdxView() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("hasInitIdx", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		view.getSelector().add("id");
		view.getSelector().add("entrys.id");
		view.getSelector().add("entrys.apportionType.id");
		return view;
	}
}