package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class JobTypeTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return JobTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		JobTypeInfo info = new JobTypeInfo();

		// ����
		String fNumber = (String) ((DataToken) hsData.get("FNumber")).data;
		// ����
		String fNamel2 = (String) ((DataToken) hsData.get("FName_l2")).data;
		// ����
		String fDescriptionl2 = (String) ((DataToken) hsData.get("FDescription_l2")).data;
		// ����
		String fIsEnabled = (String) ((DataToken) hsData.get("FIsEnabled")).data;

		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumberNotNull",resource));
		}
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNameNotNull",resource));
		}
		/*
		 * �ж��ַ����� 
		 */
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumberLen80",resource));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fNameLen80",resource));
		}
		if (fDescriptionl2.length() > 200) {
			throw new TaskExternalException(getResource(ctx,"CostAccount_Import_fDescriptionL2",resource));
		}

		
		 /*
		 * �����ж�
		 */
		// ����
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		}else {
			info.setIsEnabled(false);
		}
		info.setNumber(fNumber);
		info.setName(fNamel2);
		info.setDescription(fDescriptionl2);
      
		return info;
	}
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
