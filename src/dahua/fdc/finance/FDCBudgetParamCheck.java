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
	 * ������(2)
	 */
	private static final String NO_CONTROL = "2";
	/**
	 * ��ʾ����(1)
	 */
	private static final String PROMPT_CONTROL = "1";
	/**
	 * �ϸ����(0)
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
	 * FDC����У��
	 * 
	 * @param ctx
	 * @param param
	 *            ��������
	 * @param orgUnitId
	 *            ��֯
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void checkParamUpdate(Context ctx, ParamItemInfo model, ParamInfo param, String orgUnit) throws BOSException, EASBizException, SQLException {
		String number = param.getNumber();
		FDCBudgetParam params=FDCBudgetParam.getInstance(ctx, orgUnit);
		//			 			
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY)) {
			//Ԥ��ϵͳ����
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY)) {
			//��ͬ����
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY)) {
			//�ɱ���Ŀ����
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
		}
		
		//����޸ĵĲ����ǡ���ͬ�ƻ����Ƶ��¸��
		if (FDCConstants.FDC311_CONTRACTCTRPAY.equals(number)) {
			if (Boolean.TRUE.toString().equals(model.getValue())) {

				//��ȡ����FDC325_CONTROLPAYREQUEST��ֵ
				String paramValue = FDCUtils.getFDCParamByKey(ctx, orgUnit, FDCConstants.FDC325_CONTROLPAYREQUEST);
				//�������ֵ���ǡ������ơ����׳��쳣��ʾ
				if (!NO_CONTROL.equals(paramValue)) {
										throw new FDCException(FDCException.FDC311_UPDATE_EXCEPTION);
				}
			}
		}
		SysContext.getSysContext().getCurrentOrgUnit();
		//����޸ĵĲ����ǡ�ͨ����ͬ�¶ȹ�������ƻ����ƺ�ͬ�������뼰����Ʋ��ԡ�
		if (FDCConstants.FDC325_CONTROLPAYREQUEST.equals(number)) {

			//����޸ĵ�ֵ�� ���ϸ���ơ� �� ����ʾ���ơ�
			if (STRICT_CONTROL.equals(model.getValue()) || PROMPT_CONTROL.equals(model.getValue())) {

				//���������FDC311_CONTRACTCTRPAY��������ֵ�ǡ��ǡ�
				if (FDCUtils.getDefaultFDCParamByKey(ctx, orgUnit, FDCConstants.FDC311_CONTRACTCTRPAY)) {
										throw new FDCException(FDCException.FDC325_UPDATE_EXCEPTION);
				}
			}
		}
		
	}
}
