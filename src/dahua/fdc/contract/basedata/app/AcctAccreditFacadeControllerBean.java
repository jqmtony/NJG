package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserRolePermCollection;
import com.kingdee.eas.base.permission.UserRolePermFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserCollection;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class AcctAccreditFacadeControllerBean extends AbstractAcctAccreditFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.AcctAccreditFacadeControllerBean");
    protected void _saveAcctAccreditUsers(Context ctx, IObjectCollection accreditUsers)throws BOSException, EASBizException
    {
    }

	protected Map _fetchData(Context ctx, Map param) throws BOSException, EASBizException {
		Map retMap=new HashMap();
		String schemeId=(String)param.get("schemeId");
		if (schemeId == null) {
			return retMap;
		}
		//test
//		Set set=_getAccreditAccts(ctx, objectId);
		//get costaccount 数据库里面存的是明细科目，通过明细科目拼出上级科目
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.setFilter(new FilterInfo());
		StringBuffer filterSql=new StringBuffer();
		filterSql.append("select distinct a.fid from T_FDC_CostAccount a ");
		filterSql.append("inner join T_FDC_CostAccount b on a.ffullorgunit=b.ffullorgunit ");
		filterSql.append("where b.fid in (select fcostAccountid from T_FDC_AcctAccreditAccts where fschemeid='"+schemeId+"') ");
		filterSql.append("and charindex(a.flongnumber||'!',b.flongnumber||'!')=1 ");

		view.getFilter().getFilterItems().add(new FilterItemInfo("id",filterSql.toString(),CompareType.INNER));
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		int maxLevel=0;
		for(int i=0;i<c.size();i++){
			if(c.get(i).getLevel()>maxLevel){
				maxLevel=c.get(i).getLevel();
			}
		}
		retMap.put("CostAccountCollection", c);
		retMap.put("maxLevel", new Integer(maxLevel));
		//get user
		view=new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("role.id");
		view.getSelector().add("role.number");
		view.getSelector().add("role.name");
		view.getSelector().add("scheme.id");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id","select fid from T_FDC_AcctAccreditUser where fschemeid='"+schemeId+"'",CompareType.INNER));
		view.getSorter().add(new SorterItemInfo("role.number"));
		AcctAccreditUserCollection acctAccreditUserCollection = AcctAccreditUserFactory.getLocalInstance(ctx).getAcctAccreditUserCollection(view);
		retMap.put("AcctAccreditUserCollection", acctAccreditUserCollection);
		return retMap;
	}
	protected Set _getOrgAccreditAcctSet(Context ctx, String orgUnitId) throws BOSException {
    	//取编码相同的上下级科目
		Set set = new HashSet();
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	//getSchemeId
    	String userId=ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    	String orgUnitID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		UserRolePermCollection collection = UserRolePermFactory.getLocalInstance(ctx).getCollection(
				"where userId='" + userId + "' and orgId='" + orgUnitID + "'");
		Set roleSet = new HashSet();
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getRoleId() == null) {
				continue;
			}
			roleSet.add(collection.get(i).getRoleId().getId().toString());
		}
		if (roleSet.size() == 0) {
			return set;
		}

		builder.appendSql("select distinct a.fid from T_FDC_CostAccount a \n");
		builder.appendSql(" inner join T_FDC_CostAccount b on charindex(a.flongnumber||'!',b.flongnumber||'!')=1 or charindex(b.flongnumber||'!',a.flongnumber)=1 \n");
		builder.appendSql(" where b.flongnumber in (select distinct cost.flongnumber from T_FDC_AcctAccreditAccts dit  \n");

		builder.appendSql(" inner join t_fdc_costaccount cost on cost.fid= dit.fcostAccountid \n");
		//方案＝当前用户+当前项目or当前项目所属财务组织的上级对应的方案
		builder.appendSql("where dit.fschemeid in ( select FSchemeID from T_FDC_AcctAccreditUser where froleid in "
				+ SetConvertToString(roleSet));
		
		builder.appendSql(" ) \n");
		builder.appendSql(" ) \n");
		builder.appendSql(" and a.ffullorgunit=? \n");
		
		builder.addParam(orgUnitId);
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				set.add(rowSet.getString("fid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
        return set;
	
	}
	protected Set _getPrjAccreditAcctSet(Context ctx, String prjId) throws BOSException {
		Set set = new HashSet();
    	//取编码相同的上下级科目
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	//getSchemeId
    	String userId=ContextUtil.getCurrentUserInfo(ctx).getId().toString();
    	String orgUnitID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		UserRolePermCollection collection = UserRolePermFactory.getLocalInstance(ctx).getCollection(
				"where userId='" + userId + "' and orgId='" + orgUnitID + "'");
		Set roleSet = new HashSet();
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getRoleId() == null) {
				continue;
			}
			roleSet.add(collection.get(i).getRoleId().getId().toString());
		}
		if (roleSet.size() == 0) {
			return set;
		}

		SqlParams sp = new SqlParams();

		//方案＝当前用户+当前项目or当前项目所属财务组织的上级对应的方案
		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	SELECT DISTINCT a.fid	\r\n");
		sqlSb.append("	  FROM T_FDC_CostAccount A	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_CostAccount B	\r\n");
		sqlSb.append("	    ON (CHARINDEX((b.flongnumber || '!'), (a.flongnumber || '!')) = 1	\r\n");
		sqlSb.append("	    OR CHARINDEX(a.flongnumber, (b.flongnumber || '!')) = 1)	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_AcctAccreditAccts DIT	\r\n");
		sqlSb.append("	    ON b.fid = dit.fcostAccountid	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_AcctAccreditUser accau	\r\n");
		sqlSb.append("	    ON accau.FSchemeID = dit.fschemeid	\r\n");
		sqlSb.append("	 WHERE 1 = 1 \r\n");
		FdcSqlUtil.appendInFrag(sqlSb, sp, "accau.FRoleID", roleSet, "AND");
		sqlSb.append("	   AND a.fcurProject = ?	\r\n");

		sp.addObject(prjId);
		Object[] paramArr = sp.getParams();
		String sqlStr = sqlSb.toString();

		builder.appendSql(sqlStr);
		builder.addParams(paramArr);

		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				set.add(rowSet.getString("fid"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		String paramArrStr = FdcArrayUtil.toString(paramArr, "[", "]", ", ");

		logger.info("======================================================================");
		logger.info("AcctAccreditFacadeControllerBean._getPrjAccreditAcctSet(),sqlStr:" + sqlStr);
		logger.info("AcctAccreditFacadeControllerBean._getPrjAccreditAcctSet(),paramArrStr:" + paramArrStr);
		logger.info("======================================================================");

		return set;
	}
	
	private String SetConvertToString(Set idSet) {
		if (idSet == null || idSet.isEmpty()) {
			return "";
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i = 0;
		int size = idSet.size();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			filter.append("'").append(id).append("'");
			if (i < size - 1) {
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		return filter.toString();
	}
}