/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.TypeFactory;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.UuidException;


public class PaymentTypeTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return PaymentTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		PaymentTypeInfo info = new PaymentTypeInfo();

		// ����
		String fNumber = (String) ((DataToken) hsData.get("FNumber")).data;
		// ����
		String fNamel2 = (String) ((DataToken) hsData.get("FName_l2")).data;
		// ���
		String fPayType_Name = (String) ((DataToken)hsData.get("FPayType_name_l2")).data;
		// ����
		String fDescriptionl2 = (String) ((DataToken) hsData.get("FDescription_l2")).data;
		// ����
		String fIsEnabled = (String) ((DataToken) hsData.get("FIsEnabled")).data;

		/*
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsNull",resource));
		}
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsNull",resource));
		}
		if (StringUtils.isEmpty(fPayType_Name)) {
			throw new TaskExternalException(getResource(ctx, "Import_PayTypeIsNull",resource));
		}
		/*
		 * �ж��ַ�����
		 */
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsTooLong",resource));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsTooLong",resource));
		}
		if (fDescriptionl2.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "CostAccount_Import_fDescriptionL2",resource));
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
		
		TypeInfo typeInfo = new TypeInfo();
		if (fPayType_Name.trim().equals(
				getResource(ctx, "Import_PayTypeJ", resource))) {

			typeInfo.setId(BOSUuid.read(typeInfo.progressID));
			info.setPayType(typeInfo);

		} else if (fPayType_Name.trim().equals(
				getResource(ctx, "Import_PayTypeS", resource))) {

			typeInfo.setId(BOSUuid.read(typeInfo.settlementID));
			info.setPayType(typeInfo);

		} else if (fPayType_Name.trim().equals(
				getResource(ctx, "Import_PayTypeB", resource))) {
			typeInfo.setId(BOSUuid.read(typeInfo.keepID));
			info.setPayType(typeInfo);

		} else
			throw new TaskExternalException(getResource(ctx,
					"Import_PayTypeIsWrong", resource));

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
