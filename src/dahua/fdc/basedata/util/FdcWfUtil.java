/**
 * @(#)FdcWfUtil.java 1.0 2014-12-3
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * 描述：房地产 工作流工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-12-3
 * @version 1.0, 2014-12-3
 * @see
 * @since JDK1.4
 */
public class FdcWfUtil {

	/**
	 * 是否已经定义工作流
	 * 
	 * @param editData
	 *            编辑数据对象
	 * @param functionName
	 *            function名称
	 * @param operationName
	 *            操作名称
	 * @return
	 * @throws Exception
	 */
	public static boolean hasDefWf(CoreBaseInfo editData, String functionName, String operationName) throws Exception {
		boolean flag = false;

		if (editData.getBOSType() != null) {
			IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
			String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			IObjectValue bizObj = AgentUtility.getNoAgentValue(editData);
			String procDefID = service.findSubmitProcDef(userID, bizObj, functionName, operationName);

			if (procDefID != null) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 描述：判断单据是否在工作流中
	 * 
	 * @param id
	 *            单据ID
	 * @return
	 * @author rd_skyiter_wang
	 * @throws BOSException
	 * @createDate 2014-12-3
	 */
	public static boolean isBillInWorkflow(String id) throws BOSException {
		boolean flag = false;

		ProcessInstInfo[] processInstInfoArr = null;
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
		processInstInfoArr = service.getProcessInstanceByHoldedObjectId(id);

		if (FdcArrayUtil.isEmpty(processInstInfoArr)) {
			return flag;
		}

		ProcessInstInfo processInstInfo = null;
		for (int i = 0, len = processInstInfoArr.length; i < len; i++) {
			String state = processInstInfoArr[i].getState();
			if ("open.running".equals(state) || "open.not_running.suspended".equals(state)) {
				processInstInfo = processInstInfoArr[i];
				break;
			}
		}
		if (processInstInfo != null) {
			flag = true;
		}

		return flag;
	}

}
