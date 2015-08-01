package com.kingdee.eas.fdc.costdb.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.util.DateTimeUtils;

public class ProjectDFRptHelper {

	public static Map createFullProjMap(String[] projectIds, Date startDate, Date endDate) {

		Map map = new HashMap();

		if (FDCHelper.isEmpty(projectIds))
			return map;
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select projectID, FLastUpdateTime, FCostAccountID, flongnumber, fname_l2, sum(FCostAmount) amount from ");
		builder.appendSql("(");
		builder
				.appendSql("select aim.FOrgOrProId projectID, aim.FLastUpdateTime FLastUpdateTime, entry.FCostAccountID, acct.flongnumber, acct.fname_l2, entry.FCostAmount " +
						"from T_AIM_AimCost aim " +
						"inner join T_AIM_CostEntry entry on aim.fid = entry.FHeadID and entry.fisEnterDB=1 " +
						"inner join T_FDC_CostAccount acct on entry.FCostAccountID = acct.fid " +
						"where FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId in(");
		builder.appendParam(projectIds);
		
		/*
		 * add function that limit createTime of project between startDate and endDate.
		 * Of course the startDate and endDate can be empty.
		 */
		builder.appendSql(") and ");
		builder.appendSql(" aim.FLastUpdateTime between ");
		builder.appendParam(new java.sql.Date(startDate.getTime()));
		builder.appendSql(" and ");
		builder.appendParam(new java.sql.Date(DateTimeUtils.addDay(endDate,1).getTime()));
		
		//builder.appendSql(")");
/*目标成本不需要汇总待发生
		builder.appendSql(" union all ");

		builder
				.appendSql("select acct.FCurProject projectID, dyn.FAccountID FCostAccountID, acct.flongnumber, acct.fname_l2, entry.FCostAmount from T_AIM_DynamicCost dyn inner join T_AIM_AdjustRecordEntry entry on dyn.fid = entry.FParentID inner join T_FDC_CostAccount acct on dyn.FAccountID = acct.fid where acct.FIsLeaf = 1 and acct.FCurProject in(");
		builder.appendParam(projectIds);
		builder.appendSql(")");*/
		builder
				.appendSql(") a group by projectID, FLastUpdateTime, FCostAccountID, flongnumber, fname_l2");
		
		ResultSet set;
		try {
			set = builder.executeQuery();
			while (set.next()) {

				String projId = set.getString("projectID");
				String acctLongNum = set.getString("flongnumber");
				BigDecimal amount = set.getBigDecimal("amount");
				Date createDate = set.getDate("FLastUpdateTime");
				map.put(projId + "_" + acctLongNum, amount);
				//取数增加 编制时间
				map.put(projId + "createTime" , createDate);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	public static Map createProductMap(String[] projectIds, String acctLongNum) {
		
		
		Map values = new HashMap();
		//产品动态成本金额＝目标成本直接归属到产品－各产品类型分摊到产品－待发生调整直接归属到产品－各产品类型动态成本分摊到产品
		if (FDCHelper.isEmpty(projectIds) || FDCHelper.isEmpty(acctLongNum))
			return values;

		FDCSQLBuilder builder = new FDCSQLBuilder();

		builder.appendSql("select projID, prodID, sum(famount) famount from");
		builder.appendSql(" (");

		// aim
		builder
				.appendSql("select aim.FOrgOrProId projID, entry.fproductid prodID, entry.FCostAmount famount from T_AIM_AimCost aim inner join T_AIM_CostEntry entry on aim.fid = entry.FHeadID inner join T_FDC_CostAccount acct on entry.FCostAccountID = acct.fid where aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId in(");
		builder.appendParam(projectIds);
		builder.appendSql(") and entry.FProductID is not null");
		builder.appendSql(" and (acct.flongnumber like ");
		builder.appendParam(acctLongNum + "!% ");
		builder.appendSql(" or acct.flongnumber=");//其实只查明细科目
		builder.appendParam(acctLongNum);
		builder.appendSql(") \n union all \n");

		// aim apportion
		builder
				.appendSql("select aim.FOrgOrProId projID, prodEntry.fproductid prodID, prodEntry.FSplitAmount famount from T_AIM_AIMAimCostProdSplitEntry prodEntry inner join T_AIM_CostEntry costEntry on prodEntry.FCostEntryId = costEntry.fid inner join T_AIM_AimCost aim on costEntry.FHeadID = aim.fid inner join T_FDC_CostAccount acct on costEntry.FCostAccountID = acct.fid where aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId in(");
		builder.appendParam(projectIds);
		builder.appendSql(") and (acct.flongnumber like ");
		builder.appendParam(acctLongNum + "!%");
		builder.appendSql(" or acct.flongnumber=");
		builder.appendParam(acctLongNum);
		builder.appendSql(")");
		
/*动态成本数据
		builder.appendSql("\n union all \n");

		// dyn
		builder
				.appendSql("select acct.FCurProject projID, entry.fproductid prodID, entry.FCostAmount famount from T_AIM_DynamicCost dyn inner join T_AIM_AdjustRecordEntry entry on dyn.fid = entry.FParentID inner join T_FDC_CostAccount acct on dyn.FAccountID = acct.fid where acct.FIsLeaf = 1 and acct.FCurProject in(");
		builder.appendParam(projectIds);
		builder.appendSql(") and entry.FProductID is not null");
		builder.appendSql(" and acct.flongnumber like ");
		builder.appendParam(acctLongNum + "%");

		builder.appendSql("\n union all \n");

		// dyn apportion
		builder
				.appendSql("select acct.FCurProject, prodEntry.fproductid prodID, (prodEntry.FSplitAmount - prodEntry.FAimCostAmount) famount from T_AIM_DynCostProduSplitEntry prodEntry inner join T_AIM_DynamicCost dynCost on prodEntry.FDynamicCostId = dynCost.fid inner join T_FDC_CostAccount acct on dynCost.FAccountID = acct.fid where acct.FIsLeaf = 1 and acct.FCurProject in(");
		builder.appendParam(projectIds);
		builder.appendSql(") and acct.flongnumber like ");
		builder.appendParam(acctLongNum + "%");

*/		builder.appendSql(") a group by projID, prodID");

		try {
			ResultSet set = builder.executeQuery();
			while (set.next()) {
				String projID = set.getString("projID");
				String prodID = set.getString("prodID");
				BigDecimal amount = set.getBigDecimal("famount");
				values.put(projID + "_" + prodID, amount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return values;
	}

	public static Map createProductMap(String[] projectIds, Date startDate, Date endDate) {
		Map values = new HashMap();
		//只查目标成本数据
		if (FDCHelper.isEmpty(projectIds))
			return values;

		FDCSQLBuilder builder = new FDCSQLBuilder();

		builder.appendSql("select projID, prodID, sum(famount) famount ,flongnumber,createTime from");
		builder.appendSql(" (");

		// aim
		builder.appendSql("select aim.FOrgOrProId projID, aim.FCreateTime createTime ,entry.fproductid prodID, entry.FCostAmount famount ,acct.flongnumber flongnumber " +
				"from T_AIM_AimCost aim " +
				"inner join T_AIM_CostEntry entry on aim.fid = entry.FHeadID and entry.fisEnterDB=1 " +
				"inner join T_FDC_CostAccount acct on entry.FCostAccountID = acct.fid " +
				"where aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId in (");
		builder.appendParam(projectIds);
		builder.appendSql(") and entry.FProductID is not null");
		
		/*
		 * add function that limit createTime of project between startDate and endDate.
		 * Of course the startDate and endDate can be empty.
		 */
		builder.appendSql(" and ");
		builder.appendSql(" aim.FCreateTime between ");
		builder.appendParam(new java.sql.Date(startDate.getTime()));
		builder.appendSql(" and ");
		builder.appendParam(new java.sql.Date(DateTimeUtils.addDay(endDate,1).getTime()));
		
	
		builder.appendSql(" \n union all \n");

		// aim apportion
		builder.appendSql("select aim.FOrgOrProId projID, aim.FCreateTime createTime,prodEntry.fproductid prodID, prodEntry.FSplitAmount famount ,acct.flongnumber flongnumber " +
				"from T_AIM_AIMAimCostProdSplitEntry prodEntry " +
				"inner join T_AIM_CostEntry costEntry on prodEntry.FCostEntryId = costEntry.fid and costEntry.fisEnterDB=1 " +
				"inner join T_AIM_AimCost aim on costEntry.FHeadID = aim.fid " +
				"inner join T_FDC_CostAccount acct on costEntry.FCostAccountID = acct.fid " +
				"where aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId in(");
		builder.appendParam(projectIds);
		builder.appendSql(")");
		
		/*
		 * add function that limit createTime of project between startDate and endDate.
		 * Of course the startDate and endDate can be empty.
		 */
		builder.appendSql(" and ");
		builder.appendSql(" aim.FCreateTime between ");
		builder.appendParam(new java.sql.Date(startDate.getTime()));
		builder.appendSql(" and ");
		builder.appendParam(new java.sql.Date(DateTimeUtils.addDay(endDate,1).getTime()));
		
		builder.appendSql(") a group by projID,flongnumber ,prodID, createTime");

		try {
			ResultSet set = builder.executeQuery();
			while (set.next()) {
				String projID = set.getString("projID");
				String prodID = set.getString("prodID");
				String longnumber = set.getString("flongnumber");
				BigDecimal amount = set.getBigDecimal("famount");
				values.put(projID +"_" + prodID+ "_"+longnumber, amount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return values;
	}
	
	public static ResultSet getAimDetail(String projectId,
			String productTypeId, String acctLongNum) {

		if (FDCHelper.isEmpty(projectId) || FDCHelper.isEmpty(productTypeId)
				|| FDCHelper.isEmpty(acctLongNum))
			return null;

		FDCSQLBuilder builder = new FDCSQLBuilder();

		// aim直接归属
		builder
				.appendSql("select entry.FEntryName, entry.FWorkload, entry.FPrice, entry.FUnit, entry.FCostAmount famount,measureEntry.FIndexName origIndexName,measureEntry.FIndexValue origIndexValue " +
						"from T_AIM_AimCost aim inner join T_AIM_CostEntry entry on aim.fid = entry.FHeadID " +
						"inner join T_AIM_MeasureEntry measureEntry on entry.FMeasureEntryID = measureEntry.fid " +
						"inner join T_FDC_CostAccount acct on entry.FCostAccountID = acct.fid " +
						"where entry.fisenterdb = 1 and aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId =");
		builder.appendParam(projectId);
		builder.appendSql(" and entry.FProductID =");
		builder.appendParam(productTypeId);
		builder.appendSql(" and (acct.flongnumber like ");
		builder.appendParam(acctLongNum + "!%");
		builder.appendSql(" or acct.flongnumber = ");
		builder.appendParam(acctLongNum);
		builder.appendSql(" ) ");
		builder.appendSql("\n union all \n");

		// aim apportion目标成本分摊
		builder.appendSql("select costEntry.FEntryName, costEntry.FWorkload, costEntry.FPrice, costEntry.FUnit, prodEntry.FSplitAmount famount ,measureEntry.FIndexName origIndexName,measureEntry.FIndexValue origIndexValue ");
		builder.appendSql("from T_AIM_AIMAimCostProdSplitEntry prodEntry " +
				"inner join T_AIM_CostEntry costEntry on prodEntry.FCostEntryId = costEntry.fid " +
				"inner join T_AIM_AimCost aim on costEntry.FHeadID = aim.fid " +
				"inner join T_AIM_MeasureEntry measureEntry on costEntry.FMeasureEntryID = measureEntry.fid " +
				"inner join T_FDC_CostAccount acct on costEntry.FCostAccountID = acct.fid " +
				"where costEntry.fisenterdb = 1 and  aim.FIsLastVersion = 1 and acct.FIsLeaf = 1 and aim.FOrgOrProId =");
		builder.appendParam(projectId);
		builder.appendSql(" and prodEntry.FProductID =");
		builder.appendParam(productTypeId);
		builder.appendSql(" and (acct.flongnumber like ");
		builder.appendParam(acctLongNum + "!%");
		builder.appendSql(" or acct.flongnumber = ");
		builder.appendParam(acctLongNum);
		builder.appendSql(" ) ");

		ResultSet rs = null;
		try {
			rs = builder.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}
}
