package com.kingdee.eas.xr.xrbase;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;

public class JkyHelper {
	
	public static final String NO_EDIT_INFO="ֻ�б���״̬�µĵ��ݣ������޸�����!";
	public static final String Entry_NOT_NULL="��¼����¼��һ��!";
	public static final String GROUP_NO_ADDNEW="�ɼ���ͳһά������������!";
	public static final String GROUP_NO_EDIT="�ɼ���ͳһά���������޸�!";
	public static final String GROUP_NO_DEL="�ɼ���ͳһά��������ɾ��!";
	public static final String IS_USER="�����õ��˻�����ɾ��!";
	
	/**
	 *���������ɼ���ͳһά�� 
	 * @return
	 * @throws BOSException
	 */
	public static boolean isGroup() throws BOSException{
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo.getId().toString().equals("00000000-0000-0000-0000-000000000000CCE7AED4")) {
			return true;
		}
		else{
			return false;
		}
	}

}
