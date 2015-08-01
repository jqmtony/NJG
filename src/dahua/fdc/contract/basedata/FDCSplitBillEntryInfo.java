package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCSplitBillEntryInfo extends AbstractFDCSplitBillEntryInfo implements Serializable 
{
    public FDCSplitBillEntryInfo()
    {
        super();
    }
    protected FDCSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setSplitType(com.kingdee.eas.fdc.basedata.CostSplitTypeEnum item)
    {
    	if(item==null){
    		setString("splitType", null);
    	}
    	else{
    		super.setSplitType(item);
    	}
    }

}