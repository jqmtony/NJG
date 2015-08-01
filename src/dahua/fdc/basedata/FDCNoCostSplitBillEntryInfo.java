package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCNoCostSplitBillEntryInfo extends AbstractFDCNoCostSplitBillEntryInfo implements Serializable 
{
    public FDCNoCostSplitBillEntryInfo()
    {
        super();
    }
    protected FDCNoCostSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    public void setSplitType(com.kingdee.eas.fdc.basedata.CostSplitTypeEnum item)
    {
    	if(item==null) return;
    	else{
    		super.setSplitType(item);
    	}
    }
}