/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.fdc.basedata.AdjustReasonFactory;
import com.kingdee.eas.fdc.basedata.AdjustReasonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		�쿡
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class AdjustReasonTransmission extends AbstractDataTransmission {

	private static String resourceBase = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return AdjustReasonFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		AdjustReasonInfo info = new AdjustReasonInfo();

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
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsNull",resourceBase));
		}
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsNull",resourceBase));
		}
		/*
		 * �ж��ַ�����
		 */
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsTooLong",resourceBase));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsTooLong",resourceBase));
		}
		if (fDescriptionl2.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "CostAccount_Import_fDescriptionL2",resourceBase));
		}

		
		 /*
		 * �����ж�
		 */
		// ����
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		}else{
			info.setIsEnabled(false);
		}
		info.setNumber(fNumber);
		info.setName(fNamel2);
		info.setDescription(fDescriptionl2);
      
		return info;
	}
	/**
	 * �õ���Դ�ļ�
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}

}
