package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class CostAccountWithAccountInfo extends AbstractCostAccountWithAccountInfo implements Serializable 
{
    public CostAccountWithAccountInfo()
    {
        super();
    }
    protected CostAccountWithAccountInfo(String pkField)
    {
        super(pkField);
    }
    //��ʵ��û�б��롢���ƣ���Ϊ�ɱ���Ŀ������Ŀֻ��һһ��Ӧ����ȡ�ɱ���Ŀ��������
    public String getLogInfo() {
		String retValue = "";
        if(this.getCostAccount().getNumber()!= null)
        {
            retValue = this.getCostAccount().getNumber();
            if(this.getCostAccount().getName()!=null){
            	retValue = retValue + " " + this.getCostAccount().getName();
            }
        }
        return retValue;
	}
}