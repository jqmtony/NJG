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
	 * 	δ��ȡ
	 */
	public static final String notGetID = "COR8bAETEADgAADYwKgOlI160rk=";

	/**
	 * δ��ʼ��Ŀ��ɱ�
	 */
	public static final String notIntiID = "COR8bAETEADgAADcwKgOlI160rk=";

	/**
	 * ��ʧ
	 */
	public static final String flowID = "COR8bAETEADgAADkwKgOlI160rk=";

	/**
	 *  ������
	 */
	public static final String proceedingID = "COR8bAETEADgAADowKgOlI160rk=";

	/**
	 * ��������
	 */
	public static final String settleID = "KQNHQgEVEADgABmewKgTBY160rk=";

	/**
	 * �ر�
	 */
	public static final String closeID = "KQNHQgEVEADgABmnwKgTBY160rk=";
	
	/**
	 * ����ת
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