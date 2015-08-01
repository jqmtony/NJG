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
 * ���ز� ����������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-28
 * @see
 * @since JDK1.4
 */
public class FdcParamUtil {

	// private static HashMap hmAllParam = null;
	private static int fdcParamAmountPre = FDCConstants.SCALE_AMOUNT; // ����
	private static int fdcParamQtyPre = FDCConstants.SCALE_QTY; // ��������
	private static int fdcParamPricePre = FDCConstants.SCALE_PRICE; // ���۾���

	private static int fdcParamPercentPre = FDCConstants.SCALE_PERCENT; // �ٷֱȾ���
	private static int fdcParamRatePre = FDCConstants.SCALE_RATE; // ���ʾ���
	private static int fdcParamUnitAmountPre = FDCConstants.SCALE_UNITAMOUNT; // ��λ����
	private static int fdcParamUnitQtyPre = FDCConstants.SCALE_UNITQTY; // ��λ��������

	private FdcParamUtil() {
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * ֻ��ȡ���Ų���
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static HashMap getDefaultFdcParam(Context ctx) throws BOSException, EASBizException {
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;

		HashMap hmParamIn = new HashMap();
		// ����ڼ�����
		hmParamIn.put(FDCConstants.FDC101_PERIODTYPE, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ��ģ����Ե�������
		hmParamIn.put(FDCConstants.FDC103_CLOSEINIT, new ObjectUuidPK(FDCConstants.CORP_CU));
		// �Ƶ����ڿ����޸�
		hmParamIn.put(FDCConstants.FDC104_ModifyCreat, new ObjectUuidPK(FDCConstants.CORP_CU));
		// �ύ�������̣���ť�ɼ�
		hmParamIn.put(FDCConstants.FDC104_ModifySubWFR, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** �������� FDC10 **/

		// ����
		hmParamIn.put(FDCConstants.FDC102_AMOUT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC103_QTY, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_PRICE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC102_PERCENT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC103_RATE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_UNITAMOUNT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC104_UNITQTY, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ��ʾ����
		hmParamIn.put(FDCConstants.FDC105_LTAMOUT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC106_LTQTY, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTPRICE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC105_LTPERCENT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC106_LTRATE, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTUNITAMOUNT, new ObjectUuidPK(FDCConstants.CORP_CU));
		hmParamIn.put(FDCConstants.FDC107_LTUNITQTY, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** ��ͬ/���� FDC30 **/
		hmParamIn.put(FDCConstants.FDC351_IS_PAY_SPLIT_MERGE, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** �ɱ� FDC60 **/
		
		/** �ɱ����������������С��ɱ��������ϵ���� **/
		hmParamIn.put(FDCConstants.FDC_AIMCOST_001, new ObjectUuidPK(FDCConstants.CORP_CU));

		// ///////////////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////////////////////////
		/** ��Ӧ�� FDC40 **/

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
	 * ��ò�����������֯���ɱ����ģ�
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

		// ȡ��ʵ��
		IParamControl paramControl = getInstance(ctx);
		HashMap hmAllParam = paramControl.getParamHashMap(hmParamIn);

		return hmAllParam;
	}

	/**
	 * ��������ò�����������֯���ɱ����ģ�
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

		// ȡ��ʵ��
		IParamControl paramControl = getInstance(ctx);
		ParamItemInfo paramItemInfo = paramControl.getParamItemByNumberAndOrg(key, orgUnitID);

		HashMap hmAllParam = new HashMap(); 
		if (null != paramItemInfo) {
			hmAllParam.put(key, paramItemInfo.getValue());
		}

		return hmAllParam;
	}

	/**
	 * ������ȡ��ʵ��
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
	 * ��ȡ���Ų���ֵ
	 * 
	 * @param ctx
	 * @param key
	 * @return
	 */
	public static Object getDefaultFdcParamValue(Context ctx, String key) {
		Object value = null;

		// ����л��棬ֱ�ӷ���
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

	// ��ȡBoolean����
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
		// ����л��棬ֱ�ӷ���
		HashMap hmAllParam = null;
		// ���û�л��棬��ȡ
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
