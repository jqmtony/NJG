/**
 * @(#)FdcParamUtil.java 1.0 2013-9-28
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;

/**
 * 房地产 参数工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcParamUtil {

	// private static HashMap hmAllParam = null;
	private static int fdcParamAmountPre = FDCConstants.SCALE_AMOUNT; // 金额精度
	private static int fdcParamQtyPre = FDCConstants.SCALE_QTY; // 数量精度
	private static int fdcParamPricePre = FDCConstants.SCALE_PRICE; // 单价精度

	private static int fdcParamPercentPre = FDCConstants.SCALE_PERCENT; // 百分比精度
	private static int fdcParamRatePre = FDCConstants.SCALE_RATE; // 费率精度
	private static int fdcParamUnitAmountPre = FDCConstants.SCALE_UNITAMOUNT; // 单位金额精度
	private static int fdcParamUnitQtyPre = FDCConstants.SCALE_UNITQTY; // 单位数量精度

	private FdcParamUtil() {
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 只获取集团参数
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static HashMap getDefaultFdcParam(Context ctx) throws BOSException, EASBizException {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;

		HashMap hmParamIn = new HashMap();
		// 会计期间类型
		hmParamIn.put(FDCConstants.FDC101_PERIODTYPE, new ObjectUuidPK(FDCConstants.CORP_CU));

		// 各模块可以单独结账
		hmParamIn.put(FDCConstants.FDC103_CLOSEINIT, new ObjectUuidPK(FDCConstants.CORP_CU));
		// 制单日期可以修改
		hmParamIn.put(FDCConstants.FDC104_ModifyCreat, new ObjectUuidPK(FDCConstants.CORP_CU));
		// 提交（无流程）按钮可见
		hmParamIn.put(FDCConstants.FDC104_ModifySubWFR, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** 基础数据 FDC10 **/

		// 精度
		hmParamIn.put(FDCConstants.FDC102_AMOUT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC103_QTY, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_PRICE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC102_PERCENT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC103_RATE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_UNITAMOUNT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_UNITQTY, new ObjectUuidPK(FDCConstants.CORP_CU));

		// 显示精度
		hmParamIn.put(FDCConstants.FDC105_LTAMOUT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC106_LTQTY, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTPRICE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC105_LTPERCENT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC106_LTRATE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTUNITAMOUNT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTUNITQTY, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** 合同/财务 FDC30 **/
		hmParamIn.put(FDCConstants.FDC351_IS_PAY_SPLIT_MERGE, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** 成本 FDC60 **/
		
		/** 成本管理—其他参数”中“成本测算调整系数” **/
		hmParamIn.put(FDCConstants.FDC_AIMCOST_001, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** 供应商 FDC40 **/

		IParamControl pc;
		if (ctx != null)
			pc = ParamControlFactory.getLocalInstance(ctx);
		else
			pc = ParamControlFactory.getRemoteInstance();
		hmAllParam = pc.getParamHashMap(hmParamIn);

		return hmAllParam;
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 获得参数（财务组织、成本中心）
	 * 
	 * @param ctx
	 * @param companyID
	 * @return
	 */
	public static HashMap getDefaultFdcParam(Context ctx, String orgUnitID) throws BOSException, EASBizException {
		IObjectPK orgUnitPK = null;
		if (orgUnitID != null) {
			orgUnitPK = new ObjectUuidPK(orgUnitID);
		}
		
		HashMap hmParamIn = new HashMap();

		// 取得实例
		IParamControl paramControl = getInstance(ctx);
		HashMap hmAllParam = paramControl.getParamHashMap(hmParamIn);

		return hmAllParam;
	}

	/**
	 * 描述：获得参数（财务组织、成本中心）
	 * 
	 * @param ctx
	 * @param key
	 * @param orgUnitID
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-18
	 */
	public static HashMap getDefaultFdcParam(Context ctx, String key, String orgUnitID) throws BOSException,
			EASBizException {
		IObjectPK orgUnitPK = null;
		if (orgUnitID != null) {
			orgUnitPK = new ObjectUuidPK(orgUnitID);
		}

		HashMap hmParamIn = new HashMap();
		hmParamIn.put(key, new ObjectUuidPK(orgUnitID));

		// 取得实例
		IParamControl paramControl = getInstance(ctx);
		ParamItemInfo paramItemInfo = paramControl.getParamItemByNumberAndOrg(key, orgUnitID);

		HashMap hmAllParam = new HashMap(); 
		if (null != paramItemInfo) {
			hmAllParam.put(key, paramItemInfo.getValue());
		}

		return hmAllParam;
	}

	/**
	 * 描述：取得实例
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-9-18
	 */
	private static IParamControl getInstance(Context ctx) throws BOSException {
		IParamControl paramControl;
		
		if (ctx != null) {
			paramControl = ParamControlFactory.getLocalInstance(ctx);
		} else {
			paramControl = ParamControlFactory.getRemoteInstance();
		}
		
		return paramControl;
	}

	/**
	 * 获取集团参数值
	 * 
	 * @param ctx
	 * @param key
	 * @return
	 */
	public static Object getDefaultFdcParamValue(Context ctx, String key) {
		Object value = null;

		// 如果有缓存，直接返回
		Map hmAllParam = null;
		try {
			hmAllParam = getDefaultFdcParam(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (FdcMapUtil.isNotEmpty(hmAllParam)) {
			value = hmAllParam.get(key);
		}

		return value;
	}

	// 获取Boolean参数
	public static boolean getDefaultFdcParamValueBoolean(Context ctx, String key) {
		boolean value = false;

		Map params = null;
		try {
			params = getDefaultFdcParam(ctx);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		value = FdcMapUtil.getBooleanValue(params, key, value);

		return value;
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	public static int getFdcParamAmountPre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamAmountPre;
			}
		}
		if (hmAllParam.get(FDCConstants.FDC102_AMOUT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC102_AMOUT);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamAmountPre = Integer.parseInt(s);
			}
		}
		return fdcParamAmountPre;
	}

	public static int getFdcParamQtyPre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamQtyPre;
			}
		}

		if (hmAllParam.get(FDCConstants.FDC102_AMOUT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC103_QTY);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamQtyPre = Integer.parseInt(s);
			}
		}
		return fdcParamQtyPre;
	}

	public static int getFdcParamPricePre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamPricePre;
			}
		}
		if (hmAllParam.get(FDCConstants.FDC102_AMOUT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC104_PRICE);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamPricePre = Integer.parseInt(s);
			}
		}
		return fdcParamPricePre;
	}

	public static int getFdcParamAmountPre() {
		return getFdcParamAmountPre(null);
	}

	public static int getFdcParamQtyPre() {
		return getFdcParamQtyPre(null);
	}

	public static int getFdcParamPricePre() {
		return getFdcParamPricePre(null);
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	public static int getFdcParamPercentPre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamPercentPre;
			}
		}
		if (hmAllParam.get(FDCConstants.FDC102_PERCENT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC102_PERCENT);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamPercentPre = Integer.parseInt(s);
			}
		}
		return fdcParamPercentPre;
	}

	public static int getFdcParamRatePre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamRatePre;
			}
		}

		if (hmAllParam.get(FDCConstants.FDC102_PERCENT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC103_RATE);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamRatePre = Integer.parseInt(s);
			}
		}
		return fdcParamRatePre;
	}

	public static int getFdcParamUnitAmountPre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamUnitAmountPre;
			}
		}
		if (hmAllParam.get(FDCConstants.FDC102_PERCENT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC104_UNITAMOUNT);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamUnitAmountPre = Integer.parseInt(s);
			}
		}
		return fdcParamUnitAmountPre;
	}

	public static int getFdcParamUnitQtyPre(Context ctx) {
		// 如果有缓存，直接返回
		HashMap hmAllParam = null;
		// 如果没有缓存，先取
		if (hmAllParam == null) {
			try {
				hmAllParam = getDefaultFdcParam(ctx);
			} catch (Exception e) {
				e.printStackTrace();
				return fdcParamUnitQtyPre;
			}
		}
		if (hmAllParam.get(FDCConstants.FDC102_PERCENT) != null) {
			String s = (String) hmAllParam.get(FDCConstants.FDC104_UNITQTY);
			if (s != null && !s.trim().equals("") && !s.trim().equals("none")) {
				fdcParamUnitQtyPre = Integer.parseInt(s);
			}
		}
		return fdcParamUnitQtyPre;
	}

	public static int getFdcParamPercentPre() {
		return getFdcParamPercentPre(null);
	}

	public static int getFdcParamRatePre() {
		return getFdcParamRatePre(null);
	}

	public static int getFdcParamUnitAmountPre() {
		return getFdcParamUnitAmountPre(null);
	}

	public static int getFdcParamUnitQtyPre() {
		return getFdcParamUnitQtyPre(null);
	}
}
