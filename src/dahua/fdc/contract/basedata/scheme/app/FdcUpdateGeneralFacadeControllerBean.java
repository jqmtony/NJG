package com.kingdee.eas.fdc.basedata.scheme.app;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;

public class FdcUpdateGeneralFacadeControllerBean extends AbstractFdcUpdateGeneralFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.basedata.scheme.app.FdcUpdateGeneralFacadeControllerBean");

	protected Map _getData(Context ctx, Map param) throws BOSException {
		if (true) {
			throw new BOSException("未实现的方法");
		}

		// //SystemControlUtils
		// if(param!=null && param.get("SystemControl")!=null){
		// return SystemControlUtils._getData(ctx, param);
		// }

		return null;
	}

	protected Map _updateData(Context ctx, Map param) throws BOSException {

		if (param != null && param.get("DelProject") != null) {
			return DelProjectUtil._updateData(ctx, param);
		} else if (param != null && param.get("DelBiz") != null) {
			return DelProjectUtil._updateData(ctx, param);

		}

		return null;
	}
}