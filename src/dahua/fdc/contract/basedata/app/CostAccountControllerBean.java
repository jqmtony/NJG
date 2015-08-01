package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.basedata.AccountStageCollection;
import com.kingdee.eas.fdc.basedata.AccountStageFactory;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAcctFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.util.FdcORMappingDAOUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 描述:成本科目
 * 
 * @author jackwang date:2006-9-6
 *         <p>
 * @version EAS5.1
 */
public class CostAccountControllerBean extends AbstractCostAccountControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountControllerBean");

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		CostAccountInfo newCai = (CostAccountInfo) model;
		// 分配下去的需要跟着修改,下级节点编码不能出现断层
		if (newCai.isIsSource()) {
			// 新的长编码、类型、是否成本科目
			String newLn = newCai.getLongNumber();
			String newName = newCai.getName();
			String newType = newCai.getType() == null ? "" : newCai.getType().getValue();
			// 是否更新类别，一级不需要
			boolean isUpdateLevel = newLn.indexOf('!') != -1;
			Integer iCostAccount = newCai.isIsCostAccount() ? Integer.valueOf("1") : Integer.valueOf("0");
			String desc = newCai.getDescription();
			// 未保存之前,获取其原来的长编码、类型、是否成本科目
			CostAccountInfo oldCai = CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(pk);
			String oldLn = oldCai.getLongNumber();
			String oldName = oldCai.getName();

			// 更新当前科目
			super._update(ctx, pk, model);
			// 获取当前科目在当前组织或项目内所有下级科目ID
			HashSet set = new HashSet();
			String sql = null;
			Object[] params = null;
			if (oldCai.getCurProject() != null) {
				sql = "select fid from t_fdc_costaccount where FCurProject=? and FLongNumber like ?";
				params = new Object[] { oldCai.getCurProject().getId().toString(), oldLn + "!%" };
			} else if (oldCai.getFullOrgUnit() != null) {
				sql = "select fid from t_fdc_costaccount where FFullOrgUnit=? and FLongNumber like ?";
				params = new Object[] { oldCai.getFullOrgUnit().getId().toString(), oldLn + "!%" };
			}
			RowSet rs = DbUtil.executeQuery(ctx, sql, params);
			try {
				while (rs.next()) {
					set.add(rs.getString("fid"));
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}

			// 同步更新当前科目分配到下级组织或项目中科目的长编码、编码、名称、类型、是否成本科目、描述 属性
			params = null;
			sql =  "update T_FDC_CostAccount set FLongNumber=?,FNumber=?,FName_l2=?,FType=?,FIsCostAccount=?,FDescription_l2=? where fsrccostaccountid = ?";	
			params = new Object[]{newLn,newCai.getNumber(),newCai.getName(),newType,iCostAccount, desc,oldCai.getId().toString()};
			DbUtil.execute(ctx, sql, params);

			FDCSQLBuilder builder = new FDCSQLBuilder();
			if (set.size() > 0) {
				// 当上下级属性是一致相同时使用此if如相同上级编码，上下属性不一致的不要在此改如名称 by hpw 2012.2.15
				// 修改当前组织或项目内所有下级科目的长编码、类型、是否成本科目
				builder
						.appendSql("update T_FDC_CostAccount set FLongNumber=REPLACE(FLongNumber,?,?),FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				// builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fid", set.toArray(), "varchar(44) ");
				builder.executeUpdate(ctx);

				// 同步更新所有下级科目分配到下级组织或项目中科目的长编码、类型、是否成本科目
				builder.clear();
				builder
						.appendSql("update T_FDC_CostAccount set  FLongNumber=REPLACE(FLongNumber,?,?),FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				// builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fsrccostaccountid", set.toArray(), "varchar(44) ");
				builder.executeUpdate(ctx);
				
				// 一级科目时不更新
				if (isUpdateLevel) {
					// 修改当前组织或项目内所有下级科目的类型
					builder.appendSql("update T_FDC_CostAccount set FType=? where ");
					builder.addParam(newType);
					builder.appendParam("fid", set.toArray(), "varchar(44) ");
					builder.executeUpdate(ctx);

					// 同步更新所有下级科目分配到下级组织或项目中科目的类型
					builder.clear();
					builder.appendSql("update T_FDC_CostAccount set FType=? where ");
					builder.addParam(newType);
					builder.appendParam("fsrccostaccountid", set.toArray(), "varchar(44) ");
					builder.executeUpdate(ctx);
				}
			}
			
		}

		// 把属于营销类的对应成本科目flag标志置为1
		String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx, sqlNew);

		String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx, sqlOld);
	}

	// 更新下级的长名称 5001!02!01
	private String getNewDisplayName(Context ctx, String longNumber, String orgUnit, String curProject) throws BOSException {
		String displayName = null;
		String tempParams = "?";

		String[] numberArray = longNumber.split("!");
		for (int i = 0; i < numberArray.length - 1; i++) {
			numberArray[i + 1] = numberArray[i].concat("!".concat(numberArray[i + 1]));
			tempParams = tempParams.concat(",?");
		}

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FName_L2 from T_FDC_CostAccount where ");
		if (orgUnit == null) {
			builder.appendSql(" FFullOrgUnit is null ");
		} else {
			builder.appendSql(" FFullOrgUnit=? ");
			builder.addParam(orgUnit);
		}

		if (curProject == null) {
			builder.appendSql(" and FCurproject is null ");
		} else {
			builder.appendSql(" and FCurproject=? ");
			builder.addParam(curProject);
		}

		builder.appendSql(" and ");
		builder.appendParam("flongnumber", numberArray);
		builder.appendSql(" order by flongnumber ");
		IRowSet rs = builder.executeQuery(ctx);
		try {
			while (rs.next()) {
				displayName = displayName == null ? rs.getString("FName_L2") : displayName.concat("_".concat(rs.getString("FName_L2")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return displayName;
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		CostAccountInfo costAccountInfo = (CostAccountInfo) model;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Set stageIdSet = new HashSet();
	
		// 集团分配科目后，使用了一段时间，如果要新增科目，保存时需要检查下级公司、工程项目是否已建了相同的科目（科目编码相同）。
		if ((costAccountInfo.getFullOrgUnit() != null)) {// 挂组织节点
			// 当前要新增的科目是挂在集团上的
			if (OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString())) {
				if (costAccountInfo.getParent() != null) {// 是新增子节点
					costAccountInfo.setIsEnabled(costAccountInfo.getParent().isIsEnabled());
				}
			}
		}

		// 成本科目与测算关系对照表（中间表）
		AccountStageCollection accountStageCollection = new AccountStageCollection();
		
		// 当前科目阶段选择
		try {
			MeasureStageCollection msc = null;
			EntityViewInfo view = new EntityViewInfo();

			FilterInfo measureFilter = new FilterInfo();
			SorterItemCollection sii = new SorterItemCollection();
			SorterItemInfo sortor = new SorterItemInfo("number");
			sortor.setSortType(SortType.ASCEND);
			sii.add(sortor);

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("measureStage.id"));
			sic.add(new SelectorItemInfo("costAccount.id"));
			sic.add(new SelectorItemInfo("value"));
			sic.add(new SelectorItemInfo("number"));

			measureFilter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));

			view.setSelector(sic);
			view.setSorter(sii);
			view.setFilter(measureFilter);

			msc = MeasureStageFactory.getLocalInstance(ctx).getMeasureStageCollection(view);
			MeasureStageInfo mcInfo = null;
			for (int i = 0, size = msc.size(); i < size; i++) {
				mcInfo = msc.get(i);
				AccountStageInfo asInfo = new AccountStageInfo();
				asInfo.setId(BOSUuid.create(asInfo.getBOSType()));
				asInfo.setMeasureStage(mcInfo);
				// sp1需求新增默认所有阶段都选择
				asInfo.setValue(true);
				
				// costAccountInfo.getStageEntrys().add(asInfo);
				asInfo.setCostAccount(costAccountInfo);
				
				accountStageCollection.add(asInfo);
				stageIdSet.add(mcInfo.getId().toString());
			}
		} catch (BOSException e1) {
			throw new BOSException(e1);
		}

		// 上级科目阶段选择
		if (costAccountInfo.getParent() != null) {

			FilterInfo mergeFilter = new FilterInfo();
			if (costAccountInfo.getFullOrgUnit() != null) {
				String orgFullUnitId = costAccountInfo.getFullOrgUnit().getId().toString();
				mergeFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgFullUnitId));
			} else {
				String curProjectId = costAccountInfo.getCurProject().getId().toString();
				mergeFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
			}

			// 取当前科目的所有上级
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();

			String longNumber = costAccountInfo.getParent().getLongNumber();
			String[] parentLongNumberList = StringUtils.split(longNumber, "!");

			Set parentLongNumberSet = new HashSet();
			parentLongNumberSet.add(longNumber);

			String parentLongNumber = longNumber;
			for (int i = 0, len = parentLongNumberList.length - 1; i < len; i++) {
				parentLongNumber = parentLongNumber.substring(0, parentLongNumber.lastIndexOf("!"));
				parentLongNumberSet.add(parentLongNumber);
			}
			filter.getFilterItems().add(new FilterItemInfo("longNumber", parentLongNumberSet, CompareType.INCLUDE));
			filter.mergeFilter(mergeFilter, "and");

			evi.setFilter(filter);
			CostAccountCollection cac = getCostAccountCollection(ctx, evi);
			CostAccountInfo caInfo = null;

			Set costAccountSet = new HashSet();
			for (int i = 0, size = cac.size(); i < size; i++) {
				caInfo = cac.get(i);
				costAccountSet.add(caInfo.getId().toString());
			}

			// 更新上级科目阶段选择值
			if (costAccountSet.size() > 0 && stageIdSet.size() > 0) {
				builder.clear();
				builder.appendSql("update T_FDC_AccountStage set fvalue = 1 where ");
				builder.appendParam("FMeasureStageID", stageIdSet.toArray(), "varchar(44)");
				builder.appendSql(" and ");
				builder.appendParam("FCostAccountID", costAccountSet.toArray(), "varchar(44)");
				builder.executeUpdate();
			}
			// 继承父节点的新增权限
			costAccountInfo.setIsCanAdd(costAccountInfo.getParent().isIsCanAdd());

		}

		String sql = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx, sql);

		String sqlOR = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx, sqlOR);
		//判断当前是否对明细增加下级，如果是，则下对对上下级ID进行互换。 by hpw
		boolean isLeafAdd = false;
		if(costAccountInfo.getParent()!=null&&costAccountInfo.getParent().isIsLeaf()){
			//super
			isLeafAdd=true;
		}
		
		// accountStageCollection = costAccountInfo.getStageEntrys();
		IObjectPK pk = super._addnew(ctx, costAccountInfo);

		// ////////////////////////////////////////////////////////////////////////////////
		// R140504-0191：长房项目宕机性能优化 by skyiter_wang 2014-05-21
		// ////////////////////////////////////////////////////////////////////////////////
		// ============ start

		// 批量新增 成本科目与测算关系对照表（中间表）
		CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
		coreBaseCollection.addObjectCollection(accountStageCollection);
		// EAS750之前报错，替换成自定义工具类FdcORMappingDAOUtil
		// AccountStageFactory.getLocalInstance(ctx).addnewBatchData(coreBaseCollection);
		FdcORMappingDAOUtil.addnewBatchData(ctx, getConnection(ctx), AccountStageFactory.getLocalInstance(ctx), coreBaseCollection);
		costAccountInfo.getStageEntrys().addObjectCollection(coreBaseCollection);

		// ============ end
		// ////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////
		
		// 新增时同时也要新增对应关系
		Map param = new HashMap();
		Set set = new HashSet();
		set.add(pk.toString());
		param.put("acctIdSet", set);
		CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
					
		//bug: 明细科目新增下级科目的处理,基类中已对父级isLeaf字段进行了false处理  by hpw
		if(isLeafAdd && !costAccountInfo.getParent().isIsLeaf() ){
			
			String tempId = BOSUuid.create(((CostAccountInfo) model).getBOSType()).toString();
			BOSUuid parentPK = costAccountInfo.getParent().getId();
			
			String changeIdSql = "update t_fdc_costaccount set fid=? where fid=?";
			List params=new ArrayList();
			params.add(Arrays.asList(new String[]{tempId,parentPK.toString()}));
			params.add(Arrays.asList(new String[]{parentPK.toString(),pk.toString()}));
			params.add(Arrays.asList(new String[]{pk.toString(),tempId}));
			builder.clear();
			
			builder.executeBatch(changeIdSql, params);
			
			String parentSql = "update t_fdc_costaccount set fparentid=? where fid=?";
			List parentParams=new ArrayList();
			parentParams.add(Arrays.asList(new String[]{pk.toString(),parentPK.toString()}));
			builder.clear();
			builder.executeBatch(parentSql, parentParams);
			
			/**
			 * 更新这个明细科目为源的科目的源字估
			 */
			String updateSrcCostActSQLa = "update t_fdc_costaccount set fsrccostaccountid=? where fsrccostaccountid=?" ;
			
			List sqlParams=new ArrayList();
			sqlParams.add(Arrays.asList(new String[]{pk.toString(),parentPK.toString()}));
			builder.executeBatch(updateSrcCostActSQLa, sqlParams);
			
		}
		
		return pk;
	}

	protected boolean _enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		boolean flag = false;
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountInfo costAccountInfo = new CostAccountInfo();

		costAccountInfo = getCostAccountInfo(ctx, pk);

		if (costAccountInfo.getParent() != null) {
			IObjectPK parentPK = new ObjectStringPK(costAccountInfo.getParent().getId().toString());
			CostAccountInfo parentCostAccountInfo = getCostAccountInfo(ctx, parentPK);
			if (!parentCostAccountInfo.isIsEnabled()) {
				// 如果上级被禁用,给出提示并返回
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.COSTACCOUNT_PARENT_DISENABLE);
			} else {
				if (changeUseStatus(ctx, pk, true))
					flag = true;
			}

		} else {
			if (changeUseStatus(ctx, pk, true))
				flag = true;
		}

		if (flag) {
			// 4301.401.01.01是由上级分配下来的科目，由上级禁用掉该科目，下级启用后上级组织的该科目没有自动启用
			if (costAccountInfo.getSrcCostAccountId() != null) {
				DbUtil.execute(ctx, "update T_FDC_CostAccount set  FIsEnable = 1 where fid = ?",
						new Object[] { costAccountInfo.getSrcCostAccountId() });
			}
		}
		return flag;
	}

	protected boolean _disable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// _isReferenced(ctx, pk);

		// if (this.checkIsUsed(ctx, pk)) {// 本节电下判断引用
		// throw new
		//com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException
		// .DISENABLE_CANNOT_USED);
		// }
//		// 修改变更 2011-5-21 取消子节点唯一一个被启用的 不允许 启用 by cj
//		 if (this.checkIsOnlyOneEnabled(ctx, pk)) {
//		 throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
//		 }
		if (changeUseStatus(ctx, pk, false))
			return true;
		else
			return false;


	}

	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK) throws BOSException, EASBizException {
		CostAccountInfo cai = this.getCostAccountInfo(ctx, PK).getParent();
		if (cai != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cai.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getCostAccountCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}

	private boolean checkIsUsed(Context ctx, IObjectPK PK) throws EASBizException, BOSException {
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountInfo costAccountInfo = this.getCostAccountInfo(ctx, PK);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountInfo.getId().toString()));
		evi.setFilter(filter);
		CostAccountCollection costAccountCollection = this.getCostAccountCollection(ctx, evi);
		// 如果有下级,先判断自己,再判断下级
		if (costAccountCollection.size() > 0) {
			// 判断自己
			if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))
					|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + PK.toString() + "'"))) {
				// || (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() +
				// "'"))) {
				return true;
			} else {
				// 判断下级
				for (int i = 0; i < costAccountCollection.size(); i++) {
					IObjectPK objectPK  = new ObjectUuidPK(costAccountCollection.get(i).getId());
					_isReferenced(ctx,objectPK);
					isUseCostAccount(ctx, objectPK);
					if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='"
							+ costAccountCollection.get(i).getId().toString() + "'"))
							|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='"
									+ costAccountCollection.get(i).getId().toString() + "'"))) {
						// || (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" +
						// costAccountCollection.get(i).getId().toString() + "'"))) {
						return true;
					} else {
						checkIsUsed(ctx, new ObjectStringPK(costAccountCollection.get(i).getId().toString()));
					}
				}
			}
		} else {
			// 如果没有下级,判断自己
			if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))
					|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + PK.toString() + "'"))) {
				// || (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() +
				// "'"))) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 */
	protected boolean changeUseStatus(Context ctx, IObjectPK PK, boolean flag) throws EASBizException, BOSException {
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("fullOrgUnit.id");
		sic.add("id");
		sic.add("longnumber");
		sic.add("curProject.id");
		sic.add("curProject.longnumber");
		CostAccountInfo costAccountInfo = getCostAccountInfo(ctx, PK, sic);
		if ((!flag) && (costAccountInfo.getFullOrgUnit() != null)
				&& (OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))) {
			// 如果是集团下的禁用操作,前面已经校验了,此处通过了校验,直接sql处理候艳提要求录bug,裴红说要这样处理的20061124
			String longNumber = costAccountInfo.getLongNumber();
			// CostAccountCollection cac = this.getCostAccountCollection(ctx,"select longNumber where longNumber like '" + longNumber +
			// "%'");//fullOrgUnit.id = '" + OrgConstants.DEF_CU_ID + "' and
			// if(cac.size()!=0){
			String sql = "update T_FDC_CostAccount set  FIsEnable = 0  where FLongNumber like '" + longNumber + "%'";
			// Object[] params ;
			// for(int i = 0;i<cac.size();i++){
			// params = new Object[]{cac.get(i).getLongNumber()};
			DbUtil.execute(ctx, sql);
			// }
			// }
		} else if ((!flag) && (costAccountInfo.getFullOrgUnit() != null)) {
			// 财务组织或工程项目管控情况，对下级的处理
			String longNumber = costAccountInfo.getLongNumber();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_FDC_CostAccount set  FIsEnable = 0  ");
			builder.appendSql("where (ffullorgunit=? or fcurproject in (select fid from t_fdc_curproject where ffullorgunit=?)) ");
			String orgID = costAccountInfo.getFullOrgUnit().getId().toString();
			builder.addParam(orgID);
			builder.addParam(orgID);
			builder.appendSql("and flongnumber like '" + longNumber + "'");
			builder.executeUpdate();
			builder.clear();
		}
		if ((flag) && (costAccountInfo.getFullOrgUnit() != null)
				&& (OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))) {
			// 如果是集团下的启用操作，启用所有分配下去的
			String longNumber = costAccountInfo.getLongNumber();
			String sql = "update T_FDC_CostAccount set  FIsEnable = 1  where FLongNumber like '" + longNumber + "%'";
			DbUtil.execute(ctx, sql);
		} else {
			// 处理本科目的上下级
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountInfo.getId().toString()));
			evi.setFilter(filter);
			CostAccountCollection costAccountCollection = getCostAccountCollection(ctx, evi);
			// 如果有下级,要同时启用/禁用下级
			if (costAccountCollection.size() > 0) {
				// 先启用/禁用自己
				
				String sql;
				if (costAccountInfo.getCurProject() != null) {
					String curLongnumber = costAccountInfo.getCurProject().getLongNumber();
					sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fcurproject in (select fid from t_fdc_curproject where flongnumber = ? or flongnumber like '"
							+ curLongnumber + "!%" + "' ) and flongnumber = ?";
					Object[] params = new Object[] { Boolean.valueOf(flag), curLongnumber, costAccountInfo.getLongNumber() };
					DbUtil.execute(ctx, sql, params);
				} else {
					sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fid = ? ";
					Object[] params = new Object[] { Boolean.valueOf(flag), costAccountInfo.getId().toString() };
					DbUtil.execute(ctx, sql, params);
				}
				
				// 再启用/禁用下级
				CostAccountInfo childContractTypeInfo;
				IObjectPK childPK;
				for (int i = 0; i < costAccountCollection.size(); i++) {
					if (Boolean.valueOf(costAccountCollection.get(i).isIsEnabled()) != Boolean.valueOf(flag)) {
						childContractTypeInfo = costAccountCollection.get(i);
						childContractTypeInfo.setIsEnabled(flag);
						childPK = new ObjectStringPK(childContractTypeInfo.getId().toString());
						changeUseStatus(ctx, childPK, flag);
					}
				}
			} else {
				// 如果没有下级
				String sql;
				if (costAccountInfo.getCurProject() != null) {
					String curLongnumber = costAccountInfo.getCurProject().getLongNumber();
					sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fcurproject in (select fid from t_fdc_curproject where flongnumber = ? or flongnumber like '"
							+ curLongnumber + "!%" + "' ) and flongnumber = ?";
					Object[] params = new Object[] { Boolean.valueOf(flag), curLongnumber, costAccountInfo.getLongNumber() };
					DbUtil.execute(ctx, sql, params);
				} else {
					sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fid = ? ";
					Object[] params = new Object[] { Boolean.valueOf(flag), costAccountInfo.getId().toString() };
					DbUtil.execute(ctx, sql, params);
				}

				// costAccountInfo.setIsEnabled(flag);
				// _update(ctx, PK, costAccountInfo);
			}
		}

		// }else{

		// }
		return true;
	}
	
	private void isUseCostAccount(Context ctx,IObjectPK PK) throws BOSException, FDCBasedataException{
		
		StringBuffer checkExistsSql = new StringBuffer();
		checkExistsSql.append("select A.FCostAccountID AS FID FROM T_AIM_CostEntry A 		\r\n");
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FAccountID AS FID from T_AIM_DynamicCost A 		\r\n");
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_COSTSPLITENTRY A 		\r\n");
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_MEASUREENTRY A 		\r\n");
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_DynCostCtrlEntryItems A 		\r\n");// 成本控制单
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_AimCostCtrlCostActItems A 		\r\n");// 目标成本控制单
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		String fid = PK.toString();
		Object[] params = new Object[] { fid, fid, 
				fid,  fid,  fid, fid };
		RowSet rs = DbUtil.executeQuery(ctx, checkExistsSql.toString(), params);

		try {
			if (rs != null && rs.next()) {
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_SUBNODE_REFERENCE1);
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}
	
	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;
		// 检查下级是否存在同编码项

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// 父节点为空时检查根对象编码是否重复。
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent, null, CompareType.EQUALS));
			if (((CostAccountInfo) treeModel).getCurProject() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("curproject.id", ((CostAccountInfo) treeModel).getCurProject().getId().toString(), CompareType.EQUALS));
			} else if (((CostAccountInfo) treeModel).getFullOrgUnit() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeModel).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
			}
			filter.setMaskString("#0 and #1 and #2 ");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1 and #2 and #3 ");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				// filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
				// treeModel.innerGetParent().getId().toString(),
				// CompareType.EQUALS);
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent,
						treeModel.innerGetParent().getId().toString(),
						CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
			
			}
			// 组织下,项目下的隔离 by tim_gao
			if (((CostAccountInfo) treeModel).getCurProject() != null) {
				filter
						.getFilterItems()
						.add(
								new FilterItemInfo("curproject.id",
										((CostAccountInfo) treeModel)
												.getCurProject().getId()
												.toString(), CompareType.EQUALS));
			} else if (((CostAccountInfo) treeModel).getFullOrgUnit() != null) {
				filter
						.getFilterItems()
						.add(
								new FilterItemInfo("fullorgunit.id",
										((CostAccountInfo) treeModel)
												.getFullOrgUnit().getId()
												.toString(), CompareType.EQUALS));
			}
			
			filter.setMaskString("#0 and #1 and #2 ");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		// CU隔离
		// FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		// if (FilterUtility.hasFilterItem(filterCU)) {
		// if (FilterUtility.hasFilterItem(filter)) {
		// filter.mergeFilter(filterCU, "AND");
		// } else {
		// filter = filterCU;
		// }
		// }
		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);
		if (results != null && results.size() > 0) {
			throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED, new Object[] { treeModel.getNumber() });

		}

	}

	/**
	 * 默认实现对于相 同的长编码，但ID不同进行处理。子类可按需要覆盖实现。
	 * 
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		// 如果数据库存在相同longNumber但ID不同的数据，则异常。
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber, treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeBaseInfo.getId().toString(), CompareType.NOTEQUALS));
		if (((CostAccountInfo) treeBaseInfo).getCurProject() != null) {
			lNfilter.getFilterItems().add(
					new FilterItemInfo("curproject.id", ((CostAccountInfo) treeBaseInfo).getCurProject().getId().toString(), CompareType.EQUALS));
		} else if (((CostAccountInfo) treeBaseInfo).getFullOrgUnit() != null) {
			lNfilter.getFilterItems().add(
					new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeBaseInfo).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
		}
		lNfilter.setMaskString("#0 AND #1 AND #2 ");

		// CU隔离
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeBaseInfo);
		if (FilterUtility.hasFilterItem(filterCU)) {
			lNfilter.mergeFilter(filterCU, "AND");
		}

		if (exists(ctx, lNfilter)) {
			throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED, new Object[] { treeBaseInfo.getNumber() });
		}
	}

	protected boolean _assign(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return false;
	}

	protected boolean _disassign(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return false;

	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		CostAccountInfo costAccountInfo = this.getCostAccountInfo(ctx, pk);
		_isReferenced(ctx, pk);

		if ((costAccountInfo.getFullOrgUnit() != null) && (OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))) {
			// 如果是在集团下禁用,需要判断下级所有已分配下去的科目,是否有被引用的,
			// 如果有引用,停止
			// 如果没有引用,继续,并禁用系统中所有(包括被分配下去的)

			// 寻找当前节点(集团)下的下级科目
			StringBuffer checkExistsSql = new StringBuffer();
			checkExistsSql.append("select A.FCostAccountID AS FID FROM T_AIM_CostEntry A 		\r\n");
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FAccountID AS FID from T_AIM_DynamicCost A 		\r\n");
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_COSTSPLITENTRY A 		\r\n");
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_MEASUREENTRY A 		\r\n");
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_DynCostCtrlEntryItems A 		\r\n");// 成本控制单
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_AimCostCtrlCostActItems A 		\r\n");// 目标成本控制单
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");

			String longNumber = costAccountInfo.getLongNumber() + "!%";
			Object[] params = new Object[] { OrgConstants.DEF_CU_ID, longNumber, OrgConstants.DEF_CU_ID, longNumber, OrgConstants.DEF_CU_ID, longNumber, OrgConstants.DEF_CU_ID, longNumber,
					OrgConstants.DEF_CU_ID, longNumber, OrgConstants.DEF_CU_ID, longNumber };
			RowSet rs = DbUtil.executeQuery(ctx, checkExistsSql.toString(), params);

			try {
				if (rs != null && rs.next()) {
					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_SUBNODE_REFERENCE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
			/*
			 * CostAccountCollection cac =this.getCostAccountCollection(ctx,
			 * "select id where fullOrgUnit.id !=  '" + OrgConstants.DEF_CU_ID +
			 * "' and longNumber like '" + longNumber + "!%'"); // 13509608400
			 * if(cac.size()!=0){ for(int i=0;i<cac.size();i++){ if
			 * ((CostEntryFactory.getLocalInstance(ctx).exists(
			 * "select costAccount where costAccount.id ='" +
			 * cac.get(i).getId().toString() + "'")) ||
			 * (DynamicCostFactory.getLocalInstance
			 * (ctx).exists("select costAccount where account.id ='" +
			 * cac.get(i).getId().toString() + "'"))){ // ||
			 * (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists(
			 * "select costAccount where costAccount.id ='" + PK.toString() +
			 * "'"))) { throw new
			 * com.kingdee.eas.fdc.basedata.FDCBasedataException
			 * (FDCBasedataException.DISENABLE_SUBNODE_REFERENCE); } } }
			 */
		} else {
			isUseCostAccount(ctx, pk);
		}

		if (costAccountInfo.isIsLeaf()) {
			this._isReferenced(ctx, pk);
			super._delete(ctx, pk);

			// ////////////////////////////////////////////////////////////////////////////////
			// R140504-0191：长房项目宕机性能优化 by skyiter_wang 2014-05-21
			// ////////////////////////////////////////////////////////////////////////////////
			// ============ start

			// 批量删除成本科目与测算关系对照表（中间表）

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("costAccount.id", pk.toString()));
			AccountStageFactory.getLocalInstance(ctx).delete(filter);

			// ============ end
			// ////////////////////////////////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////////////////////////////////
		} else {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
		}

	}

	// protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws
	// BOSException, EASBizException
	// {
	// return super._delete(ctx, filter);
	// }
	protected void _importDatas(Context ctx, IObjectCollection cac, BOSUuid addressId) throws BOSException, EASBizException {

		CurProjectInfo cpi = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(addressId.toString()));

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", addressId.toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isLeaf");
		view.getSelector().add("isEnabled");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");

		CostAccountCollection costAccountCollection2 = getCostAccountCollection(ctx, view);
		Map acMap = new HashMap();
		for (Iterator iter = costAccountCollection2.iterator(); iter.hasNext();) {
			CostAccountInfo element = (CostAccountInfo) iter.next();
			acMap.put(element.getLongNumber(), element);
		}

		CostAccountCollection costAccountCollection = (CostAccountCollection) cac;// 引入的成本科目集合
		CostAccountInfo cai;
		SelectorItemCollection seletors = new SelectorItemCollection();
//		seletors.add("id");
//		seletors.add("name");
//		seletors.add("number");
//		seletors.add("longNumber");
//		seletors.add("assigned");
//		seletors.add("isEnabled");
//		seletors.add("isSource");
//		seletors.add("srcCostAccountId");
//		seletors.add("type");
//		seletors.add("flag");
//		seletors.add("isEnterDB");
//		seletors.add("isCostAccount");
//		seletors.add("codingNumber");
//		seletors.add("isCanAdd");
//		seletors.add("createOrg");
//		seletors.add("isLeaf");
//		seletors.add("level");
//		seletors.add("displayName");
//		seletors.add("fullOrgUnit.id");
//		seletors.add("UC.id");
		seletors.add("*");
		seletors.add("stageEntrys.*");
		seletors.add("parent.isAssigned");
		for (int i = 0; i < costAccountCollection.size(); i++) {
			cai = getCostAccountInfo(ctx, new ObjectUuidPK(costAccountCollection.get(i).getId().toString()), seletors);
			// TODO 去掉重复
			// 如果程编码已经在 跳过 改成本科目引入
			if (acMap.containsKey(cai.getLongNumber())) {
				continue;
			}

			if (cai.getParent() != null) {

				String longNumber = cai.getLongNumber();
				String parentLN = longNumber.substring(0, longNumber.lastIndexOf("!"));
				CostAccountInfo ca = (CostAccountInfo) acMap.get(parentLN);
				if (ca != null) {
					if (ca.getId() != null && ca.isIsLeaf()) {
						_isReferenced(ctx, new ObjectStringPK(ca.getId().toString()));
					}
				} else {
					CostAccountCollection coll = getCostAccountCollection(ctx, "select id,isleaf where longNumber = '"
							+ cai.getLongNumber().substring(0, cai.getLongNumber().lastIndexOf('!')) + "' and curProject.id = '"
							+ addressId.toString() + "'");
					ca = coll.get(0);
				}

				// 如果源项目中上级科目存在，而目标项目中不存在，则跳过此科目，引入下一个
				if (ca == null) {
					continue;
				}

				//cai.setIsEnabled(ca.isIsEnabled());
				// cai.setIsEnabled(false);
				// 上级科目换成新项目中的新上级ID
				cai.setParent(ca);

				if (ca.isIsLeaf()) {
					String sql = "update T_FDC_CostAccount set  FIsLeaf = ?  where fid = ? ";
					Object[] params = new Object[] { Boolean.valueOf(false), ca.getId().toString() };
					DbUtil.execute(ctx, sql, params);

					ca.setIsLeaf(false);
					acMap.put(parentLN, ca);
				}
			}
//				else {
//			//	cai.setIsEnabled(false);
			//			}		

			cai.setIsSource(true);
			cai.setCurProject(cpi);
			cai.setFullOrgUnit(null);
			cai.setId(null);
			AccountStageCollection stageEntrys = cai.getStageEntrys();
			
		   List params  =  new  ArrayList();
			for(int j  = 0 ,jSize = stageEntrys.size();j<jSize;j++ ){
				List list = new ArrayList();
				AccountStageInfo accountStageInfo = stageEntrys.get(j);
				accountStageInfo.setId(null);
				stageEntrys.set(j, accountStageInfo);
				list.add(accountStageInfo.getMeasureStage().getId().toString());
				list.add(accountStageInfo.isValue()?"1":"0");
				list.add(BOSUuid.create("476BD8C3").toString());
				params.add(list);
				
			}
			IObjectPK addnewPK= super._addnew(ctx, cai);
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			for(int j = 0,jSize =params.size();j<jSize;j++ ){
				    List p =  (List) params.get(j);
			       p.add(addnewPK.toString());
			       params.set(j, p);
				
			}
			StringBuffer sql =  new StringBuffer("insert into T_FDC_AccountStage (FMeasureStageID,FVAlue,FId,FCostAccountID) values (?,?,?,?)");
			builder.executeBatch(sql.toString(), params);
		}
	}

	protected ArrayList _importTemplateDatas(Context ctx, IObjectCollection cac) throws BOSException, EASBizException {
		ArrayList al = new ArrayList();
		CostAccountCollection costAccountCollection = (CostAccountCollection) cac;
		CostAccountInfo cai;
		FullOrgUnitInfo foui = new FullOrgUnitInfo();
		foui.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		CtrlUnitInfo cui = new CtrlUnitInfo();
		cui.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));

		if (costAccountCollection.size() != 0) {
			for (int i = 0; i < cac.size(); i++) {
				cai = getCostAccountInfo(ctx, new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
				cai.setCurProject(null);
				cai.setFullOrgUnit(foui);
				cai.setAssigned(false);//
				cai.setIsSource(true);
				cai.setIsEnabled(false);
				cai.setCU(cui);
				cai.setId(null);
				//引入模板创建组织为集团
				cai.setCreateOrg(foui);
				
				if (cai.getParent() != null) {
					// 父结点转移
					String ln = cai.getLongNumber().substring(0, cai.getLongNumber().lastIndexOf('!'));
					String sql = "select FID from t_fdc_costaccount where flongNumber = ?  and ffullOrgUnit = ? ";
					Object[] params = new Object[] { ln, OrgConstants.DEF_CU_ID };
					RowSet rs = DbUtil.executeQuery(ctx, sql, params);
					try {
						if (rs.next()) {
							CostAccountInfo parent = new CostAccountInfo();
							parent.setId(BOSUuid.read(rs.getString("FID")));
							cai.setParent(parent);
							if (has(ctx, cai)) {
								al.add(cai.getLongNumber().replace('!', '.'));
							} else {
								super._addnew(ctx, cai);
							}
						} 
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new BOSException(e);
					}
				} else {
					if (has(ctx, cai)) {
						al.add(cai.getLongNumber().replace('!', '.'));
					} else {
						super._addnew(ctx, cai);
					}
				}
			}
		}
		return al;
	}

	protected boolean has(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// 父节点为空时检查根对象编码是否重复。
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent, null, CompareType.EQUALS));
			if (((CostAccountInfo) treeModel).getCurProject() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("curproject.id", ((CostAccountInfo) treeModel).getCurProject().getId().toString(), CompareType.EQUALS));
			} else if (((CostAccountInfo) treeModel).getFullOrgUnit() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeModel).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
			}
			filter.setMaskString("#0 and #1 and #2 ");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1 and #2 and #3 ");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent, treeModel.innerGetParent().getId().toString(), CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		// CU隔离
		// FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		// if (FilterUtility.hasFilterItem(filterCU)) {
		// if (FilterUtility.hasFilterItem(filter)) {
		// filter.mergeFilter(filterCU, "AND");
		// } else {
		// filter = filterCU;
		// }
		// }
		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);
		if (results != null && results.size() > 0) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * 清除前后空格
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model) {
		CostAccountInfo theModel = (CostAccountInfo) model;
		if (theModel.getNumber() != null) {
			theModel.setNumber(theModel.getNumber().trim());
		}
		if (theModel.getName() != null) {
			theModel.setName(theModel.getName().trim());
		}
		if (theModel.getDescription() != null) {
			theModel.setDescription(theModel.getDescription().trim());
		}

		// 冗余编码
		String lgNumber = theModel.getLongNumber();
		if (lgNumber != null) {
			lgNumber = lgNumber.replace('!', '.');
		}
		theModel.setCodingNumber(lgNumber);
		return model;
	}

	private void updateParentStageToChecked() {

	}
	
	/**
	 * 保存可拆分科目状态
	 * 
	 * @author beyondsoft 向晓帆
	 * @param ids
	 *            id集合
	 * @param isSplit
	 *            状态逻辑判断（是/否）可拆分
	 */
	protected void _saveIsSplitCostAccount(Context ctx, List ids, boolean isSplit) throws BOSException {
		// 获取数据库操作对象
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 申明将要拼装的ids
		StringBuffer idsStr = new StringBuffer();
		// 申明sql语句
		String sql = null;
		/********* 拼装id字符串 begin **************/
		// 遍历id集合拼装字符串
		for (int i = 0; i < ids.size(); i++) {
			idsStr.append("'" + ids.get(i) + "',");
		}
		// 截取要使用的字符
		String newIds = null;
		if (idsStr.length() > 0) {
			newIds = idsStr.substring(0, idsStr.length() - 1);
		}
		/********* 拼装id字符串 end **************/

		// 如果状态为否，则是不勾选可拆分成本，将状态设置为0
		if (isSplit == false) {
			sql = "update T_FDC_CostAccount set FIsSplit = 0 where FID in (" + newIds + ")";
		} else {// 如果状态为否，则是勾选可拆分成本，将状态设置为1
			sql = "update T_FDC_CostAccount set FIsSplit = 1 where FID in (" + newIds + ")";
		}
		builder.appendSql(sql);
		builder.executeUpdate();
		
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param arrayPK
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-21
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK[])
	 */
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._delete(ctx, arrayPK);

		// 批量删除 成本科目与测算关系对照表（中间表）
		deleteAccountStage(ctx);
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param filter
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-21
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.metadata.entity.FilterInfo)
	 */
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		IObjectPK[] pkArr = super._delete(ctx, filter);

		// 批量删除 成本科目与测算关系对照表（中间表）
		deleteAccountStage(ctx);

		return pkArr;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// R140504-0191：长房项目宕机性能优化 by skyiter_wang 2014-05-21
	// ////////////////////////////////////////////////////////////////////////////////
	// ============ start

	/**
	 * 描述：批量删除 成本科目与测算关系对照表（中间表）
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-21
	 */
	protected void deleteAccountStage(Context ctx) throws BOSException, EASBizException {
		// 批量删除 成本科目与测算关系对照表（中间表）

		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	DELETE FROM T_FDC_AccountStage WHERE 		\r\n");
		sqlSb.append("	NOT EXISTS		\r\n");
		sqlSb.append("	(SELECT sub.fid FROM T_FDC_CostAccount sub WHERE sub.FID = T_FDC_AccountStage.FCostAccountId )		\r\n");

		DbUtil.execute(ctx, sqlSb.toString());
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param view
	 * @return
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountControllerBean#getCostAccountCollection(com.kingdee.bos.Context, com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	public CostAccountCollection getCostAccountCollection(Context ctx, EntityViewInfo view) throws BOSException {
		CostAccountCollection costAccountCollection = super.getCostAccountCollection(ctx, view);

		// 打印 成本科目集合 日志
		loggerInfoCostAccountCollection(costAccountCollection);

		return costAccountCollection;
	}

	/**
	 * 描述：
	 * 
	 * @param ctx
	 * @param oql
	 * @return
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountControllerBean#getCostAccountCollection(com.kingdee.bos.Context, java.lang.String)
	 */
	public CostAccountCollection getCostAccountCollection(Context ctx, String oql) throws BOSException {
		CostAccountCollection costAccountCollection = super.getCostAccountCollection(ctx, oql);

		// 打印 成本科目集合 日志
		loggerInfoCostAccountCollection(costAccountCollection);

		return costAccountCollection;
	}

	/**
	 * 描述：打印 成本科目集合 日志
	 * 
	 * @param costAccountCollection
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-22
	 */
	private void loggerInfoCostAccountCollection(CostAccountCollection costAccountCollection) {
		int size = 0;
		int firstStageEntrysSize = 0;
		int totalStageEntrysSize = 0;

		if (FdcObjectCollectionUtil.isNotEmpty(costAccountCollection)) {
			size = costAccountCollection.size();
			firstStageEntrysSize = costAccountCollection.get(0).getStageEntrys().size();
		}

		if (size < 10000) {
			CostAccountInfo costAccountInfo = null;
			int stageEntrysSize = 0;
			for (int i = 0; i < size; i++) {
				costAccountInfo = costAccountCollection.get(i);
				stageEntrysSize = costAccountInfo.getStageEntrys().size();
				totalStageEntrysSize += stageEntrysSize;
			}
		} else {
			totalStageEntrysSize = size * firstStageEntrysSize;
		}

		String methodName = System.currentTimeMillis() + ":CostAccountControllerBean.loggerInfoCostAccountCollection()";
		logger.info("======================" + methodName + ", costAccountCollection.size():" + size);
		logger.info("======================" + methodName + ", costAccountCollection.get(0).getStageEntrys().size():" + firstStageEntrysSize);
		logger.info("======================" + methodName + ", costAccountCollection.get(i).getStageEntrys().size(), total:" + totalStageEntrysSize);
	}

	// ============ end
	// ////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////
	
}