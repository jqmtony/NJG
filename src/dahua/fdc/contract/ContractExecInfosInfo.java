package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractExecInfosInfo extends AbstractContractExecInfosInfo implements Serializable 
{
    public ContractExecInfosInfo()
    {
        super();
    }
    protected ContractExecInfosInfo(String pkField)
    {
        super(pkField);
    }
	/**
	 * 更新单据时参数type的四个状态 : audit审批、unAudit反审批、pay付款、unPay反付款
	 */
	
	//审批
	public static final String EXECINFO_AUDIT = "audit";
	//反审批
	public static final String EXECINFO_UNAUDIT = "unAudit";
	//付款
	public static final String EXECINFO_PAY = "pay";
	//反付款
	public static final String EXECINFO_UNPAY = "unPay";
}