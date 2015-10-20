package com.kingdee.eas.fdc.contract.programming.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.fdc.finance.IPayPlanTemplate;
import com.kingdee.eas.fdc.finance.PayPlanTemplateFactory;
import com.kingdee.eas.fdc.finance.PayPlanTemplateInfo;

public class ProgrammingTemplateControllerBean extends AbstractProgrammingTemplateControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgrammingTemplateControllerBean");

	protected void _copy(Context ctx) throws BOSException {
		logger.assertLog(true, "复制功能");
	}
	
	protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//因名称缩进显示，调用基类校验时，不通过的话会把缩进效果去除所以覆盖掉基类方法、校验由前台完成
	}
	
	 protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException {
		//因名称缩进显示，调用基类校验时，不通过的话会把缩进效果去除所以覆盖掉基类方法、校验由前台完成
     }
	 
	 
	 /**
	 * 描述：同步付款规划模板
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingTemplateInfo info = (ProgrammingTemplateInfo) model;
		ProgrammingTemplateEntireCollection eColl = info.getEntires();
		IPayPlanTemplate localInstance = PayPlanTemplateFactory.getLocalInstance(ctx);
		for (int i = 0; eColl != null && i < eColl.size(); i++) {
			ProgrammingTemplateEntireInfo eInfo = eColl.get(i);
			if (eInfo.containsKey("PayPlan")) {
				PayPlanTemplateInfo tInfo = (PayPlanTemplateInfo) eInfo.get("PayPlan");
				tInfo.setProgrammingTemplate(eInfo);
				localInstance.submit(tInfo);
			}
		}
		return super._submit(ctx, model);
	}

	/**
	 * 描述：同步付款规划模板
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ProgrammingTemplateInfo info = (ProgrammingTemplateInfo) model;
		ProgrammingTemplateEntireCollection eColl = info.getEntires();
		IPayPlanTemplate localInstance = PayPlanTemplateFactory.getLocalInstance(ctx);
		for (int i = 0; eColl != null && i < eColl.size(); i++) {
			ProgrammingTemplateEntireInfo eInfo = eColl.get(i);
			if (eInfo.containsKey("PayPlan")) {
				PayPlanTemplateInfo tInfo = (PayPlanTemplateInfo) eInfo.get("PayPlan");
				tInfo.setProgrammingTemplate(eInfo);
				localInstance.save(tInfo);
			}
		}

		return super._save(ctx, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		_isReferenced(ctx, pk);
		super._delete(ctx, pk);
	}
}