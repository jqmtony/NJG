package com.kingdee.eas.xr.xrbase;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;

public class JkyHelper {
	
	public static final String NO_EDIT_INFO="只有保存状态下的单据，才能修改数据!";
	public static final String Entry_NOT_NULL="分录至少录入一行!";
	public static final String GROUP_NO_ADDNEW="由集团统一维护，不能新增!";
	public static final String GROUP_NO_EDIT="由集团统一维护，不能修改!";
	public static final String GROUP_NO_DEL="由集团统一维护，不能删除!";
	public static final String IS_USER="已启用的账户不能删除!";
	
	/**
	 *基础资料由集团统一维护 
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
