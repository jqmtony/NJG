package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class PaymentTypeInfo extends AbstractPaymentTypeInfo implements Serializable 
{
    public PaymentTypeInfo()
    {
        super();
    }
    protected PaymentTypeInfo(String pkField)
    {
        super(pkField);
    }
    
	/** 付款类型 类别 结算款ID */
	public static final String settlementID = TypeInfo.settlementID;

	/** 付款类型 类别 进度款ID */
	public static final String progressID = TypeInfo.progressID;

	/** 付款类型 类别 保修款ID */
	public static final String keepID = TypeInfo.keepID;
	
	/** 付款类型 类别 暂估款ID */
	public static final String tempID = TypeInfo.tempID;

	
	/** 付款类型 类别 预付款ID */
	public boolean isPreType() {
		return "预付款".equals(getName());
	}
 
	//是否进度款
	public boolean isScheuleType() {
		return "进度款".equals(getName());
	}

	//是否结算款
	public boolean isSettleType() {
		return "结算款".equals(getName());
	}
	/**
	 * 预付款ID
	 */
	// public static final String preID="Ga7RLQETEADgAAD9wKgOlOwp3Sw=";//预付款
	

	
}