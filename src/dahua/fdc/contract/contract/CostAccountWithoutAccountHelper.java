package com.kingdee.eas.fdc.contract;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

public class CostAccountWithoutAccountHelper {
	public static Map getCostAccountWithAccountMapAll(Context ctx, Set curProjectIds,Set costAccountIds) throws BOSException, ContractException, EASBizException {
		Map map=new HashMap();
		if(costAccountIds==null||costAccountIds.size()==0){
			return map;
		}
		String acctIds="";
		int i=0;
		for(Iterator iter=costAccountIds.iterator();iter.hasNext();i++){
			if(i==0){
				acctIds="'"+(String)iter.next()+"'";
			}else{
				acctIds=acctIds+",'"+(String)iter.next()+"'";
			}
		}
		
		//通过科目取出对应关系
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		
		String innersql="select fid from T_fdc_costaccount acct1,T_fdc_costaccount acct2 where acct1.fcurProject=acct2.fcurproject " +
				"			and charindex(acct1.flongnumber||'!',acct2.flongnumber||'!')=1 and acct2.fid in ("+acctIds+")";
		view.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.id",innersql,CompareType.INNER));
		
		CostAccountWithAccountCollection withs=CostAccountWithAccountFactory.getLocalInstance(ctx).getCostAccountWithAccountCollection(view); 
		
		//设置科目的对应关系
		
		return map;
	}
}
