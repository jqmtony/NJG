package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIncomeAccountAssignInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractIncomeAccountAssignInfo()
    {
        this("id");
    }
    protected AbstractIncomeAccountAssignInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����Ŀ�����¼ 's �����Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.IncomeAccountInfo getIncomeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.IncomeAccountInfo)get("IncomeAccount");
    }
    public void setIncomeAccount(com.kingdee.eas.fdc.basedata.IncomeAccountInfo item)
    {
        put("IncomeAccount", item);
    }
    /**
     * Object: �����Ŀ�����¼ 's �������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getFullOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("fullOrgUnit");
    }
    public void setFullOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("fullOrgUnit", item);
    }
    /**
     * Object: �����Ŀ�����¼ 's ����Ĺ�����Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78CBFA21");
    }
}