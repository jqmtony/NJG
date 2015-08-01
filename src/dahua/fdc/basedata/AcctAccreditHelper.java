package com.kingdee.eas.fdc.basedata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;

public class AcctAccreditHelper {
	
	public static boolean hasUsed(Context ctx) throws BOSException{
		HashMap hmParamIn=new HashMap();
        String key = FDCConstants.FDC_PARAM_COSTACCOUNTSMANDATE;
		hmParamIn.put(key, null);
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam;
		try {
			hmAllParam = pc.getParamHashMap(hmParamIn);
		} catch (EASBizException e) {
			throw new BOSException(e);
		}
        Object theValue = hmAllParam.get(key);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
	}
	/**
	 * 取项目上授权的成本科目
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 */
	public static Set getPrjAccreditAcctSet(Context ctx,String prjId) throws BOSException{
		IAcctAccreditFacade iAcctAccredit = null;
		if(ctx!=null){
			 iAcctAccredit = AcctAccreditFacadeFactory.getLocalInstance(ctx);
		}else{
			iAcctAccredit = AcctAccreditFacadeFactory.getRemoteInstance();
		}
		Set set=iAcctAccredit.getPrjAccreditAcctSet(prjId);
		if(set==null){
			set=new HashSet();
		}
		return set;
	}
	
	/**
	 * 取组织上授权的成本科目，仅限公司
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 */
	public static Set getOrgAccreditAcctSet(Context ctx,String orgUnitId) throws BOSException{
		IAcctAccreditFacade iAcctAccredit = null;
		if(ctx!=null){
			 iAcctAccredit = AcctAccreditFacadeFactory.getLocalInstance(ctx);
		}else{
			iAcctAccredit = AcctAccreditFacadeFactory.getRemoteInstance();
		}
		Set set=iAcctAccredit.getOrgAccreditAcctSet(orgUnitId);
		if(set==null){
			set=new HashSet();
		}
		return set;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	
	/**
	 *虚拟科目ID 
	 */
	private static String newAcctId = BOSUuid.create(new CostAccountInfo().getBOSType()).toString();
	/**如果启用参数添加科目授权的控制,返回授权的科目
	 * @param ctx
	 * @param objectId
	 * @param filter
	 * @return 授权的科目集
	 * @throws BOSException
	 */
	public static Set handAcctAccreditFilter(Context ctx,String objectId,FilterInfo filter) throws BOSException{
		if(filter==null||objectId==null||!hasUsed(ctx)){
			return new HashSet();
		}
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		Set accreditSet=null;
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			accreditSet=getPrjAccreditAcctSet(ctx, objectId);
		} else {
			accreditSet=getOrgAccreditAcctSet(ctx, objectId);
		}
		FilterInfo myFilter=new FilterInfo();
		if(accreditSet.size()>0){
			myFilter.getFilterItems().add(new FilterItemInfo("id",accreditSet,CompareType.INCLUDE));
		}else{
			//等于一个不存在的值,即不取任何科目
			myFilter.getFilterItems().add(new FilterItemInfo("id",newAcctId));
		}
		filter.mergeFilter(myFilter, "and");
		
		return accreditSet;
	}

}
