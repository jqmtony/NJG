package com.kingdee.eas.fdc.basedata.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.app.dao.ReferenceDAO;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IIncomeAccount;
import com.kingdee.eas.fdc.basedata.IncomeAccountAssignInfo;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.BizReference;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class IncomeAccountFacadeControllerBean extends AbstractIncomeAccountFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.IncomeAccountFacadeControllerBean");
    
  //收入科目insert sql
	//private static String insert_sql = "insert into t_fdc_IncomeAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCIncomeAccountID, FTYPE,FIsIncomeAccount,FCodingNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//private static String update_sql = "update t_fdc_IncomeAccount set FIsLeaf=0 where FID=?";
    private static String insert_sql = "insert into t_fdc_IncomeAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCIncomeAccountID,FCodingNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String update_sql = "update t_fdc_IncomeAccount set FIsLeaf=0 where FID=?";
	/**
	 * ---------------------------------------------以下为以组织为源,组织为目标的分配/反分配处理---------------------------------
	 */
	/**
	 * 描述：分配 基于组织
	 * <p>
	 */
	protected void _assignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {
		
		FDCUtils.lockAssignIncomeAccount(ctx);
		
		// 获取源 = 当前组织下的收入科目集合 + 当前组织被分配的收入科目集合
		IncomeAccountCollection cac = this.getOrgIncomeAccounts(ctx, orgPK.toString());
		// 获取所有目标(下级组织)
		IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(orgPK);
		String longNumber = fullOrgUnitInfo.getLongNumber();
		FullOrgUnitCollection fouc = iFullOrgUnit.getFullOrgUnitCollection("select id where isCompanyOrgUnit = 1 and longNumber like '" + longNumber + "!%'");
		
		String sSql = "SELECT T0.FID, T0.FLONGNUMBER, T0.FFULLORGUNIT,T0.FISENABLE "
			+ " FROM T_FDC_IncomeAccount t0 " 
			+ " INNER JOIN T_ORG_BaseUnit t1 ON t1.fid=t0.FFullOrgUnit "
			+ " WHERE t1.fisCompanyOrgUnit = 1 and t1.flongNumber like '" + longNumber + "!%'";
		
		Map accMap = new HashMap();
		try {
			IRowSet rs = DbUtil.executeQuery(ctx,sSql);
			Map caMap = null;
			String myKey = null;
			while (rs.next()) {
				caMap = new HashMap();
				caMap.put("id",rs.getString("FID"));
				caMap.put("isEnable",Boolean.valueOf(rs.getBoolean("FIsEnable")));
	
				myKey = rs.getString("FFullOrgUnit") + "_" + rs.getString("FLongnumber");
				accMap.put(myKey, caMap);
			}
			
			Set nodeIds = FDCHelper.getIds(fouc.toArray());
			List errorList = insertIncomeAccount(ctx,nodeIds,cac,accMap,null,false);
		} catch (SQLException ex) {
			throw new BOSException(ex);
		} finally {
			accMap.clear();
		}

		String sql = "update t_fdc_IncomeAccount set fAssigned = 1  where ffullOrgUnit = '"
				+ orgPK.toString()
				+ "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		/*
		//把属于营销类的对应收入科目flag标志置为1		
		String sqlNew = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
		*/
	}

	/*
	 * 获取指定组织下自有的收入科目(全部)
	 */
	private IncomeAccountCollection getIncomeAccountOrgOwn(Context ctx, String orgPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 1 order by longNumber");
		return IncomeAccountCollection;
	}

	/*
	 * 获取指定组织下自有的收入科目(启用的)
	 */
	private IncomeAccountCollection getEnIncomeAccountOrgOwn(Context ctx, String orgPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 1 and isEnabled = 1  order by longNumber");
		return IncomeAccountCollection;
	}

	/*
	 * 判断指定的科目是否已经分配到指定的组织下
	 */
	private boolean isAssignToOrg(Context ctx, String IncomeAccountPK, String orgPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select id where srcIncomeAccountId = '" + IncomeAccountPK + "' and fullOrgUnit.id = '" + orgPK + "' and isSource = 0");
		if (IncomeAccountCollection.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 构造对于组织的分配记录
	 */
	private IncomeAccountAssignInfo createIncomeAccountAssignOrg(Context ctx, String IncomeAccountPK, String orgPK) throws EASBizException, BOSException {
		IncomeAccountAssignInfo IncomeAccountAssignInfo = new IncomeAccountAssignInfo();
		IncomeAccountInfo IncomeAccountInfo = new IncomeAccountInfo();
		IncomeAccountInfo.setId(BOSUuid.read(IncomeAccountPK));
		FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
		fullOrgUnitInfo.setId(BOSUuid.read(orgPK));
		IncomeAccountAssignInfo.setIncomeAccount(IncomeAccountInfo);
		IncomeAccountAssignInfo.setFullOrgUnit(fullOrgUnitInfo);
		return IncomeAccountAssignInfo;
	}

	/**
	 * 描述：反分配 基于组织
	 * <p>
	 */
	protected void _disAssignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {// 界面是否需要判断，当前是否为源？？？？
		// 删除当前组织自有收入科目为源收入科目的记录
		// 获取源 = 当前组织下的收入科目集合
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		IncomeAccountCollection cac = this.getIncomeAccountOrgOwn(ctx, orgPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// 删除分配记录里所有相关该源头的数据即可
			filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", lnUps, CompareType.INCLUDE));

			iIncomeAccount.delete(filter);
		}
		// 删除当前组织间接分配下去的当前组织下被分配的收入科目为源的记录,即删除当前组织下级的组织节点上所有的分配记录即可
		IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(orgPK);
		String longNumber = fullOrgUnitInfo.getLongNumber();
		FullOrgUnitCollection fouc = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection("select id where isCompanyOrgUnit = 1 and longNumber like '" + longNumber + "!%'");
		HashSet lnUpsOrg = new HashSet();
		for (int i = 0; i < fouc.size(); i++) {
			lnUpsOrg.add(fouc.get(i).getId().toString());
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", lnUpsOrg, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
		iIncomeAccount.delete(filter);
		String sql = "update t_fdc_IncomeAccount set fAssigned = 0  where ffullOrgUnit = '" + orgPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
	}
	
	 
	/**
	 * --------------------------------------以下为以工程项目为源,工程项目为目标的分配/反分配处理------------------------------------
	 */
	/**
	 * 描述：分配 基于工程项目
	 * <p>
	 */
	protected List _assignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException {
	
		FDCUtils.lockAssignIncomeAccount(ctx);
		
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(projectPK);
		String longNumber = curProjectInfo.getLongNumber();
		// 获取源 = 当前工程项目下的收入科目集合 + 当前工程项目被分配的收入科目集合
		IncomeAccountCollection cac = this.getProjectIncomeAccounts(ctx, projectPK.toString());
		
		// 获取所有目标(下级工程项目)
		CurProjectCollection cpc = iCurProject.getCurProjectCollection("select * where longNumber like '" + longNumber + "!%'");

		//获取所选项目集合的displayName属性，以备错误信息使用
		Map projectMap = new HashMap();
		for (Iterator iter = cpc.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
        	projectMap.put(element.getId().toString(), element.getDisplayName());
        }
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectMap.keySet(), CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("isEnabled");
		view.getSelector().add("curProject.id");
		
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(view);
		
		Map accMap = new HashMap();
		Map caMap = null;
		for (Iterator iter = IncomeAccountCollection.iterator(); iter.hasNext();) {
			IncomeAccountInfo element = (IncomeAccountInfo) iter.next();
			caMap = new HashMap();
			caMap.put("id",element.getId().toString());
			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
			
			String projId = element.getCurProject().getId().toString();
			String longNumber2 = element.getLongNumber();
			String comb = projId + "_" + longNumber2;
			accMap.put(comb, caMap);
		}
		
		List errorList = insertIncomeAccount(ctx,projectMap.keySet(),cac,accMap,projectMap,true);		
	
		String sql = "update t_fdc_IncomeAccount set fAssigned = 1  where fCurproject = '" + projectPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		/*
		//把属于营销类的对应收入科目flag标志置为1		
		String sqlNew = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
		*/
		
		return errorList;
	}

	/*
	 * 获取指定工程项目下自有的收入科目(全部)
	 */
	private IncomeAccountCollection getIncomeAccountProjectOwn(Context ctx, String projectPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select * where curProject.id = '" + projectPK + "' and isSource = 1 order by longNumber");
		return IncomeAccountCollection;
	}

	/*
	 * 获取指定工程项目下自有的收入科目(启用的)
	 */
	private IncomeAccountCollection getEnIncomeAccountProjectOwn(Context ctx, String projectPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select * where curProject.id = '" + projectPK + "' and isSource = 1 and isEnabled = 1  order by longNumber");
		return IncomeAccountCollection;
	}

	/*
	 * 判断指定的科目是否已经分配到指定的工程项目下
	 */
	private boolean isAssignToProject(Context ctx, String IncomeAccountPK, String projectPK) throws BOSException {
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(
				"select id where srcIncomeAccountId = '" + IncomeAccountPK + "' and curProject.id = '" + projectPK + "'");
		if (IncomeAccountCollection.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 构造对于工程项目的分配记录
	 */
	private IncomeAccountAssignInfo createIncomeAccountAssignProject(Context ctx, String IncomeAccountPK, String projectPK) throws EASBizException, BOSException {
		IncomeAccountAssignInfo IncomeAccountAssignInfo = new IncomeAccountAssignInfo();
		IncomeAccountInfo IncomeAccountInfo = new IncomeAccountInfo();
		IncomeAccountInfo.setId(BOSUuid.read(IncomeAccountPK));
		CurProjectInfo curProjectInfo = new CurProjectInfo();
		curProjectInfo.setId(BOSUuid.read(projectPK));
		IncomeAccountAssignInfo.setIncomeAccount(IncomeAccountInfo);
		IncomeAccountAssignInfo.setCurProject(curProjectInfo);
		return IncomeAccountAssignInfo;
	}

	/**
	 * 描述：反分配 基于工程项目
	 * <p>
	 */
	protected void _disAssignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException {// 界面是否需要判断，当前是否为源？？？？

		// 获取源(当前工程项目下的收入科目集合)
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		IncomeAccountCollection cac = this.getIncomeAccountProjectOwn(ctx, projectPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// 删除分配记录里所有相关该源头的数据即可
			filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", lnUps, CompareType.INCLUDE));
			iIncomeAccount.delete(filter);
		}
		// 更新当前源收入科目的分配状态
		IncomeAccountInfo IncomeAccountInfo;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("assigned"));
		for (int i = 0; i < cac.size(); i++) {
			IncomeAccountInfo = cac.get(i);
			IncomeAccountInfo.setAssigned(false);
			iIncomeAccount.updatePartial(IncomeAccountInfo, selector);
		}
		// 删除当前工程项目间接分配下去的当前工程项目下被分配的收入科目为源的记录,即删除当前工程项目下级节点上所有的分配记录即可
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(projectPK);
		String longNumber = curProjectInfo.getLongNumber();
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where longNumber like '" + longNumber + "!%'");
		HashSet lnUpsProject = new HashSet();
		for (int i = 0; i < cpc.size(); i++) {
			lnUpsProject.add(cpc.get(i).getId().toString());
		}
		if (lnUpsProject.size() != 0) {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", lnUpsProject, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
			iIncomeAccount.delete(filter);
		}
	}

	/**
	 * --------------------------------------以下为以组织为源,工程项目为目标的分配/反分配处理------------------------------------
	 */
	/**
	 * 描述：分配 基于组织
	 */
	protected List _assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {	
		
		FDCUtils.lockAssignIncomeAccount(ctx);
		
		// 获取源(当前组织下的收入科目集合)
		IncomeAccountCollection cac = this.getOrgIncomeAccounts(ctx, orgPK.toString());
		
		// 获取所有目标(左侧树中当前组织的下级工程项目)
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select * where fullOrgUnit.id = '" + orgPK.toString() + "' ");
		
		// 将下级工程项目的收入科目存入List，用于下面判断该科目是否已分配
		//获取所选项目集合的displayName属性，以备错误信息使用
		Map projectMap = new HashMap();
		for (Iterator iter = cpc.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
        	projectMap.put(element.getId().toString(), element.getDisplayName());
        }
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectMap.keySet(), CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("isEnabled");
		view.getSelector().add("curProject.id");
		IncomeAccountCollection IncomeAccountCollection = IncomeAccountFactory.getLocalInstance(ctx).getIncomeAccountCollection(view);
		Map accMap = new HashMap();
		Map caMap = null;
		for (Iterator iter = IncomeAccountCollection.iterator(); iter.hasNext();) {
			IncomeAccountInfo element = (IncomeAccountInfo) iter.next();
			caMap = new HashMap();
			caMap.put("id",element.getId().toString());
			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
			String key = element.getCurProject().getId().toString() + "_" + element.getLongNumber();
			accMap.put(key, caMap);
		}
		
		List errorList = insertIncomeAccount(ctx,projectMap.keySet(),cac,accMap,projectMap,true);

		String sql = "update t_fdc_IncomeAccount set fAssigned = 1  where ffullOrgUnit = '" + orgPK.toString() + "' and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		/*
		//把属于营销类的对应收入科目flag标志置为1		
		String sqlNew = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
		*/
		
		return errorList;
	}

	protected void _disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		// 界面是否需要判断，当前是否为源？？？？
		// 获取源(当前组织下的收入科目集合)
		IncomeAccountCollection cac = this.getIncomeAccountOrgOwn(ctx, orgPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// 删除分配记录里所有相关该源头的数据即可
			filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", lnUps, CompareType.INCLUDE));
			iIncomeAccount.delete(filter);
		}
		// 删除间接分配下去的记录,即删除当前组织节点下所有工程项目下的非源数据
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where fullOrgUnit.id = '" + orgPK.toString() + "'");
		HashSet lnUpsProject = new HashSet();
		for (int i = 0; i < cpc.size(); i++) {
			lnUpsProject.add(cpc.get(i).getId().toString());
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", lnUpsProject, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
		iIncomeAccount.delete(filter);
		String sql = "update t_fdc_IncomeAccount set fAssigned = 0  where ffullOrgUnit = '" + orgPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
	}

	// ----------------------------------------------------------------------------------------------------------------
	/**
	 * 获取指定组织下已启用的收入科目集合,包括自有的以及分配得来的!
	 * 
	 * @throws EASBizException
	 */
	private IncomeAccountCollection getOrgIncomeAccounts(Context ctx, String orgPK) throws BOSException, EASBizException {
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		// 自有的,即当前指定组织为源的
		IncomeAccountCollection cac = this.getEnIncomeAccountOrgOwn(ctx, orgPK);
		IncomeAccountCollection caac = iIncomeAccount.getIncomeAccountCollection("select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 0 and isEnabled = 1 order by longNumber");
		IncomeAccountCollection tmp;
		// IncomeAccountInfo cai;
		if (caac != null && caac.size() != 0) {
			tmp = new IncomeAccountCollection();
			for (int i = 0; i < caac.size(); i++) {
				IncomeAccountInfo cai = null;
				cai = iIncomeAccount.getIncomeAccountInfo(new ObjectUuidPK(caac.get(i).getSrcIncomeAccountId()));
				if (cai != null) {
					tmp.add(cai);
				}
			}
			cac.addCollection(tmp);
		}
		return cac;
	}

	/**
	 * 获取指定工程项目下已启用的收入科目集合,包括自有的以及分配得来的!
	 * 
	 * @throws EASBizException
	 */
	private IncomeAccountCollection getProjectIncomeAccounts(Context ctx, String projectPK) throws BOSException, EASBizException {
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		// 自有的,即当前指定工程项目为源的
		IncomeAccountCollection cac = this.getEnIncomeAccountProjectOwn(ctx, projectPK);
		IncomeAccountCollection caac = iIncomeAccount.getIncomeAccountCollection("select * where curProject.id = '" + projectPK + "' and isSource = 0 and isEnabled = 1 order by longNumber ");
		IncomeAccountCollection tmp;
		if (caac != null && caac.size() != 0) {
			tmp = new IncomeAccountCollection();
			EntityViewInfo evi;
			FilterInfo filter;
			IncomeAccountCollection IncomeAccountCollection;
			for (int i = 0; i < caac.size(); i++) {
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", caac.get(i).getSrcIncomeAccountId()));
				evi.setFilter(filter);
				// 存在性能问题,有时间需要将srcIncomeAccountId自有属性更改为连接属性
				// cai = new IncomeAccountInfo();
				// cai.setId(BOSUuid.read(caac.get(i).getSrcIncomeAccountId()));
				// tmp.add(cai);
				IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
				if (IncomeAccountCollection.size() != 0) {
					tmp.add(IncomeAccountCollection.get(0));
				}
			}
			cac.addCollection(tmp);
		}

		return cac;
	}

	// 反分配
	private IncomeAccountCollection disAssignSelectIncomeAccount(Context ctx, IncomeAccountInfo cai) throws BOSException, EASBizException {
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		IncomeAccountCollection say = new IncomeAccountCollection();
		if (cai.isIsSource()) {
			// 删除所有的以该cai为源的项
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", cai.getId().toString(), CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));

			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("*"));
			evi.getSelector().add(new SelectorItemInfo("parent.*"));
			evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
			evi.getSelector().add(new SelectorItemInfo("curProject.*"));

			IncomeAccountCollection IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
			// IncomeAccountInfo IncomeAccountInfo;
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("assigned"));
			if (IncomeAccountCollection.size() != 0) {
				IncomeAccountCollection tmp;
				for (int i = 0; i < IncomeAccountCollection.size(); i++) {
					tmp = iIncomeAccount.getIncomeAccountCollection("where parent.id = '" + IncomeAccountCollection.get(i).getId().toString() + "'");
					if (tmp.size() != 0) { // 被引用为parent
						say.add(IncomeAccountCollection.get(i));
					} else {
						BizReference ref = null;
						try {
							ref = ReferenceDAO.getReference(ctx, BOSUuid.read(IncomeAccountCollection.get(i).getId().toString()));
						} catch (Exception e) {
							throw new ObjectReferedException(ref, e);
						}

						if (ref != null) {
							say.add(IncomeAccountCollection.get(i));
						} else {
							iIncomeAccount.delete(new ObjectUuidPK(IncomeAccountCollection.get(i).getId().toString()));// 删除分配记录
						}
					}
				}
			}
			IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
			SelectorItemCollection selector1 = new SelectorItemCollection();
			selector1.add(new SelectorItemInfo("assigned"));
			if (IncomeAccountCollection.size() == 0) {
				cai.setAssigned(false);
				iIncomeAccount.updatePartial(cai, selector1);// 更新源的分配状态
			}

		} else {//对非源科目反分配
			if (cai.getFullOrgUnit() != null) {// 如果是挂靠在组织上
				CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where fullOrgUnit.id = '" + cai.getFullOrgUnit().getId().toString() + "' ");
				if (cpc.size() == 0) {// 如果该组织下没有工程项目,就是组织之间的反分配
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", cai.getSrcIncomeAccountId(), CompareType.EQUALS));
					HashSet hs = getSubOrgUnit(ctx, cai.getFullOrgUnit().getLongNumber());
					if (hs.size() != 0) {//有下级组织,对下级组织反分配
						filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", hs, CompareType.INCLUDE));

						evi.setFilter(filter);
						evi.getSelector().add(new SelectorItemInfo("*"));
						evi.getSelector().add(new SelectorItemInfo("parent.*"));
						evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
						evi.getSelector().add(new SelectorItemInfo("curProject.*"));
						IncomeAccountCollection IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
						// IncomeAccountInfo IncomeAccountInfo;
						SelectorItemCollection selector = new SelectorItemCollection();
						selector.add(new SelectorItemInfo("assigned"));
						if (IncomeAccountCollection.size() != 0) {
							IncomeAccountCollection tmp;
							for (int i = 0; i < IncomeAccountCollection.size(); i++) {
								tmp = iIncomeAccount.getIncomeAccountCollection("where parent.id = '" + IncomeAccountCollection.get(i).getId().toString() + "'");
								if (tmp.size() != 0) { // 被引用为parent
									say.add(IncomeAccountCollection.get(i));
									// throw new
									// com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
								} else {
									iIncomeAccount.delete(new ObjectUuidPK(IncomeAccountCollection.get(i).getId().toString()));// 删除分配记录
								}
							}
						}
						IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
						SelectorItemCollection selector1 = new SelectorItemCollection();
						selector1.add(new SelectorItemInfo("assigned"));
						if (IncomeAccountCollection.size() == 0) {
							cai.setAssigned(false);
							iIncomeAccount.updatePartial(cai, selector1);
						}
					}

				} else {// 如果该组织下有工程项目,就是组织对工程项目的反分配
					HashSet hs = new HashSet();
					for (int i = 0; i < cpc.size(); i++) {
						hs.add(cpc.get(i).getId().toString());
					}
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", cai.getSrcIncomeAccountId(), CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
					evi.setFilter(filter);
					evi.getSelector().add(new SelectorItemInfo("*"));
					evi.getSelector().add(new SelectorItemInfo("parent.*"));
					evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
					evi.getSelector().add(new SelectorItemInfo("curProject.*"));
					IncomeAccountCollection IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
					if (IncomeAccountCollection.size() != 0) {
						IncomeAccountCollection tmp;
						for (int i = 0; i < IncomeAccountCollection.size(); i++) {
							tmp = iIncomeAccount.getIncomeAccountCollection("where parent.id = '" + IncomeAccountCollection.get(i).getId().toString() + "'");
							if (tmp.size() != 0) { // 被引用为parent
								say.add(IncomeAccountCollection.get(i));
							} else {
								iIncomeAccount.delete(new ObjectUuidPK(IncomeAccountCollection.get(i).getId().toString()));// 删除分配记录
							}
						}
					}
					// 改变状态
					IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("assigned"));
					if (IncomeAccountCollection.size() == 0) {
						cai.setAssigned(false);
						iIncomeAccount.updatePartial(cai, selector);
					}

				}
			} else if (cai.getCurProject() != null) {// 如果是挂靠在工程项目上
				// 对工程项目下级反分配
				CurProjectInfo cpi = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo("select id,fullOrgUnit.id where id = '" + cai.getCurProject().getId().toString() + "'");
				if (cpi != null) {
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcIncomeAccountId", cai.getSrcIncomeAccountId(), CompareType.EQUALS));
					HashSet hs = getSubProject(ctx, cai.getCurProject().getLongNumber(), cpi.getFullOrgUnit().getId().toString());
					if (hs.size() != 0) {
						filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
						evi.setFilter(filter);
						evi.getSelector().add(new SelectorItemInfo("*"));
						evi.getSelector().add(new SelectorItemInfo("parent.*"));
						evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
						evi.getSelector().add(new SelectorItemInfo("curProject.*"));
						IncomeAccountCollection IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
						// IncomeAccountInfo IncomeAccountInfo;
						if (IncomeAccountCollection.size() != 0) {
							IncomeAccountCollection tmp;
							for (int i = 0; i < IncomeAccountCollection.size(); i++) {
								tmp = iIncomeAccount.getIncomeAccountCollection("where parent.id = '" + IncomeAccountCollection.get(i).getId().toString() + "'");
								if (tmp.size() != 0) { // 被引用为parent
									say.add(IncomeAccountCollection.get(i));
									// throw new
									// com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
								} else {
									iIncomeAccount.delete(new ObjectUuidPK(IncomeAccountCollection.get(i).getId().toString()));// 删除分配记录
								}
							}
						}
						// 改变状态
						IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(evi);
						if (IncomeAccountCollection.size() == 0) {
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add(new SelectorItemInfo("assigned"));
							cai.setAssigned(false);
							iIncomeAccount.updatePartial(cai, selector);
						}
					}
				}
			}
		}

		// // 重新遍历，改变被完全反分配的收入科目项的分配状态
		// if(cai.isIsSource()){}else{}
		// // }
		return say;
	}

	// 寻找组织下级
	private HashSet getSubOrgUnit(Context ctx, String longNumber) throws BOSException {
		// 获取所有目标(下级组织)
		HashSet hs = new HashSet();
		FullOrgUnitCollection fouc = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection("select id where isCompanyOrgUnit = 1 and longNumber like '" + longNumber + "!%'");
		if (fouc.size() != 0) {
			for (int i = 0; i < fouc.size(); i++) {
				hs.add(fouc.get(i).getId().toString());
			}
		}
		return hs;
	}

	// 寻找工程项目下级
	private HashSet getSubProject(Context ctx, String longNumber, String ouid) throws BOSException {
		HashSet hs = new HashSet();
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where fullOrgUnit.id = '" + ouid + "' and longNumber like '" + longNumber + "!%'");
		if (cpc.size() != 0) {
			for (int i = 0; i < cpc.size(); i++) {
				hs.add(cpc.get(i).getId().toString());
			}
		}
		return hs;
	}

	protected void _disAssignSelectOrgsIncomeAccount(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException {
		if (IncomeAccounts.size() != 0) {

			IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
			IncomeAccountCollection cac = (IncomeAccountCollection) IncomeAccounts;
			IncomeAccountCollection tmp;
			IncomeAccountCollection say = new IncomeAccountCollection();

			HashSet lnUps = new HashSet();
			// for (int i = 0; i < cac.size(); i++) {
			// lnUps.add(cac.get(i).getId().toString());
			// }

			// --
			for (int i = 0; i < IncomeAccounts.size(); i++) {
				say.addCollection(disAssignSelectIncomeAccount(ctx, cac.get(i)));
			}
			// --

			if (say.size() != 0) {
				lnUps.clear();
				for (int i = 0; i < say.size(); i++) {
					lnUps.add(say.get(i).getName());
				}

				// 给出提示
				throw (new FDCBasedataException(FDCBasedataException.DISASSIGN_ISREFERENCE2, new Object[] { lnUps }));
			}
		}
	}

	protected void _disAssignSelectProjsIncomeAccount(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException {
		// TODO 自动生成方法存根

	}

	protected ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection IncomeAccounts) throws BOSException, EASBizException {
		ArrayList al = new ArrayList();
		ArrayList itemOnOrg = new ArrayList();
		ArrayList itemOnProject = new ArrayList();
		if (IncomeAccounts.size() != 0) {
			IncomeAccountCollection cac = (IncomeAccountCollection) IncomeAccounts;
			IncomeAccountCollection say = new IncomeAccountCollection();
			//喉炎提出要同步反分配下级节点!
			//获取下级科目			
			IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
			IncomeAccountCollection cactemp = null;
			for (int i = 0; i < IncomeAccounts.size(); i++) {
				cactemp = new IncomeAccountCollection();
				if (cac.get(i).getFullOrgUnit() != null) {//组织节点下的科目数据
					//获取该科目下级					
					cactemp = iIncomeAccount.getIncomeAccountCollection("select *,fullOrgUnit.* where longNumber like '" + cac.get(i).getLongNumber() + "!%' and fullOrgUnit.id = '"
							+ cac.get(i).getFullOrgUnit().getId().toString() + "' order by longNumber desc");///

				} else if (cac.get(i).getCurProject() != null) {//工程项目节点下的科目数据
					//获取该科目下级
					cactemp = iIncomeAccount.getIncomeAccountCollection("select *,curproject.* where longNumber like '" + cac.get(i).getLongNumber() + "!%' and curproject.id = '"
							+ cac.get(i).getCurProject().getId().toString() + "' order by longNumber desc");///

				}
				if (cactemp.size() != 0) {
					for (int j = 0; j < cactemp.size(); j++) {
						say.addCollection(disAssignSelectIncomeAccount(ctx, cactemp.get(j)));
					}
				}
				say.addCollection(disAssignSelectIncomeAccount(ctx, cac.get(i)));
			}
			//////////////////////////
			IncomeAccountInfo cai = null;
			for (int i = 0; i < say.size(); i++) {
				cai = say.get(i);
				if (cai.getFullOrgUnit() != null) {
					itemOnOrg.add(cai.getFullOrgUnit().getName() + "组织节点下下" + cai.getLongNumber().replace('!', '.'));
				} else {
					itemOnProject.add(cai.getCurProject().getName() + "工程项目节点下" + cai.getLongNumber().replace('!', '.'));
				}
			}
			if (!itemOnOrg.isEmpty()) {
				al.add(itemOnOrg);
			}
			if (!itemOnProject.isEmpty()) {
				al.add(itemOnProject);
			}
		}
		return al;
	}
	
	protected void _handleEnterDB(Context ctx, IObjectValue model,boolean isEnterDB)
			throws BOSException, EASBizException {
//		if(model==null) return;
//		IncomeAccountInfo acct=(IncomeAccountInfo)model;
//		if(acct.getId()==null||acct.getLongNumber()==null) return;
//		if(acct.isIsEnterDB()==isEnterDB){
//			return;
//		}
//		
//		try{
//			if(isEnterDB){
//				enterDB(ctx,acct);
//			}else{
//				cancleEnterDB(ctx,acct);
//			}
//		}catch(Exception e){
//			throw new BOSException(e);
//		}
		
	}
	
	private void enterDB(Context ctx,IncomeAccountInfo acct)throws BOSException, EASBizException, SQLException{
    	FDCSQLBuilder builder=new FDCSQLBuilder();
		//将所有上下级及本级都选上
		String longNumber=acct.getLongNumber()+"!%";
		String id=acct.getId().toString();
		builder.appendSql("update t_Fdc_IncomeAccount set fisenterdb=? where fid in (select fid from (");
		builder.appendSql(" (Select c1.fid From t_Fdc_IncomeAccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_IncomeAccount t Where t.fid=?)c2 Where charIndex(c1.flongnumber,c2.flongnumber)>0 and c1.flevel<c2.flevel and");
		builder.appendSql(" (c1.ffullorgunit=c2.ffullorgunit or (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and");
		builder.appendSql(" (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null and c2.fcurproject is null)))");
		builder.appendSql(" union (select ? as fid)");//本身
		builder.appendSql(" union (select c1.fid From t_Fdc_IncomeAccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_IncomeAccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
		builder.appendSql(" (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null");
		builder.appendSql(" and c2.fcurproject is null))))a);");
		
		builder.addParam(new Integer(1));
		builder.addParam(id);
		builder.addParam(id);
		builder.addParam(id);
		builder.addParam(longNumber);
/*		IRowSet rowSet=builder.executeQuery(ctx);
		Set set=new HashSet();
		while(rowSet.next()){
			set.add(rowSet.getString("fid"));
		}
		if(set.size()>0){
			builder.clear();
			builder.appendSql("update t_Fdc_IncomeAccount set fisenterdb=? where ");
			builder.addParam(new Integer(1));
			builder.appendParam("fid", set.toArray());
		}*/
		
		builder.executeUpdate(ctx);
	
	}
	
	private void cancleEnterDB(Context ctx,IncomeAccountInfo acct)throws BOSException, EASBizException, SQLException{
		String longNumber=acct.getLongNumber()+"!%";
		String id=acct.getId().toString();
		
		FDCSQLBuilder builder=new FDCSQLBuilder();
		//更新下级及本级
		builder.appendSql("update t_Fdc_IncomeAccount set fisenterdb=? where fid in (select fid from ");
		builder.appendSql(" ((select ? as fid)");
		builder.appendSql(" union (select c1.fid From t_Fdc_IncomeAccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_IncomeAccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
		builder.appendSql(" (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null");
		builder.appendSql(" and c2.fcurproject is null)))) a );");
		
		builder.addParam(new Integer(0));
		builder.addParam(id);
		builder.addParam(id);
		builder.addParam(longNumber);
		builder.executeUpdate(ctx);

	//更新上级
		
		builder.clear();
		builder.appendSql("select fparentid from T_FDC_IncomeAccount where fid=?");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery(ctx);
		if(rowSet.size()!=1){
			return;
		}
		
		rowSet.next();
		String pid = rowSet.getString("fparentid");
		Set set=new HashSet();
		while(pid!=null){
			builder.clear();
			builder.appendSql("select fisenterdb from T_FDC_IncomeAccount where fparentid=? and fisenterdb=1");
			builder.addParam(pid);
			if(set.size()>0){
				builder.appendSql(" and fid not in (");
				builder.appendParam(set.toArray());
				builder.appendSql(")");
			}
			rowSet=builder.executeQuery(ctx);
			if(rowSet.size()>0){
				break;
			}else{
				builder.clear();
				set.add(pid);
				builder.appendSql("select fparentid from T_FDC_IncomeAccount where fid=?");
				builder.addParam(pid);
				rowSet=builder.executeQuery(ctx);
	    		if(rowSet.size()!=1){
	    			break;
	    		}
	    		
	    		rowSet.next();
	    		pid = rowSet.getString("fparentid");
			}
		}
			
		if(set.size()>0){
			builder.clear();
			builder.appendSql("update T_FDC_IncomeAccount set FIsEnterDB=? where ");
			builder.addParam(new Integer(0));
			builder.appendParam("fid", set.toArray());
			builder.executeUpdate(ctx);
		}
	}

	/**
	 * 逐级分配
	 */
	protected List _assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException {
	
		FDCUtils.lockAssignIncomeAccount(ctx);
		
		IIncomeAccount iIncomeAccount = IncomeAccountFactory.getLocalInstance(ctx);
		//获取当前源头组织下已启用的收入科目集合,包括自有的以及分配得来的
		IncomeAccountCollection cac = iIncomeAccount.getIncomeAccountCollection("select * where fullOrgUnit.id = '" + orgPK.toString() + "' and isEnabled = 1 order by longNumber");

		//获取所选项目集合的displayName属性，以备错误信息使用
		Map projectMap = new HashMap();
		if(isProjectSet){
			EntityViewInfo pView = new EntityViewInfo();
			FilterInfo pFilter = new FilterInfo();
			pFilter.getFilterItems().add(new FilterItemInfo("id", nodeIds, CompareType.INCLUDE));
			pView.setFilter(pFilter);
			pView.getSelector().add("id");
			pView.getSelector().add("displayName");
			CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(pView);
			for (Iterator iter = cpc.iterator(); iter.hasNext();) {
				CurProjectInfo element = (CurProjectInfo) iter.next();
				projectMap.put(element.getId().toString(), element.getDisplayName());
			}
		}
		//获取所选目标对象(组织或项目)集合下所有科目，以组织ID+科目长编码为KEY、科目对象为值保存到accMap
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(isProjectSet ? "curProject.id" : "fullOrgUnit.id", nodeIds, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isEnabled");
		view.getSelector().add("longNumber");
		view.getSelector().add(isProjectSet?"curProject.id":"fullOrgUnit.id");
		
		IncomeAccountCollection IncomeAccountCollection = iIncomeAccount.getIncomeAccountCollection(view);
		Map accMap = new HashMap();
		Map caMap = null;
		String ownerId = null;
		for (Iterator iter = IncomeAccountCollection.iterator(); iter.hasNext();) {
			IncomeAccountInfo element = (IncomeAccountInfo) iter.next();
			caMap = new HashMap();
			caMap.put("id",element.getId().toString());
			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
			ownerId = isProjectSet ? element.getCurProject().getId().toString() : element.getFullOrgUnit().getId().toString();
			accMap.put(ownerId + "_" + element.getLongNumber(), caMap);
		}
		
		List errorList = insertIncomeAccount(ctx,nodeIds,cac,accMap,projectMap,isProjectSet);
		
		String sql = "update t_fdc_IncomeAccount set fAssigned = 1  where ffullOrgUnit = '"
				+ orgPK.toString()
				+ "' and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		/*
		//把属于营销类的对应收入科目flag标志置为1		
		String sqlNew = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_IncomeAccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
		*/
		
		return errorList;
	}
	
	private List insertIncomeAccount(Context ctx,Set nodeIds,IncomeAccountCollection cac,Map accMap,Map ownerMap,boolean isProjectSet) throws BOSException, EASBizException {
		List errorList = new ArrayList();
		Map parentMap = new HashMap();
		
		Connection connection = null;
		PreparedStatement insertStmt = null;
		PreparedStatement updateStmt = null;
		try {
			connection = getConnection(ctx);
			insertStmt = connection.prepareStatement(insert_sql);
			updateStmt = connection.prepareStatement(update_sql);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();

			Locale l1 = new Locale("L1");
			Locale l2 = new Locale("L2");
			Locale l3 = new Locale("L3");
			/*
			 * 此set用于建立收入科目与会计科目的对应关系
			 */
			Set acctIdSet=new HashSet();
			Iterator tor = nodeIds.iterator();
			String longNumber = "";
			while (tor.hasNext()) {		//遍历所选目标组织或项目集合
				String nodeId = tor.next().toString();
				for (int j = 0; j < cac.size(); j++) {		//遍历当前源头组织下所有启用的科目
					String comb = nodeId + "_" + cac.get(j).getLongNumber();
					//如果当前组织或项目下没有此科目，则分配
					if (!accMap.containsKey(comb)) {

						IncomeAccountInfo info = cac.get(j);
						BOSUuid uuid = BOSUuid.create(info.getBOSType());
						String id = uuid.toString();
						acctIdSet.add(id);
						longNumber = info.getLongNumber();
						
						boolean isEnable = info.isIsEnabled();
						String parentlongNumber = null;
						String parentId = null;
						int idx = longNumber.lastIndexOf("!");
						if(idx > 0) {
							parentlongNumber = longNumber.substring(0, idx);
							String key = nodeId + "_" + parentlongNumber;
							
							Map ca = null;
							if(accMap.containsKey(key)){
								ca = (HashMap)accMap.get(key);
							}
							if(ca != null) {
								isEnable = ((Boolean)ca.get("isEnable")).booleanValue();
								parentId = ca.get("id").toString();
								parentMap.put(parentlongNumber, parentId);
								if(isProjectSet){
									//上级科目如果已经发生业务数据(即已被引用,但收入科目与会计科目的对应关系引用不予考虑)，则本次不允许给其下面分配子科目
									BizReference reference = ReferenceDAO.getReference(ctx,BOSUuid.read(parentId));
									if(reference != null && ownerMap != null) {
										if(!reference.getRefEntityName().equalsIgnoreCase("IncomeAccountWithAccount")){
											String errorInfo = ownerMap.get(nodeId) + " " + info.getLongNumber();
											errorList.add(errorInfo);
											continue;
										}										
									}
								}
							}
							if(parentId == null) continue;
						}
						
						int x = 1;
						insertStmt.setString(x++, id); // id
						insertStmt.setString(x++, userId); // creator
						insertStmt.setTimestamp(x++, now); // createtime
						insertStmt.setString(x++, userId); // lastupduser
						insertStmt.setTimestamp(x++, now); // lastupdtime
						insertStmt.setString(x++, info.getCU().getId().toString()); // cu

						insertStmt.setString(x++, info.getName(l1)); // name1
						insertStmt.setString(x++, info.getName(l2)); // name2
						insertStmt.setString(x++, info.getName(l3)); // name3
						insertStmt.setString(x++, info.getNumber()); // number
						insertStmt.setString(x++, info.getDescription(l1)); // desc1
						insertStmt.setString(x++, info.getDescription(l2)); // desc2
						insertStmt.setString(x++, info.getDescription(l3)); // desc3
						insertStmt.setString(x++, info.getSimpleName()); // simplename
						insertStmt.setBoolean(x++, true); // isLeaf
						insertStmt.setInt(x++, info.getLevel()); // level
						insertStmt.setString(x++, longNumber); // longnumber
						insertStmt.setString(x++, info.getDisplayName(l1)); // dispName1
						insertStmt.setString(x++, info.getDisplayName(l2)); // dispname2
						insertStmt.setString(x++, info.getDisplayName(l3)); // dispname3
						insertStmt.setBoolean(x++, false); // isassigned					
						
						insertStmt.setBoolean(x++, isEnable); // enabled
						insertStmt.setString(x++, isProjectSet ? nodeId : null); // curproject
						
						if(parentId == null && parentlongNumber != null){
							parentId = (String) parentMap.get(parentlongNumber);
						}
						if(parentId != null){
							DbUtil.prepareVarcharParam(updateStmt, 1, parentId);
							updateStmt.addBatch();
						}
						insertStmt.setString(x++, parentId); // parentid
						
						insertStmt.setString(x++, isProjectSet ? null : nodeId);	//fullorgunit
						insertStmt.setBoolean(x++, false);	//issource
						insertStmt.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcIncomeAccountId());	//FSRCIncomeAccountID
						//String type = info.getType() == null ? null : info
						//		.getType().getValue();
						//type = info.getLevel() == 1 ? null : type;
						//insertStmt.setString(x++, type);		//TYPE
						//insertStmt.setBoolean(x++, info.isIsIncomeAccount()); // isIsIncomeAccount
						insertStmt.setString(x++, info.getCodingNumber()); // codingNumber  by sxhong 2008-10-20 18:38:43
						parentMap.put(info.getLongNumber(), id);
						
						info.setId(uuid);
						
						Map caMap = new HashMap();
						caMap.put("id",id);
						caMap.put("isEnable",Boolean.valueOf(isEnable));
						accMap.put(nodeId + "_" + longNumber, caMap);
						insertStmt.addBatch();
					}
				}
				
				parentMap.clear();
			}
			insertStmt.executeBatch();
			updateStmt.executeBatch();
			
			/*
			if(acctIdSet.size()>0){
				Map param=new HashMap();
				param.put("acctIdSet", acctIdSet);
				IncomeAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
			}
			*/
			
		} catch (SQLException ex) {
			throw new BOSException(ex);
		} finally {
			parentMap.clear();
			SQLUtils.cleanup(insertStmt, connection);
			SQLUtils.cleanup(updateStmt, connection);
		}
		return errorList;
	}
    
}