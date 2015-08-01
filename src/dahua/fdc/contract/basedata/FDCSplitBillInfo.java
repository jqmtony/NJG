package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCSplitBillInfo extends AbstractFDCSplitBillInfo implements Serializable 
{
    public FDCSplitBillInfo()
    {
        super();
    }
    protected FDCSplitBillInfo(String pkField)
    {
        super(pkField);
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
    	if(item==null){
    		setString("splitState", null);
    	}else{
    		super.setSplitState(item);
    	}
        
    }
}