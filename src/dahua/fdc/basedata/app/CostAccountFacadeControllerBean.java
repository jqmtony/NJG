package com.kingdee.eas.fdc.basedata.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
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
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.app.dao.ReferenceDAO;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.CostAccountAssignInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAcctFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.BizReference;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;
import com.kingdee.util.db.SQLUtils;

/**
 * ����:�ɱ���Ŀ����facade
 * 
 * @author jackwang date:2006-9-18
 *         <p>
 * @version EAS5.1
 */
public class CostAccountFacadeControllerBean extends AbstractCostAccountFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountFacadeControllerBean");

	//�ɱ���Ŀinsert sql
	private static String insert_sql = "insert into t_fdc_costAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCCOSTACCOUNTID, FTYPE,FIsCostAccount,FCodingNumber) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static String insert_batch_sql = "insert into t_fdc_costAccount(FID, FCREATORID, FCREATETIME, FLASTUPDATEUSERID, FLASTUPDATETIME, FCONTROLUNITID, FNAME_L1, FNAME_L2, FNAME_L3, FNUMBER, FDESCRIPTION_L1, FDESCRIPTION_L2, FDESCRIPTION_L3, FSIMPLENAME, FISLEAF, FLEVEL, FLONGNUMBER, FDISPLAYNAME_L1, FDISPLAYNAME_L2, FDISPLAYNAME_L3, FASSIGNED, FISENABLE, FCURPROJECT, FPARENTID, FFULLORGUNIT, FISSOURCE, FSRCCOSTACCOUNTID, FTYPE,FIsCostAccount,FCodingNumber ,FISCANADD,FCREATEORG,FISSPLIT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static String insert_accStage_sql = "insert into t_fdc_accountstage (FID ,FMEASURESTAGEID,FCOSTACCOUNTID,FVALUE ) values (?,?,?,?) ";
	
	private static String update_sql = "update t_fdc_costAccount set FIsLeaf=0 where FID=?";
	
	/**
	 * ---------------------------------------------����Ϊ����֯ΪԴ,��֯ΪĿ��ķ���/�����䴦��---------------------------------
	 */
	/**
	 * ���������� ������֯
	 * @deprecated
	 * @author:jackwang
	 * @date:2006-9-18
	 * <p>
	 */
	protected void _assignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {
		
		FDCUtils.lockAssignCostAccount(ctx);
		
		// ��ȡԴ = ��ǰ��֯�µĳɱ���Ŀ���� + ��ǰ��֯������ĳɱ���Ŀ����
		CostAccountCollection cac = this.getOrgCostAccounts(ctx, orgPK.toString());
		// ��ȡ����Ŀ��(�¼���֯)
		IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(orgPK);
		String longNumber = fullOrgUnitInfo.getLongNumber();
		FullOrgUnitCollection fouc = iFullOrgUnit.getFullOrgUnitCollection("select id where isCompanyOrgUnit = 1 and longNumber like '" + longNumber + "!%'");
		
		String sSql = "SELECT T0.FID, T0.FLONGNUMBER, T0.FFULLORGUNIT,T0.FISENABLE "
			+ " FROM T_FDC_COSTACCOUNT t0 " 
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
			List errorList = insertCostAccount(ctx,nodeIds,cac,accMap,null,false);
		} catch (SQLException ex) {
			throw new BOSException(ex);
		} finally {
			accMap.clear();
		}

		String sql = "update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '"
				+ orgPK.toString()
				+ "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		//������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
		
		setSaleCategoryCostAccountUpdate(ctx);
	}

	/*
	 * ��ȡָ����֯�����еĳɱ���Ŀ(ȫ��)
	 */
	private CostAccountCollection getCostAccountOrgOwn(Context ctx, String orgPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 1 order by longNumber");
		return costAccountCollection;
	}

	/*
	 * ��ȡָ����֯�����еĳɱ���Ŀ(���õ�)
	 */
	private CostAccountCollection getEnCostAccountOrgOwn(Context ctx, String orgPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 1 and isEnabled = 1  order by longNumber");
		return costAccountCollection;
	}

	/*
	 * �ж�ָ���Ŀ�Ŀ�Ƿ��Ѿ����䵽ָ������֯��
	 */
	private boolean isAssignToOrg(Context ctx, String costAccountPK, String orgPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select id where srcCostAccountId = '" + costAccountPK + "' and fullOrgUnit.id = '" + orgPK + "' and isSource = 0");
		if (costAccountCollection.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ���������֯�ķ����¼
	 */
	private CostAccountAssignInfo createCostAccountAssignOrg(Context ctx, String costAccountPK, String orgPK) throws EASBizException, BOSException {
		CostAccountAssignInfo costAccountAssignInfo = new CostAccountAssignInfo();
		CostAccountInfo costAccountInfo = new CostAccountInfo();
		costAccountInfo.setId(BOSUuid.read(costAccountPK));
		FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
		fullOrgUnitInfo.setId(BOSUuid.read(orgPK));
		costAccountAssignInfo.setCostAccount(costAccountInfo);
		costAccountAssignInfo.setFullOrgUnit(fullOrgUnitInfo);
		return costAccountAssignInfo;
	}

	/**
	 * ������������ ������֯
	 * @deprecated
	 * @author:jackwang
	 * @date:2006-9-18
	 * <p>
	 */
	protected void _disAssignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {// �����Ƿ���Ҫ�жϣ���ǰ�Ƿ�ΪԴ��������
		// ɾ����ǰ��֯���гɱ���ĿΪԴ�ɱ���Ŀ�ļ�¼
		// ��ȡԴ = ��ǰ��֯�µĳɱ���Ŀ����
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountCollection cac = this.getCostAccountOrgOwn(ctx, orgPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// ɾ�������¼��������ظ�Դͷ�����ݼ���
			filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));

			iCostAccount.delete(filter);
		}
		// ɾ����ǰ��֯��ӷ�����ȥ�ĵ�ǰ��֯�±�����ĳɱ���ĿΪԴ�ļ�¼,��ɾ����ǰ��֯�¼�����֯�ڵ������еķ����¼����
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
		iCostAccount.delete(filter);
		String sql = "update t_fdc_costAccount set fAssigned = 0  where ffullOrgUnit = '" + orgPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
	}
	
	/**
	 * --------------------------------------����Ϊ�Թ�����ĿΪԴ,������ĿΪĿ��ķ���/�����䴦��------------------------------------
	 */
	/**
	 * ���������� ���ڹ�����Ŀ
	 * @deprecated
	 * @author:jackwang
	 * @date:2006-9-18
	 * <p>
	 */
	protected List _assignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException {
	
		FDCUtils.lockAssignCostAccount(ctx);
		
		ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
		CurProjectInfo curProjectInfo = iCurProject.getCurProjectInfo(projectPK);
		String longNumber = curProjectInfo.getLongNumber();
		// ��ȡԴ = ��ǰ������Ŀ�µĳɱ���Ŀ���� + ��ǰ������Ŀ������ĳɱ���Ŀ����
		CostAccountCollection cac = this.getProjectCostAccounts(ctx, projectPK.toString());
		
		// ��ȡ����Ŀ��(�¼�������Ŀ)
		CurProjectCollection cpc = iCurProject.getCurProjectCollection("select * where longNumber like '" + longNumber + "!%'");

		//��ȡ��ѡ��Ŀ���ϵ�displayName���ԣ��Ա�������Ϣʹ��
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
		
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		
		Map accMap = new HashMap();
		Map caMap = null;
		for (Iterator iter = costAccountCollection.iterator(); iter.hasNext();) {
			CostAccountInfo element = (CostAccountInfo) iter.next();
			caMap = new HashMap();
			caMap.put("id",element.getId().toString());
			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
			
			String projId = element.getCurProject().getId().toString();
			String longNumber2 = element.getLongNumber();
			String comb = projId + "_" + longNumber2;
			accMap.put(comb, caMap);
		}
		
		List errorList = insertCostAccount(ctx,projectMap.keySet(),cac,accMap,projectMap,true);		
	
		String sql = "update t_fdc_costAccount set fAssigned = 1  where fCurproject = '" + projectPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		//������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
		
		setSaleCategoryCostAccountUpdate(ctx);
		
		return errorList;
	}

	/*
	 * ��ȡָ��������Ŀ�����еĳɱ���Ŀ(ȫ��)
	 */
	private CostAccountCollection getCostAccountProjectOwn(Context ctx, String projectPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select * where curProject.id = '" + projectPK + "' and isSource = 1 order by longNumber");
		return costAccountCollection;
	}

	/*
	 * ��ȡָ��������Ŀ�����еĳɱ���Ŀ(���õ�)
	 */
	private CostAccountCollection getEnCostAccountProjectOwn(Context ctx, String projectPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select * where curProject.id = '" + projectPK + "' and isSource = 1 and isEnabled = 1  order by longNumber");
		return costAccountCollection;
	}

	/*
	 * �ж�ָ���Ŀ�Ŀ�Ƿ��Ѿ����䵽ָ���Ĺ�����Ŀ��
	 */
	private boolean isAssignToProject(Context ctx, String costAccountPK, String projectPK) throws BOSException {
		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
				"select id where srcCostAccountId = '" + costAccountPK + "' and curProject.id = '" + projectPK + "'");
		if (costAccountCollection.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ������ڹ�����Ŀ�ķ����¼
	 */
	private CostAccountAssignInfo createCostAccountAssignProject(Context ctx, String costAccountPK, String projectPK) throws EASBizException, BOSException {
		CostAccountAssignInfo costAccountAssignInfo = new CostAccountAssignInfo();
		CostAccountInfo costAccountInfo = new CostAccountInfo();
		costAccountInfo.setId(BOSUuid.read(costAccountPK));
		CurProjectInfo curProjectInfo = new CurProjectInfo();
		curProjectInfo.setId(BOSUuid.read(projectPK));
		costAccountAssignInfo.setCostAccount(costAccountInfo);
		costAccountAssignInfo.setCurProject(curProjectInfo);
		return costAccountAssignInfo;
	}

	/**
	 * ������������ ���ڹ�����Ŀ
	 * @deprecated
	 * @author:jackwang
	 * @date:2006-9-18
	 * <p>
	 */
	protected void _disAssignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException {// �����Ƿ���Ҫ�жϣ���ǰ�Ƿ�ΪԴ��������

		// ��ȡԴ(��ǰ������Ŀ�µĳɱ���Ŀ����)
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountCollection cac = this.getCostAccountProjectOwn(ctx, projectPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// ɾ�������¼��������ظ�Դͷ�����ݼ���
			filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));
			iCostAccount.delete(filter);
		}
		// ���µ�ǰԴ�ɱ���Ŀ�ķ���״̬
		CostAccountInfo costAccountInfo;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("assigned"));
		for (int i = 0; i < cac.size(); i++) {
			costAccountInfo = cac.get(i);
			costAccountInfo.setAssigned(false);
			iCostAccount.updatePartial(costAccountInfo, selector);
		}
		// ɾ����ǰ������Ŀ��ӷ�����ȥ�ĵ�ǰ������Ŀ�±�����ĳɱ���ĿΪԴ�ļ�¼,��ɾ����ǰ������Ŀ�¼��ڵ������еķ����¼����
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
			iCostAccount.delete(filter);
		}
	}

	/**
	 * --------------------------------------����Ϊ����֯ΪԴ,������ĿΪĿ��ķ���/�����䴦��------------------------------------
	 */
	/**
	 * ���������� ������֯
	 * @deprecated 
	 * @author:jackwang
	 * @date:2006-9-18
	 * <p>
	 */
	protected List _assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {	
		
		FDCUtils.lockAssignCostAccount(ctx);
		
		// ��ȡԴ(��ǰ��֯�µĳɱ���Ŀ����)
		CostAccountCollection cac = this.getOrgCostAccounts(ctx, orgPK.toString());
		
		// ��ȡ����Ŀ��(������е�ǰ��֯���¼�������Ŀ)
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select * where fullOrgUnit.id = '" + orgPK.toString() + "' ");
		
		// ���¼�������Ŀ�ĳɱ���Ŀ����List�����������жϸÿ�Ŀ�Ƿ��ѷ���
		//��ȡ��ѡ��Ŀ���ϵ�displayName���ԣ��Ա�������Ϣʹ��
		Map projectMap = new HashMap();
		for (Iterator iter = cpc.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
        	projectMap.put(element.getId().toString(), element.getDisplayName());
        }
		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectMap.keySet(), CompareType.INCLUDE));
//		view.setFilter(filter);
//		view.getSelector().add("id");
//		view.getSelector().add("longNumber");
//		view.getSelector().add("isEnabled");
//		view.getSelector().add("curProject.id");
//		CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		String countSql = "SELECT count(*) FROM T_FDC_COSTACCOUNT where  ";
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(countSql);
		builder.appendParam("FCURPROJECT", projectMap.keySet().toArray());
		IRowSet rs = builder.executeQuery();
		int count = 0;
		Map accMap = new HashMap();
		Map caMap = null;

		
//		for (Iterator iter = costAccountCollection.iterator(); iter.hasNext();) {
//			CostAccountInfo element = (CostAccountInfo) iter.next();
//			caMap = new HashMap();
//			caMap.put("id",element.getId().toString());
//			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
//			String key = element.getCurProject().getId().toString() + "_" + element.getLongNumber();
//			accMap.put(key, caMap);
//		}
		try {
			if(rs.next()){
				count = rs.getInt(1);
			}
			Set idSet = new HashSet();
			if(count > 1){
				int pageSize = 5000;
				int pageCount = count/pageSize;
				if(count%pageSize > 0){
					++pageCount;
				}
				for(int pageNo = 1; pageNo <= pageCount; ++pageNo){
					builder.clear();
					builder.appendSql("SELECT TOP "+pageSize+" * from ( ");
					builder.appendSql("SELECT TOP "+ pageSize*pageNo +" FID, FLONGNUMBER, FCURPROJECT, FISENABLE FROM T_FDC_COSTACCOUNT where ");
					builder.appendParam("FCURPROJECT", projectMap.keySet().toArray());
					builder.appendSql(" order  by fid) t order by fid desc ");//sqlserver db2 �ӱ���� by hpw 2010.10.25
					rs = builder.executeQuery();
					while(rs.next()){
						caMap = new HashMap();
						idSet.add(rs.getString(1));
						caMap.put("id", rs.getString(1));
						caMap.put("isEnable",Boolean.valueOf(rs.getBoolean(4)));
						String key = rs.getString(3)+"_"+rs.getString(2);
						accMap.put(key, caMap);
					}		
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}

		
		List errorList = insertCostAccount(ctx,projectMap.keySet(),cac,accMap,projectMap,true);

		String sql = "update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '" + orgPK.toString() + "' and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		setSaleCategoryCostAccountUpdate(ctx);
		
		return errorList;
	}

	protected void _disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException {
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		// �����Ƿ���Ҫ�жϣ���ǰ�Ƿ�ΪԴ��������
		// ��ȡԴ(��ǰ��֯�µĳɱ���Ŀ����)
		CostAccountCollection cac = this.getCostAccountOrgOwn(ctx, orgPK.toString());
		HashSet lnUps = new HashSet();
		for (int i = 0; i < cac.size(); i++) {
			lnUps.add(cac.get(i).getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		if (lnUps.size() != 0) {
			// ɾ�������¼��������ظ�Դͷ�����ݼ���
			filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", lnUps, CompareType.INCLUDE));
			iCostAccount.delete(filter);
		}
		// ɾ����ӷ�����ȥ�ļ�¼,��ɾ����ǰ��֯�ڵ������й�����Ŀ�µķ�Դ����
		CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where fullOrgUnit.id = '" + orgPK.toString() + "'");
		HashSet lnUpsProject = new HashSet();
		for (int i = 0; i < cpc.size(); i++) {
			lnUpsProject.add(cpc.get(i).getId().toString());
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", lnUpsProject, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));
		iCostAccount.delete(filter);
		String sql = "update t_fdc_costAccount set fAssigned = 0  where ffullOrgUnit = '" + orgPK.toString() + "' and fisSource = 1 and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
	}

	// ----------------------------------------------------------------------------------------------------------------
	/**
	 * ��ȡָ����֯�������õĳɱ���Ŀ����,�������е��Լ����������!
	 * 
	 * @throws EASBizException
	 */
	private CostAccountCollection getOrgCostAccounts(Context ctx, String orgPK) throws BOSException, EASBizException {
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		// ���е�,����ǰָ����֯ΪԴ��
		CostAccountCollection cac = this.getEnCostAccountOrgOwn(ctx, orgPK);
		CostAccountCollection caac = iCostAccount.getCostAccountCollection("select * where fullOrgUnit.id = '" + orgPK + "' and isSource = 0 and isEnabled = 1 order by longNumber");
		CostAccountCollection tmp;
		// CostAccountInfo cai;
		if (caac != null && caac.size() != 0) {
			tmp = new CostAccountCollection();
			for (int i = 0; i < caac.size(); i++) {
				CostAccountInfo cai = null;
				cai = iCostAccount.getCostAccountInfo(new ObjectUuidPK(caac.get(i).getSrcCostAccountId()));
				if (cai != null) {
					tmp.add(cai);
				}
			}
			cac.addCollection(tmp);
		}
		return cac;
	}

	/**
	 * ��ȡָ��������Ŀ�������õĳɱ���Ŀ����,�������е��Լ����������!
	 * 
	 * @throws EASBizException
	 */
	private CostAccountCollection getProjectCostAccounts(Context ctx, String projectPK) throws BOSException, EASBizException {
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		// ���е�,����ǰָ��������ĿΪԴ��
		CostAccountCollection cac = this.getEnCostAccountProjectOwn(ctx, projectPK);
		CostAccountCollection caac = iCostAccount.getCostAccountCollection("select * where curProject.id = '" + projectPK + "' and isSource = 0 and isEnabled = 1 order by longNumber ");
		CostAccountCollection tmp;
		if (caac != null && caac.size() != 0) {
			tmp = new CostAccountCollection();
			EntityViewInfo evi;
			FilterInfo filter;
			CostAccountCollection costAccountCollection;
			for (int i = 0; i < caac.size(); i++) {
				evi = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", caac.get(i).getSrcCostAccountId()));
				evi.setFilter(filter);
				// ������������,��ʱ����Ҫ��srcCostAccountId�������Ը���Ϊ��������
				// cai = new CostAccountInfo();
				// cai.setId(BOSUuid.read(caac.get(i).getSrcCostAccountId()));
				// tmp.add(cai);
				costAccountCollection = iCostAccount.getCostAccountCollection(evi);
				if (costAccountCollection.size() != 0) {
					tmp.add(costAccountCollection.get(0));
				}
			}
			cac.addCollection(tmp);
		}

		return cac;
	}

	// ������
	/**
	 * @deprecated
	 * @param ctx
	 * @param cai
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private CostAccountCollection disAssignSelectCostAccount(Context ctx, CostAccountInfo cai) throws BOSException, EASBizException {
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountCollection say = new CostAccountCollection();
		if (cai.isIsSource()) {
			// ɾ�����е��Ը�caiΪԴ����
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getId().toString(), CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isSource", Boolean.valueOf(false), CompareType.EQUALS));

			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("*"));
			evi.getSelector().add(new SelectorItemInfo("parent.*"));
			evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
			evi.getSelector().add(new SelectorItemInfo("curProject.*"));

			CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
			// CostAccountInfo costAccountInfo;
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("assigned"));
			if (costAccountCollection.size() != 0) {
				CostAccountCollection tmp;
				for (int i = 0; i < costAccountCollection.size(); i++) {
					tmp = iCostAccount.getCostAccountCollection("where parent.id = '" + costAccountCollection.get(i).getId().toString() + "'");
					if (tmp.size() != 0) { // ������Ϊparent
						say.add(costAccountCollection.get(i));
					} else {
						BizReference ref = null;
						try {
							ref = ReferenceDAO.getReference(ctx, BOSUuid.read(costAccountCollection.get(i).getId().toString()));
						} catch (Exception e) {
							throw new ObjectReferedException(ref, e);
						}

						if (ref != null) {
							say.add(costAccountCollection.get(i));
						} else {
							iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));// ɾ�������¼
						}
					}
				}
			}
			costAccountCollection = iCostAccount.getCostAccountCollection(evi);
			SelectorItemCollection selector1 = new SelectorItemCollection();
			selector1.add(new SelectorItemInfo("assigned"));
			if (costAccountCollection.size() == 0) {
				cai.setAssigned(false);
				iCostAccount.updatePartial(cai, selector1);// ����Դ�ķ���״̬
			}

		} else {//�Է�Դ��Ŀ������
			if (cai.getFullOrgUnit() != null) {// ����ǹҿ�����֯��
				CurProjectCollection cpc = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id where fullOrgUnit.id = '" + cai.getFullOrgUnit().getId().toString() + "' ");
				if (cpc.size() == 0) {// �������֯��û�й�����Ŀ,������֮֯��ķ�����
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
					HashSet hs = getSubOrgUnit(ctx, cai.getFullOrgUnit().getLongNumber());
					if (hs.size() != 0) {//���¼���֯,���¼���֯������
						filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", hs, CompareType.INCLUDE));

						evi.setFilter(filter);
						evi.getSelector().add(new SelectorItemInfo("*"));
						evi.getSelector().add(new SelectorItemInfo("parent.*"));
						evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
						evi.getSelector().add(new SelectorItemInfo("curProject.*"));
						CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
						// CostAccountInfo costAccountInfo;
						SelectorItemCollection selector = new SelectorItemCollection();
						selector.add(new SelectorItemInfo("assigned"));
						if (costAccountCollection.size() != 0) {
							CostAccountCollection tmp;
							for (int i = 0; i < costAccountCollection.size(); i++) {
								tmp = iCostAccount.getCostAccountCollection("where parent.id = '" + costAccountCollection.get(i).getId().toString() + "'");
								if (tmp.size() != 0) { // ������Ϊparent
									say.add(costAccountCollection.get(i));
									// throw new
									// com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
								} else {
									iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));// ɾ�������¼
								}
							}
						}
						costAccountCollection = iCostAccount.getCostAccountCollection(evi);
						SelectorItemCollection selector1 = new SelectorItemCollection();
						selector1.add(new SelectorItemInfo("assigned"));
						if (costAccountCollection.size() == 0) {
							cai.setAssigned(false);
							iCostAccount.updatePartial(cai, selector1);
						}
					}

				} else {// �������֯���й�����Ŀ,������֯�Թ�����Ŀ�ķ�����
					HashSet hs = new HashSet();
					for (int i = 0; i < cpc.size(); i++) {
						hs.add(cpc.get(i).getId().toString());
					}
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
					evi.setFilter(filter);
					evi.getSelector().add(new SelectorItemInfo("*"));
					evi.getSelector().add(new SelectorItemInfo("parent.*"));
					evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
					evi.getSelector().add(new SelectorItemInfo("curProject.*"));
					CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
					if (costAccountCollection.size() != 0) {
						CostAccountCollection tmp;
						for (int i = 0; i < costAccountCollection.size(); i++) {
							tmp = iCostAccount.getCostAccountCollection("where parent.id = '" + costAccountCollection.get(i).getId().toString() + "'");
							if (tmp.size() != 0) { // ������Ϊparent
								say.add(costAccountCollection.get(i));
							} else {
								iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));// ɾ�������¼
							}
						}
					}
					// �ı�״̬
					costAccountCollection = iCostAccount.getCostAccountCollection(evi);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("assigned"));
					if (costAccountCollection.size() == 0) {
						cai.setAssigned(false);
						iCostAccount.updatePartial(cai, selector);
					}

				}
			} else if (cai.getCurProject() != null) {// ����ǹҿ��ڹ�����Ŀ��
				// �Թ�����Ŀ�¼�������
				CurProjectInfo cpi = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo("select id,fullOrgUnit.id where id = '" + cai.getCurProject().getId().toString() + "'");
				if (cpi != null) {
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcCostAccountId", cai.getSrcCostAccountId(), CompareType.EQUALS));
					HashSet hs = getSubProject(ctx, cai.getCurProject().getLongNumber(), cpi.getFullOrgUnit().getId().toString());
					if (hs.size() != 0) {
						filter.getFilterItems().add(new FilterItemInfo("curProject.id", hs, CompareType.INCLUDE));
						evi.setFilter(filter);
						evi.getSelector().add(new SelectorItemInfo("*"));
						evi.getSelector().add(new SelectorItemInfo("parent.*"));
						evi.getSelector().add(new SelectorItemInfo("fullOrgUnit.*"));
						evi.getSelector().add(new SelectorItemInfo("curProject.*"));
						CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evi);
						// CostAccountInfo costAccountInfo;
						if (costAccountCollection.size() != 0) {
							CostAccountCollection tmp;
							for (int i = 0; i < costAccountCollection.size(); i++) {
								tmp = iCostAccount.getCostAccountCollection("where parent.id = '" + costAccountCollection.get(i).getId().toString() + "'");
								if (tmp.size() != 0) { // ������Ϊparent
									say.add(costAccountCollection.get(i));
									// throw new
									// com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
								} else {
									iCostAccount.delete(new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));// ɾ�������¼
								}
							}
						}
						// �ı�״̬
						costAccountCollection = iCostAccount.getCostAccountCollection(evi);
						if (costAccountCollection.size() == 0) {
							SelectorItemCollection selector = new SelectorItemCollection();
							selector.add(new SelectorItemInfo("assigned"));
							cai.setAssigned(false);
							iCostAccount.updatePartial(cai, selector);
						}
					}
				}
			}
		}

		return say;
	}

	// Ѱ����֯�¼�
	/**
	 * 
	 * @param ctx
	 * @param longNumber
	 * @return
	 * @throws BOSException
	 */
	private HashSet getSubOrgUnit(Context ctx, String longNumber) throws BOSException {
		// ��ȡ����Ŀ��(�¼���֯)
		HashSet hs = new HashSet();
		FullOrgUnitCollection fouc = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection("select id where isCompanyOrgUnit = 1 and longNumber like '" + longNumber + "!%'");
		if (fouc.size() != 0) {
			for (int i = 0; i < fouc.size(); i++) {
				hs.add(fouc.get(i).getId().toString());
			}
		}
		return hs;
	}

	// Ѱ�ҹ�����Ŀ�¼�
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

	protected void _disAssignSelectOrgsCostAccount(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException {
		if (costAccounts.size() != 0) {

			ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
			CostAccountCollection cac = (CostAccountCollection) costAccounts;
			CostAccountCollection tmp;
			CostAccountCollection say = new CostAccountCollection();

			HashSet lnUps = new HashSet();
			// for (int i = 0; i < cac.size(); i++) {
			// lnUps.add(cac.get(i).getId().toString());
			// }

			// --
			for (int i = 0; i < costAccounts.size(); i++) {
				say.addCollection(disAssignSelectCostAccount(ctx, cac.get(i)));
			}
			// --

			if (say.size() != 0) {
				lnUps.clear();
				for (int i = 0; i < say.size(); i++) {
					lnUps.add(say.get(i).getName());
				}

				// ������ʾ
				throw (new FDCBasedataException(FDCBasedataException.DISASSIGN_ISREFERENCE, new Object[] { lnUps }));
			}
		}
	}

	protected void _disAssignSelectProjsCostAccount(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������

	}

	protected ArrayList _disAssignSelectOrgProject(Context ctx, IObjectCollection costAccounts) throws BOSException, EASBizException {
		ArrayList al = new ArrayList();
		ArrayList itemOnOrg = new ArrayList();
		ArrayList itemOnProject = new ArrayList();
		if (costAccounts.size() != 0) {
			CostAccountCollection cac = (CostAccountCollection) costAccounts;
			CostAccountCollection say = new CostAccountCollection();
			//�������Ҫͬ���������¼��ڵ�!
			//��ȡ�¼���Ŀ			
			ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
			CostAccountCollection cactemp = null;
			for (int i = 0; i < costAccounts.size(); i++) {
				cactemp = new CostAccountCollection();
				if (cac.get(i).getFullOrgUnit() != null) {//��֯�ڵ��µĿ�Ŀ����
					//��ȡ�ÿ�Ŀ�¼�					
					cactemp = iCostAccount.getCostAccountCollection("select *,fullOrgUnit.* where longNumber like '" + cac.get(i).getLongNumber() + "!%' and fullOrgUnit.id = '"
							+ cac.get(i).getFullOrgUnit().getId().toString() + "' order by longNumber desc");///

				} else if (cac.get(i).getCurProject() != null) {//������Ŀ�ڵ��µĿ�Ŀ����
					//��ȡ�ÿ�Ŀ�¼�
					cactemp = iCostAccount.getCostAccountCollection("select *,curproject.* where longNumber like '" + cac.get(i).getLongNumber() + "!%' and curproject.id = '"
							+ cac.get(i).getCurProject().getId().toString() + "' order by longNumber desc");///

				}
				if (cactemp.size() != 0) {
					for (int j = 0; j < cactemp.size(); j++) {
						say.addCollection(disAssignSelectCostAccount(ctx, cactemp.get(j)));
					}
				}
				say.addCollection(disAssignSelectCostAccount(ctx, cac.get(i)));
			}
			//////////////////////////
			CostAccountInfo cai = null;
			for (int i = 0; i < say.size(); i++) {
				cai = say.get(i);
				if (cai.getFullOrgUnit() != null) {
					itemOnOrg.add(cai.getFullOrgUnit().getName() + "��֯�ڵ�����" + cai.getLongNumber().replace('!', '.'));
				} else {
					itemOnProject.add(cai.getCurProject().getName() + "������Ŀ�ڵ���" + cai.getLongNumber().replace('!', '.'));
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
		if(model==null) return;
		CostAccountInfo acct=(CostAccountInfo)model;
		if(acct.getId()==null||acct.getLongNumber()==null) return;
		if(acct.isIsEnterDB()==isEnterDB){
			return;
		}
		
		try{
			if(isEnterDB){
				enterDB(ctx,acct);
			}else{
				cancleEnterDB(ctx,acct);
			}
		}catch(Exception e){
			throw new BOSException(e);
		}
		
	}
	
	private void enterDB(Context ctx,CostAccountInfo acct)throws BOSException, EASBizException, SQLException{
    	FDCSQLBuilder builder=new FDCSQLBuilder();
		//���������¼���������ѡ��
		String longNumber=acct.getLongNumber()+"!%";
		String id=acct.getId().toString();
		builder.appendSql("update t_Fdc_Costaccount set fisenterdb=? where fid in (select fid from (");
		builder.appendSql(" (Select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where charIndex(c1.flongnumber,c2.flongnumber)>0 and c1.flevel<c2.flevel and");
		builder.appendSql(" (c1.ffullorgunit=c2.ffullorgunit or (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and");
		builder.appendSql(" (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null and c2.fcurproject is null)))");
		builder.appendSql(" union (select ? as fid)");//����
		builder.appendSql(" union (select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
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
			builder.appendSql("update t_Fdc_Costaccount set fisenterdb=? where ");
			builder.addParam(new Integer(1));
			builder.appendParam("fid", set.toArray());
		}*/
		
		builder.executeUpdate(ctx);
	
	}
	
	private void cancleEnterDB(Context ctx,CostAccountInfo acct)throws BOSException, EASBizException, SQLException{
		String longNumber=acct.getLongNumber()+"!%";
		String id=acct.getId().toString();
		
		FDCSQLBuilder builder=new FDCSQLBuilder();
		//�����¼�������
		builder.appendSql("update t_Fdc_Costaccount set fisenterdb=? where fid in (select fid from ");
		builder.appendSql(" ((select ? as fid)");
		builder.appendSql(" union (select c1.fid From t_Fdc_Costaccount c1,(Select t.fid,t.ffullorgunit,t.fcurproject,t.flevel,t.flongnumber From");
		builder.appendSql(" t_Fdc_Costaccount t Where t.fid=?)c2 Where c1.flongnumber like ? and (c1.ffullorgunit=c2.ffullorgunit or");
		builder.appendSql(" (c1.ffullorgunit is null and  c2.ffullorgunit is null)) and (c1.fcurproject=c2.fcurproject or (c1.fcurproject is null");
		builder.appendSql(" and c2.fcurproject is null)))) a );");
		
		builder.addParam(new Integer(0));
		builder.addParam(id);
		builder.addParam(id);
		builder.addParam(longNumber);
		builder.executeUpdate(ctx);

	//�����ϼ�
		
		builder.clear();
		builder.appendSql("select fparentid from T_FDC_CostAccount where fid=?");
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
			builder.appendSql("select fisenterdb from T_FDC_CostAccount where fparentid=? and fisenterdb=1");
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
				builder.appendSql("select fparentid from T_FDC_CostAccount where fid=?");
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
			builder.appendSql("update T_FDC_CostAccount set FIsEnterDB=? where ");
			builder.addParam(new Integer(0));
			builder.appendParam("fid", set.toArray());
			builder.executeUpdate(ctx);
		}
	}

	/**
	 * �𼶷���
	 */
	protected List _assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException {
	
		FDCUtils.lockAssignCostAccount(ctx);
		
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		//��ȡ��ǰԴͷ��֯�������õĳɱ���Ŀ����,�������е��Լ����������
		CostAccountCollection cac = iCostAccount.getCostAccountCollection("select * where fullOrgUnit.id = '" + orgPK.toString() + "' and isEnabled = 1 order by longNumber");

		//��ȡ��ѡ��Ŀ���ϵ�displayName���ԣ��Ա�������Ϣʹ��
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
		//��ȡ��ѡĿ�����(��֯����Ŀ)���������п�Ŀ������֯ID+��Ŀ������ΪKEY����Ŀ����Ϊֵ���浽accMap
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(isProjectSet ? "curProject.id" : "fullOrgUnit.id", nodeIds, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isEnabled");
		view.getSelector().add("longNumber");
		view.getSelector().add(isProjectSet?"curProject.id":"fullOrgUnit.id");
		
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(view);
		Map accMap = new HashMap();
		Map caMap = null;
		String ownerId = null;
		for (Iterator iter = costAccountCollection.iterator(); iter.hasNext();) {
			CostAccountInfo element = (CostAccountInfo) iter.next();
			caMap = new HashMap();
			caMap.put("id",element.getId().toString());
			caMap.put("isEnable",Boolean.valueOf(element.isIsEnabled()));
			ownerId = isProjectSet ? element.getCurProject().getId().toString() : element.getFullOrgUnit().getId().toString();
			accMap.put(ownerId + "_" + element.getLongNumber(), caMap);
		}
		
		List errorList = insertCostAccount(ctx,nodeIds,cac,accMap,projectMap,isProjectSet);
		
		String sql = "update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '"
				+ orgPK.toString()
				+ "' and fisEnable = 1  ";
		DbUtil.execute(ctx, sql);
		
		//������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
		
		setSaleCategoryCostAccountUpdate(ctx);
		
		return errorList;
	}
	
	/**
	 * @deprecated
	 * @param ctx
	 * @param nodeIds
	 * @param cac
	 * @param accMap
	 * @param ownerMap
	 * @param isProjectSet
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private List insertCostAccount(Context ctx,Set nodeIds,CostAccountCollection cac,Map accMap,Map ownerMap,boolean isProjectSet) throws BOSException, EASBizException {
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
			 * ��set���ڽ����ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ
			 */
			Set acctIdSet=new HashSet();
			Iterator tor = nodeIds.iterator();
			String longNumber = "";
			while (tor.hasNext()) {		//������ѡĿ����֯����Ŀ����
				String nodeId = tor.next().toString();
				for (int j = 0; j < cac.size(); j++) {		//������ǰԴͷ��֯���������õĿ�Ŀ
					String comb = nodeId + "_" + cac.get(j).getLongNumber();
					//�����ǰ��֯����Ŀ��û�д˿�Ŀ�������
					if (!accMap.containsKey(comb)) {

						CostAccountInfo info = cac.get(j);
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
									//�ϼ���Ŀ����Ѿ�����ҵ������(���ѱ�����,���ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ���ò��迼��)���򱾴β����������������ӿ�Ŀ
									BizReference reference = ReferenceDAO.getReference(ctx,BOSUuid.read(parentId));
									if(reference != null && ownerMap != null) {
										if(!reference.getRefEntityName().equalsIgnoreCase("CostAccountWithAccount")){
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
						insertStmt.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcCostAccountId());	//FSRCCOSTACCOUNTID
						String type = info.getType() == null ? null : info
								.getType().getValue();
						type = info.getLevel() == 1 ? null : type;
						insertStmt.setString(x++, type);		//TYPE
						insertStmt.setBoolean(x++, info.isIsCostAccount()); // isIsCostAccount
						insertStmt.setString(x++, info.getCodingNumber()); // codingNumber  by sxhong 2008-10-20 18:38:43
						parentMap.put(info.getLongNumber(), id);
						
						info.setId(uuid);
						
						//R110518-0151:���ŷ���ɱ���Ŀ�����¼�����ԴID����ȷ��
						info.setIsSource(false);
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
			
			if(acctIdSet.size()>0){
				Map param=new HashMap();
				param.put("acctIdSet", acctIdSet);
				CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
			}
		} catch (SQLException ex) {
			throw new BOSException(ex);
		} finally {
			parentMap.clear();
			SQLUtils.cleanup(insertStmt, connection);
			SQLUtils.cleanup(updateStmt, connection);
		}
		return errorList;
	}
	
	public boolean _checkCurrentCostAccountIsDetailsNode(Context ctx, String id, String orgId) throws BOSException {
		String sql = "select count(1) nodesize  from T_FDC_CostAccount where (ffullorgunit = ? or fcurproject = ?) and fparentid = ? "; 
		int size = 0 ;
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx, sql ,new Object[]{orgId,orgId,id});
			if(rowSet.next()){
				size = rowSet.getInt("nodesize");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}
		return size > 0 ? false : true;				
	}

	public boolean _updateStageData(Context ctx, Set idSet, boolean isChecked) throws BOSException {
		
		if(idSet == null || idSet.isEmpty()){
			return false ;
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i=0;
		int size = idSet.size();
		while(iter.hasNext()){
			String id = (String)iter.next();
			filter.append("'").append(id).append("'");
			if(i<size-1){
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		
		StringBuffer sql = new StringBuffer();
		
		if(isChecked){
			sql.append("update T_FDC_Accountstage set fvalue = 1");
		}else{
			sql.append("update T_FDC_Accountstage set fvalue = 0") ;
		}
		sql.append(" where fid in  ");
		sql.append(filter.toString());		
		DbUtil.execute(ctx, sql.toString());			
		return true ;
	}
	
	public void _updateToLeafNode(Context ctx, Set idSet) throws BOSException {
		if(idSet == null || idSet.isEmpty()){
			return ;
		}
		String filter = this.SetConvertToString(idSet);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("update T_FDC_COSTACCOUNT SET FISLEAF = 1 ");
		sql.append(" where fid in  ");
		sql.append(filter);
		DbUtil.execute(ctx, sql.toString());			
	}
	
	/**
	 * @param idSet
	 * @return
	 */
	private String SetConvertToString(Set idSet) {		
		if(idSet == null || idSet.isEmpty()){
			return "" ;
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i=0;
		int size = idSet.size();
		while(iter.hasNext()){
			String id = (String)iter.next();
			filter.append("'").append(id).append("'");
			if(i<size-1){
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		return filter.toString();
	}
	
	/**
	 * //������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
	 * @param ctx
	 * @throws BOSException
	 */
	private void setSaleCategoryCostAccountUpdate(Context ctx) throws BOSException {
		
		String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
	}
	private Set costSrcNull;
	
	/**
	 * �缶����
	 * @param ctx
	 * @param orgSet
	 * @param projectSet
	 * @return
	 * @throws BOSException
	 */
	public List _assignCostAccountBatch(Context ctx, Set orgSet , 
						Set projectSet ,Set costAccountList ,Map isAddMap ) throws BOSException{
		
		
		EntityViewInfo viewF = new EntityViewInfo();
		viewF.getSelector().add("fullOrgUnit.id");
		viewF.getSelector().add("longNumber");
		
		FDCUtils.lockAssignCostAccount(ctx);
		
		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
		
		if(company == null){
			return null ;
		}
		try {
			CostAccountCollection costAccountCol = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(
					"select fullOrgUnit.id,longNumber where srcCostAccountId is null");
			costSrcNull = new HashSet();
			for (int i = 0; i < costAccountCol.size(); i++) {
				CostAccountInfo accountInfo = costAccountCol.get(i);
				if (accountInfo.getFullOrgUnit() != null) {
					costSrcNull.add(accountInfo.getFullOrgUnit().getId().toString() + "_" + accountInfo.getLongNumber());
				}
			}
			//�����빴ѡ����صĿ�Ŀ
			/**
			 * cac��Ŀ�Ŀid�Ǳ�ѡ�еĵ�ǰ��֯�Ŀ�Ŀ���Լ���ѡ�еĿ�Ŀ����Դ��Ŀ
			 */
			CostAccountCollection cac = getCostAccountBySet(ctx, costAccountList,company.getId().toString());
			List errorListOrg = new ArrayList();
			Map costAccountOrgs = new HashMap();
			Map costAccountProject = new HashMap();
			//��֯
			if(orgSet != null && !orgSet.isEmpty()){
				
				Map accountIsHasMap = getOrgIsAssignedAccountList(ctx,orgSet);
				errorListOrg = this.updateCostAccountByProjectOrOrg(ctx, orgSet, cac, costAccountOrgs, accountIsHasMap, false ,isAddMap);
				
				if(costAccountOrgs != null &&  !costAccountOrgs.isEmpty()){
					
					String sql = "update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '"
						+ company.getId().toString()
						+ "' and fisSource = 1 and fisEnable = 1  "
						+ " and fid in "+SetConvertToString(new HashSet(costAccountOrgs.values()));
					DbUtil.execute(ctx, sql);
				}
				
				
			}
			List errorListProject = new ArrayList();
			//��Ŀ
			if(projectSet != null && !projectSet.isEmpty()){
				
				Map accountIsHasMap = getProjectIsAssignedAccountList(ctx,projectSet);
				errorListProject = this.updateCostAccountByProjectOrOrg(ctx, projectSet, cac,costAccountProject, accountIsHasMap, true ,isAddMap);
				
				if(costAccountProject != null && !costAccountProject.isEmpty()){
					//�༶�ɱ��ܿأ�ȥ��where�����and fissource=1,��Ϊ�����������֯���������·����Ŀ by hpw 2011.12.1
					String sql = "update t_fdc_costAccount set fAssigned = 1  where ffullOrgUnit = '"
						+ company.getId().toString()
						+ "' and fisEnable = 1  "
						/*cacʵ���벻����ΪʲôҪ���ϵ�ǰ��֯�ϼ��Ŀ�Ŀ,�ᵼ���ϼ����������Ŀ�Ŀ�����¼�����ʱ����״̬Ϊδ����
							 * ���޸�ԭ���߼���ֻ����ѡ�еĿͻ��ķ���״̬
							 */
							+ " and fid in " + SetConvertToString(costAccountList);
					
					DbUtil.execute(ctx, sql);
				}
			}

			
			setSaleCategoryCostAccountUpdate(ctx);
			//��Ŀ����50����û50����Ŀִ��һ��
			if (projectSet.size() > 50) {
				Set set = new HashSet();
				for (int i = 1; i < projectSet.size() + 1; i++) {
					set.add(projectSet.iterator().next());
					if (i % 50 == 0) {
						afterAssgin(ctx, set);
						set = new HashSet();
						continue;
					} else if (i == projectSet.size()) {
						afterAssgin(ctx, set);
					}
				}
			} else {
				afterAssgin(ctx, projectSet);
			}
			

			return   (List) org.apache.commons.collections.CollectionUtils.union(errorListOrg, errorListProject);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		}
	}

	/**
	 * ������
	 * @param projectSet 
	 * @param orgSet 
	 * @param ctx 
	 * @throws BOSException 
	 * @Author��keyan_zhao
	 * @CreateTime��2013-3-21
	 */
	private void afterAssgin(Context ctx, Set curProjectIds) throws BOSException {
		if (curProjectIds.isEmpty()) {
			return;
		}
		long l = System.currentTimeMillis();
		Set isRefBizBillSet = this.getRefBizBillList(ctx);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo costActFilter = new FilterInfo();
	 	costActFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectIds, CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("isLeaf");
		view.getSelector().add("longNumber");
		view.getSelector().add("fullOrgUnit.id");
		view.getSelector().add("curProject.id");

		view.setFilter(costActFilter);
		CostAccountCollection orgCostColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		Map orgCostActMap = new HashMap();
		for (int i = 0, size = orgCostColl.size(); i < size; i++) {
			CostAccountInfo orgCostInfo = orgCostColl.get(i);

			String tempId = "";
			if (orgCostInfo.getFullOrgUnit() != null) {
				tempId = orgCostInfo.getFullOrgUnit().getId().toString();
			}
			if (orgCostInfo.getCurProject() != null) {
				tempId = orgCostInfo.getCurProject().getId().toString();
			}
			if (orgCostActMap.containsKey(tempId)) {
				Map records = (Map) orgCostActMap.get(tempId);
				records.put(orgCostInfo.getLongNumber(), orgCostInfo);
			} else {
				Map records = new TreeMap();

				records.put(orgCostInfo.getLongNumber(), orgCostInfo);
				orgCostActMap.put(tempId, records);
			}
		}


		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String changeIdSql = "update t_fdc_costaccount set fid=? where fid=?";
		String parentSql = "update t_fdc_costaccount set fparentid=? where fparentid=?";
		String updateSrcCostActSQLa = "update t_fdc_costaccount set fsrccostaccountid=? where fsrccostaccountid=?";
		List paramsO = new ArrayList();
		List paramsT = new ArrayList();
		List paramsTH = new ArrayList();

		for (Iterator iter = curProjectIds.iterator(); iter.hasNext();) {
			//ȡÿ����֯��Ӧ�ĳɱ���Ŀ
			Map records = (Map) orgCostActMap.get(iter.next());
			if (records == null) {
				continue;
			}
			//ȡ�ɱ���Ŀ�Ķ���
			for (Iterator nextIter = records.entrySet().iterator(); nextIter.hasNext();) {
				Entry entry = (Entry) nextIter.next();
				CostAccountInfo costActInfo = (CostAccountInfo) entry.getValue();

				if (costActInfo != null) {
					if (isRefBizBillSet.contains(costActInfo.getId().toString())) {
						if (!costActInfo.isIsLeaf()) {
							//�����������ϸ�Ŀ�Ŀ���¼�ֻ���õ�������ϸ�ڵ�ĵ�һ���ڵ�
							Iterator treeIter = records.entrySet().iterator();
							while (treeIter.hasNext()) {
								Entry treeEntry = (Entry) treeIter.next();

								String treeKey = (String) treeEntry.getKey();
								CostAccountInfo treeBizInfo = (CostAccountInfo) treeEntry.getValue();

								if (treeKey.startsWith(costActInfo.getLongNumber() + "!") && treeBizInfo.isIsLeaf()) {

									String detailNodeId = treeBizInfo.getId().toString();
									String tempId = BOSUuid.create(costActInfo.getBOSType()).toString();
									BOSUuid parentPK = costActInfo.getId();

									//									List params = new ArrayList();
									paramsO.add(Arrays.asList(new String[] { tempId, parentPK.toString() }));
									paramsO.add(Arrays.asList(new String[] { parentPK.toString(), detailNodeId }));
									paramsO.add(Arrays.asList(new String[] { detailNodeId, tempId }));
									//									paramsO.add(params);

									//									List parentParams = new ArrayList();
//									paramsT.add(Arrays.asList(new String[] { detailNodeId, costActInfo.getId().toString() }));
									//									paramsT.add(parentParams);

									/**
									 * ���������ϸ��ĿΪԴ�Ŀ�Ŀ��Դ��Ŀ
									 */

									//									List sqlParams = new ArrayList();
//									paramsTH.add(Arrays.asList(new String[] { detailNodeId, parentPK.toString() }));
									//									paramsTH.add(sqlParams);
									break;
								}
							}
						}
					}
				}
			}
		}
		builder.executeBatch(changeIdSql, paramsO);
		//����������..��ⲻ��	ken_liu
//		builder.executeBatch(changeIdSql, paramsT);
//		builder.executeBatch(changeIdSql, paramsTH);

	}
	/**
	 * ������
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractCostAccountFacadeControllerBean#_disAssignAccountBatch(com.kingdee.bos.Context, java.util.Set, java.util.Set, java.util.Map)
	 */
	protected List _disAssignAccountBatch(Context ctx, Set curOrgIds, Set curProjectIds, Map costAccountList) throws BOSException {
		List msgList = new ArrayList();
		Set billList = getRefBizBillList(ctx);
		if (curOrgIds == null) {
			curOrgIds = new HashSet();
		}
		if (curProjectIds == null) {
			curProjectIds = new HashSet();
		}
		Map orgUnitMap = new HashMap();
		Map idName = new HashMap();
		IFullOrgUnit orgInstance = FullOrgUnitFactory.getLocalInstance(ctx);
		if (curOrgIds != null && curOrgIds.size() > 0) {
			FullOrgUnitCollection orgUnitCollection = orgInstance.getFullOrgUnitCollection("select id,longNumber,name where id in "
					+ SetConvertToString(curOrgIds));
			for (int i = 0; i < orgUnitCollection.size(); i++) {
				FullOrgUnitInfo orgUnitInfo = orgUnitCollection.get(i);
				orgUnitMap.put(orgUnitInfo.getId().toString(), orgUnitInfo.getLongNumber());
				idName.put(orgUnitInfo.getId().toString(), orgUnitInfo.getName());
				
			}
		}

		Map curProMap = new HashMap(); 
		ICurProject curInstance = CurProjectFactory.getLocalInstance(ctx);
		if (curProjectIds != null && curProjectIds.size() > 0) {
			CurProjectCollection collection = curInstance.getCurProjectCollection("select id,longNumber,name where id in "
					+ SetConvertToString(curProjectIds));
			for (int i = 0; i < collection.size(); i++) {
				CurProjectInfo orgUnitInfo = collection.get(i);
				curProMap.put(orgUnitInfo.getId().toString(), orgUnitInfo.getLongNumber());
				idName.put(orgUnitInfo.getId().toString(), orgUnitInfo.getName());
			}
		}

		curOrgIds.addAll(curProjectIds);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		//��ɾ����Ŀ����key��Ŀ������֯id��Value ��Set����Ŀ������
		Map deleteMap = new HashMap();
		//����ɾ����Ŀ����key��Ŀ������֯id��Value ��Set����Ŀ������
		Map cantDelMap = new HashMap();

		//����Ϊ"δ����"�Ŀ�Ŀ
		Set disAssignCAIDs = new HashSet();
		
		ICostAccount localInstance = CostAccountFactory.getLocalInstance(ctx);
		for (Iterator ite = curOrgIds.iterator(); ite.hasNext();) {
			String curOrOrgID = (String) ite.next();
			String name = (String) idName.get(curOrOrgID);
				for (Iterator iteT = costAccountList.keySet().iterator(); iteT.hasNext();) {
					String longNumber = (String) iteT.next();
					view = new EntityViewInfo();
					filter = new FilterInfo();

					filter.getFilterItems().add(new FilterItemInfo("id", billList, CompareType.INCLUDE));
					if (BOSUuid.read(curOrOrgID).getType().equals((new CurProjectInfo()).getBOSType())) {
						String curLongNumber = (String) curProMap.get(curOrOrgID);
						filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", curLongNumber));
						filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", curLongNumber + "!%", CompareType.LIKE));
					} else if (BOSUuid.read(curOrOrgID).getType().equals((new FullOrgUnitInfo()).getBOSType())) {
						String curLongNumber = (String) orgUnitMap.get(curOrOrgID);
						
						/* modified by zhaoqin for R140604-0173 on 2014/07/01 start */
						//filter.getFilterItems().add(new FilterItemInfo("curProject.fullOrgUnit.longNumber", curLongNumber));
						filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", curLongNumber));
						//filter.getFilterItems().add(new FilterItemInfo("curProject.fullOrgUnit.longNumber", curLongNumber + "!%", CompareType.LIKE));
						filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", curLongNumber + "!%", CompareType.LIKE));
						/* modified by zhaoqin for R140604-0173 on 2014/07/01 end */
					}
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "!%", CompareType.LIKE));
					filter.setMaskString("#0 and (#1 or #2) and (#3 or #4 ) ");
					//filter.setMaskString("(#0 or #1) and (#2 or #3 ) ");
					try {
						if (localInstance.exists(filter)) {
							if (cantDelMap.containsKey(name)) {
								TreeSet set = (TreeSet) cantDelMap.get(name);
								set.add(longNumber.replaceAll("!", "."));
								cantDelMap.put(name, set);
							} else {
								Set set = new TreeSet();
								set.add(longNumber.replaceAll("!", "."));
								cantDelMap.put(name, set);
							}
						} else {
							if (deleteMap.containsKey(curOrOrgID)) {
								Set set = (Set) deleteMap.get(curOrOrgID);
								set.add(longNumber);
								deleteMap.put(curOrOrgID, set);
								/* modified by zhaoqin for BT880715 on 2015/05/20 */
								disAssignCAIDs.add(costAccountList.get(longNumber));
							} else {
								Set set = new HashSet();
								set.add(longNumber);
								deleteMap.put(curOrOrgID, set);
								/* modified by zhaoqin for BT880715 on 2015/05/20 */
								disAssignCAIDs.add(costAccountList.get(longNumber));
							}
						}
					} catch (EASBizException e) {
						throw new BOSException(e);
					}
				}

			
		}
		for (Iterator ite = deleteMap.keySet().iterator(); ite.hasNext();) {
			String curOrOrgID = (String) ite.next();
			Set numberSet = (Set) deleteMap.get(curOrOrgID);
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("longNumber", numberSet, CompareType.INCLUDE));
			if (BOSUuid.read(curOrOrgID).getType().equals((new CurProjectInfo()).getBOSType())) {
				//				filter.getFilterItems().add(new FilterItemInfo("curProject.id", curOrOrgID));
				String curLongNumber = (String) curProMap.get(curOrOrgID);
				filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", curLongNumber));
				filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", curLongNumber + "!%", CompareType.LIKE));
			} else if (BOSUuid.read(curOrOrgID).getType().equals((new FullOrgUnitInfo()).getBOSType())) {
				//				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", curOrOrgID));
				String curLongNumber = (String) orgUnitMap.get(curOrOrgID);
				
				/* modified by zhaoqin for R140604-0173 on 2014/07/01 start */
				//filter.getFilterItems().add(new FilterItemInfo("curProject.fullOrgUnit.longNumber", curLongNumber));
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", curLongNumber));
				//filter.getFilterItems().add(new FilterItemInfo("curProject.fullOrgUnit.longNumber", curLongNumber + "!%", CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", curLongNumber + "!%", CompareType.LIKE));
				/* modified by zhaoqin for R140604-0173 on 2014/07/01 end */
			}
			filter.setMaskString("#0 and (#1 or #2)");
			try {
				localInstance.delete(filter);
			} catch (EASBizException e) {
				throw new BOSException(e);
			}
		}
		
		/* modified by zhaoqin for BT880715 on 2015/05/20 */
		//��û�����õĿ�Ŀ��Ϊ"δ����"
		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
		if(null != company && null != company.getId() &&
				null != disAssignCAIDs && disAssignCAIDs.size() > 0) {
			String sql = "update t_fdc_costAccount ca set fAssigned = 0 "
				+ " where fisSource = 1 and fisEnable = 1  "
				+ " and not exists (select 1 from t_fdc_costAccount where fsrcCostAccountId = ca.fid ) "
				+ " and FFullOrgUnit = '" + company.getId().toString() + "' "
				+ " and fid in "+SetConvertToString(disAssignCAIDs);
			DbUtil.execute(ctx, sql);
		}
		
		msgList.add(cantDelMap);
		return msgList;
	}


	/**
	 * �缶������
	 * @param orgPK
	 */
	//	protected List _disAssignAccountBatch(Context ctx, Set curOrgIds 
	//				,Set curProjectIds ,Map costAccountList) throws BOSException {
	//		//������ʱ��Ҫ�ж��Ƿ�ǰ��֯�����˿�Ŀ���¼���֯������Ŀ�����������ʾ�ȷ��������֯�µ���Ŀ���ܷ����䵱ǰ��֯�Ŀ�Ŀ
	//		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
	//		// Ĭ���м��ţ����Բ�����Ϊ��
	//		// String groupID =
	//		// if(company == null){
	//		// return null ;
	//		// }
	//		
	//		List msgList = new ArrayList();
	//		
	//		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
	//
	//		Set isRefCostAccountList = getRefBizBillList(ctx);
	////		if (curProjectIds.size() > 0) {
	////			String hasUsed = checkProjectHasUsed(ctx, curProjectIds);
	////			if (hasUsed != null) {
	////				msgList.add(hasUsed);
	////				return msgList;
	////			}
	////		}
	//		
	//		EntityViewInfo allCostActView = new EntityViewInfo();
	//		FilterInfo allCostActFilter = new FilterInfo();
	//		
	//		allCostActView.getSelector().add("*");
	//		
	//		allCostActFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",company.getId().toString()));
	//		// ��ĿԴID��������ǰ��֯���� by hpw 12.3.1
	//		allCostActFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID));
	//		allCostActFilter.setMaskString("#0 or #1");
	//		allCostActView.setFilter(allCostActFilter);
	//		
	//		//��ǰ��֯�Ŀ�Ŀ
	//		CostAccountCollection allColl = iCostAccount.getCostAccountCollection(allCostActView);
	//		
	//		EntityViewInfo view = new EntityViewInfo();
	//		FilterInfo costActFilter = new FilterInfo();
	//		
	//		FilterInfo costOrgfilter = new FilterInfo();
	//		// ԴID
	//		Set srcIds = new HashSet();
	//		
	//		for(int i=0,size=allColl.size();i<size;i++){
	//			srcIds.add(allColl.get(i).getId().toString());
	//		}
	//		
	//		if(!curOrgIds.isEmpty() && !curProjectIds.isEmpty()){
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",curOrgIds,CompareType.INCLUDE));
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
	//			
	//			costActFilter.getFilterItems().add(new FilterItemInfo("srcCostAccountId",srcIds,CompareType.INCLUDE));
	//			
	//			costOrgfilter.setMaskString(" #0 or #1 ");
	//			
	//		}else if(!curOrgIds.isEmpty()){
	//			
	//			costActFilter.getFilterItems().add(new FilterItemInfo("srcCostAccountId",srcIds,CompareType.INCLUDE));
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",curOrgIds,CompareType.INCLUDE));
	//			
	//		}else if(!curProjectIds.isEmpty()){
	//			
	//			costActFilter.getFilterItems().add(new FilterItemInfo("srcCostAccountId",srcIds,CompareType.INCLUDE));
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
	//		}
	//		
	//		costActFilter.mergeFilter(costOrgfilter,"and");
	//		view.getSelector().add("*");
	//		view.getSelector().add("fullOrgUnit.*");
	//		view.getSelector().add("curProject.*");
	//		
	//		view.setFilter(costActFilter);
	//		//��ǰ������֯����Ŀ�Ŀ
	//		CostAccountCollection costColl =  iCostAccount.getCostAccountCollection(view);
	//		CostAccountInfo costInfo = null ;
	//		
	//		Map costActMap = new HashMap(); 
	//		Map clonMap = new HashMap(); 
	//		
	//		for(int i=0,size = costColl.size();i<size;i++){
	//			costInfo = costColl.get(i);
	//			
	//			String tempId = "" ;
	//			if(costInfo.getFullOrgUnit() != null){
	//				tempId = costInfo.getFullOrgUnit().getId().toString();
	//			}
	//			if(costInfo.getCurProject() != null){
	//				tempId = costInfo.getCurProject().getId().toString();
	//			}
	//			if(costActMap.containsKey(tempId)){
	//				Map records =  (Map) costActMap.get(tempId);
	//				Map clonRecords =  (Map) clonMap.get(tempId);
	//				
	//				records.put(costInfo.getLongNumber(),costInfo );
	//				clonRecords.put(costInfo.getLongNumber(),costInfo );
	//				costActMap.put(tempId, records);
	//				clonMap.put(tempId, clonRecords);
	//			}else{
	//				Map records = new TreeMap();
	//				Map clonRecords = new TreeMap();
	//				
	//				records.put(costInfo.getLongNumber(), costInfo);
	//				costActMap.put(tempId, records);
	//				
	//				clonRecords.put(costInfo.getLongNumber(), costInfo);
	//				clonMap.put(tempId, clonRecords);
	//			}
	//		}
	//		
	//		//------------------------------��֯����Ŀ��ȫ��Ŀ�ṹ-------------------------------------------------//
	//
	//		view = new EntityViewInfo();
	//		costActFilter = new FilterInfo();
	//		costOrgfilter = new FilterInfo();
	//		
	//		if(!curOrgIds.isEmpty() && !curProjectIds.isEmpty()){
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",curOrgIds,CompareType.INCLUDE));
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
	//			
	//			costOrgfilter.setMaskString(" #0 or #1 ");
	//			
	//		}else if(!curOrgIds.isEmpty()){
	//			
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",curOrgIds,CompareType.INCLUDE));
	//			
	//		}else if(!curProjectIds.isEmpty()){
	//			
	//			costOrgfilter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
	//		}
	//		costActFilter.mergeFilter(costOrgfilter,"and");
	//		view.getSelector().add("*");
	//		view.getSelector().add("fullOrgUnit.*");
	//		view.getSelector().add("curProject.*");
	//		
	//		view.setFilter(costActFilter);
	//		
	//		Map orgCostActMap = new HashMap(); 
	//		//��ǰ��֯����Ŀ�ѷ���Ŀ�Ŀ
	//		CostAccountCollection orgCostColl =  iCostAccount.getCostAccountCollection(view);
	//		CostAccountInfo orgCostInfo = null ;
	//		
	//		for(int i=0,size = orgCostColl.size();i<size;i++){
	//			orgCostInfo = orgCostColl.get(i);
	//			
	//			String tempId = "" ;
	//			if(orgCostInfo.getFullOrgUnit() != null){
	//				tempId = orgCostInfo.getFullOrgUnit().getId().toString();
	//			}
	//			if(orgCostInfo.getCurProject() != null){
	//				tempId = orgCostInfo.getCurProject().getId().toString();
	//			}
	//			if(orgCostActMap.containsKey(tempId)){
	//				Map records =  (Map) orgCostActMap.get(tempId);
	//				
	//				records.put(orgCostInfo.getLongNumber(),orgCostInfo );
	//			}else{
	//				Map records = new TreeMap();
	//				
	//				records.put(orgCostInfo.getLongNumber(), orgCostInfo);
	//				orgCostActMap.put(tempId, records);
	//			}
	//		}
	//		
	//		//-----------------------------------------------------------------------
	//		
	//		CostAccountInfo curCacInfo = null; 
	//		Map curCostAccountMap = new HashMap();
	//		
	//		for(int i=0,size=allColl.size();i<size;i++){
	//			curCacInfo = allColl.get(i);
	//			curCostAccountMap.put(curCacInfo.getId().toString(), curCacInfo);
	//			
	//		}
	//		//TODO�ȿ�һ���޸ĵ�Ч��
	////		boolean isAdd = curOrgIds.addAll(curProjectIds);
	//		
	////		Iterator iter = curOrgIds.iterator();
	//		
	//		//��ǰȡ������Ĳ��������Ե�ǰѡ������ϸ�򸸼��𲽵��ƣ��Դﵽ������ȡֱ���ӽڵ�����ݵ���ȷ�ԣ�����ȶ���Ŀ���з���������ٷ����������֯
	//		//ȡ��Ŀ����������
	//		//TODO
	//		
	//
	//		ICurProject curProjectSerivce = CurProjectFactory.getLocalInstance(ctx);
	//		IFullOrgUnit orgSerivce = FullOrgUnitFactory.getLocalInstance(ctx);
	//		
	//		EntityViewInfo projectView = null;
	//		
	//		List allOrgList = new ArrayList();
	//		if(curProjectIds != null && !curProjectIds.isEmpty()){
	//			
	//			SorterItemCollection sortItems = new SorterItemCollection();
	//			SorterItemInfo sorter = new SorterItemInfo("longNumber");
	//			sorter.setSortType(SortType.DESCEND);
	//			
	//			projectView = new EntityViewInfo();
	//			FilterInfo projectFilter = new FilterInfo();
	//			
	//			SelectorItemCollection projectSic = new SelectorItemCollection();
	//			projectSic.add("id");
	//			
	//			projectFilter.getFilterItems().add(new FilterItemInfo("id",curProjectIds,CompareType.INCLUDE));
	//			
	//			sortItems.add(sorter);
	//			projectView.setSorter(sortItems);
	//			
	//			projectView.setFilter(projectFilter);
	//			projectView.setSelector(projectSic);
	//			
	//			CurProjectCollection curProjectColl = curProjectSerivce.getCurProjectCollection(projectView); 
	//			CurProjectInfo curProjectInfo = null ;
	//			
	//			//������ID
	//			
	//			for(int i =0,size=curProjectColl.size();i<size;i++){
	//				curProjectInfo = curProjectColl.get(i);
	//				allOrgList.add(curProjectInfo.getId().toString());
	//				
	//			}
	//		}
	//		
	//		EntityViewInfo orgView = null ;
	//		//ȡ��֯������
	//		if(curOrgIds != null && !curOrgIds.isEmpty()){
	//			
	//			SorterItemCollection sortItems = new SorterItemCollection();
	//			SorterItemInfo sorter = new SorterItemInfo("longNumber");
	//			sorter.setSortType(SortType.DESCEND);
	//			
	//			orgView = new EntityViewInfo();
	//			FilterInfo orgFilter = new FilterInfo();
	//			
	//			SelectorItemCollection orgSic = new SelectorItemCollection();
	//			orgSic.add("id");
	//			
	//			orgFilter.getFilterItems().add(new FilterItemInfo("id",curOrgIds,CompareType.INCLUDE));
	//			
	//			sortItems.add(sorter);
	//			
	//			orgView.setSorter(sortItems);
	//			orgView.setSelector(orgSic);
	//			
	//			orgView.setFilter(orgFilter);
	//			
	//			FullOrgUnitCollection fullOrgColl = orgSerivce.getFullOrgUnitCollection(orgView); 
	//			FullOrgUnitInfo orgInfo = null ;
	//			
	//			orgView.setFilter(orgFilter);
	//			
	//			for(int i =0,size=fullOrgColl.size();i<size;i++){
	//				orgInfo = fullOrgColl.get(i);
	//				allOrgList.add(orgInfo.getId().toString());
	//				
//			}
//		}
	//		
	//		for(int i=0,size=allOrgList.size();i<size;i++){
	//			
	//			String orgId = (String) allOrgList.get(i);
	//			
	//			//�õ���ǰ��֯�����¼��ڵ㣬��������Ŀ����ϸ��û���ϼ�ֱ��return
	//			Map existsAccount = new TreeMap();
	//			
	//			if(BOSUuid.read(orgId).getType().equals((new CurProjectInfo()).getBOSType())){
	//				
	//				EntityViewInfo curView = new EntityViewInfo();
	//				FilterInfo curFilter = new FilterInfo();
	//				curFilter.getFilterItems().add(new FilterItemInfo("parent.id",orgId));
	//				curView.setFilter(curFilter);
	//				
	//				CurProjectCollection cpc = curProjectSerivce.getCurProjectCollection(curView);
	//				
	//				boolean isProjectLeafNode = false ;
	//				
	//				if(cpc.size()== 0){
	//					isProjectLeafNode = true ;
	//				}
	//				
	//				if(!isProjectLeafNode){
	//					
	//					Set prjIds = new HashSet();
	//					CurProjectInfo prjInfo = null ;
	//					for(int j=0;j<cpc.size();j++){
	//						prjInfo = cpc.get(j);
	//						
	//						prjIds.add(prjInfo.getId().toString());
	//						
	//					}
	//					//ȡ����ǰ��Ŀ�������¼��ڵ��ID�õ���Щid�����еĿ�Ŀ��
	//					
	//					StringBuffer sbSQL = new StringBuffer();
	//					
	//					sbSQL.append(" SELECT * FROM t_fdc_costaccount t1");
	//					sbSQL.append(" WHERE t1.fcurproject = ");
	//					sbSQL.append(" '"+orgId+"'");
	//					sbSQL.append(" AND EXISTS ");
	//					sbSQL.append(" ( SELECT * FROM t_fdc_costaccount t2 ");
	//					sbSQL.append(" WHERE t1.flongnumber = t2.flongnumber ");
	//					sbSQL.append(" AND t2.fcurproject IN ");
	//					sbSQL.append( SetConvertToString(prjIds));
	//					sbSQL.append(" ) ");
	//					
	//					IRowSet prjRows = DbUtil.executeQuery(ctx, sbSQL.toString());
	//					
	//					//����¼������ϼ�Ҫȡ���Ŀ�Ŀ�������Ŀ���ܱ�ȡ��
	//					
	//					try {
	//						prjRows.beforeFirst();
	//						while(prjRows.next()){
	//							String costAccounIdtWithPrj = prjRows.getString("fid");
	//							String costAccountNumberWithPrj = prjRows.getString("flongNumber");
	//							
	//							existsAccount.put(costAccountNumberWithPrj, costAccounIdtWithPrj);
	//						}
	//						prjRows.beforeFirst();
	//					} catch (SQLException e) {
	//						logger.error(e.getMessage(), e);
	//						throw new BOSException(e);
	//					}
	//				}
	//			}else if(BOSUuid.read(orgId).getType().equals((new FullOrgUnitInfo()).getBOSType())){
	//				//��ǰ��������֯�ǲ�����֯��ô�����֯���������¼���һ����Ŀ����ô��ϲ�����֯Ҫ���и���һ�����ж�
	//				//1,��ǰ��֯����֯�¼���֯������Ŀ
	//				//2,������֯�¶��ǲ�����֯
	//				//3,������֯��������Ŀ���в�����֯
	//				ICompanyOrgUnit companySrv = CompanyOrgUnitFactory.getLocalInstance(ctx);
	//				
	//				CompanyOrgUnitInfo curCompany = null;
	//				try {
	//					curCompany = companySrv.getCompanyOrgUnitInfo(new ObjectUuidPK(orgId));
	//				} catch (EASBizException e) {
	//					logger.error(e.getMessage(), e);
	//					throw new BOSException(e);
	//				}
	//				
	//				EntityViewInfo curView = new EntityViewInfo();
	//				SelectorItemCollection sic = new SelectorItemCollection();
	//				sic.add("*");
	//				
	//				curView.setSelector(sic);
	//				
	//				FilterInfo curFilter = new FilterInfo();
	//				curFilter.getFilterItems().add(new FilterItemInfo("longNumber",curCompany.getLongNumber()+"!%",CompareType.LIKE ));
	//				
	//				curView.setFilter(curFilter);
	//				CompanyOrgUnitCollection subCompanyNodes = companySrv.getCompanyOrgUnitCollection(curView);
	//				CompanyOrgUnitInfo subCompanyInfo = null ;
	//				
	//				Map companyMap = new TreeMap();
	//				
	//				for(int idx=0,isize=subCompanyNodes.size();idx<isize;idx++){
	//					subCompanyInfo = subCompanyNodes.get(idx);
	//					if(subCompanyInfo.getLongNumber().substring(
	//							0, subCompanyInfo.getLongNumber().lastIndexOf("!"))
	//								.equals(curCompany.getLongNumber())){
	//						
	//						companyMap.put(subCompanyInfo.getId().toString(),subCompanyInfo );
	//						
	//					}
	//				}
	//				
	//				StringBuffer subProjectSQL = new StringBuffer();
	//				subProjectSQL.append("select * from t_fdc_curproject  where ffullorgunit = ? and fparentid is null");
	//				
	//				IRowSet subLevelProjectsRowSet = DbUtil.executeQuery(ctx, subProjectSQL.toString(), new Object[]{orgId});
	//				
	//				Set prjSet = new HashSet();
	//				
	//				try {
	//					subLevelProjectsRowSet.beforeFirst();
	//					while(subLevelProjectsRowSet.next()){
	//						String prjId = subLevelProjectsRowSet.getString("fid");
	//						prjSet.add(prjId);
	//					}
	//					subLevelProjectsRowSet.beforeFirst();
	//					
	//				} catch (SQLException e) {
	//					logger.error(e.getMessage(), e);
	//					throw new BOSException(e);
	//				}
	//				
	//				StringBuffer sbSQL = new StringBuffer();
	//				
	//				if(!companyMap.isEmpty() && !prjSet.isEmpty()){
	//					
	//					sbSQL.append(" SELECT * FROM t_fdc_costaccount t1");
	//					sbSQL.append(" WHERE t1.ffullorgunit = ");
	//					sbSQL.append(" '"+orgId+"'");
	//					sbSQL.append(" AND EXISTS ");
	//					sbSQL.append(" ( SELECT * FROM t_fdc_costaccount t2 ");
	//					sbSQL.append(" WHERE t1.flongnumber = t2.flongnumber ");
	//					sbSQL.append(" AND ( t2.ffullorgunit IN ");
	//					sbSQL.append( SetConvertToString(companyMap.keySet()));
	//					sbSQL.append(" or t2.fcurProject IN ");
	//					sbSQL.append( SetConvertToString(prjSet));
	//					sbSQL.append(" )) ");
	//					
	//					
	//				}else if(!companyMap.isEmpty() && prjSet.isEmpty()){
	//					
	//					//ȡ����ǰ��Ŀ�������¼��ڵ��ID�õ���Щid�����еĿ�Ŀ��
	//					
	//					sbSQL.append(" SELECT * FROM t_fdc_costaccount t1");
	//					sbSQL.append(" WHERE t1.ffullorgunit = ");
	//					sbSQL.append(" '"+orgId+"'");
	//					sbSQL.append(" AND EXISTS ");
	//					sbSQL.append(" ( SELECT * FROM t_fdc_costaccount t2 ");
	//					sbSQL.append(" WHERE t1.flongnumber = t2.flongnumber ");
	//					sbSQL.append(" AND t2.ffullorgunit IN ");
	//					sbSQL.append( SetConvertToString(companyMap.keySet()));
	//					sbSQL.append(" ) ");
	//					
	//				}else if(companyMap.isEmpty() && !prjSet.isEmpty()){
	//					
	//					sbSQL.append(" SELECT * FROM t_fdc_costaccount t1");
	//					sbSQL.append(" WHERE t1.ffullorgUnit = ");
	//					sbSQL.append(" '"+orgId+"'");
	//					sbSQL.append(" AND EXISTS ");
	//					sbSQL.append(" ( SELECT * FROM t_fdc_costaccount t2 ");
	//					sbSQL.append(" WHERE t1.flongnumber = t2.flongnumber ");
	//					sbSQL.append(" AND t2.fcurproject IN ");
	//					sbSQL.append( SetConvertToString(prjSet));
	//					sbSQL.append(" ) ");
	//					
	//				}else{
	//					logger.info("no sub node");
	//				}
	//				
	//				try {
	//					if(sbSQL != null && !StringUtils.isEmpty(sbSQL.toString())){
	//						IRowSet actRows = DbUtil.executeQuery(ctx, sbSQL.toString());
	//						
	//						actRows.beforeFirst();
	//						while(actRows.next()){
	//							String costAccounIdtWithPrj = actRows.getString("fid");
	//							String costAccountNumberWithPrj = actRows.getString("flongNumber");
	//							
	//							existsAccount.put(costAccountNumberWithPrj, costAccounIdtWithPrj);
	//						}
	//						actRows.beforeFirst();
	//					}
	//				} catch (SQLException e) {
	//					logger.error(e.getMessage(), e);
	//					throw new BOSException(e);
	//				}
	//				
	//			}
	//			
	//			Map records = (Map) costActMap.get(orgId);
	//			Map clonRecords = (Map) clonMap.get(orgId);
	//			
	//			if(records == null || records.isEmpty()){
	//				continue;
	//			}
	//			Iterator selectIter = costAccountList.entrySet().iterator();
	//			
	//			while(selectIter.hasNext()){
	//				
	//				String selectedId = ((Entry) selectIter.next()).getValue().toString();
	//				
	//				//�õ�������֯�������Ŀ�Ŀ��longNumberƥ�䵱ǰҪɾ����ѡ����֯�Ŀ�Ŀ
	//				curCacInfo = (CostAccountInfo) curCostAccountMap.get(selectedId);
	//				String longNumber = curCacInfo.getLongNumber();
	//				
	//				//record���˵�ǰѭ�����ڵ���֯�ķ�������
	//				if(records == null || (CostAccountInfo) records.get(longNumber) == null){
	//					continue ;
	//				}
	//				CostAccountInfo cai = (CostAccountInfo) records.get(longNumber);
	//				
	//				String costActId = cai.getId().toString();
	//				String costActNumber = cai.getLongNumber();
	//				
	//				String accountWithCost =
	//					" select count(FAccountID) ct from T_FDC_CostAccountWithAccount where  FCostAccountID= '"+costActId+"'" ;
	//				IRowSet rows = DbUtil.executeQuery(ctx, accountWithCost);
	//				boolean isRef =false ;
	//				try {
	//					rows.beforeFirst();
	//					if(rows.next()){
	//						int count = rows.getInt("ct");
	//						if(count >0){
	//							isRef = true ;
	//						}
	//					}
	//					rows.beforeFirst();
	//				} catch (SQLException e) {
	//					logger.error(e.getMessage(), e);
	//					throw new BOSException(e);
	//				}
	//				
	//				boolean isHasAssagned =false ;
	//				if(!isRef){
	//					String existsSql =
	//						" select count(fid) refSize from T_FDC_COSTACCOUNT where  FSRCCostAccountID= '"+costActId+"'" ;
	//					
	//					IRowSet existsRows = DbUtil.executeQuery(ctx, existsSql);
	//					try {
	//						existsRows.beforeFirst();
	//						if(existsRows.next()){
	//							int count = existsRows.getInt("refSize");
	//							if(count >0){
	//								isHasAssagned = true ;
	//							}
	//						}
	//						existsRows.beforeFirst();
	//					} catch (SQLException e) {
	//						logger.error(e.getMessage(), e);
	//						throw new BOSException(e);
	//					}
	//				}
	//				
	//				if( isRefCostAccountList.contains(costActId)){
	//					doCheckBeDisassgned(costAccountList, clonMap, records, longNumber);
	//					msgList.add("ORG"+"_"+orgId+"_"+"REF1"+"_"+cai.getName());
	//					
	//					break;
	//				}							
	//				else if(isRef){
	//					doCheckBeDisassgned(costAccountList, clonMap, records, longNumber);
	//					msgList.add("ORG"+"_"+orgId+"_"+"REF1"+"_"+cai.getName());
	//					
	//					break;
	//				}
	//				else if(isHasAssagned){
	//					doCheckBeDisassgned(costAccountList, clonMap, records, longNumber);
	//					msgList.add("ORG"+"_"+orgId+"_"+"REF3"+"_"+cai.getName());
	//					
	//					break;
	//				}
	//				else if(!isRef){
	//					
	//					Iterator recordIter = clonRecords.entrySet().iterator();
	//					
	//					while(recordIter.hasNext()){
	//						Entry entry = (Entry) recordIter.next();
	//						
	//						String tempLongNumber = (String) entry.getKey();
	//						//������ڵ㱻������������ӽڵ㶼���ܱ�ɾ��
	//						if(tempLongNumber.lastIndexOf("!") == -1){
	//							continue ;
	//						}
	//						else if(longNumber.equals(
	//								tempLongNumber.substring(0, tempLongNumber.lastIndexOf("!")))){
	//							//�жϵ�ǰ�ж���ڵ㣬�ҽڵ�����һ������ɾ����������ø��ڵ㲻��ɾ��
	//							if(!costAccountList.containsKey(tempLongNumber)){
	//								String prentLongNumber = tempLongNumber.substring(0,tempLongNumber.lastIndexOf("!"));
	//								
	//								CostAccountInfo tempParentCai = (CostAccountInfo) clonRecords.get(prentLongNumber);
	//								tempParentCai.setExtendedProperty("isDeleted", String.valueOf(false));
	//								
	//								break;
	//								
	//							}else{//��ǰ��ɾ���Ľڵ�ֻ��һ���ڵ㣬��Ҫ��ɾ��
	//								Iterator tempIter = clonRecords.entrySet().iterator();
	//								
	//								while(tempIter.hasNext()){
	//									Entry tempEntry = (Entry) tempIter.next();
	//									
	//									String entryLongNumber = (String) tempEntry.getKey();
	//									if(!entryLongNumber.equals(tempLongNumber) && entryLongNumber.startsWith(tempLongNumber)){
	//										if(!costAccountList.containsKey(entryLongNumber)){
	//											
	//											CostAccountInfo tempParentCai = (CostAccountInfo) clonRecords.get(longNumber);
	//											tempParentCai.setExtendedProperty("isDeleted", String.valueOf(false));
	//										}
	//									}
	//								}
	//							}
	//						}else{
	//							if(costAccountList.keySet().contains(curCacInfo.getLongNumber())){
	//								curCacInfo.setExtendedProperty("isDeleted", String.valueOf("true"));
	//							}
	//						}
	//					}
	//				}
	//			}
	//			Map beDisassagnMap = new TreeMap();
	//			
	//			Iterator costActRecord = records.values().iterator();
	//			while(costActRecord.hasNext()){
	//				CostAccountInfo cai = (CostAccountInfo) costActRecord.next();
	//				if((cai.getExtendedProperties().get("isDeleted") == null 
	//							|| ( cai.getExtendedProperties().get("isDeleted") != null  
	//								&&  !cai.getExtendedProperties().get("isDeleted").equals("false")))
	//								&& costAccountList.keySet().contains(cai.getLongNumber())){
	//					beDisassagnMap.put(cai.getLongNumber(), cai);
	//				}
	//			}
	//			
	//			//��ǰ��������Ŀ�Ŀ��ѯ�Ƿ����½����ӽڵ㡣��������ܱ������䣬��������ʾ
	//			Iterator disIter = beDisassagnMap.entrySet().iterator();
	//			
	//			while(disIter.hasNext()){
	//				Entry tempEntry = (Entry) disIter.next();
	//				//�õ���ǰ��Ҫ��������ĳɱ���Ŀ 
	//				CostAccountInfo costAccount = (CostAccountInfo) tempEntry.getValue();
	//								
	//				String isDeleted = costAccount.getExtendedProperty("isDeleted");
	//				
	//				if(!StringUtils.isEmpty(isDeleted) &&  isDeleted.equals("false")){
	//					continue;
	//				}
	//				
	//				String longlongNumber = costAccount.getLongNumber();
	//				//ȫ�������֮֯�µ����гɱ���Ŀ�������������Լ�������
	//				Map allInAssagnMap = (Map) orgCostActMap.get(orgId);
	//				
	//				Iterator orgCostIter = allInAssagnMap.entrySet().iterator();
	//				//��ǰ��֯ȫ����Ŀ����
	//				while(orgCostIter.hasNext()){
	//					
	//					Entry orgCostEntry = (Entry) orgCostIter.next();
	//					String orgSubStrNumber = orgCostEntry.getKey().toString();
	//					CostAccountInfo tempInfo = (CostAccountInfo) orgCostEntry.getValue();
	//					
	//					//1,��֯�Լ������Ŀ�Ŀ��û��Դ���������Ŀ�ĸ�����Ҫ��������
	//					//2,��֯�������Ŀ�Ŀ�ĿԴ�ǲ�Ϊ�յģ���ʹ��ȫ��֯���ж�Ҫ��ɾ���Ŀ�Ŀ�µ��Ƿ����Ӽ�
	//					
	//					if(tempInfo.getLongNumber().lastIndexOf("!") == -1){
	//						continue ;
	//					}
	//					
	//					if((StringUtils.isEmpty(tempInfo.getSrcCostAccountId()) 
	//							&& longlongNumber.equals(tempInfo.getLongNumber().substring(
	//									0, tempInfo.getLongNumber().lastIndexOf("!"))))
	//						|| (tempInfo.getLongNumber().startsWith(longlongNumber) 
	//								&& !tempInfo.getLongNumber().equals(longlongNumber) 
	//									&& !beDisassagnMap.containsKey(tempInfo.getLongNumber())  
	//										&&  longlongNumber.equals(tempInfo.getLongNumber().substring(
	//												0, tempInfo.getLongNumber().lastIndexOf("!")))) 			
	//					){
	//						
	//						if(tempInfo.getParent() != null){
	//							//�õ���ǰû��Դ�Ŀ�Ŀ�ĸ�����Ŀid�Ƿ��ڱ���������б���
	//							String parentId = tempInfo.getParent().getId().toString();
	//							String parentLongNumber = tempInfo.getParent().getLongNumber();
	//
	//							//�����ǰ��Ŀ��û��Դ���½���Ŀ�ĸ�����Ŀ��Ҫ��ɾ�����б���
	//							if(costAccount.getId().toString().equals(parentId)){
	//								if(records.containsKey(longlongNumber)){
	//									costAccount.setExtendedProperty("isDeleted", String.valueOf("false"));
	//									
	//									if(!msgList.contains("ORG"+"_"+orgId+"_"+"REF3"+"_"+costAccount.getLongNumber().replace('!', '.'))){
	//										msgList.add("ORG"+"_"+orgId+"_"+"REF3"+"_"+costAccount.getLongNumber().replace('!', '.'));
	//									}
	//									
	//									//���ø���
	//									String tLongNumber =costAccount.getLongNumber(); 
	//									while(tLongNumber.lastIndexOf("!") != -1){
	//										int index =tLongNumber.lastIndexOf("!");
	//										tLongNumber = tLongNumber.substring(0, index);
	//										CostAccountInfo pInfo = (CostAccountInfo)records.get(tLongNumber);
	//										if(pInfo != null){
	//											pInfo.setExtendedProperty("isDeleted", String.valueOf("false"));
	//										}
	//										
	//									}
	//									if(costAccount.getLongNumber().lastIndexOf("!") == -1){
	//										continue ;
	//									}
	//									
	//									//�����Ӽ�
	//									String subLongNumber = costAccount.getLongNumber().substring(0, costAccount.getLongNumber().lastIndexOf("!")); 
	//									Iterator recordIter = records.entrySet().iterator();
	//									while(recordIter.hasNext()){
	//										Entry entry = (Entry) recordIter.next();
	//										
	//										String tempLongNumber = (String) entry.getKey();
	//										//������ڵ㱻������������ӽڵ㶼���ܱ�ɾ��
	//										if(tempLongNumber.startsWith(subLongNumber)){
	//											if(costAccountList.containsKey(subLongNumber)){
	//												CostAccountInfo tempCai = (CostAccountInfo) records.get(subLongNumber);
	//												if(tempCai != null){
	//													tempCai.setExtendedProperty("isDeleted", String.valueOf(false));
	//												}
	//											}
	//										}
//									}
	////									//�ѵ�ǰҪ������������еĸ���Ŀ���Ӽ���Ŀ���ò���ɾ��
	////									if(!msgList.contains("ORG"+"_"+orgId+"_"+"REF3"+"_"+tempInfo.getLongNumber().replace('!', '.'))){
	////										msgList.add("ORG"+"_"+orgId+"_"+"REF3"+"_"+tempInfo.getLongNumber().replace('!', '.'));
	////									}
	//									break ;
	//									
	//								}
	//							}
	//						}else{
	//							continue;
	//						}
	//					}else{
	//						
	//					}
	//				}
	//			}
	//			
	//			Map isDeleteNodes = new TreeMap();
	//			Set ids = new HashSet();
	//			costActRecord = records.values().iterator();
	//			while(costActRecord.hasNext()){
	//				CostAccountInfo cai = (CostAccountInfo) costActRecord.next();
	//				if((cai.getExtendedProperties().get("isDeleted") == null  
	//						|| ( cai.getExtendedProperties().get("isDeleted") != null  
	//								&&  !cai.getExtendedProperties().get("isDeleted").equals("false")))
	//								&& costAccountList.keySet().contains(cai.getLongNumber())
	//				){
	//					if(!existsAccount.containsKey(cai.getLongNumber())){
	//						isDeleteNodes.put(cai.getLongNumber(), cai);
	//						ids.add(cai.getId().toString());
	//					}
	//				}
	//			}
	//			
	//			//��Ҫɾ���Ľڵ�������ֵܽڵ㶼��ɾ�������ǵĸ���������ΪҶ�ӽڵ㣬�������ڵ�û���ֵܽڵ�û�б�ɾ����������
	//			Set parentSet = new HashSet();
	//			costActRecord = records.values().iterator();
	//			
	//			//ʹ����֯ȫ��Ŀ������
	//			Map allInAssagnMap = (Map) orgCostActMap.get(orgId);
	//			Iterator allCostAccountIter = allInAssagnMap.values().iterator();
	//			
	//			while(allCostAccountIter.hasNext()){
	//				CostAccountInfo cai = (CostAccountInfo) allCostAccountIter.next();
	//				
	//				//������ڵ���ڱ�ɾ�����б����򲻽����´�ѭ��
	//				if(isDeleteNodes.containsKey(cai.getLongNumber())){
	//					continue ;
	//				}
	//				
	//				boolean isAllInContain = true ;
	//				
	//				Iterator iitt = allInAssagnMap.keySet().iterator();
	//				String costLongNumber =  "" ;
	//				//ֱ���ӽڵ�ı���
	//				while(iitt.hasNext()){
	//					costLongNumber = (String) iitt.next();
	//					
	//					if(costLongNumber.indexOf("!") == -1 ){
	//						continue ;
	//					}
	//					//��һ��������ɶ������,����Ҫ�Ѹ��ڵ��������
	//					if(costLongNumber.substring(0, 
	//							costLongNumber.lastIndexOf("!")).equals(cai.getLongNumber())){
	//						if(!isDeleteNodes.containsKey(costLongNumber)){
	//							isAllInContain = false ;
	//							
	//						}
	//					}
	//				}
	//				//TODO ����Ҫ���ø��ڵ�
	//				if(isAllInContain){
	//					parentSet.add(cai.getId().toString());
	//				}
	//			}
	//			
	//			if(!ids.isEmpty()){
	//				FilterInfo filter = new FilterInfo();
	//				filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
	//				try {
	//					iCostAccount.delete(filter);
	//				} catch (EASBizException e) {
	//					logger.error(e.getMessage());
	//					throw new BOSException(e);
	//				}
	//			}
	//			
	//			_updateToLeafNode(ctx,parentSet);
	//		}
	//		//�������Ե�ǰ��Ŀ�����ж������Ŀ�ǲ��Ǳ����䣬û���������Ϊ0
	//		StringBuffer strBuf = new StringBuffer();
	//		
	//		strBuf.append(" SELECT t1.flongnumber ,t1.fassigned ,t1.fid fid ,t1.fparentid ");
	//		strBuf.append(" FROM t_fdc_costaccount t1 ");
	//		strBuf.append(" WHERE ffullorgunit = ? ");
	//		//�༶�ɱ��ܿأ�ֻҪ�Ǽ�������񶼿����·���  by hpw
	////		strBuf.append(" AND fisSource = 1");
	//		strBuf.append(" AND fisEnable = 1 ");
	//		strBuf.append(" AND not EXISTS ");
	//		strBuf.append(" ( SELECT t2.fid FROM t_fdc_costaccount t2 WHERE t1.fid=t2.fsrccostaccountid )");
	//		strBuf.append(" order by t1.flongnumber "); 
	//		
	//		IRowSet rows = DbUtil.executeQuery(ctx, strBuf.toString(), new Object[]{company.getId().toString()});
	//		
	//		Set idSet = new HashSet();
	//		try {
	//			rows.beforeFirst();
	//			while(rows.next()){
	//				idSet.add(rows.getString("fid"));
	//			}
	//			rows.beforeFirst();
	//		} catch (SQLException e) {
	//			logger.info(e.getMessage(), e);
	//			throw new BOSException(e);
	//		}
	//		
	//		if(idSet != null && !idSet.isEmpty()){
	//			
	//			String updateAssagnedSQL = "update t_fdc_costaccount set fassigned = 0 where fid in "+SetConvertToString(idSet) ;
	//			DbUtil.execute(ctx, updateAssagnedSQL);
	//		}
	//		
	//		return msgList ;
	//	}

	/**
	 * ������
	 * ������Ŀ�µĿ�Ŀ�Ƿ��������ݣ��������������������з�����
	 * @param curProjectIds
	 * @throws BOSException 
	 * @Author��keyan_zhao
	 * @CreateTime��2013-1-24
	 */
	private String checkProjectHasUsed(Context ctx, Set curProjectIds) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("project.id");
		view.setSelector(sic);
		filter.getFilterItems().add(new FilterItemInfo("project.id", curProjectIds, CompareType.INCLUDE));
		MeasureCostCollection costCollection = MeasureCostFactory.getLocalInstance(ctx).getMeasureCostCollection(view);
		if (costCollection.size() > 0) {
			return "PRG" + "_" + costCollection.get(0).getProject().getId().toString() + "_" + "REF1" + "_" + " ";
		} else {
			return null;
		}
	}

	private Set getRefBizBillList(Context ctx) throws BOSException {
		Set isRefCostAccountList = new HashSet();		
		StringBuffer refSQL = new StringBuffer();
		
		refSQL.append(" select a.fid billId from T_FDC_CostAccount a " );
		refSQL.append(" inner join T_CON_ContractCostSplitEntry b " );
		refSQL.append(" on a.fid = b.fcostaccountid " );
		refSQL.append(" union " );
		
		refSQL.append(" select distinct a.fid billId from T_FDC_CostAccount a " );
		refSQL.append(" inner join T_CON_ConChangeSplitEntry c " );
		refSQL.append(" on a.fid = c.fcostaccountid " );
		refSQL.append(" union " );
		
		refSQL.append(" select distinct a.fid billId from T_FDC_CostAccount a " );
		refSQL.append(" inner join T_AIM_MeasureEntry d " );
		refSQL.append(" on a.fid = d.fcostaccountid " );
		refSQL.append(" union " );
		
		refSQL.append(" select t0.fid billId from t_fdc_costaccount t0  ");
		refSQL.append(" inner join  T_AIM_DynCostCtrlEntryItems  t1  " );
		refSQL.append(" on t0.fid = t1.fcostaccountid " );
		refSQL.append(" union " );
		
		refSQL.append(" select t0.fid billId from t_fdc_costaccount t0  ");
		refSQL.append(" inner join  T_AIM_AimCostCtrlCostActItems  t1  " );
		refSQL.append(" on t0.fid = t1.fcostaccountid " );
		refSQL.append(" union ");

		refSQL.append(" select t0.FID billId from t_fdc_costaccount  t0 ");
		refSQL.append(" inner join  T_FDC_CostAccountWithAccount  t1  ");
		refSQL.append(" on t0.fid = t1.FAccountID ");
		
		
		IRowSet rowSetList = DbUtil.executeQuery(ctx,refSQL.toString());
		
		try {
			while(rowSetList.next()){
				isRefCostAccountList.add(rowSetList.getString("billId"));
			}
		} catch (SQLException e1) {
			throw new BOSException(e1);
		}
		return isRefCostAccountList;
	}

	private void doCheckBeDisassgned(Map costAccountList, Map clonMap, Map records, String longNumber) {
		Iterator recordIter = records.entrySet().iterator();
		while(recordIter.hasNext()){
			Entry entry = (Entry) recordIter.next();
			
			String tempLongNumber = (String) entry.getKey();
			//������ڵ㱻������������ӽڵ㶼���ܱ�ɾ��
			if(tempLongNumber.startsWith(longNumber)){
				if(costAccountList.containsKey(longNumber)){
					CostAccountInfo tempCai = (CostAccountInfo) records.get(longNumber);
					tempCai.setExtendedProperty("isDeleted", String.valueOf(false));
					
					Iterator subNodesIter = records.keySet().iterator();
					while(subNodesIter.hasNext()){
						
						String subChildNodeLongNumber = (String) subNodesIter.next();
						if(subChildNodeLongNumber.startsWith(longNumber)){
							CostAccountInfo subNode = (CostAccountInfo) records.get(subChildNodeLongNumber);
							
							subNode.setExtendedProperty("isDeleted", String.valueOf(false));
						}
					}
				}
			}
			//����ӽڵ㱻������������еĸ��ڵ㲻�ܱ�ɾ��
			Iterator clonMapIter = clonMap.entrySet().iterator();
			int idx = 0 ;
			
			String parentLongNumber = tempLongNumber ;
			while(clonMapIter.hasNext()){
				Entry clonEntry = (Entry) clonMapIter.next();
				
				if(parentLongNumber.lastIndexOf("!") == -1){
					break;
				}else{
					parentLongNumber = parentLongNumber
							.substring(0, parentLongNumber.lastIndexOf("!"));
					
				}
				CostAccountInfo tempCai = (CostAccountInfo) records.get(parentLongNumber);
				if(tempCai != null){
					tempCai.setExtendedProperty("isDeleted", String.valueOf(false));
				}
				
				idx ++ ;
			}
		}
	}
	/**
	 * ��������֯��صĿ�Ŀ
	 * @param ctx
	 * @param orgSet
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 */
	private Map getOrgIsAssignedAccountList(Context ctx,Set orgSet) 
								throws BOSException, EASBizException, UuidException {
		Map accMap = new HashMap();
		try {
			String sSql = " select * from t_fdc_costaccount where ffullorgunit in " + this.SetConvertToString(orgSet) + "";
			IRowSet rs = DbUtil.executeQuery(ctx,sSql);
			Map caMap = null;
			String myKey = null;
			while (rs.next()) {
				caMap = new HashMap();
				caMap.put("id",rs.getString("FID"));
				caMap.put("isEnable",Boolean.valueOf(rs.getBoolean("FIsEnable")));
				caMap.put("isSource",Boolean.valueOf(rs.getBoolean("fissource")));
				caMap.put("fullOrgUnit", rs.getString("FFullOrgUnit"));
				caMap.put("isSplit", Boolean.valueOf(rs.getBoolean("fissplit")));
				
				myKey = rs.getString("FFullOrgUnit") + "_" + rs.getString("FLongnumber");
				accMap.put(myKey, caMap);
			}
			
		} catch (SQLException ex) {
			throw new BOSException(ex);
		}
		return accMap ;
	}

	/**
	 * ��������Ŀ��صĿ�Ŀ
	 * @param ctx
	 * @param projectSet
	 * @return
	 * @throws BOSException 
	 */
	private Map getProjectIsAssignedAccountList(Context ctx, Set projectSet) throws BOSException {
		
		if(projectSet == null || projectSet.isEmpty()){
			return null ;
		}
		
		Map accMap = new HashMap();
		try {
			String sSql = " select * from t_fdc_costaccount where fcurproject in " + this.SetConvertToString(projectSet) + "";
			IRowSet rs = DbUtil.executeQuery(ctx,sSql);
			Map caMap = null;
			String myKey = null;
			while (rs.next()) {
				caMap = new HashMap();
				caMap.put("id",rs.getString("FID"));
				caMap.put("isEnable",Boolean.valueOf(rs.getBoolean("FIsEnable")));
				caMap.put("isSource",Boolean.valueOf(rs.getBoolean("fissource")));
				caMap.put("curProject", rs.getString("FCurProject"));
				
				myKey = rs.getString("fcurproject") + "_" + rs.getString("FLongnumber");
				accMap.put(myKey, caMap);
			}

		} catch (SQLException ex) {
			throw new BOSException(ex);
		}
		return accMap ;
	}

	
	private void getBeAssignedCostAccounts(Map parents ,String costAccountId,Set costAccountSet){
		
		if(parents.get(costAccountId) == null){
			costAccountSet.add(costAccountId);
			return ;
		}
		if(!costAccountSet.contains(costAccountId)){
			costAccountSet.add(costAccountId);
			
		}
		String parentId = parents.get(costAccountId).toString();
		getBeAssignedCostAccounts(parents, parentId, costAccountSet);
	}
	
	/**
	 * @param ctx
	 * @param costAccountList
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private CostAccountCollection getCostAccountBySet(Context ctx, Set costAccountList,String companyId) throws BOSException, EASBizException {

		//ȡ����ǰ��֯�µĳɱ���Ŀ�����ӽڵ�ȫ��ȡ��
		CostAccountCollection cac = this.getOrgCostAccounts(ctx, companyId);
		CostAccountInfo costAccountInfo = null ;
		
		Map numberRefIdMap = new TreeMap();
		Map longNumberRefInfoMap = new TreeMap();
		
		//����ݹ����ݽṹ
		for(int i=0,size=cac.size();i<size;i++){
			costAccountInfo = cac.get(i);
			
			numberRefIdMap.put(costAccountInfo.getId().toString(),costAccountInfo.getLongNumber());
			longNumberRefInfoMap.put(costAccountInfo.getLongNumber(), costAccountInfo);
			
		}
		Set costAccountSet = new HashSet();
		
		Iterator costIter = costAccountList.iterator();
		while(costIter.hasNext()){
			String id = (String) costIter.next();
			
			costAccountSet.add(id);
			String longNumber = (String) numberRefIdMap.get(id);
			
			if(!StringUtils.isEmpty(longNumber)){
				
				while(longNumber.lastIndexOf("!") != -1){
					longNumber = longNumber.substring(0, longNumber.lastIndexOf("!"));
					CostAccountInfo cstAct =  (CostAccountInfo) longNumberRefInfoMap.get(longNumber);
					if(cstAct != null){
						costAccountSet.add(cstAct.getId().toString());
					}
				}
			}else{
				logger.info(id);
				logger.error("is null");
			}
		}
		ICostAccount icostAccount = CostAccountFactory.getLocalInstance(ctx);
		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.number"));
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("fullOrgUnit.number"));
        sic.add(new SelectorItemInfo("fullOrgUnit.name"));
        sic.add(new SelectorItemInfo("createOrg.id"));
        sic.add(new SelectorItemInfo("createOrg.number"));
        sic.add(new SelectorItemInfo("createOrg.name"));
        sic.add(new SelectorItemInfo("CU"));
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("stageEntrys.*"));
		
		view.setSelector(sic);
		
		SorterItemCollection sortColl = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo("longNumber");
		sorter.setSortType(SortType.ASCEND);
		sortColl.add(sorter);
		
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",costAccountSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));
		
		view.setSorter(sortColl);
		view.setFilter(filter);
		
		CostAccountCollection ccc = icostAccount.getCostAccountCollection(view);
		
		return ccc;
	}
	
	/**
	 * ������������߼�
	 * ������е�Ҫ����ĳɱ���Ŀ�������䵽����֯����Ŀȡ�������б�����ֵ
	 * ����֯������Ŀ��ȡ����������У�鲻���ڿ�Ŀ�ű�����
	 * @param ctx
	 * @param beAssignUnits
	 * @param cac
	 * @param accountIsHasInMap
	 * @param costAccountMap
	 * @param isProjectSet
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private List updateCostAccountByProjectOrOrg(Context ctx,Set beAssignUnits,CostAccountCollection cac 
			,Map costAccountMap ,Map accountIsHasInMap,boolean isProject ,Map isCanAddMap)
					throws BOSException, EASBizException {
		int count = 0;
		List errorList = new ArrayList();
		Map parentMap = new HashMap();
		
		
		Connection connection = null;
		PreparedStatement insertStmt = null;
		PreparedStatement updateStmt = null;
		PreparedStatement stageStmt = null;
		try {
			connection = getConnection(ctx);
			insertStmt = connection.prepareStatement(insert_batch_sql);
			updateStmt = connection.prepareStatement(update_sql);
			stageStmt = connection.prepareStatement(insert_accStage_sql);

			Timestamp now = new Timestamp(System.currentTimeMillis());
			String userId = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
	
			Locale l1 = new Locale("L1");
			Locale l2 = new Locale("L2");
			Locale l3 = new Locale("L3");
			/*
			 * ��set���ڽ����ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ
			 */
			Set acctIdSet=new HashSet();
			
			Iterator tor = beAssignUnits.iterator();
			
			String longNumber = "";
			boolean isHasSta = false;
			Map caMap = new HashMap();
			while (tor.hasNext()) {		//������ѡĿ����֯����Ŀ����
				
				String nodeId = tor.next().toString();
				Map numberDupMap = new HashMap();
				
				Map accountIsHasInMapCopy = new HashMap();
			accountIsHasInMapCopy.putAll(accountIsHasInMap);
				
				Set addNewIds = new HashSet();
				for (int j = 0; j < cac.size(); j++) {		//������ǰԴͷ��֯���������õĿ�Ŀ
					//ÿѭ��10000���ύһ��
					if (count > 0 && count % 10000 == 0) {
						insertStmt.executeBatch();
						updateStmt.executeBatch();
						stageStmt.executeBatch();
						insertStmt.clearBatch();
						updateStmt.clearBatch();
						stageStmt.clearBatch();
						
					}

					if (acctIdSet.size() > 0 && acctIdSet.size() == 100000) {
						Map param = new HashMap();
						param.put("acctIdSet", acctIdSet);
						CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
						acctIdSet.clear();
					}
					count++; 
					String comb = nodeId + "_" + cac.get(j).getLongNumber();
					//�����ǰ��֯����Ŀ��û�д˿�Ŀ�������
					if (!accountIsHasInMap.containsKey(comb)) {
						
						CostAccountInfo info = cac.get(j);
						BOSUuid uuid = BOSUuid.create(info.getBOSType());
						String id = uuid.toString();
						acctIdSet.add(id);
						longNumber = info.getLongNumber();
						
						if (numberDupMap != null && !numberDupMap.isEmpty() && longNumber.lastIndexOf("!") > 0) {
							CostAccountInfo refCostAct =  (CostAccountInfo) numberDupMap.get(nodeId);
							String existsLongNumber = (String) refCostAct.getLongNumber();
							if(!StringUtils.isEmpty(existsLongNumber)){
								String parentLongNumber = longNumber.substring(0, longNumber.lastIndexOf("!"));
								if(!StringUtils.isEmpty(existsLongNumber) && parentLongNumber.equals(existsLongNumber)){
									continue ;
								}
							}
						}
						
						boolean isEnable = info.isIsEnabled();
						String parentlongNumber = null;
						String parentId = null;
						int idx = longNumber.lastIndexOf("!");
						if(idx > 0) {
							parentlongNumber = longNumber.substring(0, idx);
							String key = nodeId + "_" + parentlongNumber;
							
							Map ca = null;
							if (accountIsHasInMapCopy.containsKey(key)) {
							ca = (HashMap) accountIsHasInMapCopy.get(key);
							}
							if(ca != null) {
								isEnable = ((Boolean)ca.get("isEnable")).booleanValue();
								parentId = ca.get("id").toString();
								parentMap.put(parentlongNumber, parentId);
							}
							if(parentId == null){
								continue;
							}
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
						insertStmt.setString(x++, isProject ? nodeId : null); // curproject
						
						
						
						if(parentId == null && parentlongNumber != null){
							parentId = (String) parentMap.get(parentlongNumber);
						}
						if (parentId != null) {
							DbUtil.prepareVarcharParam(updateStmt, 1, parentId);
							updateStmt.addBatch();
						}
						insertStmt.setString(x++, parentId); // parentid
						
						insertStmt.setString(x++, isProject ? null : nodeId); //fullorgunit
						insertStmt.setBoolean(x++, false); //issource
						//						insertStmt.setString(x++, info.getId().toString());	//FSRCCOSTACCOUNTID
						/**
						 * ֱ��ID�������޸��ϼ����ƣ����룬������ʱ����ͬ�������¼���֯����Ŀ
						 * ���༶����ʱ���¼�Ҳ����Դ������Ӧ�ú��ϼ���srcidһ��
						 * by hpw 2011.12.09
						 */
						insertStmt.setString(x++, info.isIsSource() ? info.getId().toString() : info.getSrcCostAccountId()); //FSRCCOSTACCOUNTID
						String type = info.getType() == null ? null : info
								.getType().getValue();
						type = info.getLevel() == 1 ? null : type;
						insertStmt.setString(x++, type); //TYPE
						insertStmt.setBoolean(x++, info.isIsCostAccount()); // isIsCostAccount
						insertStmt.setString(x++, info.getCodingNumber()); // codingNumber  by sxhong 2008-10-20 18:38:43
						
						//vincent_zhu modify 2011-4-29
						Boolean isCanAdd = (Boolean) isCanAddMap.get(info.getLongNumber());
						insertStmt.setBoolean(x++, isCanAdd == null ? false : isCanAdd.booleanValue() ? true : false);
						insertStmt.setString(x++, info.getCreateOrg() == null ? null 
								:info.getCreateOrg().getId().toString());
						
						insertStmt.setBoolean(x++, info.isIsSplit()); // �Ƿ���
						//End
						
						parentMap.put(info.getLongNumber(), id);
						//��������������һ�����õ�ֵ�����´�����
						//info.setId(uuid);
						
						caMap = new HashMap();
						caMap.put("id",id);
						caMap.put("isEnable",Boolean.valueOf(isEnable));
						accountIsHasInMapCopy.put(nodeId + "_" + longNumber, caMap);
						insertStmt.addBatch();
						for (int k = 0; k < info.getStageEntrys().size(); k++) {
							AccountStageInfo stageInfo = info.getStageEntrys().get(k);
							stageStmt.setString(1, BOSUuid.create("476BD8C3").toString());
							stageStmt.setString(2, stageInfo.getMeasureStage().getId().toString());
							stageStmt.setString(3, id);
							stageStmt.setBoolean(4, stageInfo.isValue());
							stageStmt.addBatch();
							isHasSta = true;
						}
						
						costAccountMap.put(id, info.getId().toString());
					
						
					}else{
						CostAccountInfo info = cac.get(j);
						//�������,��Ѹ��ڵ������
						Map inMap = (Map) accountIsHasInMapCopy.get(comb);
						if (costSrcNull.contains(comb)) {
							
							if(isProject){
								if (!errorList.contains("PRG_" + "num" + "_" + inMap.get("curProject"))) {
									errorList.add("PRG_" + "num" + "_" + inMap.get("curProject"));
								}
								errorList.add("PRG_"+info.getLongNumber()
										.replace('!', '.')+"_"+inMap.get("curProject").toString()+"");
							}else{
								//accountIsHasInMap���췽�������֯����ʱû�з� "curProject"��ֵ��inMap.get("curProject").toString()�ᱨNP
								//by andy_liu 2012-3-7
								if (!errorList.contains("ORG_" + "num" + "_" + inMap.get("fullOrgUnit"))) {
									errorList.add("ORG_" + "num" + "_" + inMap.get("fullOrgUnit"));									
								}
							}
							numberDupMap.put(nodeId, info);
						}
					}
				}
				parentMap.clear();
			}
			insertStmt.executeBatch();
			updateStmt.executeBatch();
			stageStmt.executeBatch();
			
			if(acctIdSet.size()>0){
				
				Map param=new HashMap();
				param.put("acctIdSet", acctIdSet);
				CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
			}
		} catch (SQLException ex) {
			throw new BOSException(ex);
		} finally {
			parentMap.clear();
			SQLUtils.cleanup(insertStmt, connection);
			SQLUtils.cleanup(updateStmt, connection);
			SQLUtils.cleanup(stageStmt, connection);
		}
		
		return errorList;
	}
	
	/**
	 * ��������׶κ�Ĭ�����ÿ�Ŀ����׶�ֵΪ1
	 * ������ʷ��������Ҳ��ִ�д�sql,ֻҪ��Ŀ�׶α���û�д˽׶ξͿ�ִ��
	 */
	protected RetValue _updateAccountStage(Context ctx, ParamValue paramValue)
			throws BOSException, EASBizException {
		//ץ��sql����ֱ�ӿ�ִ��
		/*Insert into T_FDC_AccountStage(fid,fcostaccountid,fmeasurestageid,fvalue) 
		select newbosid('476BD8C3') id ,acct.fid,ms.fid,1 from t_fdc_costaccount acct,t_fdc_measurestage ms 
		where ms.fid not in (select distinct(fmeasurestageid) from t_fdc_accountstage);*/
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("Insert into T_FDC_AccountStage(fid,fcostaccountid,fmeasurestageid,fvalue) ");
		builder.appendSql("select newbosid('476BD8C3') id ,acct.fid,ms.fid,1 from t_fdc_costaccount acct,t_fdc_measurestage ms ");
		builder.appendSql("where ms.fid not in (select distinct(fmeasurestageid) from t_fdc_accountstage) ");
		builder.execute();
		return null;
	}
	
}
