package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PayRequestBillInfo extends AbstractPayRequestBillInfo implements Serializable 
{
    public PayRequestBillInfo()
    {
        super();
    }
    protected PayRequestBillInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setUrgentDegree(com.kingdee.eas.fdc.contract.UrgentDegreeEnum item)
    {
    	if(item==null){
    		 setInt("urgentDegree", UrgentDegreeEnum.NORMAL.getValue());
    	}else{
    		 super.setUrgentDegree(item);
    	}
       
    }
}