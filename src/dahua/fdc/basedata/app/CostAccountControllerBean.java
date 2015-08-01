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
 * ����:�ɱ���Ŀ
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
		// ������ȥ����Ҫ�����޸�,�¼��ڵ���벻�ܳ��ֶϲ�
		if (newCai.isIsSource()) {
			// �µĳ����롢���͡��Ƿ�ɱ���Ŀ
			String newLn = newCai.getLongNumber();
			String newName = newCai.getName();
			String newType = newCai.getType() == null ? "" : newCai.getType().getValue();
			// �Ƿ�������һ������Ҫ
			boolean isUpdateLevel = newLn.indexOf('!') != -1;
			Integer iCostAccount = newCai.isIsCostAccount() ? Integer.valueOf("1") : Integer.valueOf("0");
			String desc = newCai.getDescription();
			// δ����֮ǰ,��ȡ��ԭ���ĳ����롢���͡��Ƿ�ɱ���Ŀ
			CostAccountInfo oldCai = CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(pk);
			String oldLn = oldCai.getLongNumber();
			String oldName = oldCai.getName();

			// ���µ�ǰ��Ŀ
			super._update(ctx, pk, model);
			// ��ȡ��ǰ��Ŀ�ڵ�ǰ��֯����Ŀ�������¼���ĿID
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

			// ͬ�����µ�ǰ��Ŀ���䵽�¼���֯����Ŀ�п�Ŀ�ĳ����롢���롢���ơ����͡��Ƿ�ɱ���Ŀ������ ����
			params = null;
			sql =  "update T_FDC_CostAccount set FLongNumber=?,FNumber=?,FName_l2=?,FType=?,FIsCostAccount=?,FDescription_l2=? where fsrccostaccountid = ?";	
			params = new Object[]{newLn,newCai.getNumber(),newCai.getName(),newType,iCostAccount, desc,oldCai.getId().toString()};
			DbUtil.execute(ctx, sql, params);

			FDCSQLBuilder builder = new FDCSQLBuilder();
			if (set.size() > 0) {
				// �����¼�������һ����ͬʱʹ�ô�if����ͬ�ϼ����룬�������Բ�һ�µĲ�Ҫ�ڴ˸������� by hpw 2012.2.15
				// �޸ĵ�ǰ��֯����Ŀ�������¼���Ŀ�ĳ����롢���͡��Ƿ�ɱ���Ŀ
				builder
						.appendSql("update T_FDC_CostAccount set FLongNumber=REPLACE(FLongNumber,?,?),FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				// builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fid", set.toArray(), "varchar(44) ");
				builder.executeUpdate(ctx);

				// ͬ�����������¼���Ŀ���䵽�¼���֯����Ŀ�п�Ŀ�ĳ����롢���͡��Ƿ�ɱ���Ŀ
				builder.clear();
				builder
						.appendSql("update T_FDC_CostAccount set  FLongNumber=REPLACE(FLongNumber,?,?),FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				// builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fsrccostaccountid", set.toArray(), "varchar(44) ");
				builder.executeUpdate(ctx);
				
				// һ����Ŀʱ������
				if (isUpdateLevel) {
					// �޸ĵ�ǰ��֯����Ŀ�������¼���Ŀ������
					builder.appendSql("update T_FDC_CostAccount set FType=? where ");
					builder.addParam(newType);
					builder.appendParam("fid", set.toArray(), "varchar(44) ");
					builder.executeUpdate(ctx);

					// ͬ�����������¼���Ŀ���䵽�¼���֯����Ŀ�п�Ŀ������
					builder.clear();
					builder.appendSql("update T_FDC_CostAccount set FType=? where ");
					builder.addParam(newType);
					builder.appendParam("fsrccostaccountid", set.toArray(), "varchar(44) ");
					builder.executeUpdate(ctx);
				}
			}
			
		}

		// ������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
		String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx, sqlNew);

		String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx, sqlOld);
	}

	// �����¼��ĳ����� 5001!02!01
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
	
		// ���ŷ����Ŀ��ʹ����һ��ʱ�䣬���Ҫ������Ŀ������ʱ��Ҫ����¼���˾��������Ŀ�Ƿ��ѽ�����ͬ�Ŀ�Ŀ����Ŀ������ͬ����
		if ((costAccountInfo.getFullOrgUnit() != null)) {// ����֯�ڵ�
			// ��ǰҪ�����Ŀ�Ŀ�ǹ��ڼ����ϵ�
			if (OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString())) {
				if (costAccountInfo.getParent() != null) {// �������ӽڵ�
					costAccountInfo.setIsEnabled(costAccountInfo.getParent().isIsEnabled());
				}
			}
		}

		// �ɱ���Ŀ������ϵ���ձ��м��
		AccountStageCollection accountStageCollection = new AccountStageCollection();
		
		// ��ǰ��Ŀ�׶�ѡ��
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
				// sp1��������Ĭ�����н׶ζ�ѡ��
				asInfo.setValue(true);
				
				// costAccountInfo.getStageEntrys().add(asInfo);
				asInfo.setCostAccount(costAccountInfo);
				
				accountStageCollection.add(asInfo);
				stageIdSet.add(mcInfo.getId().toString());
			}
		} catch (BOSException e1) {
			throw new BOSException(e1);
		}

		// �ϼ���Ŀ�׶�ѡ��
		if (costAccountInfo.getParent() != null) {

			FilterInfo mergeFilter = new FilterInfo();
			if (costAccountInfo.getFullOrgUnit() != null) {
				String orgFullUnitId = costAccountInfo.getFullOrgUnit().getId().toString();
				mergeFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgFullUnitId));
			} else {
				String curProjectId = costAccountInfo.getCurProject().getId().toString();
				mergeFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectId));
			}

			// ȡ��ǰ��Ŀ�������ϼ�
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

			// �����ϼ���Ŀ�׶�ѡ��ֵ
			if (costAccountSet.size() > 0 && stageIdSet.size() > 0) {
				builder.clear();
				builder.appendSql("update T_FDC_AccountStage set fvalue = 1 where ");
				builder.appendParam("FMeasureStageID", stageIdSet.toArray(), "varchar(44)");
				builder.appendSql(" and ");
				builder.appendParam("FCostAccountID", costAccountSet.toArray(), "varchar(44)");
				builder.executeUpdate();
			}
			// �̳и��ڵ������Ȩ��
			costAccountInfo.setIsCanAdd(costAccountInfo.getParent().isIsCanAdd());

		}

		String sql = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx, sql);

		String sqlOR = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx, sqlOR);
		//�жϵ�ǰ�Ƿ����ϸ�����¼�������ǣ����¶Զ����¼�ID���л����� by hpw
		boolean isLeafAdd = false;
		if(costAccountInfo.getParent()!=null&&costAccountInfo.getParent().isIsLeaf()){
			//super
			isLeafAdd=true;
		}
		
		// accountStageCollection = costAccountInfo.getStageEntrys();
		IObjectPK pk = super._addnew(ctx, costAccountInfo);

		// ////////////////////////////////////////////////////////////////////////////////
		// R140504-0191��������Ŀ崻������Ż� by skyiter_wang 2014-05-21
		// ////////////////////////////////////////////////////////////////////////////////
		// ============ start

		// �������� �ɱ���Ŀ������ϵ���ձ��м��
		CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
		coreBaseCollection.addObjectCollection(accountStageCollection);
		// EAS750֮ǰ�����滻���Զ��幤����FdcORMappingDAOUtil
		// AccountStageFactory.getLocalInstance(ctx).addnewBatchData(coreBaseCollection);
		FdcORMappingDAOUtil.addnewBatchData(ctx, getConnection(ctx), AccountStageFactory.getLocalInstance(ctx), coreBaseCollection);
		costAccountInfo.getStageEntrys().addObjectCollection(coreBaseCollection);

		// ============ end
		// ////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////
		
		// ����ʱͬʱҲҪ������Ӧ��ϵ
		Map param = new HashMap();
		Set set = new HashSet();
		set.add(pk.toString());
		param.put("acctIdSet", set);
		CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
					
		//bug: ��ϸ��Ŀ�����¼���Ŀ�Ĵ���,�������ѶԸ���isLeaf�ֶν�����false����  by hpw
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
			 * ���������ϸ��ĿΪԴ�Ŀ�Ŀ��Դ�ֹ�
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
				// ����ϼ�������,������ʾ������
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
			// 4301.401.01.01�����ϼ����������Ŀ�Ŀ�����ϼ����õ��ÿ�Ŀ���¼����ú��ϼ���֯�ĸÿ�Ŀû���Զ�����
			if (costAccountInfo.getSrcCostAccountId() != null) {
				DbUtil.execute(ctx, "update T_FDC_CostAccount set  FIsEnable = 1 where fid = ?",
						new Object[] { costAccountInfo.getSrcCostAccountId() });
			}
		}
		return flag;
	}

	protected boolean _disable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// _isReferenced(ctx, pk);

		// if (this.checkIsUsed(ctx, pk)) {// ���ڵ����ж�����
		// throw new
		//com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException
		// .DISENABLE_CANNOT_USED);
		// }
//		// �޸ı�� 2011-5-21 ȡ���ӽڵ�Ψһһ�������õ� ������ ���� by cj
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
		// ������¼�,���ж��Լ�,���ж��¼�
		if (costAccountCollection.size() > 0) {
			// �ж��Լ�
			if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))
					|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + PK.toString() + "'"))) {
				// || (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() +
				// "'"))) {
				return true;
			} else {
				// �ж��¼�
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
			// ���û���¼�,�ж��Լ�
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
			// ����Ǽ����µĽ��ò���,ǰ���Ѿ�У����,�˴�ͨ����У��,ֱ��sql���������Ҫ��¼bug,���˵Ҫ���������20061124
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
			// ������֯�򹤳���Ŀ�ܿ���������¼��Ĵ���
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
			// ����Ǽ����µ����ò������������з�����ȥ��
			String longNumber = costAccountInfo.getLongNumber();
			String sql = "update T_FDC_CostAccount set  FIsEnable = 1  where FLongNumber like '" + longNumber + "%'";
			DbUtil.execute(ctx, sql);
		} else {
			// ������Ŀ�����¼�
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountInfo.getId().toString()));
			evi.setFilter(filter);
			CostAccountCollection costAccountCollection = getCostAccountCollection(ctx, evi);
			// ������¼�,Ҫͬʱ����/�����¼�
			if (costAccountCollection.size() > 0) {
				// ������/�����Լ�
				
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
				
				// ������/�����¼�
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
				// ���û���¼�
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
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_DynCostCtrlEntryItems A 		\r\n");// �ɱ����Ƶ�
		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
		checkExistsSql.append("where  B.Fid = ? 		\r\n");
		checkExistsSql.append("UNION 		\r\n");
		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_AimCostCtrlCostActItems A 		\r\n");// Ŀ��ɱ����Ƶ�
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
		// ����¼��Ƿ����ͬ������

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// ���ڵ�Ϊ��ʱ������������Ƿ��ظ���
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
			// ��֯��,��Ŀ�µĸ��� by tim_gao
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
		// CU����
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
	 * Ĭ��ʵ�ֶ����� ͬ�ĳ����룬��ID��ͬ���д�������ɰ���Ҫ����ʵ�֡�
	 * 
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		// ������ݿ������ͬlongNumber��ID��ͬ�����ݣ����쳣��
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

		// CU����
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
			// ������ڼ����½���,��Ҫ�ж��¼������ѷ�����ȥ�Ŀ�Ŀ,�Ƿ��б����õ�,
			// ���������,ֹͣ
			// ���û������,����,������ϵͳ������(������������ȥ��)

			// Ѱ�ҵ�ǰ�ڵ�(����)�µ��¼���Ŀ
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
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_DynCostCtrlEntryItems A 		\r\n");// �ɱ����Ƶ�
			checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
			checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
			checkExistsSql.append("UNION 		\r\n");
			checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_AimCostCtrlCostActItems A 		\r\n");// Ŀ��ɱ����Ƶ�
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
			// R140504-0191��������Ŀ崻������Ż� by skyiter_wang 2014-05-21
			// ////////////////////////////////////////////////////////////////////////////////
			// ============ start

			// ����ɾ���ɱ���Ŀ������ϵ���ձ��м��

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

		CostAccountCollection costAccountCollection = (CostAccountCollection) cac;// ����ĳɱ���Ŀ����
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
			// TODO ȥ���ظ�
			// ����̱����Ѿ��� ���� �ĳɱ���Ŀ����
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

				// ���Դ��Ŀ���ϼ���Ŀ���ڣ���Ŀ����Ŀ�в����ڣ��������˿�Ŀ��������һ��
				if (ca == null) {
					continue;
				}

				//cai.setIsEnabled(ca.isIsEnabled());
				// cai.setIsEnabled(false);
				// �ϼ���Ŀ��������Ŀ�е����ϼ�ID
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
				//����ģ�崴����֯Ϊ����
				cai.setCreateOrg(foui);
				
				if (cai.getParent() != null) {
					// �����ת��
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
		// ���ڵ�Ϊ��ʱ������������Ƿ��ظ���
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
		// CU����
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
	 * ���ǰ��ո�
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

		// �������
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
	 * ����ɲ�ֿ�Ŀ״̬
	 * 
	 * @author beyondsoft ������
	 * @param ids
	 *            id����
	 * @param isSplit
	 *            ״̬�߼��жϣ���/�񣩿ɲ��
	 */
	protected void _saveIsSplitCostAccount(Context ctx, List ids, boolean isSplit) throws BOSException {
		// ��ȡ���ݿ��������
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ������Ҫƴװ��ids
		StringBuffer idsStr = new StringBuffer();
		// ����sql���
		String sql = null;
		/********* ƴװid�ַ��� begin **************/
		// ����id����ƴװ�ַ���
		for (int i = 0; i < ids.size(); i++) {
			idsStr.append("'" + ids.get(i) + "',");
		}
		// ��ȡҪʹ�õ��ַ�
		String newIds = null;
		if (idsStr.length() > 0) {
			newIds = idsStr.substring(0, idsStr.length() - 1);
		}
		/********* ƴװid�ַ��� end **************/

		// ���״̬Ϊ�����ǲ���ѡ�ɲ�ֳɱ�����״̬����Ϊ0
		if (isSplit == false) {
			sql = "update T_FDC_CostAccount set FIsSplit = 0 where FID in (" + newIds + ")";
		} else {// ���״̬Ϊ�����ǹ�ѡ�ɲ�ֳɱ�����״̬����Ϊ1
			sql = "update T_FDC_CostAccount set FIsSplit = 1 where FID in (" + newIds + ")";
		}
		builder.appendSql(sql);
		builder.executeUpdate();
		
	}

	/**
	 * ������
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

		// ����ɾ�� �ɱ���Ŀ������ϵ���ձ��м��
		deleteAccountStage(ctx);
	}

	/**
	 * ������
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

		// ����ɾ�� �ɱ���Ŀ������ϵ���ձ��м��
		deleteAccountStage(ctx);

		return pkArr;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// R140504-0191��������Ŀ崻������Ż� by skyiter_wang 2014-05-21
	// ////////////////////////////////////////////////////////////////////////////////
	// ============ start

	/**
	 * ����������ɾ�� �ɱ���Ŀ������ϵ���ձ��м��
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-21
	 */
	protected void deleteAccountStage(Context ctx) throws BOSException, EASBizException {
		// ����ɾ�� �ɱ���Ŀ������ϵ���ձ��м��

		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	DELETE FROM T_FDC_AccountStage WHERE 		\r\n");
		sqlSb.append("	NOT EXISTS		\r\n");
		sqlSb.append("	(SELECT sub.fid FROM T_FDC_CostAccount sub WHERE sub.FID = T_FDC_AccountStage.FCostAccountId )		\r\n");

		DbUtil.execute(ctx, sqlSb.toString());
	}

	/**
	 * ������
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

		// ��ӡ �ɱ���Ŀ���� ��־
		loggerInfoCostAccountCollection(costAccountCollection);

		return costAccountCollection;
	}

	/**
	 * ������
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

		// ��ӡ �ɱ���Ŀ���� ��־
		loggerInfoCostAccountCollection(costAccountCollection);

		return costAccountCollection;
	}

	/**
	 * ��������ӡ �ɱ���Ŀ���� ��־
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