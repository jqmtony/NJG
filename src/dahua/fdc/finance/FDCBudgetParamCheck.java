package com.kingdee.eas.fdc.finance;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.base.param.IParamSetModify;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCException;
import com.kingdee.eas.fdc.common.REParamCheckAdaptor;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;

public class FDCBudgetParamCheck extends REParamCheckAdaptor implements IParamSetModify {

	/**
	 * 不控制(2)
	 */
	private static final String NO_CONTROL = "2";
	/**
	 * 提示控制(1)
	 */
	private static final String PROMPT_CONTROL = "1";
	/**
	 * 严格控制(0)
	 */
	private static final String STRICT_CONTROL = "0";
	
	
	public void check(Context ctx, ParamItemInfo model) throws BOSException, EASBizException {

		String orgunitid = null;
		if (model.getOrgUnitID() != null) {
			orgunitid = model.getOrgUnitID().getId().toString();
		}

		try {
			checkParamUpdate(ctx, model, model.getKeyID(), orgunitid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	public boolean setParamModify(Context ctx, ParamInfo paramInfo) throws BOSException, EASBizException {

		return true;
	}

	/**
	 * FDC参数校验
	 * 
	 * @param ctx
	 * @param param
	 *            参数对象
	 * @param orgUnitId
	 *            组织
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void checkParamUpdate(Context ctx, ParamItemInfo model, ParamInfo param, String orgUnit) throws BOSException, EASBizException, SQLException {
		String number = param.getNumber();
		FDCBudgetParam params=FDCBudgetParam.getInstance(ctx, orgUnit);
		//			 			
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY)) {
			//预算系统控制
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY)) {
			//合同控制
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY)) {
			//成本科目控制
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
		}
		
		//如果修改的参数是【合同计划控制当月付款】
		if (FDCConstants.FDC311_CONTRACTCTRPAY.equals(number)) {
			if (Boolean.TRUE.toString().equals(model.getValue())) {

				//获取参数FDC325_CONTROLPAYREQUEST的值
				String paramValue = FDCUtils.getFDCParamByKey(ctx, orgUnit, FDCConstants.FDC325_CONTROLPAYREQUEST);
				//如果参数值不是‘不控制’就抛出异常提示
				if (!NO_CONTROL.equals(paramValue)) {
										throw new FDCException(FDCException.FDC311_UPDATE_EXCEPTION);
				}
			}
		}
		SysContext.getSysContext().getCurrentOrgUnit();
		//如果修改的参数是【通过合同月度滚动付款计划控制合同付款申请及其控制策略】
		if (FDCConstants.FDC325_CONTROLPAYREQUEST.equals(number)) {

			//如果修改的值是 ‘严格控制’ 和 ‘提示控制’
			if (STRICT_CONTROL.equals(model.getValue()) || PROMPT_CONTROL.equals(model.getValue())) {

				//如果参数‘FDC311_CONTRACTCTRPAY’参数的值是‘是’
				if (FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDC311_CONTRACTCTRPAY)) {
										throw new FDCException(FDCException.FDC325_UPDATE_EXCEPTION);
				}
			}
		}
		
	}
}
