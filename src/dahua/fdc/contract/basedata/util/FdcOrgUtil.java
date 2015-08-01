/**
 * @(#)FdcOrgUtil.java 1.0 2014-1-13
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.mycompany.internal.edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 描述：房地产组织单元工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-1-13
 * @version 1.0, 2014-1-13
 * @see
 * @since JDK1.4
 */
public class FdcOrgUtil {

	/**
	 * 描述：取得工程项目对应的成本中心
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @param curProjectInfo
	 *            工程项目；非空
	 * @return
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2014-1-13
	 */
	public static CostCenterOrgUnitInfo getCostCenterOrgUnit(Context ctx, CurProjectInfo curProjectInfo)
			throws BOSException {
		CostCenterOrgUnitInfo costCenterOrgUnitInfo = null;
		if (null == curProjectInfo) {
			return null;
		}
		
		/* modified by zhaoqin for R140424-0192 on 2014/05/28 start */
		// 取当前工程项目及其所有上级的长编码集合,通过长编码查到当前工程项目对应的成本中心
		String longNumber = curProjectInfo.getLongNumber();
		Set longNumbers = new HashSet();
		longNumbers.add(longNumber);
		int lastIndex = longNumber.lastIndexOf("!");
		while(lastIndex != -1) {
			longNumber = longNumber.substring(0, lastIndex);
			longNumbers.add(longNumber);
			lastIndex = longNumber.lastIndexOf("!");
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", longNumbers, CompareType.INCLUDE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemInfo sort = new SorterItemInfo("curProject.longNumber");
		sort.setSortType(SortType.DESCEND);
		view.getSorter().add(sort);
		/* modified by zhaoqin for R140424-0192 on 2014/05/28 end */

		view.getSelector().add("costCenterOU.id");
		view.getSelector().add("costCenterOU.number");
		view.getSelector().add("costCenterOU.longNumber");
		view.getSelector().add("costCenterOU.name");
		view.getSelector().add("costCenterOU.CU.id");
		// view.getSelector().add("costCenterOU.partFI.unit.id");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.number");
		view.getSelector().add("curProject.longNumber");
		view.getSelector().add("curProject.name");

		IProjectWithCostCenterOU iProjectWithCostCenterOU = getIProjectWithCostCenterOUInstacne(ctx);
		ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = iProjectWithCostCenterOU
				.getProjectWithCostCenterOUCollection(view);
		if (FdcObjectCollectionUtil.isEmpty(projectWithCostCenterOUCollection)) {
			return null;
		}

		//String longNumber = curProjectInfo.getLongNumber();
		// 工程项目具有层级(根据长编码来判断)
		// 成本中心与工程项目的对应关系，可以使用长编码模糊匹配
		CurProjectInfo tempProjectInfo = null;
		String tempLongNumber = null;
		ProjectWithCostCenterOUInfo projectWithCostCenterOUInfo = null;
		for (int i = 0, size = projectWithCostCenterOUCollection.size(); i < size; i++) {
			projectWithCostCenterOUInfo = (ProjectWithCostCenterOUInfo) projectWithCostCenterOUCollection.get(i);
			tempLongNumber = projectWithCostCenterOUInfo.getCurProject().getLongNumber();
			if (longNumber.startsWith(tempLongNumber)) {
				if (tempProjectInfo == null) {
					tempProjectInfo = projectWithCostCenterOUInfo.getCurProject();
					costCenterOrgUnitInfo = projectWithCostCenterOUInfo.getCostCenterOU();
				} else {
					if (tempLongNumber.startsWith(tempProjectInfo.getLongNumber())) {
						tempProjectInfo = projectWithCostCenterOUInfo.getCurProject();
						costCenterOrgUnitInfo = projectWithCostCenterOUInfo.getCostCenterOU();
					}
				}

				if (null != costCenterOrgUnitInfo) {
					break;
				}
			}
		}

		return costCenterOrgUnitInfo;
	}

	/**
	 * 描述：取得 工程项目与成本中心的对应关系 接口实例
	 * 
	 * @param ctx
	 *            应用上下文；可空
	 * @return
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2014-1-13
	 */
	public static IProjectWithCostCenterOU getIProjectWithCostCenterOUInstacne(Context ctx) throws BOSException {
		IProjectWithCostCenterOU iProjectWithCostCenterOU = null;

		if (null != ctx) {
			iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getLocalInstance(ctx);
		} else {
			iProjectWithCostCenterOU = ProjectWithCostCenterOUFactory.getRemoteInstance();
		}

		return iProjectWithCostCenterOU;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得有工程项目的成本中心ID集合
	 * 
	 * @param ctx
	 * @param orgUnitInfo
	 * @param userInfo
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static Set getHasProjectCostCenterIdSet(Context ctx, OrgUnitInfo orgUnitInfo, UserInfo userInfo) throws BOSException, SQLException {
		Set idSet = new LinkedHashSet();

		String orgLongNumber = orgUnitInfo.getLongNumber();
		String userId = userInfo.getId().toString();
		// Object[] paramArr = new Object[] { orgLongNumber, userId };
		Object[] paramArr = new Object[] { userId };
		List paramList = Arrays.asList(paramArr);

		// 取得有工程项目的成本中心ID的SQL
		String sql = getHasProjectCostCenterIdSql(orgLongNumber);
		// sql = sql.replaceFirst("\\@\\?", orgLongNumber);
		IFDCSQLFacade fDCSQLFacade = getFDCSQLFacade(ctx);
		
		IRowSet rs = fDCSQLFacade.executeQuery(sql, paramList);
		while (rs.next()) {
			String orgId = rs.getString("orgId");

			idSet.add(orgId);
		}
		
		return idSet;
	}

	/**
	 * 描述：取得有工程项目的成本中心ID的SQL
	 * 
	 * @param orgUnitInfo
	 * @param userInfo
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static String getHasProjectCostCenterIdSql(OrgUnitInfo orgUnitInfo, UserInfo userInfo) {
		StringBuffer sqlSb = new StringBuffer();

		String orgLongNumber = orgUnitInfo.getLongNumber();
		String userId = userInfo.getId().toString();

		sqlSb.append("	SELECT DISTINCT org.FID AS orgId	\r\n");
		sqlSb.append("	  FROM T_Org_BaseUnit org	\r\n");
		sqlSb.append("	 INNER JOIN T_PM_OrgRangeIncludeSubOrg orgRange	\r\n");
		sqlSb.append("	    ON orgRange.FOrgId = org.FID	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_CurProject pro	\r\n");
		sqlSb.append("	    ON pro.FFullOrgUnit = org.FID	\r\n");
		sqlSb.append("	 WHERE org.FLongNumber LIKE '" + orgLongNumber + "%'	\r\n");
		sqlSb.append("	   AND org.FIsCostOrgUnit = 1	\r\n");
		sqlSb.append("	   AND orgRange.FUserId = '" + userId + "'	\r\n");

		String sql = sqlSb.toString();

		return sql;
	}
	
	/**
	 * 描述：取得有工程项目的成本中心ID的SQL
	 * 
	 * @param orgLongNumber
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static String getHasProjectCostCenterIdSql(String orgLongNumber) {
		StringBuffer sqlSb = new StringBuffer();
		
		sqlSb.append("	SELECT DISTINCT org.FID AS orgId	\r\n");
		sqlSb.append("	  FROM T_Org_BaseUnit org	\r\n");
		sqlSb.append("	 INNER JOIN T_PM_OrgRangeIncludeSubOrg orgRange	\r\n");
		sqlSb.append("	    ON orgRange.FOrgId = org.FID	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_CurProject pro	\r\n");
		sqlSb.append("	    ON pro.FFullOrgUnit = org.FID	\r\n");
		sqlSb.append("	 WHERE org.FLongNumber LIKE '" + orgLongNumber + "%'	\r\n");
		sqlSb.append("	   AND org.FIsCostOrgUnit = 1	\r\n");
		sqlSb.append("	   AND orgRange.FUserId = ?	\r\n");
		
		String sql = sqlSb.toString();

		return sql;
	}

	
	/**
	 * 描述：取得角色范围内组织ID的SQL
	 * 
	 * @param orgLongNumber
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static String getAuthorizedOrgsSql(OrgUnitInfo orgUnitInfo, UserInfo userInfo) {
		StringBuffer sqlSb = new StringBuffer();

		String orgLongNumber = orgUnitInfo.getLongNumber();
		String userId = userInfo.getId().toString();

		sqlSb.append("	SELECT DISTINCT org.FID AS orgId	\r\n");
		sqlSb.append("	  FROM T_Org_BaseUnit org	\r\n");
		sqlSb.append("	 INNER JOIN T_PM_OrgRangeIncludeSubOrg orgRange	\r\n");
		sqlSb.append("	    ON orgRange.FOrgId = org.FID	\r\n");
		sqlSb.append("	 WHERE org.FLongNumber LIKE '" + orgLongNumber + "%'	\r\n");
		sqlSb.append("	   AND org.FIsCostOrgUnit = 1	\r\n");
		sqlSb.append("	   AND orgRange.FUserId = '" + userId + "'	\r\n");
		
		String sql = sqlSb.toString();
		
		return sql;
	}

	/**
	 * 描述：取得角色范围内组织ID的SQL
	 * 
	 * @param orgLongNumber
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static String getAuthorizedOrgsSql(String orgLongNumber) {
		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	SELECT DISTINCT org.FID AS orgId	\r\n");
		sqlSb.append("	  FROM T_Org_BaseUnit org	\r\n");
		sqlSb.append("	 INNER JOIN T_PM_OrgRangeIncludeSubOrg orgRange	\r\n");
		sqlSb.append("	    ON orgRange.FOrgId = org.FID	\r\n");
		sqlSb.append("	 WHERE org.FLongNumber LIKE '" + orgLongNumber + "%'	\r\n");
		sqlSb.append("	   AND org.FIsCostOrgUnit = 1	\r\n");
		sqlSb.append("	   AND orgRange.FUserId = ?	\r\n");

		String sql = sqlSb.toString();

		return sql;
	}

	/**
	 * 描述：取得当前登录用户
	 * 
	 * @param ctx
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	public static UserInfo getCurrentUserInfo(Context ctx) {
		UserInfo userInfo = null;

		if (null == ctx) {
			userInfo = SysContext.getSysContext().getCurrentUserInfo();
		} else {
			userInfo = ContextUtil.getCurrentUserInfo(ctx);
		}

		return userInfo;
	}

	/**
	 * 描述：取得房地产SQL功能
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2015-2-13
	 */
	private static IFDCSQLFacade getFDCSQLFacade(Context ctx) throws BOSException {
		IFDCSQLFacade fDCSQLFacade = null;
		
		if (null == ctx) {
			fDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			fDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}
		
		return fDCSQLFacade;
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得当前组织
	 * 
	 * @param ctx
	 *            上下文；可以为空
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-8
	 */
	public static OrgUnitInfo getCurrentOrgUnit(Context ctx) {
		OrgUnitInfo currentOrgUnit = getCurrentOrgUnit(ctx, null);

		return currentOrgUnit;
	}

	/**
	 * 描述：取得当前组织
	 * 
	 * @param ctx
	 *            上下文；可以为空
	 * @param orgType
	 *            组织类型；可以为空
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-8
	 */
	public static OrgUnitInfo getCurrentOrgUnit(Context ctx, OrgType orgType) {
		OrgUnitInfo currentOrgUnit = null;

		if (null == ctx) {
			if (null == orgType) {
				currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			} else {
				currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit(orgType);
			}
		} else {
			if (null == orgType) {
				currentOrgUnit = ContextUtil.getCurrentOrgUnit(ctx);
			} else {
				currentOrgUnit = ContextUtil.getCurrentOrgUnit(ctx, orgType);
			}
		}

		return currentOrgUnit;
	}
	
	/**
	 * 描述：当前组织是否为集团组织
	 * 
	 * @param ctx
	 *            上下文；可以为空
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-8
	 */
	public static boolean isGroupOrg(Context ctx) {
		boolean isGroup = isGroupOrg(ctx, null);

		return isGroup;
	}

	/**
	 * 描述：当前组织是否为集团组织
	 * 
	 * @param ctx
	 *            上下文；可以为空
	 * @param orgType
	 *            组织类型；可以为空
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-8
	 */
	public static boolean isGroupOrg(Context ctx, OrgType orgType) {
		boolean isGroup = false;

		OrgUnitInfo currentOrgUnit = getCurrentOrgUnit(ctx, orgType);

		String curOrgId = currentOrgUnit.getId().toString();
		isGroup = FDCConstants.CORP_CU.equals(curOrgId);

		return isGroup;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
}
