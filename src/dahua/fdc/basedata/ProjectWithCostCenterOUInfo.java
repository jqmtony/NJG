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
    //��ʵ��û�б��롢���ƣ���Ϊ��Ŀ��ɱ�����ֻ��һһ��Ӧ����ȡ��Ŀ��������
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