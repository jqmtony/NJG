package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class InvalidCostSplitHelper {
	
	public static void invalidCostSplit(Context ctx,CostSplitBillTypeEnum costSplitBillType, BOSUuid costBillId, BOSUuid splitBillId) throws BOSException, EASBizException {
		//作废之前的汇总
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_AIM_CostSplit set FIsInvalid=1 where FCostBillType=? and FSplitBillId=? and FIsInvalid=0 ");
		builder.addParam(costSplitBillType.getValue().toString());
//		builder.addParam(costBillId.toString());
		builder.addParam(splitBillId.toString());
		builder.executeUpdate();
		
	}
	
}
