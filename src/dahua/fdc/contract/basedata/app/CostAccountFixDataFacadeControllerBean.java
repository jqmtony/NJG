package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class CostAccountFixDataFacadeControllerBean extends AbstractCostAccountFixDataFacadeControllerBean
{
    private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountFixDataFacadeControllerBean");

	protected void _fixOrgSourseID(Context ctx, BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException {

		// 1:获取当前组织被分配的成本科目集合 accountMap<longnumber, CostAccountInfo>
		HashMap accountMap = getOrgCostAccountsMap(ctx, orgID.toString(), false);

		//2:获取上级组织对应的自有科目集合:sourceIdMap<longnumber, CostAccountInfo>
		HashMap sourceIdMap = new HashMap();
		getSourceIDs(ctx, parentOrgList, sourceIdMap, (HashMap) accountMap.clone());
		
		HashMap selfOwnAccMap = getOrgCostAccountsMap(ctx, orgID.toString(), true);
		updateSourceDisplayName(ctx, selfOwnAccMap, sourceIdMap);

		//3：更新sourceID以及名称、长名称
		updateSourceID(ctx, accountMap, sourceIdMap);
	}

	/**
	 * 更新成本科目的sourceID
	 * @param ctx
	 * @param accountMap
	 * @param sourceIdMap
	 * @throws BOSException
	 */
	private void updateSourceID(Context ctx, HashMap accountMap, HashMap sourceIdMap) throws BOSException {
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "update T_FDC_CostAccount set FSrcCostAccountId=?, fname_l2=?, fdisplayname_l2=? where fid=?";
		List params = new ArrayList();
		if (sourceIdMap != null && sourceIdMap.size() > 0) {
			CostAccountInfo accInfo = null;
			CostAccountInfo sourceInfo = null;
			for (Iterator it = sourceIdMap.keySet().iterator(); it.hasNext();) {
				String longnumber = it.next().toString();
				sourceInfo = (CostAccountInfo) sourceIdMap.get(longnumber);
				accInfo = (CostAccountInfo) accountMap.get(longnumber);
				params.add(Arrays.asList(new Object[] { sourceInfo.getId().toString(), sourceInfo.getName(), sourceInfo.getDisplayName(),
						accInfo.getId().toString() }));
			}
		}
		if (params != null && params.size() > 0) {
			builder.executeBatch(sql, params);
		}
	}

	/**
	 * 获取上级组织对应的自有科目集合
	 * @param ctx
	 * @param parentNodeList 父节点的集合
	 * @param sourceIdMap 用来输出的sourceId的Map<longnumber, sourceID>
	 * @param accountMap  组织的被分配的成本科目的Map<longnumber, CostAccountInfo>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void getSourceIDs(Context ctx, List parentNodeList, HashMap sourceIdMap, HashMap accountMap) throws BOSException,
			EASBizException {
		
		if (parentNodeList == null || parentNodeList.size() <= 0) {
			return;
		}

		FullOrgUnitInfo fullOrgUnitInfo = null;
		ProjectInfo projectInfo = null;
		CostAccountInfo accInfo;
		for (int i = 0; i < parentNodeList.size(); i++) {
			if (parentNodeList.get(i) instanceof FullOrgUnitInfo) {

				fullOrgUnitInfo = (FullOrgUnitInfo) parentNodeList.get(i);
				CostAccountCollection parentAccColl = this.getOrgCostAccounts(ctx, fullOrgUnitInfo.getId().toString(), true);
				for (Iterator it = parentAccColl.iterator(); it.hasNext();) {
					accInfo = (CostAccountInfo) it.next();
					if (accountMap.containsKey(accInfo.getLongNumber())) {
						sourceIdMap.put(accInfo.getLongNumber(), accInfo);
						accountMap.remove(accInfo.getLongNumber());
					}
				}
			} else if (parentNodeList.get(i) instanceof ProjectInfo) {

				projectInfo = (ProjectInfo) parentNodeList.get(i);
				CostAccountCollection parentAccColl = this.getPrjCostAccounts(ctx, projectInfo.getId().toString(), true);
				for (Iterator it = parentAccColl.iterator(); it.hasNext();) {
					accInfo = (CostAccountInfo) it.next();
					if (accountMap.containsKey(accInfo.getLongNumber())) {
						sourceIdMap.put(accInfo.getLongNumber(), accInfo);
						accountMap.remove(accInfo.getLongNumber());
					}
				}
			}
		}
	}

	/**
	 * 获取指定组织下已启用的成本科目集合,自有的或者分配得来的!
	 * @param ctx
	 * @param orgPK
	 * @param isSource
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private CostAccountCollection getOrgCostAccounts(Context ctx, String orgPK, boolean isSource) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgPK));
		filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(isSource)));
		view.setFilter(filter);
		SorterItemCollection coll = new SorterItemCollection();
		coll.add(new SorterItemInfo("longNumber"));
		view.setSorter(coll);
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		return costAccountCollection;
	}

	/**
	 * 
	 * @param ctx
	 * @param orgID
	 * @param isSource
	 * @return  accountMap <longnumber, CostAccountInfo>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private HashMap getOrgCostAccountsMap(Context ctx, String orgID, boolean isSource) throws BOSException, EASBizException {
		// 1:获取当前组织被分配的成本科目集合
		CostAccountCollection accountColl = this.getOrgCostAccounts(ctx, orgID.toString(), isSource);

		//collection转换为key，便于查找: accountMap<longnumber, CostAccountInfo>
		HashMap accountMap = new HashMap();
		for (Iterator it = accountColl.iterator(); it.hasNext();) {
			CostAccountInfo tempInfo = (CostAccountInfo) it.next();
			accountMap.put(tempInfo.getLongNumber(), tempInfo);
		}
		return accountMap;
	}

	protected void _fixPrjSourseID(Context ctx, BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException {

		// 1:获取当前工程项目被分配的成本科目集合 accountMap<longnumber, CostAccountInfo>
		HashMap accountMap = getPrjCostAccountsMap(ctx, projectID.toString(), false);

		//2:获取上级组织对应的自有科目集合:sourceIdMap<longnumber, CostAccountInfo>
		HashMap sourceIdMap = new HashMap();
		getSourceIDs(ctx, parentNodeList, sourceIdMap, (HashMap) accountMap.clone());
		
		HashMap selfOwnAccMap = getPrjCostAccountsMap(ctx, projectID.toString(), true);
		updateSourceDisplayName(ctx, selfOwnAccMap, sourceIdMap);

		//3：更新sourceID以及名称、长名称
		updateSourceID(ctx, accountMap, sourceIdMap);
		
		//4:更新下级工程项目
		fixLowerLevelPrj(ctx, projectID, parentNodeList);
	}

	/**
	 * 获取指定工程项目下已启用的成本科目集合,自有的或者分配得来的!
	 * @param ctx
	 * @param projectID
	 * @param isSource
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private CostAccountCollection getPrjCostAccounts(Context ctx, String projectID, boolean isSource) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectID));
		filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(isSource)));
		view.setFilter(filter);
		SorterItemCollection coll = new SorterItemCollection();
		coll.add(new SorterItemInfo("longNumber"));
		view.setSorter(coll);
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		return costAccountCollection;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param orgID
	 * @param isSource
	 * @return  accountMap <longnumber, CostAccountInfo>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private HashMap getPrjCostAccountsMap(Context ctx, String projectID, boolean isSource) throws BOSException, EASBizException {
		// 1:获取当前工程项目被分配的成本科目集合
		CostAccountCollection accountColl = this.getPrjCostAccounts(ctx, projectID, isSource);

		//collection转换为key，便于查找: accountMap<longnumber, CostAccountInfo>
		HashMap accountMap = new HashMap();
		for (Iterator it = accountColl.iterator(); it.hasNext();) {
			CostAccountInfo tempInfo = (CostAccountInfo) it.next();
			accountMap.put(tempInfo.getLongNumber(), tempInfo);
		}
		return accountMap;
	}
	
	private void fixLowerLevelPrj(Context ctx, BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException {
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo projectInfo = iCurProject.getCurProjectInfo(new ObjectStringPK(projectID.toString()));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", projectID));
		view.setFilter(filter);
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
		if (cpc != null && cpc.size() > 0) {
			parentNodeList.add(0, projectInfo);
			CurProjectInfo info = null;
			for (Iterator it = cpc.iterator(); it.hasNext();) {
				info = (CurProjectInfo) it.next();
				_fixPrjSourseID(ctx, info.getId(), parentNodeList);
			}
		}
	}
	
	private void updateSourceDisplayName(Context ctx, HashMap selfOwnAccMap, HashMap sourceIdMap) throws BOSException {
		
		//HashMap<id, displayname>
		HashMap displayNameMap = new HashMap();
		for (Iterator it = selfOwnAccMap.keySet().iterator(); it.hasNext();) {
			String longnumber = it.next().toString();
			CostAccountInfo sourceInfo = (CostAccountInfo) selfOwnAccMap.get(longnumber);
			String fullOrgId = sourceInfo.getFullOrgUnit() == null ? null : sourceInfo.getFullOrgUnit().getId().toString();
			String curprojectid = sourceInfo.getCurProject() == null ? null : sourceInfo.getCurProject().getId().toString();
			String displayName = getNewDisplayName(ctx, longnumber, fullOrgId, curprojectid);
			sourceInfo.setDisplayName(displayName);
			displayNameMap.put(sourceInfo.getId().toString(), displayName);
		}
		
		for (Iterator it = sourceIdMap.keySet().iterator(); it.hasNext();) {
			String longnumber = it.next().toString();
			CostAccountInfo sourceInfo = (CostAccountInfo) sourceIdMap.get(longnumber);
			String fullOrgId = sourceInfo.getFullOrgUnit() == null ? null : sourceInfo.getFullOrgUnit().getId().toString();
			String curprojectid = sourceInfo.getCurProject() == null ? null : sourceInfo.getCurProject().getId().toString();
			String displayName = getNewDisplayName(ctx, longnumber, fullOrgId, curprojectid);
			sourceInfo.setDisplayName(displayName);
			displayNameMap.put(sourceInfo.getId().toString(), displayName);
		}
		
		
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "update T_FDC_CostAccount set FDisplayName_l2 = ? where fid=?";
		List params = new ArrayList();
		if (displayNameMap != null && displayNameMap.size() > 0) {
			for (Iterator it = displayNameMap.keySet().iterator(); it.hasNext();) {
				String id = it.next().toString();
				String displayName = displayNameMap.get(id).toString();
				params.add(Arrays.asList(new Object[] { displayName, id }));
			}
		}
		if (params != null && params.size() > 0) {
			builder.executeBatch(sql, params);
		}
		
	}
	
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
}