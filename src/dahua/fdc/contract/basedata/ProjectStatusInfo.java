package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.util.HashMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.util.app.ContextUtil;

public class ProjectStatusInfo extends AbstractProjectStatusInfo implements
		Serializable {
	public ProjectStatusInfo() {
		super();
	}

	protected ProjectStatusInfo(String pkField) {
		super(pkField);
	}


	/**
	 * 	未获取
	 */
	public static final String notGetID = "COR8bAETEADgAADYwKgOlI160rk=";

	/**
	 * 未初始化目标成本
	 */
	public static final String notIntiID = "COR8bAETEADgAADcwKgOlI160rk=";

	/**
	 * 流失
	 */
	public static final String flowID = "COR8bAETEADgAADkwKgOlI160rk=";

	/**
	 *  进行中
	 */
	public static final String proceedingID = "COR8bAETEADgAADowKgOlI160rk=";

	/**
	 * 竣工结算
	 */
	public static final String settleID = "KQNHQgEVEADgABmewKgTBY160rk=";

	/**
	 * 关闭
	 */
	public static final String closeID = "KQNHQgEVEADgABmnwKgTBY160rk=";
	
	/**
	 * 已中转
	 */
	public static final String transferID = "a00J7wEdEADgAAAAwKgQ4o160rk=";
	
	private static class Holder{
		private static final HashMap cache=new HashMap(); 
	}
	public static ProjectStatusInfo getProjectStatueById(String id) throws BOSException, EASBizException{
		if(Holder.cache.get(id)==null){
			Context ctx = ContextUtils.getContextFromSessionNoException();
			IProjectStatus iProjectStatus;
			if(ctx==null){
				iProjectStatus = ProjectStatusFactory.getRemoteInstance();
			}else{
				iProjectStatus=ProjectStatusFactory.getLocalInstance(ctx);
			}
			Holder.cache.put(id, iProjectStatus.getProjectStatusInfo(new ObjectUuidPK(id)));
		}
		return (ProjectStatusInfo)Holder.cache.get(id);
	}
}