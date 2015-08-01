package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class AimDynCostGetFacadeControllerBean extends AbstractAimDynCostGetFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.AimDynCostGetFacadeControllerBean");
    protected Map _getAimDynCost(Context ctx, String proId)throws BOSException, EASBizException
    {
    	Map map = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	Map aimCost = new HashMap();
		builder.appendSql("select acc.flongnumber longnumber,sum(entry.fcostamount) amout from t_aim_aimcost aim ");
		builder.appendSql("left join t_aim_costentry entry on aim.fid= entry.fheadid ");
		builder.appendSql("left join t_fdc_costaccount acc on acc.fid=entry.fcostaccountid ");
		builder.appendSql("where aim.FOrgOrProId='" + proId + "' ");
		builder.appendSql("and aim.FIsLastVersion=1 ");
		builder.appendSql("group by acc.flongnumber ");
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				aimCost.put(rowSet.getString("longnumber"), rowSet.getBigDecimal("amout"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		Map parentCost = setParentCost(aimCost);
		map.put("aimGetter", parentCost);
		builder.clear();
		builder.appendSql("select acc.flongnumber longnumber,sum(dyn.FAdjustSumAmount) amout from T_AIM_DynamicCost dyn ");
		builder.appendSql("left join t_fdc_costaccount acc on acc.fid=dyn.FAccountID ");
		builder.appendSql("where acc.fcurproject='" + proId + "' ");
		builder.appendSql("group by acc.flongnumber ");
		Map dynMap = new HashMap();
		dynMap.putAll(aimCost);
		IRowSet rowSet2 = builder.executeQuery();
		try {
			while (rowSet2.next()) {
				String longnumber = rowSet2.getString("longnumber");
				BigDecimal bigDecimal = rowSet2.getBigDecimal("amout");
				if (dynMap.containsKey(longnumber)) {
					dynMap.put(longnumber, FDCHelper.add(bigDecimal, dynMap.get(longnumber)));
				} else {
					dynMap.put(longnumber, bigDecimal);
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		map.put("dyGetter", setParentCost(dynMap));
		return map;
    }
	/**
	 * ÃèÊö£º
	 * @param aimCost
	 * @Author£ºkeyan_zhao
	 * @CreateTime£º2013-1-30
	 */
	private Map setParentCost(Map cost) {
		Map allCost = new HashMap();
		for (Iterator ite = cost.keySet().iterator(); ite.hasNext();) {
			String number = (String) ite.next();
			Object object = cost.get(number);
			setNumber(number, allCost, object);
		}
		allCost.putAll(cost);
		return allCost;
	}
	
	public boolean isEmpty(Object obj) {
		return !(obj instanceof String && ((String) obj).trim().length() > 0);
	}

	/**
	 * ÃèÊö£º
	 * @param number
	 * @param allCost
	 * @param object 
	 * @Author£ºkeyan_zhao
	 * @CreateTime£º2013-1-30
	 */
	private void setNumber(String number, Map allCost, Object object) {
		if (isEmpty(number)) {
			return;
		}
		int lastIndexOf = number.lastIndexOf("!");
		if (lastIndexOf > 0) {
			number = number.substring(0, lastIndexOf);
			if (allCost.containsKey(number)) {
				BigDecimal add = FDCHelper.add(allCost.get(number), object);
				allCost.put(number, add);
			} else {
				allCost.put(number, object);
			}
			setNumber(number, allCost, object);
		} else {
			return;
		}
	}
}