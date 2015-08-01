package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
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
 * ������		����ϵ��ת���ࡪ����˼��
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see
 */
public class ProjectTypeTransmission extends AbstractDataTransmission{

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";


	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return ProjectTypeFactory.getLocalInstance(ctx);
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
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx) throws TaskExternalException {
		
		String number = getString(hsdata, "FNumber");						// ����
		String name = getString(hsdata, "FName_l2");						// ����
		String description = getString(hsdata, "FDescription_l2");			// ����
		String isEnabled = getString(hsdata, "FIsEnabled");					// ����
		
		
		// ��¼У��
		isFitLength(ctx,"ProjectTypeNumberIsNull", number);
		isFitLength(ctx,"ProjectTypeNameIsNull", name);
		// ����У��
		isFitLength(ctx, number, 80, "ProjectTypeNumberIsOver80");
		isFitLength(ctx, name, 80, "ProjectTypeNameIsOver80");
		isFitLength(ctx,description, 200, "ProjectTypeDescriptionIsOver200");
		
		boolean bIsEnabled =getResource(ctx, "no",resourceCommon).equalsIgnoreCase(isEnabled) ? false : true;
		
		
		/// У�������
		ProjectTypeInfo info=new ProjectTypeInfo();
		info.setNumber(number);
		info.setName(name);
		info.setDescription(description);
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
	private void isThrow(String value, String alertMsg)	throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) :  alertMsg;
		throw new TaskExternalException(msg);
	}

	
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
