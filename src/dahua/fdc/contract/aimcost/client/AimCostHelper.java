package com.kingdee.eas.fdc.aimcost.client;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 目标成本取数辅助类
 * 
 * @author pengwei_hou
 * 
 */
public class AimCostHelper {

	//科目目标成本
	public static Map getAimCostByAcct(Context ctx, Set acctIdSet) throws BOSException {
		Map aimCostMap = new HashMap();
		if (acctIdSet == null || acctIdSet.size() == 0) {
			return aimCostMap;
		}
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);

			builder
					.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount from T_AIM_CostEntry entry ");
			builder
					.appendSql("inner join T_AIM_AimCost head on entry.fheadid=head.fid ");
			builder.appendSql(" where head.fisLastVersion=1 and ");
			builder.appendParam("entry.fcostaccountid", acctIdSet.toArray(),"varchar(44)");
			builder.appendSql(" group by entry.fcostaccountid ");

			final IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				aimCostMap.put(rowSet.getString("acctid"), rowSet
						.getBigDecimal("amount"));
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return aimCostMap;
	}

}
