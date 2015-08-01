package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class ProjectWithCostCenterOUInfo extends AbstractProjectWithCostCenterOUInfo implements Serializable 
{
    public ProjectWithCostCenterOUInfo()
    {
        super();
    }
    protected ProjectWithCostCenterOUInfo(String pkField)
    {
        super(pkField);
    }
    //此实体没有编码、名称，因为项目与成本中心只能一一对应，故取项目编码名称
    public String getLogInfo() {
		String retValue = "";
        if(this.getCurProject().getNumber()!= null)
        {
            retValue = this.getCurProject().getNumber();
            if(this.getCurProject().getName()!=null){
            	retValue = retValue + " " + this.getCurProject().getName();
            }
        }
        return retValue;
	}
}