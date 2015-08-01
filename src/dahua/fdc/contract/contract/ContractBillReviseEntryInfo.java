package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractBillReviseEntryInfo extends AbstractContractBillReviseEntryInfo implements Serializable 
{
    public ContractBillReviseEntryInfo()
    {
        super();
    }
    protected ContractBillReviseEntryInfo(String pkField)
    {
        super(pkField);
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.DataTypeEnum item)
    {
    	if(item==null){
//    		setInt("dataType", item.getValue());
    	}else{
    		super.setDataType(item);
    	}
    }
}