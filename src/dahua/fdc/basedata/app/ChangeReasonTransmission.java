package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		���ԭ��ת���ࡪ����˼��
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-1	 
 * @see
 */
public class ChangeReasonTransmission extends AbstractDataTransmission {
	

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ChangeReasonFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}

	/**ת������*/
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		return innerTransform(hsdata, ctx);
	}

	/**�ڵ�ת��������*/
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		
		String number = getString(hsdata, "FNumber");							// ����
		String name = getString(hsdata, "FName_l2");							// ����
		String changeTypeNumber = getString(hsdata, "FChangeType_number");		// �������
		String description = getString(hsdata, "FDescription_l2");				// ����
		String isEnabled = getString(hsdata, "FIsEnabled");						// ����

		
		// ��¼У��
		isFitLength(ctx, "ChangeReasonNumberIsNull", number);
		isFitLength(ctx, "ChangeReasonNameIsNull", name);
		isFitLength(ctx,"ChangeTypeIsNull", changeTypeNumber);
		// ����У��
		isFitLength(ctx, number, 80, "ChangeReasonNumberIsOver80");
		isFitLength(ctx, name, 80, "ChangeReasonNameIsOver80");
		isFitLength(ctx, description, 200, "ChangeReasonDescriptionIsOver200");

		boolean bIsEnabled = getResource(ctx, "yes",resourceCommon).equalsIgnoreCase(isEnabled) ? true : false;
		
		
		ChangeTypeInfo changeTypeInfo = null;
		try {
			ChangeTypeCollection changeTypeCol = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(
							getEntityViewInfoInstance("number", changeTypeNumber));
			
			if(changeTypeCol==null||changeTypeCol.size()==0){
				this.isThrow(getResource(ctx, "ChangeTypeIsNotExist",resource));
			} else {
				changeTypeInfo = changeTypeCol.get(0); 
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		
		ChangeReasonInfo info = new ChangeReasonInfo();
		info.setNumber(number);
		info.setName(name);
		info.setDescription(description);
		info.setChangeType(changeTypeInfo);
		info.setIsEnabled(bIsEnabled);
						
		return info;
	}

	/** ��ȡ�ַ������� */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** �ж϶�Ӧ�ַ��������Ƿ����ָ������ */
	private void isFitLength(Context ctx, String value, int length, String alertMsgKey) 
			throws TaskExternalException {
		if (value != null && value.trim().length() != 0) {
			if (value.trim().length() > length) isThrow(getResource(ctx, alertMsgKey,resource));
		}
	}

	/** �ж��ַ����Ƿ�Ϊ�� */
	private void isFitLength(Context ctx, String alertMsgKey, String value)
			throws TaskExternalException {
		if (value == null || value.trim().length() == 0) {
			isThrow(getResource(ctx, alertMsgKey,resource));
		}
	}
	
	/** �����׳�ָ��Msg���쳣 */
	private void isThrow(String alertMsg) throws TaskExternalException {
		isThrow(null, alertMsg);
	}
	
	/** �����׳�ָ��Msg���쳣 */
	private void isThrow(String value, String alertMsg)
			throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) : (value.trim() + " " + alertMsg);
		throw new TaskExternalException(msg);
	}

	/** ָ������������ȡ��ͼʵ�� */
	private EntityViewInfo getEntityViewInfoInstance(String property,String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property,value,CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		return view;
	}
	
	
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
