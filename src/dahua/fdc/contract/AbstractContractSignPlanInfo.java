package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractSignPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractSignPlanInfo()
    {
        this("id");
    }
    protected AbstractContractSignPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractSignPlanEntryCollection());
    }
    /**
     * Object: ��ͬǩԼ�ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ��ͬǩԼ�ƻ� 's ǩԼ�ƻ���ϸ property 
     */
    public com.kingdee.eas.fdc.contract.ContractSignPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractSignPlanEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FB39A5F3");
    }
}