package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ContractCodingTypeInfo extends AbstractContractCodingTypeInfo implements Serializable 
{
    public ContractCodingTypeInfo()
    {
        super();
    }
    protected ContractCodingTypeInfo(String pkField)
    {
        super(pkField);
    }
    
    //Ĭ�ϲ��ñ���
    public String getLogInfo() {
		String retValue = "";
        if(this.getNumber()!= null)
        {
            retValue = this.getNumber();
        }
        return retValue;
	}
}