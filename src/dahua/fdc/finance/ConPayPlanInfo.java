package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public class ConPayPlanInfo extends AbstractConPayPlanInfo implements Serializable 
{
    public ConPayPlanInfo()
    {
        super();
    }
    protected ConPayPlanInfo(String pkField)
    {
        super(pkField);
    }
    
    //是否计算未签订部分
	public boolean isCalUnsign() {
		return getBoolean("isCalUnsign");
	}

	public void setIsCalUnsign(boolean isCalUnsign) {
		put("isCalUnsign", new Boolean(isCalUnsign));
	}
}