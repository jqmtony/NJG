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
    
    //�Ƿ����δǩ������
	public boolean isCalUnsign() {
		return getBoolean("isCalUnsign");
	}

	public void setIsCalUnsign(boolean isCalUnsign) {
		put("isCalUnsign", new Boolean(isCalUnsign));
	}
}