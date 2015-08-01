package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectWithCostCenterOUInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProjectWithCostCenterOUInfo()
    {
        this("id");
    }
    protected AbstractProjectWithCostCenterOUInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������Ŀ��ɱ����ĵĶ�Ӧ��ϵ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ������Ŀ��ɱ����ĵĶ�Ӧ��ϵ 's �ɱ�������֯ property 
     */
    public com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo getCostCenterOU()
    {
        return (com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo)get("costCenterOU");
    }
    public void setCostCenterOU(com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo item)
    {
        put("costCenterOU", item);
    }
    /**
     * Object:������Ŀ��ɱ����ĵĶ�Ӧ��ϵ's ����property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D8D38DD5");
    }
}