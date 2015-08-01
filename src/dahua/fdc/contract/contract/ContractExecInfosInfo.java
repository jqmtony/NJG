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
	 * ���µ���ʱ����type���ĸ�״̬ : audit������unAudit��������pay���unPay������
	 */
	
	//����
	public static final String EXECINFO_AUDIT = "audit";
	//������
	public static final String EXECINFO_UNAUDIT = "unAudit";
	//����
	public static final String EXECINFO_PAY = "pay";
	//������
	public static final String EXECINFO_UNPAY = "unPay";
}